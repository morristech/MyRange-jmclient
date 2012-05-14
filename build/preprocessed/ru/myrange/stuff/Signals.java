/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff;

import java.util.Enumeration;
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.Records;
import ru.myrange.screens.UserProfileScreen;
import ru.myrange.soap.EditSignalsRequest;
import ru.myrange.soap.EditSignalsResponse;
import ru.myrange.soap.GetSignalsRequest;
import ru.myrange.soap.GetSignalsResponse;
import ru.myrange.soap.HeaderType;
import ru.myrange.soap.Scrobbler;

/**
 * Static class to store and work with signal users
 *
 * @author Oleg Ponfilenok
 */
public class Signals {

    // Vector of favourite users IDs
	private static Vector ids = new Vector();

    public static Vector getIds() {
		return ids;
	}

    public static boolean isSignal(Integer id) {
		for (int i=0; i< ids.size(); i++) {
            if ( ((Integer)ids.elementAt(i)).intValue() == id.intValue() )
                return true;
        }
        return false;
	}

    /**
     * Update list of signal users
     * (ONLY FOR CALL FROM A SEPARATE THREAD)
     */
    public synchronized static void download() throws Exception
    {
        // Download favourites from the server
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        GetSignalsRequest getSignalsRequest = new GetSignalsRequest(header);
        GetSignalsResponse getSignalsResponse = Scrobbler.getSignals(getSignalsRequest);
        ids = getSignalsResponse.getUserId();
        // Download profiles
        Users.addUsers(ids, UserProfileScreen.bestUserpicSize);
        // Update signals list in RMS
        saveToRMS();
    }

    /**
     * Add or remove userId to the list of signal users
     * (ONLY FOR CALL FROM A SEPARATE THREAD)
     */
    public synchronized static void changeUser(final Integer userId, final boolean addOrRemove) throws Exception
    {
        if (userId == null) throw new IllegalArgumentException("userId cannot be null");

        // Schange state
        if (addOrRemove) {  // add
            ids.addElement(userId);
        }
        else {              // remove
            for (int i =0; i<ids.size(); i++) {
                if ( ((Integer)ids.elementAt(i)).intValue() == userId.intValue() ){
                    ids.removeElementAt(i);
                    break;
                }
            }
        }

        // Update signals list in RMS
        saveToRMS();

        // Upload signals to the server
        try {
            HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
            Vector v = new Vector();
            v.addElement(userId);
            EditSignalsRequest editSignalsRequest;
            if (addOrRemove)    // add
                editSignalsRequest = new EditSignalsRequest(header, v, null);
            else                // remove
                editSignalsRequest = new EditSignalsRequest(header, null, v);
            Scrobbler.editSignals(editSignalsRequest);
            MyRangeMIDlet.out.println("[Signals]  upload signals ...OK");
        } catch (Exception e) {
            e.printStackTrace();
            MyRangeMIDlet.out.println("[Signals] Error: " + e.getMessage());
            try {
                // Need to send new profile to the server
                Records.recSettings.addIntData(Records.I_CHANGED_YES, Records.REC_SIGNALS_CHANGED);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * Upload signals in favorites
     * (ONLY FOR CALL FROM A SEPARATE THREAD)
     */
    public static synchronized Integer upload() throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        // Download signals from the server
        GetSignalsRequest getSignalsRequest = new GetSignalsRequest(header);
        GetSignalsResponse getSignalsResponse = Scrobbler.getSignals(getSignalsRequest);
        final Vector oldIds = getSignalsResponse.getUserId();

        Vector idsToAdd = new Vector();
        for (int i=0; i < getIds().size(); i++) {
            int k=0;
            while (k < oldIds.size()) {
                if ( ((Integer) getIds().elementAt(i)).intValue() == ((Integer) oldIds.elementAt(k)).intValue() )
                    break;
                k++;
            }
            if (k >= oldIds.size()) idsToAdd.addElement((Integer) getIds().elementAt(i));
        }
        Vector idsToRemove = new Vector();
        for (int i=0; i < oldIds.size(); i++) {
            int k=0;
            while (k < getIds().size()) {
                if ( ((Integer) oldIds.elementAt(i)).intValue() == ((Integer) getIds().elementAt(k)).intValue() )
                    break;
                k++;
            }
            if (k >= getIds().size()) idsToRemove.addElement((Integer) oldIds.elementAt(i));
        }

        // Upload changes in signals
        EditSignalsRequest editSignalsRequest = new EditSignalsRequest(header, idsToAdd, idsToRemove);
        EditSignalsResponse editSignalsResponse = Scrobbler.editSignals(editSignalsRequest);
        return editSignalsResponse.getResponse();
    }

    /**
     * Save signals to RMS
     * @throws java.lang.Exception
     */
    public synchronized static void saveToRMS() throws Exception {
        // Add test data
        Records.recSignals.addByteData(new byte[1]);
        // OK
        Records.recSignals.eraseRecord();
        for (Enumeration e = ids.elements() ; e.hasMoreElements() ;) {
            Records.recSignals.addIntData( ((Integer) e.nextElement()).intValue());
        }
	}

    /**
     * Load signals from RMS
     * @throws java.lang.Exception
     */
    public synchronized static void loadFromRMS() throws Exception {
        ids.removeAllElements();
        for (int i=1; i<=Records.recSignals.getNumRec(); i++) {
            ids.addElement(new Integer(Records.recSignals.readIntData(i)));
        }
	}

}
