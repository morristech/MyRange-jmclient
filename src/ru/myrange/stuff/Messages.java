/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff;

import java.util.Enumeration;
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.Records;
import ru.myrange.soap.Message;

/**
 * Static class to store and work with all messages
 *
 * @author Oleg Ponfilenok
 */
public class Messages {
    private static final int MSG_MAX = 50;	    // Maximum number of messages in the vector
    private static Vector messages = new Vector();   // the last message has index 0

    
    /**
     * Number of uread inbox messages
     */
    public static int numNewInboxMsg() {
        int newInboxMsgNum = 0;
        for (int i=0; i < messages.size(); i++) {
            if ( ((Message) messages.elementAt(i)).getDirection().equals(Message.DIRECTION_INBOX))
                if ( ((Message) messages.elementAt(i)).getStatus().equals(Message.STATUS_UNREAD))
                    newInboxMsgNum ++;
        }
        return newInboxMsgNum;
    }

    /**
     * Number of uread inbox messages from concrete user
     */
    public static int numNewInboxMsg(Integer userId) {
        int newInboxMsgNum = 0;
        Vector msgs = getMessagesFromUsers(userId);
        for (int i=0; i < msgs.size(); i++) {
            if ( ((Message) msgs.elementAt(i)).getDirection().equals(Message.DIRECTION_INBOX))
                if ( ((Message) msgs.elementAt(i)).getStatus().equals(Message.STATUS_UNREAD))
                    newInboxMsgNum ++;
        }
        return newInboxMsgNum;
    }

    public static Object[] getMessages() {
        Object[] result = new Object[messages.size()];
        messages.copyInto(result);
        return result;
    }

    private static Vector getSMSMessages() {
        Vector res = new Vector();
        for (int i=0; i < messages.size(); i++) {
            if ( ((Message) messages.elementAt(i)).getType().equals(Message.TYPE_SMS))
                res.addElement((Message) messages.elementAt(i));
        }
        return res;
    }

    /**
     * Return vector of unique users in the list of private messages
     */
    /*
    public static Object[] getMessagesUsers() throws Exception
    {
        Vector users = new Vector();
        for (Enumeration e = messages.elements() ; e.hasMoreElements() ;) {
            Message msg = (Message)e.nextElement();
            
            int i=0;
            while (i < users.size()) {
                if ( msg.getSenderId().intValue() == ((UserInfo)users.elementAt(i)).getUserId().intValue() )
                    break;
                i++;
            }
            // add user to the list
            if (i >= users.size()) {
                users.addElement(Users.getUserWithPic(msg.getSenderId()));
            }
        }
        Object[] result = new Object[users.size()];
        users.copyInto(result);
        return result;
    }
    */

    public static Vector getMessagesFromUsers(Integer userId) {
        Vector res = new Vector();
        for (Enumeration e = messages.elements() ; e.hasMoreElements() ;) {
            Message msg = (Message) e.nextElement();
            if ( msg.getSenderId().intValue() == userId.intValue() )
                res.addElement(msg);
        }
        return res;
    }

    /**
     * Get new private and sms messages from the server
     * (ONLY FOR CALL FROM SEPARATE THREAD)
     *
     * @return number of new messages
     * @throws java.lang.Exception
     */
    public synchronized static int getNewMessages() throws Exception {
        int result = 0;

        /*** Update status of unread messages ***/

        for (int i=0; i < messages.size(); i++) {
            Message msg = (Message) messages.elementAt(i);
            if (msg.getStatus().equals(Message.STATUS_UNREAD)) {
                Vector msgId = new Vector();
                msgId.addElement(msg.getMsgId());
                // SOAP getPrivateMessage. In preview mode
                Message msgNew = (Message) (StaticActions.getPrivateMessage(msgId, new Boolean(true))).lastElement();
                if (msgNew.getStatus().equals(Message.STATUS_READ)) {
                    // Change the status of target message to "read"
                    msg.setStatus(Message.STATUS_READ);
                }
            }
        }

        /*** GET NEW PRIVATE MESSAGES ***/

        // Find the last getting messages time
        // lastGetTime is the time of the first message in the list
        long lastGetTime = 0;
        if (messages.size() > 0) lastGetTime = ((Message) messages.elementAt(0)).getTime() + 1000; // Time of the last message + 1s.;

        // Get new messages from the server
        Vector newMessages = StaticActions.getPrivateMessages(null, lastGetTime, new Integer(MSG_MAX));
        result = newMessages.size();

        boolean signal = false; // Do indicate via vibration and sound signal if get new inbox incoming message?

        // Server returned the list of new messages sort by decrease time
        while (newMessages.size() > 0) {
            int index = newMessages.size() - 1;     // the last element in Vector
            Message newMsg = (Message) newMessages.elementAt(index);

            // Try to download profile of sender
            Users.getUserWithPic(newMsg.getSenderId());

            // Insert new message to the lis
            messages.insertElementAt(newMsg, 0);
            if (messages.size() > MSG_MAX) messages.removeElementAt(messages.size()-1);

            // Try to find new unread inbox message
            if (newMsg.getDirection().equals(Message.DIRECTION_INBOX) &&
                    newMsg.getStatus().equals(Message.STATUS_UNREAD)) {
                signal = true;
            }

            newMessages.removeElementAt(index);
        }

        /*** GET NEW SMS MESSAGES ***/

        // Find the last getting sms time
        // lastGetTime is the time of the first sms in the list
        lastGetTime = 0;
        Vector smsMessages = getSMSMessages();
        if (smsMessages.size() > 0) lastGetTime = ((Message) smsMessages.elementAt(0)).getTime() + 1000; // Time of the last message + 1s.;

        // Get new smses from the server
        Vector newSmses = StaticActions.getSmsMessages(new Long(lastGetTime), new Long(System.currentTimeMillis()));
        // Server returned the list of new smses sort by decrease time
        while (newSmses.size() > 0) {
            int index = newSmses.size() - 1;     // the last element in Vector
            Message newSms = (Message) newSmses.elementAt(index);

            // Try to download profile of sender
            Users.getUserWithPic(newSms.getSenderId());

            // Insert new message to the lis
            messages.insertElementAt(newSms, 0);
            if (messages.size() > MSG_MAX) messages.removeElementAt(messages.size()-1);

            newSmses.removeElementAt(index);
        }

        /* UPDATE */

        // Update private messages list in RMS
        saveToRMS();

        if (signal) {
            // Indicate about new message via vibration and sound signal
            MyRangeMIDlet.attention(StringConsts.S_NEW_MSG_ALERT, SoundConsts.NEW_MSG_MELODY);
        }

        // Return number of new messages
        return result;
    }

    /**
     * Get full versions of private messages. Update them in the list
     *
     * @param msgIds - messages id for download
     * @return - number of downloaded messages
     * @throws java.lang.Exception
     */
    public static int getFullMessages(Vector msgIds) throws Exception {
        // SOAP getPrivateMessage.
        Vector newMsgs = StaticActions.getPrivateMessage(msgIds, new Boolean(false));
        // Update messages in the list
        for (int i=0; i < newMsgs.size(); i++) {
            Message newMsg = (Message) newMsgs.elementAt(i);
            int j = 0;
            while (j < messages.size()) {
                if ( newMsg.getMsgId().intValue() == ((Message) messages.elementAt(j)).getMsgId().intValue() ) {
                    messages.removeElementAt(j);
                    messages.insertElementAt(newMsg, j);
                    break;
                }
                j++;
            }
            if (j >= messages.size()) messages.addElement(newMsg);
        }
        return newMsgs.size();
    }


    /**
     * Get new sms messages from the server
     * and show at the screen
     */
    /*
    public static void getAndShowNewSmses(final Command backCommand)
    {
        (new Thread() {
            public void run(){
                try {
                    //MyRange.showWaitingAlert(MyConsts.S_DOWNLOAD, backDisplayable);
                    // Set loaging flag to the TRUE state and show the sms chat

                    ((SmsScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.SMS_SCREEN]).setLoading(true);
                    MyRangeMIDlet.SCREENS[MyRangeMIDlet.SMS_SCREEN].run(backCommand);

                    // Find the last getting sms time
                    // lastGetTime is the time of the first sms in the list
                    long lastGetTime = 0;
                    if (smsMessages.size() > 0) lastGetTime = ((SmsMessage) smsMessages.elementAt(0)).getTime() + 1000; // Time of the last message + 1s.;

                    // Get new smses from the server
                    Vector newSmses = StaticActions.getSmsMessages(new Long(lastGetTime), new Long(System.currentTimeMillis()));
                    // Server returned the list of new smses sort by decrease time
                    while (newSmses.size() > 0) {
                        int index = newSmses.size() - 1;     // the last element in Vector
                        SmsMessage newSms = (SmsMessage) newSmses.elementAt(index);

                        // Try to download profile of sender
                        Users.getUserWithPic(newSms.getSenderId());

                        // Insert new message to the lis
                        smsMessages.insertElementAt(newSms, 0);
                        if (smsMessages.size() > SMS_MAX) smsMessages.removeElementAt(smsMessages.size()-1);

                        newSmses.removeElementAt(index);
                    }
                    // Update sms list in RMS
                    saveToRMS();

                    // Set loaging flag to the FALSE state and show the sms chat
                    ((SmsScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.SMS_SCREEN]).setLoading(false);
                    if (MyRangeMIDlet.getDisplayable().isShown() &&
                            Display.getInstance().getCurrent() ==
                            MyRangeMIDlet.SCREENS[MyRangeMIDlet.SMS_SCREEN].getForm() ) {
                        MyRangeMIDlet.SCREENS[MyRangeMIDlet.SMS_SCREEN].run(backCommand);
                    }
                } catch (Exception e) {
                    MyRangeMIDlet.out.println("[SmsList] Error: " + e.toString());
                    // Show the sms chat without new messages
                    try {
                        // Set loaging flag to the FALSE state and show the sms chat
                        ((SmsScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.SMS_SCREEN]).setLoading(false);
                        MyRangeMIDlet.SCREENS[MyRangeMIDlet.SMS_SCREEN].run(backCommand);
                        // Show error alert
                        MyRangeMIDlet.showFailAlert("Exception", e.toString());
                    } catch (Exception e2) {
                        MyRangeMIDlet.out.println("[SmsList] Error: " + e2.toString());
                        // Show error alert
                        MyRangeMIDlet.showFailAlert("Exception", e.toString());
                    }
                }
            }
        }).start();
    }
    */

    public synchronized static void saveToRMS() throws Exception {
        // Add test data
        Records.recMessages.addByteData(new byte[1]);
        // OK
        Records.recMessages.eraseRecord();
        for (Enumeration e = messages.elements() ; e.hasMoreElements() ;) {
            Records.recMessages.addByteData( ((Message) e.nextElement()).toRecord());
        }
	}

	public synchronized static void loadFromRMS() throws Exception {
        // Load private messages
        messages.removeAllElements();
        for (int i=1; i<=Records.recMessages.getNumRec(); i++) {
            messages.addElement(new Message(Records.recMessages.readByteData(i)));
        }
	}

}
