/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */

package ru.myrange.stuff;

import java.util.Vector;
import ru.myrange.datastore.Records;
import ru.myrange.soap.EventInfoType;

/**
 *
 * @author Nickolay Yegorov
 */
public class Events {

    private static Vector events = new Vector();

    public static void getMyEvents(){
        try {
            events = StaticActions.getEvents();

            saveToRMS();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Object[] myEvents(){
        Object[] result = new Object[events.size()];
        events.copyInto(result);

        return result;
    }

    /**
     * Save my events to RMS
     * @throws java.lang.Exception
     */
    public synchronized static void saveToRMS() throws Exception {
        // Add test data
        Records.recEvents.addByteData(new byte[1]);
        // OK
        Records.recEvents.eraseRecord();
        Object[] eventsToRMS = new Object[events.size()];
		events.copyInto(eventsToRMS);
        for (int i=0; i<eventsToRMS.length; i++) {
            EventInfoType ev = null;
            if (eventsToRMS[i] != null)
                ev = (EventInfoType) eventsToRMS[i];
            if (ev != null)
                Records.recEvents.addByteData(ev.toRecord());
        }
        eventsToRMS = null;
	}

    /**
     * Load my conferences from RMS
     * @throws java.lang.Exception
     */
    public synchronized static void loadFromRMS() throws Exception {
        events.removeAllElements();
        for (int i=1; i<=Records.recEvents.getNumRec(); i++) {
            events.addElement(new EventInfoType(Records.recEvents.readByteData(i)));
        }
	}

}
