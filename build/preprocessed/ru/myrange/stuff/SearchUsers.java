/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */

package ru.myrange.stuff;

import java.util.Enumeration;
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.Records;
import ru.myrange.soap.HeaderType;
import ru.myrange.soap.Scrobbler;
import ru.myrange.soap.SearchUserRequestElement;
import ru.myrange.soap.SearchUserResponseElement;

/**
 * Static class to work with search users
 *
 * @author Oleg Ponfilenok, Yegorov Nickoaly
 */
public class SearchUsers {

    /**
     * Title of last search results (например, "Все участники конференции")
     */
	private static String searchResultTitle = "Все участники конференции";

    /**
     * Vector of UserId's from last search results
     * Sort by full name
     */
	private static Vector ids = new Vector();

        private static boolean ready = false;

    /**
     * Get users ids
     */
    public static Vector getIds() {
		return ids;
	}

    /**
     * Set users ids
     */
    public static void setIds(Vector idents) {
        if (idents == null) return;
        ids = new Vector();
        for (int i=0; i<idents.size(); i++) {
            if (idents.elementAt(i) instanceof Integer)
                ids.addElement((Integer) idents.elementAt(i));
        }
        sortIds();
	}

    public static String getTitle() {
		return searchResultTitle;
	}


    /**
     * Start new search users
     *
     * @param confId - search by conference id. null - global conference
     */
    public static void newSearch(Integer confId) throws Exception {
        loadFromRMS();

        ready = false;

        // Set new search result
        setIds(searchUsers(confId));
        Users.addUsers(getIds(), 0);
        // Set title
        if (confId != null) searchResultTitle = StringConsts.S_USER_SEARCH_CONF_ALL_TITLE;
        else searchResultTitle = StringConsts.S_USER_SEARCH_GLOBAL_ALL_TITLE;

        saveToRMS();

        ready = true;
    }



    /**
     * Search users
     */
    public static Vector searchUsers(Integer confId) throws Exception {
        // Get users Id'es
        System.out.println("confid: " + confId);
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        SearchUserRequestElement searchUserRequest = new SearchUserRequestElement(header, confId);
        //if(searchUserRequest == null) System.out.println("req is null");
        SearchUserResponseElement searchUserResponse = Scrobbler.searchUsers(searchUserRequest);
        //if(searchUserResponse == null) System.out.println("req is null");
        Vector userIdSequence = searchUserResponse.getUserIdSequence();
        //if(userIdSequence == null) System.out.println("uid is null");
        // Get (update) users profiles without pictures

        return userIdSequence;
    }

     public static Vector searchUsers(Integer confId, Integer compId) throws Exception {
        // Get users Id'es
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        SearchUserRequestElement searchUserRequest = new SearchUserRequestElement(header, confId, compId);
        SearchUserResponseElement searchUserResponse = Scrobbler.searchUsers(searchUserRequest);
        Vector userIdSequence = searchUserResponse.getUserIdSequence();
        // Get (update) users profiles without pictures
        //Users.addUsers(userIdSequence, 0);

        return userIdSequence;
    }


    /**
     * Sort ids by full name
     */
    public static synchronized void sortIds() {
        Vector oldIds = new Vector();
        for(int i=0; i<ids.size(); i++) { // Copy user to oldUsers
            oldIds.addElement(ids.elementAt(i));
        }
        ids.removeAllElements();
        // Sort
        while(oldIds.size() > 0) {
            Integer min = (Integer) oldIds.elementAt(0);
            for(int i=1; i<oldIds.size(); i++) {
                if ( Users.getUser((Integer) oldIds.elementAt(i)).getFullName()
                        .compareTo(Users.getUser(min).getFullName()) < 0) {
					min = (Integer) oldIds.elementAt(i);
				}
			}
            ids.addElement(min);
            oldIds.removeElement(min);
    	}
	}

    /**
     * Save last search results to RMS
     * @throws java.lang.Exception
     */
    public synchronized static void saveToRMS() throws Exception {
        // Add test data
        Records.recLastSearch.addByteData(new byte[1]);
        // OK
        Records.recLastSearch.eraseRecord();
        for (Enumeration e = ids.elements() ; e.hasMoreElements() ;) {
            Records.recLastSearch.addIntData( ((Integer) e.nextElement()).intValue());
        }
        Records.recSettings.addStringData(searchResultTitle, Records.REC_SEARCH_RESULT_TITLE);
	}

    /**
     * Load last search results from RMS
     * @throws java.lang.Exception
     */
    public synchronized static void loadFromRMS() throws Exception {
        ids.removeAllElements();
        for (int i=1; i<=Records.recLastSearch.getNumRec(); i++) {
            ids.addElement(new Integer(Records.recLastSearch.readIntData(i)));
        }
        sortIds();
        searchResultTitle = Records.recSettings.readStringData(Records.REC_SEARCH_RESULT_TITLE);
	}

    /**
     * @return the ready
     */
    public static boolean isReady() {
        return ready;
    }

}
