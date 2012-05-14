/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */

package ru.myrange.stuff;

import java.util.Vector;
import ru.myrange.datastore.Records;
import ru.myrange.soap.CompInfoType;

/**
 *
 * @author Nickolay Yegorov
 */
public class Companies {

    private static Vector companies = new Vector();
    private static Object[] comps = null;
    private static boolean ready = false;

    public static void getCompanies(){
        try {
            //ready = false;

            companies = StaticActions.getCompanies(StaticActions.searchCompaniesIds(), null);//without pic

            saveToRMS();

            ready = true;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static CompInfoType getCompany(Integer compId, Integer picSize){
        try {
            Integer[] c = new Integer[1];
            c[0] = compId;
            Vector cmp = StaticActions.getCompanies(c, picSize);
            CompInfoType cc = (CompInfoType) cmp.elementAt(0);
            System.out.println("picSize:" + picSize + "logo: ");
            if(cc.getLogo() != null) System.out.println("OK");

            //save if got with pic
//            for(int i = 0; i < comps.length; i++)
//                if( ((CompInfoType)comps[i]).getCompId().intValue() == compId.intValue())
//                    return (CompInfoType)comps[i];

            return cc;
        } catch (Exception ex) {
            return getCompany(compId);
        }


    }

    public static CompInfoType getCompany(Integer compId){
        for(int i = 0; i < comps.length; i++)
                if( ((CompInfoType)comps[i]).getCompId().intValue() == compId.intValue())
                    return (CompInfoType)comps[i];
       return null;
    }

    public static Object[] myCompanies(){
        try {
            loadFromRMS();
        } catch (Exception ex) {
            //do nothing
        }
        return comps;
    }

    public static Vector getMyCompanies(){
        return companies;
    }

    /**
     * Save my companies to RMS
     * @throws java.lang.Exception
     */
    public synchronized static void saveToRMS() throws Exception {
        // Add test data
        Records.recCompanies.addByteData(new byte[1]);
        // OK
        Records.recEvents.eraseRecord();
        Object[] eventsToRMS = new Object[companies.size()];
		companies.copyInto(eventsToRMS);
        for (int i=0; i<eventsToRMS.length; i++) {
            CompInfoType ev = null;
            if (eventsToRMS[i] != null)
                ev = (CompInfoType) eventsToRMS[i];
            if (ev != null)
                Records.recEvents.addByteData(ev.toRecord());
        }
        eventsToRMS = null;
	}

    /**
     * Load my companies from RMS
     * @throws java.lang.Exception
     */
    public synchronized static void loadFromRMS() throws Exception {
        companies.removeAllElements();
        for (int i=1; i<=Records.recEvents.getNumRec(); i++) {
            companies.addElement(new CompInfoType(Records.recEvents.readByteData(i)));
        }

        comps = new Object[companies.size()];
        companies.copyInto(comps);
	}

    public static boolean isReady() {
        return ready;
    }

}
