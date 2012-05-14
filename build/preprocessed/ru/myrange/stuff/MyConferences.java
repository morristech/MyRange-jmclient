/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff;

import java.util.Vector;
import ru.myrange.datastore.Records;
import ru.myrange.soap.ConfInfo;

/**
 * Helper class to work with conferences
 *
 * @author Oleg Ponfilenok
 */
public class MyConferences {

    
    private static Vector confs = new Vector();
    
    /**
     * Return the closest conference
     */
    public static ConfInfo getActualConf() {
        long time = System.currentTimeMillis();
        long minDist = Long.MAX_VALUE;
        ConfInfo res = null;
        for (int i=0; i<confs.size(); i++) {
            ConfInfo c = (ConfInfo) confs.elementAt(i);
            if (c.getStartTime().longValue() > time) {
                if (c.getStartTime().longValue() - time < minDist) {
                    res = new ConfInfo(c);
                    minDist = c.getStartTime().longValue() - time;
                }
            } else if (c.getEndTime().longValue() > 0 &&
                    c.getEndTime().longValue() < time) {
                if (time - c.getEndTime().longValue() < minDist) {
                    res = new ConfInfo(c);
                    minDist = time - c.getEndTime().longValue();
                }
            } else if (c.getEndTime().longValue() > 0) { // current conference
                res = new ConfInfo(c);
                minDist = 0;
            }
        }
        return res;
    }

    /**
     * Get id of user to send feedback
     *
     * @return id of user to send feedback or null
     */
    public static Integer getFeedbackId() {
        Integer id = null;
        ConfInfo ac = getActualConf();
        if (ac != null) {
            // Try to get id of owner of actual conference
            if (ac.getUserId() != null && ac.getUserId().intValue() > 0)
                id = ac.getUserId();
        } else {
            // Try to get id of owner of global conference
            for (int i=0; i<confs.size(); i++) {
                ConfInfo c = (ConfInfo) confs.elementAt(i);
                if (c != null && c.getConfId() != null &&
                        c.getConfId().intValue() == StringConsts.I_GLOBAL_CONF_ID.intValue()) {
                    if (c.getUserId() != null && c.getUserId().intValue() > 0)
                        id = c.getUserId();
                }
            }
        }
        return id;
    }

    /**
     * Update information about my conferences
     * (ONLY FOR CALL FROM A SEPARATE THREAD)
     */
    public synchronized static void getMyConfs() throws Exception
    {
        confs = StaticActions.getConferenceInfo();
        // Update my conferences list in RMS
        saveToRMS();
    }

    /**
     * Save my conferences to RMS
     * @throws java.lang.Exception
     */
    public synchronized static void saveToRMS() throws Exception {
        // Add test data
        Records.recConferences.addByteData(new byte[1]);
        // OK
        Records.recConferences.eraseRecord();
        Object[] conferencesToRMS = new Object[confs.size()];
		confs.copyInto(conferencesToRMS);
        for (int i=0; i<conferencesToRMS.length; i++) {
            ConfInfo conf = null;
            if (conferencesToRMS[i] != null)
                conf = (ConfInfo) conferencesToRMS[i];
            if (conf != null)
                Records.recConferences.addByteData(conf.toRecord());
        }
        conferencesToRMS = null;
	}

    /**
     * Load my conferences from RMS
     * @throws java.lang.Exception
     */
    public synchronized static void loadFromRMS() throws Exception {
        confs.removeAllElements();
        for (int i=1; i<=Records.recConferences.getNumRec(); i++) {
            confs.addElement(new ConfInfo(Records.recConferences.readByteData(i)));
        }
	}
}
