/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.Records;
import ru.myrange.screens.MyProfileScreen;
import ru.myrange.screens.UserProfileScreen;
import ru.myrange.soap.GetUserInfoRequestElement;
import ru.myrange.soap.GetUserInfoResponseElement;
import ru.myrange.soap.HeaderType;
import ru.myrange.soap.Scrobbler;
import ru.myrange.soap.UserInfo;

/**
 * Static class to store and work with users
 * @author Oleg Ponfilenok
 */
public class Users {

    private static UserInfo nullUserpicProfile = null;

    /**
     * My Profile
     */
    private static UserInfo myProfile = null;

    /**
     * Hashtable (key=userId, value=userInfo)
     */
    private static Hashtable users = new Hashtable();

    /**
     * Get my profile
     */
    public static Image getNullUserpic(int sizeX, int sizeY) {
        if (nullUserpicProfile == null) {
            nullUserpicProfile = new UserInfo();
            nullUserpicProfile.setPic(MyRangeMIDlet.res.getImage("nullUserpic"));
        }
        return nullUserpicProfile.getPic(sizeX, sizeY);
	}

    /**
     * Get my profile
     */
    public static UserInfo getMyProfile() {
        return myProfile;
	}

    /**
     * Download (update) my profile
     */
    public static boolean downloadMyProfile() throws Exception {
        UserInfo newProfile = downloadProfile(MyRangeMIDlet.myUserId, MyProfileScreen.bestUserpicSize);
        if (newProfile == null) return false;
        myProfile = newProfile;
        tryToSaveToRMS();
        return true;
	}

    /**
     * Get userInfo by userId, or returns new UserInfo object
     */
    public static UserInfo getUser(Integer id) {
        if (id == null) return new UserInfo();
        
        UserInfo res = (UserInfo) users.get(id);
        if (res == null) res = new UserInfo();
        return res;
	}

    /**
     * Get userInfos by userIds
     */
    public static Vector getUsers(Vector ids) {
		Vector res = new Vector();
        if (ids == null) throw new IllegalArgumentException("ids cannot be null");
        for (Enumeration e = ids.elements() ; e.hasMoreElements() ;) {
            UserInfo next = getUser((Integer) e.nextElement());
            if (next.getUserId().intValue() > 0) res.addElement(next);
        }
        return res;
	}

    /**
     * Is there user int the list with specified by userId?
     */
    public static boolean isUser(Integer id) {
        return users.containsKey(id);
	}

    /**
     * Does user has a picture?
     */
    public static boolean hasUserPicture(Integer id) {
        UserInfo res = (UserInfo) users.get(id);
        if (res != null)
            if (res.getPic() != null) return true;
        return false;
	}

    private static void insertUser(UserInfo user) {
        if (user == null) return;
        Integer id = user.getUserId();
        if (isUser(id)) {
            // update
            users.remove(id);
            users.put(id, user);
        } else {
            // put
            users.put(id, user);
        }
	}

    /**
     * Download one user profile from the server
     *
     * @return true if user has added succeed
     */
    public static boolean addUser(Integer id, int picSize) throws Exception
    {
        UserInfo newProfile = downloadProfile(id, picSize);
        if (newProfile == null) return false;
        insertUser(newProfile);
        tryToSaveToRMS();
        return true;
	}

    /**
     * Download many users profiles from the server
     */
    public static void addUsers(Vector ids, int picSize) throws Exception
    {
        Vector profiles = downloadProfiles(ids, picSize);
        for (Enumeration e = profiles.elements() ; e.hasMoreElements() ;) {
            UserInfo nextUser = (UserInfo) e.nextElement();
            insertUser(nextUser);
        }
        tryToSaveToRMS();
	}

    /**
     * Try to update online statuses of all users
     */
    public static void updateOnlineStatuses() {
        // Get all users id
        Vector ids = new Vector();
        for (Enumeration e = users.keys() ; e.hasMoreElements() ;) {
            ids.addElement((Integer) e.nextElement());
        }
        // Download only online statuses
        Vector param = new Vector();
        param.addElement("onlineStatus");
        param.addElement("lastOnline");
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        GetUserInfoRequestElement getUserInfoRequest =
                new GetUserInfoRequestElement(header, param, null, ids);
        // SOAP getUserInfo
        try {
            GetUserInfoResponseElement userInfoResponse = Scrobbler.getUserInfo(getUserInfoRequest);
            Vector newProfiles = userInfoResponse.getUsersInfo();
            // Update online statuses
            for (Enumeration e = newProfiles.elements() ; e.hasMoreElements() ;) {
                UserInfo next = ((UserInfo) e.nextElement());
                if (isUser(next.getUserId())) {
                    getUser(next.getUserId()).setOnlineStatus(next.getOnlineStatus());
                    getUser(next.getUserId()).setLastOnlineTime(next.getLastOnlineTime());
                }
            }
        } catch (Exception ignored) {}
	}

    /**
     * If there is no user pic connect to the server to get the profile of the selected user
     */
	public static synchronized UserInfo getUserWithPic (final Integer id) throws Exception {
        if (id == null) return null;
        if (!hasUserPicture(id) && (getUser(id).getHavePic() == null || getUser(id).getHavePic().booleanValue())) {
            // download user's profile
            addUser(id, UserProfileScreen.bestUserpicSize);
        }
        return getUser(id);
    }

    /**
     * Connect to the server to get the long profile of the selected user
     */
	public static synchronized void showUserProfile (final Integer id, final Command backCommand) {
        if (id == null) return;
        final Dialog pleaseWait = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);
        (new Thread() {
            public void run(){
                try {
                    // Get user profile
                    final UserInfo profile = getUserWithPic(id);
                    // Show user profile
                    Display.getInstance().callSerially(new Runnable() {
                        public void run() {
                            pleaseWait.dispose();
                            ((UserProfileScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.USER_PROFILE_SCREEN]).run(backCommand, profile);
                        }
                    });
                } catch (final Exception ex) {
                    Display.getInstance().callSerially(new Runnable() {
                        public void run() {
                            pleaseWait.dispose();
                            MyRangeMIDlet.showFailAlert("Exception", ex.toString());
                        }
                    });
                }
            }
        }).start();
    }

    public static Vector getUsersIds() {
        Vector res = new Vector();
        for (Enumeration e = users.keys() ; e.hasMoreElements() ;) {
            res.addElement((Integer) e.nextElement());
        }
        return res;
	}

    /**
     * Try to save users to RMS
     */
    private static void tryToSaveToRMS() {
        final int DIALOG_SHOW_TIME = 3000;
        try {
            // Try to save to RMS
            saveToRMS();
        } catch (final Exception ex) {
            ex.printStackTrace();
            MyRangeMIDlet.out.println("[Users.saveToRMS()] Excaption: " + ex.toString());
            Display.getInstance().callSerially(new Runnable() {
                public void run() {
                    Dialog fail = MyRangeMIDlet.showFailAlert("Exception", ex.toString());
                    try {
                        Thread.sleep(DIALOG_SHOW_TIME);
                    } catch (InterruptedException ex1) {
                        ex1.printStackTrace();
                    }
                    fail.dispose();
                }
            });
        }
	}

    /**
     * Save my profile to RMS.
     * Save users to RMS.
     *
     * @throws java.lang.Exception
     */
    public static synchronized void saveToRMS() throws Exception {
        //Save my profile to RMS.
        // Add test data
        Records.recMyProfile.addByteData(new byte[1]);
        // OK
        Records.recMyProfile.eraseRecord();
        Records.recMyProfile.addByteData(getMyProfile().toRecord());

        // Save users to RMS.

        //System.out.println("[Users.saveToRMS()] Before Size=" + Records.recUsers.getSize());
        //System.out.println("[Users.saveToRMS()] Before SizeAvailable=" + Records.recUsers.getSizeAvailable());

        int size = Records.recUsers.getSize();
        int available = Records.recUsers.getSizeAvailable();

        int data = 0;
        for (Enumeration e = users.elements() ; e.hasMoreElements() ;) {
            data += ((UserInfo) e.nextElement()).toRecord().length;
        }

        if (data < size + available) {
            // Add test data
            Records.recUsers.addByteData(new byte[1]);
            // OK
            Records.recUsers.eraseRecord();
            for (Enumeration e = users.elements() ; e.hasMoreElements() ;) {
               Records.recUsers.addByteData(((UserInfo) e.nextElement()).toRecord());
            }
        } else {
            // Erase record
            Records.recUsers.eraseRecord();
        }
	}

    /**
     * Load my profile from from RMS.
     * Load users from RMS.
     * And set meet users ids.
     *
     * @throws java.lang.Exception
     */
	public static synchronized void loadFromRMS() throws Exception {
        // Load my profile from from RMS
        if (Records.recMyProfile.getNumRec() > 0) {
            myProfile = new UserInfo(Records.recMyProfile.readByteData(1));
        }
        // Load users from RMS
        Vector meetUserIds = new Vector();
        users.clear();
        for (int i=1; i<=Records.recUsers.getNumRec(); i++) {
            UserInfo ui = new UserInfo(Records.recUsers.readByteData(i));
            users.put(ui.getUserId(), ui);
            if (ui.getLastSeenTime() > 0) meetUserIds.addElement(ui.getUserId());
        }
        // set and sort meet users ids
        MeetUsers.setIds(meetUserIds);
        MeetUsers.sortIds();
	}

    /**
     * Get one user profile from the remote server.
     * @param userId
     * @param picSize
     * @return
     * @throws java.lang.Exception
     */
    private static synchronized UserInfo downloadProfile(Integer id, int picSize) throws Exception {
        if (id == null) return null;
        Vector ids = new Vector();
        ids.addElement(id);
        Vector profiles = downloadProfiles(ids, picSize);
        UserInfo res = null;
        if (profiles.size() > 0) {
            res = (UserInfo) profiles.elementAt(0);
        }
        return res;
    }


    /**
     * Get users profiles from the remote server.
     *
     * @param picSize - size of avatar to download
     * @param ids - vector of isers id
     * @return Vector of profiles
     * @throws java.lang.Exception
     */
    private static synchronized Vector downloadProfiles(Vector ids, int picSize) throws Exception {
        if (ids == null || ids.size() <= 0) return new Vector();
        MyRangeMIDlet.out.println("[getProfile] Getting user profile with userIds=" + ids + " and picSize = " + Integer.toString(picSize));

        Vector param = new Vector();
        param.addElement("accountType");
        param.addElement("havePhoneNumber");
        param.addElement("firstName");
        param.addElement("lastName");
        param.addElement("textStatus");
        param.addElement("about");
        param.addElement("workPlace");
        param.addElement("workPost");
        param.addElement("onlineStatus");
        param.addElement("lastOnline");
        param.addElement("company");
        param.addElement("havePic");
        Integer pictureSize = null;
        if (picSize > 0 && Records.recSettings.readIntData(Records.REC_DOWNLOAD_PIC) == StringConsts.I_DOWNLOAD_PIC_YES) {
            param.addElement("pic");
            pictureSize = new Integer (picSize);
        }
        param.addElement("contact");
        param.addElement("link");
        param.addElement("customParams");

        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        GetUserInfoRequestElement getUserInfoRequest =
                new GetUserInfoRequestElement(header, param, pictureSize, ids);
        // SOAP getUserInfo
        GetUserInfoResponseElement userInfoResponse = Scrobbler.getUserInfo(getUserInfoRequest);
        return userInfoResponse.getUsersInfo();
	}

}
