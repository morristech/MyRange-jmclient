/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff;

import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.screens.UserProfileScreen;
import ru.myrange.soap.Actor;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.xlwuit.UserPicCellRenderer;

/**
 * Static class to work with meet users
 *
 * @author Oleg Ponfilenok
 */
public class MeetUsers {

	//private static final int UNREG_MAX = 1000;	// Maximum number of uregistered devices in the vector
	//private static final long UPDATE = 86400000;	// 1 day - Actual profile information time.
    private static final long SIGNAL_TIME = 600000;
	private static Vector ids = new Vector();            // Vector of UserId's. Sort by increase meetTime
	public static Vector unreg = new Vector();			// Vector of unregistereg devices (UnregDevice)
    public static boolean isRefreshMode = false;        // In refresh mode update information about users
    public static int refreshModeCount = 0;             // In refresh mode update information about users

    /**
     * Get meet users ids. Sort by decrease meetTime
     */
    public static Vector getIds() {
        Vector res = new Vector();
        for (Enumeration e = ids.elements() ; e.hasMoreElements() ;) {
            res.insertElementAt(e.nextElement(), 0);
        }
		return res;
	}

    /**
     * Set meet users ids
     */
    public static void setIds(Vector idents) {
        if (idents != null) ids = idents;
	}

    // Search this btId in the list of users and devices
	public static boolean isNewBtId(final String btId, final long lastTime) {
		// Search this btID in list of current users
		for(int i = 0; i < ids.size(); i++) {
			UserInfo user = Users.getUser((Integer) ids.elementAt(i));
			/*
            if (nowTime - user.getDownloadTime() > UPDATE ) {	// Delete old profile
				users.removeElementAt(i);
				i--;
				continue;
			}
			*/
			if (user.getBtAddress().equals(btId)) {
				if (user.getLastSeenTime() < lastTime - SIGNAL_TIME) {
                    // Indicate about user via vibration and sound signal
                    if (Signals.isSignal(user.getUserId())) {
                        MyRangeMIDlet.attention(StringConsts.S_NEW_PERSONAL_USER_ALERT +
                                "\"" + user.getFullName() + "\"",
                                SoundConsts.USER_ENCOUNTERED_MELODY);
                    }

                }
                if (user.getLastSeenTime() < lastTime) {
                    user.setLastSeenTime(lastTime);    // Update the last seen time
                    sortIds();
                }
                return false;
			}
		}
		// Search this btID in list of unregistered devices
		for(int i = 0; i < unreg.size(); i++) {
			UnregDevice dev = (UnregDevice) unreg.elementAt(i);
			/*
            if (nowTime - dev.getTime > UPDATE ) {		// Delete old unregistred device
				unreg.removeElementAt(i);
				i--;
				continue;
			}
			*/
			if (dev.btID.equals(btId)) {
				return false;
			}
		}
		return true;    // This btID is New
	}

    /**
     * Search id in the list of ids
     */
	public static boolean isNewUserId(final Integer id, final long lastTime) {
		for (Enumeration e = ids.elements() ; e.hasMoreElements() ;) {
            if ( id.intValue() == ((Integer) e.nextElement()).intValue() ) {
				if (Users.getUser(id).getLastSeenTime() < lastTime) {
                    Users.getUser(id).setLastSeenTime(lastTime);  // Update the last seen time
                    sortIds();
                }
				return false;
			}
        }
		return true;		// This UserId is New
	}

    /**
     * Add new user to the list of Users.users and to the list of ids
     * or add new btId to the list of unregistered devices
	 * <ol>
	 * <li>If refresh mode update encountered user profile</li>
	 * <li>Search getting btID in list of users and in list of unregistered devices</li>
	 * <li>If new user encoutered connect to the server to get the profile of new user</li>
	 * </ol>
     *
     * @param btId Bluetooth MAC address of near device.
     * @param lastSeenTime time of device discovered.
     */
	public static synchronized void addUser(final String btId, final long lastSeenTime) {
        (new Thread() {
			public void run(){
                // If refresh mode update encountered user profile
                if (isRefreshMode) {
                    // Search this btID in list of current meet users
                    for(int i = 0; i < ids.size(); i++) {
                        UserInfo user = (UserInfo) Users.getUser((Integer) ids.elementAt(i));
                        if (user.getBtAddress().equals(btId)) {
                            // Delete this profile for refresh it (get the new one)
                            ids.removeElementAt(i);
                            i--;
                        }
                    }
                    // Search this btID in list of unregistered devices
                    for(int i = 0; i < unreg.size(); i++) {
                        UnregDevice dev = (UnregDevice) unreg.elementAt(i);
                        if (dev.btID.equals(btId)) {
                             // Delete this btAddres from the list of ureg devices for refresh it
                            unreg.removeElementAt(i);
                            i--;
                        }
                    }
                }

                // Return if current btId has been discovered early
                if (!isNewBtId(btId, lastSeenTime)) return;  // This btID is already contained in the lists

		        // Connect to the server to get the profile of new user
                try {
                    // Get short profile of user
                    MyRangeMIDlet.out.println("[MeetUsers] Check the BtID=" + btId);
                    Actor newActor = StaticActions.getActor(btId);
                    if (newActor != null) {   // Bluetooth address is registered
                        MyRangeMIDlet.out.println("[MeetUsers] BtID=" + btId + " is registered myRange user");

                        Integer newId = newActor.getActorId();
                        if (!Users.hasUserPicture(newId)) {
                            // download
                            Users.addUser(newId, UserProfileScreen.bestUserpicSize);
                        }
                        final UserInfo newUser = Users.getUser(newId);
                        newUser.setLastSeenTime(lastSeenTime);

						// Add new user profile in the vector
                        if (!isNewUserId(newId, lastSeenTime)) {
                            // This userId is already contained in the lists (if single user has many devices)
                            return;
                        }
						ids.addElement(newId);

                        // Update lists of users
                        MyRangeMIDlet.staticMidlet.updateShow();

                        // Indicate about user via vibration and sound signal
                        if (Signals.isSignal(newId)) {
                            MyRangeMIDlet.attention(StringConsts.S_NEW_PERSONAL_USER_ALERT +
                                    "\"" + newUser.getFullName() + "\"",
                                    SoundConsts.USER_ENCOUNTERED_MELODY);
                        }
					} else {    // Bluetooth address is unregistered
                        MyRangeMIDlet.out.println("[MeetUsers] BtID=" + btId + " is unregistered");
                        // Add new unregistered user in the vector
						//if (unreg.size() > UNREG_MAX) unreg.removeElementAt(0);
						if (isNewBtId(btId, lastSeenTime)) {
                            // This btID is new. Add it
                            unreg.addElement(new UnregDevice(btId, new Date().getTime()));
                        }
                    }
				} catch (Exception e){
                    MyRangeMIDlet.out.println("[MeetUsers] Error:" + e.getMessage());
				}
			}
		}).start();
	}

    /**
     * Sort ids by increase meetTime
     */
    public static synchronized void sortIds() {
        Vector oldIds = new Vector();
        for(int i=0; i<ids.size(); i++) { // Copy user to oldUsers
            oldIds.addElement(ids.elementAt(i));
        }
        ids.removeAllElements();
        // Sort
        while(oldIds.size() > 0) {
            Integer oldest = (Integer) oldIds.elementAt(0);
            for(int i=1; i<oldIds.size(); i++) {
				if ( Users.getUser((Integer) oldIds.elementAt(i)).getLastSeenTime() <
                        Users.getUser(oldest).getLastSeenTime()) {
					oldest = (Integer) oldIds.elementAt(i);
				}
			}
            ids.addElement(oldest);
            oldIds.removeElement(oldest);
    	}
	}

	/**
     * Culate number of near users now
     *
     * @return number of near users
     */
    public static int numNearUsers() {
		int num = 0;
		for(int i = 0; i < ids.size(); i++) {
            if ( Users.getUser((Integer) ids.elementAt(i)).getLastSeenTime() >
                    System.currentTimeMillis()) {
                num++;
            }
		}
		return num;
	}

    /**
     * Class represent unregistered device
     */
	static class UnregDevice {

        /**
         * Bluetooth address of device
         */
		public String btID;

        /**
         * Time of check (ask from server)
         */
		public long getTime = 0;

		/**
         * Simple constructor
         *
         * @param btID
         * @param getTime
         */
		public UnregDevice(final String btID, final long getTime)
		{
			this.btID = btID.toLowerCase();
			this.getTime = getTime;
		}

        /*
		public byte[] toRecord() {		        // Create the byte[] record to add to the record store
			return null;
		}
        */
	}

}
