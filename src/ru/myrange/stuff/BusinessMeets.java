/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */

package ru.myrange.stuff;

import java.util.Vector;
import ru.myrange.datastore.Records;
import ru.myrange.soap.BusinessMeetInfoType;

/**
 *
 * @author Nickolay Yegorov
 */
public class BusinessMeets {

    private static Vector businessMeets = new Vector();

    public static void getBusinessMeets(){
        try {
            businessMeets = StaticActions.getBusinessMeets();

            saveToRMS();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Object[] myBusinessMeets(){
        try {
            loadFromRMS();
        } catch (Exception ex) {
            //do nothing
        }
        Object[] result = new Object[businessMeets.size()];
        businessMeets.copyInto(result);

        return result;
    }

    /**
     * Save my businessMeets to RMS
     * @throws java.lang.Exception
     */
    public synchronized static void saveToRMS() throws Exception {
        // Add test data
        Records.recBusinessMeets.addByteData(new byte[1]);
        // OK
        Records.recBusinessMeets.eraseRecord();
        Object[] meetsToRMS = new Object[businessMeets.size()];
		businessMeets.copyInto(meetsToRMS);
        for (int i=0; i<meetsToRMS.length; i++) {
            BusinessMeetInfoType bm = null;
            if (meetsToRMS[i] != null)
                bm = (BusinessMeetInfoType) meetsToRMS[i];
            if (bm != null)
                Records.recBusinessMeets.addByteData(bm.toRecord());
        }
        meetsToRMS = null;
	}

    /**
     * Load my businessMeets from RMS
     * @throws java.lang.Exception
     */
    public synchronized static void loadFromRMS() throws Exception {
        businessMeets.removeAllElements();
        for (int i=1; i<=Records.recBusinessMeets.getNumRec(); i++) {
            businessMeets.addElement(new BusinessMeetInfoType(Records.recBusinessMeets.readByteData(i)));
        }
	}

}
