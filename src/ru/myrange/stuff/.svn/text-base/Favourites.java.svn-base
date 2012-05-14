/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff;

import java.util.Enumeration;
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.Records;
import ru.myrange.soap.EditFavoritesRequest;
import ru.myrange.soap.EditFavoritesResponse;
import ru.myrange.soap.GetFavoritesRequest;
import ru.myrange.soap.GetFavoritesResponse;
import ru.myrange.soap.HeaderType;
import ru.myrange.soap.Scrobbler;
import ru.myrange.stuff.xlwuit.UserPicCellRenderer;

/**
 * Static class to store and work with favourite users
 *
 * @author Oleg Ponfilenok
 */
public class Favourites {

    // Vector of favourite users IDs
	private static Vector ids = new Vector();

    /**
     * Get favourite users ids
     */
    public static Vector getIds() {
		return ids;
	}

    public static boolean isFavourite(Integer id) {
		for (int i = 0; i < ids.size(); i++) {
            if ( ((Integer)ids.elementAt(i)).intValue() == id.intValue() )
                return true;
        }
        return false;
	}

    /**
     * Update list of favourite users
     * (ONLY FOR CALL FROM A SEPARATE THREAD)
     */
    public synchronized static void download() throws Exception
    {
        // Download favourites userIds from the server
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        GetFavoritesRequest getFavoritesRequest = new GetFavoritesRequest(header);
        GetFavoritesResponse getFavoritesResponse = Scrobbler.getFavorites(getFavoritesRequest);
        ids = getFavoritesResponse.getUserId();
        // Update favourites list in RMS
        saveToRMS();
    }

    /**
     * Add or remove userId to the list of favourite users
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

        // Update favourites list in RMS
        saveToRMS();

        // Upload favourites to the server
        try {
            HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
            Vector v = new Vector();
            v.addElement(userId);
            EditFavoritesRequest editFavoritesRequest;
            if (addOrRemove)    // add
                editFavoritesRequest = new EditFavoritesRequest(header, v, null);
            else                // remove
                editFavoritesRequest = new EditFavoritesRequest(header, null, v);
            Scrobbler.editFavorites(editFavoritesRequest);
            MyRangeMIDlet.out.println("[Favourites]  upload favourites ...OK");
        } catch (Exception e) {
            e.printStackTrace();
            MyRangeMIDlet.out.println("[Favourites] Error: " + e.getMessage());
            try {
                // Need to send new profile to the server
                Records.recSettings.addIntData(Records.I_CHANGED_YES, Records.REC_FAVOURITES_CHANGED);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Upload changes in favorites
     * (ONLY FOR CALL FROM A SEPARATE THREAD)
     */
    public static synchronized Integer upload() throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        // Download favourites from the server
        GetFavoritesRequest getFavoritesRequest = new GetFavoritesRequest(header);
        GetFavoritesResponse getFavoritesResponse = Scrobbler.getFavorites(getFavoritesRequest);
        final Vector oldIds = getFavoritesResponse.getUserId();

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

        // Upload changes in favorites
        EditFavoritesRequest editFavoritesRequest = new EditFavoritesRequest(header, idsToAdd, idsToRemove);
        EditFavoritesResponse editFavoritesResponse = Scrobbler.editFavorites(editFavoritesRequest);
        return editFavoritesResponse.getResponse();
    }

    /**
     * Save favourites to RMS
     * @throws java.lang.Exception
     */
    public synchronized static void saveToRMS() throws Exception {
        // Add test data
        Records.recFavourites.addByteData(new byte[1]);
        // OK
        Records.recFavourites.eraseRecord();
        for (Enumeration e = ids.elements() ; e.hasMoreElements() ;) {
            Records.recFavourites.addIntData( ((Integer) e.nextElement()).intValue());
        }
	}

    /**
     * Load favourites from RMS
     * @throws java.lang.Exception
     */
    public synchronized static void loadFromRMS() throws Exception {
        ids.removeAllElements();
        for (int i=1; i<=Records.recFavourites.getNumRec(); i++) {
            ids.addElement(new Integer(Records.recFavourites.readIntData(i)));
        }
	}

}
