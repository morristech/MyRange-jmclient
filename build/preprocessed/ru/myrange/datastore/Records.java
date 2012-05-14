/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.datastore;

import java.util.Calendar;
import java.util.Date;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.BusinessMeets;
import ru.myrange.stuff.Companies;
import ru.myrange.stuff.Events;
import ru.myrange.stuff.Favourites;
import ru.myrange.stuff.Messages;
import ru.myrange.stuff.MyConferences;
import ru.myrange.stuff.SearchUsers;
import ru.myrange.stuff.Signals;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.Users;

/**
 * Class for work with number of records in midlet
 *
 * @author Oleg Ponfilenok
 */
public class Records {

    // *** CONSTS ***
    public static final int I_CHANGED_NOT = 0;      // Doesn't need to upload new data to the server
    public static final int I_CHANGED_YES = 1;      // Need to upload new data to the server

	public static final String REC_SETTINGS_NAME = "settings";
	public static final int REC_SETTINGS_SIZE = 17;
	public static final int REC_LOGIN = 1;                // User's e-mail or phone number
	public static final int REC_PASSWORD = 2;             // User's password (md5 hash)
    public static final int REC_MY_ID = 3;                // My user id
    //public static final int REC_PHONE = 3;                // Phone number. e.g: "79261632546"
    //public static final int REC_VALIDATION_PHONE = 4;     // Phone number for validate
    public static final int REC_VALIDATION_PASSWORD = 4;  // Code for validate phone number
    public static final int REC_DEVICE_REGISTERED = 5;    // Start from Start form(false) or Main menu(true)?
	public static final int REC_SCAN_TO = 6;              // Scan time out
	public static final int REC_MERGE_INTERVAL = 7;       // btLogs merge interval
    //public static final int REC_INET_CONNECTION = 7;      // Internet connection setting
    public static final int REC_DOWNLOAD_PIC = 8;         // Pectures download option
    //public static final int REC_PROFILE = 8;              // My profile
    public static final int REC_SMS_U2U_INFO = 9;        // U2U SMS Service info
    public static final int REC_PROFILE_CHANGED = 10;     // Does need to upload profile to the server?
    public static final int REC_FAVOURITES_CHANGED = 11;  // Does need to upload favourite users to the server?
    public static final int REC_SIGNALS_CHANGED = 12;     // Does need to upload signal users to the server?
    public static final int REC_TRAFFIC_TODAY_SINCE = 13; // The unixtime of day of start the traffic of today (renew every day)
    public static final int REC_TRAFFIC_TODAY = 14;       // Traffic of today (renew every day)
    public static final int REC_TRAFFIC_TOTAL_SINCE = 15; // The unixtime of day of first start the applicaton
    public static final int REC_TRAFFIC_TOTAL = 16;       // Total traffic from the first day
    public static final int REC_SEARCH_RESULT_TITLE = 17; // Title of the last search result

    public static final String REC_BTLOGS_NAME = "btrecords";
    public static final String REC_MYPROFILE_NAME = "myprofile";
    public static final String REC_USERS_NAME = "users";
    public static final String REC_FAVOURITES_NAME = "favourites";
    public static final String REC_SIGNALS_NAME = "signals";
    public static final String REC_LAST_SEARCH_NAME = "lastsearch";
    public static final String REC_MESSAGES_NAME = "messages";
    public static final String REC_SMSMESSAGES_NAME = "smsmessages";
    public static final String REC_CONFS_NAME = "conferences";
    public static final String REC_EVENTS_NAME = "events";
    public static final String REC_COMPANIES_NAME = "companies";
    public static final String REC_BUSINESS_MEETS_NAME = "bmeets";

    /**
     * Store all config information and user settings
     */
    public static RMS recSettings;

    /**
     * Store information about Bluetooth meets.
     */
	public static RMS recBtMeets;

    /**
     * Store my profile
     */
    public static RMS recMyProfile;

    /**
     * Store users profile
     */
    public static RMS recUsers;

    /**
     * Store favourites
     */
    public static RMS recFavourites;

    /**
     * Store signals
     */
    public static RMS recSignals;

    /**
     * Store last search result
     */
    public static RMS recLastSearch;

    /**
     * Store private messages.
     */
    public static RMS recMessages;


    /**
     * Store Conference infos
     */
    public static RMS recConferences;

    /**
     * Store some infos
     */
    public static RMS recEvents;
    public static RMS recCompanies;

    /**
     * Store meets infos
     */
    public static RMS recBusinessMeets;

    /**
     * True if RMS is full
     */
    //public static boolean isFull = false;


    /**
     * Opens all record stores for access.
     *
     * @throws java.lang.Exception
     */
	public static void openAll() throws Exception {
		recSettings = new RMS(REC_SETTINGS_NAME);
        recSettings.open();
        checkSettingsRecord(); //Check the Settings record

        recBtMeets = new RMS(REC_BTLOGS_NAME);
        recBtMeets.open();

        recMyProfile = new RMS(REC_MYPROFILE_NAME);
        recMyProfile.open();

        recUsers = new RMS(REC_USERS_NAME);
        recUsers.open();

        recFavourites = new RMS(REC_FAVOURITES_NAME);
        recFavourites.open();

        recSignals = new RMS(REC_SIGNALS_NAME);
        recSignals.open();

        recLastSearch = new RMS(REC_LAST_SEARCH_NAME);
        recLastSearch.open();

        recMessages = new RMS(REC_MESSAGES_NAME);
        recMessages.open();

        recConferences = new RMS(REC_CONFS_NAME);
        recConferences.open();

        recEvents = new RMS(REC_EVENTS_NAME);
        recEvents.open();

        recCompanies = new RMS(REC_COMPANIES_NAME);
        recCompanies.open();

        recBusinessMeets = new RMS(REC_BUSINESS_MEETS_NAME);
        recBusinessMeets.open();
        }

    /**
     * Close all record stores.
     */
	public static void closeAll() {
		recSettings.close();
        recBtMeets.close();
        recMyProfile.close();
        recUsers.close();
        recFavourites.close();
        recSignals.close();
        recLastSearch.close();
        recMessages.close();
        recConferences.close();
        recEvents.close();
        recCompanies.close();
        recBusinessMeets.close();
	}

    /**
     * Open RMS and load all from RMS my infos:
     */
    public static void loadMyInfos() throws Exception {
        // Load very important values
        MyRangeMIDlet.login = Records.recSettings.readStringData(Records.REC_LOGIN);
        MyRangeMIDlet.pass = Records.recSettings.readStringData(Records.REC_PASSWORD);
        MyRangeMIDlet.myUserId = new Integer(Records.recSettings.readIntData(Records.REC_MY_ID));
        // Load user list from RMS
        try {
            Users.loadFromRMS();
        } catch (Exception e) {
            e.printStackTrace();
            MyRangeMIDlet.out.println("[Users] Error:" + e.toString());
        }
        // Load my conferences info
        try {
            MyConferences.loadFromRMS();
        } catch (Exception e){
            e.printStackTrace();
            MyRangeMIDlet.out.println("[MyConf] Error: " + e.getMessage());
        }
        // Load last search results from RMS
        try {
            SearchUsers.loadFromRMS();
        } catch (Exception e) {
            e.printStackTrace();
            MyRangeMIDlet.out.println("[SearchUsers] Error:" + e.toString());
        }
        // Load private messages and smses from RMS
        try {
            Messages.loadFromRMS();
        } catch (Exception e) {
            e.printStackTrace();
            MyRangeMIDlet.out.println("[Init] Error:" + e.toString());
        }
        // Load favourite users
        try {
            Favourites.loadFromRMS();
        } catch (Exception e){
            e.printStackTrace();
            MyRangeMIDlet.out.println("[Favourites] Error: " + e.getMessage());
        }
        // Load signals users
        try {
            Signals.loadFromRMS();
        } catch (Exception e){
            e.printStackTrace();
            MyRangeMIDlet.out.println("[Signals] Error: " + e.getMessage());
        }

        try {
            Events.loadFromRMS();
        } catch (Exception e){
            e.printStackTrace();
            MyRangeMIDlet.out.println("[Events] Error: " + e.getMessage());
        }

        try {
            Companies.loadFromRMS();
        } catch (Exception e){
            e.printStackTrace();
            MyRangeMIDlet.out.println("[Companies] Error: " + e.getMessage());
        }

        try {
            BusinessMeets.loadFromRMS();
        } catch (Exception e){
            e.printStackTrace();
            MyRangeMIDlet.out.println("[BusinessMeets] Error: " + e.getMessage());
        }
    }

    /**
     * Erase all user personal data.
     * Use it when logout.
     *
     * @throws java.lang.Exception
     */
	public static void erasePersonalData() throws Exception {
		recSettings.eraseStringField(REC_LOGIN);
        recSettings.eraseStringField(REC_PASSWORD);
        //recSettings.eraseStringField(REC_PHONE);    // Also erase the saved phone number!
        recSettings.addBooleanData(false, REC_DEVICE_REGISTERED);
        recSettings.eraseStringField(REC_SEARCH_RESULT_TITLE);
        recMessages.eraseRecord();
        recMyProfile.eraseRecord();
        recUsers.eraseRecord();
        recFavourites.eraseRecord();
        recSignals.eraseRecord();
        recLastSearch.eraseRecord();
        recBtMeets.eraseRecord();
        recConferences.eraseRecord();
        recEvents.eraseRecord();
        recCompanies.eraseRecord();
        recBusinessMeets.eraseRecord();
	}

    /**
     * Check the Settings record at start of midlet.
     *
     * @throws java.lang.Exception
     */
    private static void checkSettingsRecord() throws Exception {
        if (recSettings.getNumRec() != REC_SETTINGS_SIZE) {
			// Create empty recSettings
            recSettings.createEmpty(REC_SETTINGS_SIZE);
            recSettings.addStringData("", REC_LOGIN);
            recSettings.addStringData("", REC_PASSWORD);
            recSettings.addIntData(0, REC_MY_ID);
            recSettings.addBooleanData(false, REC_DEVICE_REGISTERED);
            //recSettings.addStringData("", REC_VALIDATION_PHONE);
            recSettings.addStringData("", REC_VALIDATION_PASSWORD);
            recSettings.addStringData("", REC_SEARCH_RESULT_TITLE);
		}

        // Set initial states to some sittings in recSettings
        try {
            if (recSettings.readLongData(REC_SCAN_TO) <= 0) {    // If there is no any scan sleep value
                recSettings.addLongData(StringConsts.L_SCAN_TO, REC_SCAN_TO);
            }
        } catch (Exception e) {
            recSettings.addLongData(StringConsts.L_SCAN_TO, REC_SCAN_TO);
        }

        try {
            if (recSettings.readLongData(REC_MERGE_INTERVAL) <= 0) {    // If there is no any merge interval value
                recSettings.addLongData(MyRangeMIDlet.mergeIntervalVar, REC_MERGE_INTERVAL);
            }
            MyRangeMIDlet.mergeIntervalVar = recSettings.readLongData(REC_MERGE_INTERVAL);
        } catch (Exception e) {
            recSettings.addLongData(MyRangeMIDlet.mergeIntervalVar, REC_MERGE_INTERVAL);
        }

        /*
        try {
            int inetConnection = recSettings.readIntData(REC_INET_CONNECTION);
            if ((inetConnection != StringConsts.I_INET_CONNECTION_ALWAYS) && (inetConnection != StringConsts.I_INET_CONNECTION_ONDEMAND)) {
                recSettings.addIntData(StringConsts.I_INET_CONNECTION_ALWAYS, REC_INET_CONNECTION);
            }
        } catch (Exception e) {
            recSettings.addIntData(StringConsts.I_INET_CONNECTION_ALWAYS, REC_INET_CONNECTION);
        }
        */

        try {
            int downloadPic = recSettings.readIntData(REC_DOWNLOAD_PIC);
            if ((downloadPic != StringConsts.I_DOWNLOAD_PIC_YES) && (downloadPic != StringConsts.I_DOWNLOAD_PIC_NO)) {
                recSettings.addIntData(StringConsts.I_DOWNLOAD_PIC_YES, REC_DOWNLOAD_PIC);
            }
        } catch (Exception e) {
            recSettings.addIntData(StringConsts.I_DOWNLOAD_PIC_YES, REC_DOWNLOAD_PIC);
        }

        /*
        try {
            UserInfo myProfile = new UserInfo(recSettings.readByteData(Records.REC_PROFILE));
        } catch (Exception e) {
            try {
                // Create empty profile
                UserInfo myProfile = new UserInfo();
                Records.recSettings.addByteData(myProfile.toRecord(), Records.REC_PROFILE); // Change profile
            } catch (Exception ignored) {}
        }
        */

        try {
            int profileChanged = recSettings.readIntData(REC_PROFILE_CHANGED);
            if ((profileChanged != I_CHANGED_NOT) && (profileChanged != I_CHANGED_YES)) {
                recSettings.addIntData(I_CHANGED_NOT, REC_PROFILE_CHANGED);
            }
        } catch (Exception e) {
            recSettings.addIntData(I_CHANGED_NOT, REC_PROFILE_CHANGED);
        }

        try {
            int favouritesChanged = recSettings.readIntData(REC_FAVOURITES_CHANGED);
            if ((favouritesChanged != I_CHANGED_NOT) && (favouritesChanged != I_CHANGED_YES)) {
                recSettings.addIntData(I_CHANGED_NOT, REC_FAVOURITES_CHANGED);
            }
        } catch (Exception e) {
            recSettings.addIntData(I_CHANGED_NOT, REC_FAVOURITES_CHANGED);
        }

        try {
            int signalsChanged = recSettings.readIntData(REC_SIGNALS_CHANGED);
            if ((signalsChanged != I_CHANGED_NOT) && (signalsChanged != I_CHANGED_YES)) {
                recSettings.addIntData(I_CHANGED_NOT, REC_SIGNALS_CHANGED);
            }
        } catch (Exception e) {
            recSettings.addIntData(I_CHANGED_NOT, REC_SIGNALS_CHANGED);
        }

        // Traffic
        try {
            long trafficTodaySince = recSettings.readLongData(REC_TRAFFIC_TODAY_SINCE);
        } catch (Exception e) {
            // Set the time of begin of current day
            Calendar cur = Calendar.getInstance();
            cur.setTime(new Date(System.currentTimeMillis()));
            Calendar beginOfDay = Calendar.getInstance();
            beginOfDay.set(Calendar.YEAR, cur.get(Calendar.YEAR));
            beginOfDay.set(Calendar.MONTH, cur.get(Calendar.MONTH));
            beginOfDay.set(Calendar.DAY_OF_MONTH, cur.get(Calendar.DAY_OF_MONTH));
            recSettings.addLongData(beginOfDay.getTime().getTime(), REC_TRAFFIC_TODAY_SINCE);
        }
        try {
            int trafficToday = recSettings.readIntData(REC_TRAFFIC_TODAY);
        } catch (Exception e) {
            recSettings.addIntData(0, REC_TRAFFIC_TODAY);
        }
        try {
            long trafficTotalSince = recSettings.readLongData(REC_TRAFFIC_TOTAL_SINCE);
        } catch (Exception e) {
            // Set the time of begin of current day
            Calendar cur = Calendar.getInstance();
            cur.setTime(new Date(System.currentTimeMillis()));
            Calendar beginOfDay = Calendar.getInstance();
            beginOfDay.set(Calendar.YEAR, cur.get(Calendar.YEAR));
            beginOfDay.set(Calendar.MONTH, cur.get(Calendar.MONTH));
            beginOfDay.set(Calendar.DAY_OF_MONTH, cur.get(Calendar.DAY_OF_MONTH));
            recSettings.addLongData(beginOfDay.getTime().getTime(), REC_TRAFFIC_TOTAL_SINCE);
        }
        try {
            int trafficTotal = recSettings.readIntData(REC_TRAFFIC_TOTAL);
        } catch (Exception e) {
            recSettings.addIntData(0, REC_TRAFFIC_TOTAL);
        }

    }
}
