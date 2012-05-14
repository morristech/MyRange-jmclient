/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.animations.Transition;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import java.util.Enumeration;
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.soap.Message;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.Messages;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.StaticActions;
import ru.myrange.stuff.Users;
import ru.myrange.stuff.xlwuit.AutoSwapContainer;
import ru.myrange.stuff.xlwuit.ChatText;
import ru.myrange.stuff.xlwuit.InfiniteProgressIndicator;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.stuff.xlwuit.UserProfileButton;

/**
 * Chat with user
 *
 * @author Oleg Ponfilenok
 */
public class UserMsgScreen extends Screen {

    /**
     * The best size of an userpic image near the message
     */
    private static final int BEST_USERPIC_SIZE = 48;

    private Vector messages = new Vector();

    private Container chatScreen = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    private Vector aligned = new Vector();

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.USER_MSG_SCREEN];
    }

    /**
     * Overload run method
     *
     * @param backCommand - back to previous screen command
     * @param messages - messages to show
     */
    public final void run(final Command backCommand, final Vector msgs) {
        messages = new Vector();
        for (int i=0; i<msgs.size(); i++) {
            if (msgs.elementAt(i) instanceof Message)
                messages.addElement((Message) msgs.elementAt(i));
        }
        createAll(backCommand);
        show();
    }

    protected void execute(final Form f) {

        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        if (messages != null && messages.size() == 0) {
            // No messages
            f.addComponent(new TextComponent(StringConsts.S_PRIVATE_NO_MESSAGES));
            return;
        }

        // Get Full Messages. It's necessary for set status to READ
        (new Thread() {
            public void run(){

                // Get opponent Id
                Integer oppId = null;
                for (Enumeration e = messages.elements() ; e.hasMoreElements() ;) {
                    Message msg = (Message) e.nextElement();
                    if ( !msg.getType().equals(Message.DIRECTION_OUTBOX) ) {
                        oppId = msg.getSenderId();
                        break;
                    }
                }

                final UserInfo oppProfile = Users.getUser(oppId);
                
                // Add opponent profile button
                if (oppProfile != null) {
                    f.addComponent(new UserProfileButton(oppProfile, new Command(StringConsts.S_PROFILE) {
                        public void actionPerformed(ActionEvent ae) {
                            // Back to main menu
                            final Command backToMainMenu = new Command(StringConsts.S_BACK){
                                public void actionPerformed(ActionEvent ae) {
                                    MyRangeMIDlet.SCREENS[MyRangeMIDlet.MAINMENU_SCREEN].
                                            run(MyRangeMIDlet.SCREENS[MyRangeMIDlet.MAINMENU_SCREEN].getBackCommand());
                                }
                            };
                            Users.showUserProfile(oppProfile.getUserId(), backToMainMenu);
                        }
                    }));
                }

                // Add send message component
                if (oppProfile != null) {
                    final TextArea msgArea = new TextArea();
                    f.addComponent(msgArea);
                    f.addCommand(new Command(StringConsts.S_SEND) {
                        public void actionPerformed(ActionEvent ae) {
                            (new Thread() {
                                public void run() {  // Send message to remote device
                                    String msg = msgArea.getText().trim();
                                    if (msg.length() < 1) {
                                        // No any text was entered
                                        MyRangeMIDlet.showInfoAlert(StringConsts.S_SEND_MSG_NO_TEXT);
                                        return;
                                    }
                                    
                                    final Dialog pleaseWait = MyRangeMIDlet.showWaitingAlert(StringConsts.S_SENDING);
                                    try {
                                        StaticActions.sendPrivateMessage(oppProfile.getUserId(), null, msg);
                                        // Get new Outbox messages from the server. For show the message that just was send.
                                        Messages.getNewMessages();
                                        // Message send OK
                                        pleaseWait.dispose();
                                        // Update messages
                                        ((UserMsgScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.USER_MSG_SCREEN]).run(backCommand,
                                                Messages.getMessagesFromUsers(oppProfile.getUserId()));
                                    } catch( Exception e ){
                                        pleaseWait.dispose();
                                        MyRangeMIDlet.showFailAlert(StringConsts.S_SEND_MSG_FAIL_ALERT, "[Private Message] Exception: " + e.toString());
                                    }
                                }
                            }).start();
                        }
                    });
                }

                // Add messages to the form
                aligned = new Vector();
                chatScreen = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                chatScreen.setScrollableY(true);
                f.addComponent(chatScreen);
                //f.registerAnimated(f);
                for (Enumeration e = messages.elements() ; e.hasMoreElements() ;) {
                    addMessage((Message) e.nextElement());
                }

                 // Add please wait
                InfiniteProgressIndicator pleaseWait = new InfiniteProgressIndicator(MyRangeMIDlet.waitCircle);
                chatScreen.addComponent(0, pleaseWait);

                f.revalidate();

                // Update onlise statuses and messages
                /*
                try {
                    Users.updateOnlineStatuses();
                    if (Messages.getNewMessages() > 0) {
                        // Update messages
                        if (Display.getInstance().getCurrent() == MyRangeMIDlet.SCREENS[MyRangeMIDlet.USER_MSG_SCREEN].getForm()) {
                            ((UserMsgScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.USER_MSG_SCREEN]).run(backCommand,
                                    Messages.getMessagesFromUsers(oppId));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                */

                // Try to download the full version of messages from the server.
                // It's necessary for set messages to the READ state
                try {
                    Vector getFullMsgIds = new Vector();
                    for (Enumeration e = messages.elements() ; e.hasMoreElements() ;) {
                        Message msg = (Message) e.nextElement();
                        if (msg.getType().equals(Message.TYPE_PRIVATE)) {
                            if (msg.getPreview().booleanValue() ||
                                    ( msg.getDirection().equals(Message.DIRECTION_INBOX) && msg.getStatus().equals(Message.STATUS_UNREAD) ) ) {
                                getFullMsgIds.addElement(msg.getMsgId());
                            }
                        }
                    }
                    // Download full versions
                    if (Messages.getFullMessages(getFullMsgIds) > 0) {
                        // Update messages
                        if (Display.getInstance().getCurrent() == MyRangeMIDlet.SCREENS[MyRangeMIDlet.USER_MSG_SCREEN].getForm()) {
                            ((UserMsgScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.USER_MSG_SCREEN]).run(backCommand,
                                    Messages.getMessagesFromUsers(oppId));
                        }
                    }
                } catch (Exception e){
                    MyRangeMIDlet.showFailAlert("Exception", e.toString());
                }

                chatScreen.removeComponent(pleaseWait);
                f.revalidate();
            }
        }).start();
    }

    private void addMessage(final Message msg) {
        if (msg==null || msg.getMessage()==null || msg.getMessage().length()<1) return;
        boolean me = msg.getDirection().equals(Message.DIRECTION_OUTBOX);
        Container e = new Container(new BorderLayout());
        final UserInfo participant;
        if (me) {
            if (Users.getMyProfile() != null ) participant = Users.getMyProfile();
            else participant = new UserInfo();
        } else {
            participant = Users.getUser(msg.getSenderId());
        }
        Transition replaceTransition = CommonTransitions.createFade(300);
        final Label avatar = new Label();
        if (participant.getPic() != null) {
            avatar.setIcon(participant.getPic(BEST_USERPIC_SIZE, BEST_USERPIC_SIZE));
        } else {
            avatar.setIcon(Users.getNullUserpic(BEST_USERPIC_SIZE, BEST_USERPIC_SIZE));
        }
        final Label date = new Label(msg.getTimeHR());
        AutoSwapContainer box = new AutoSwapContainer(new Component[] {
            avatar,
            new Label(participant.getFullName()),
            new Label()
        }, replaceTransition, 2000);
        TextArea t = new ChatText(msg.getMessage(), me, box);
        t.setGrowByContent(true);
        t.setSingleLineTextArea(false);
        t.setEditable(false);
        e.addComponent(BorderLayout.CENTER, t);
        alignComponent(48, Display.getInstance().getDisplayWidth() / 4, box);
        if(me) {
            date.setAlignment(Label.LEFT);
            e.addComponent(BorderLayout.NORTH, date);
            e.addComponent(BorderLayout.WEST, box);
        } else {
            date.setAlignment(Label.RIGHT);
            e.addComponent(BorderLayout.NORTH, date);
            e.addComponent(BorderLayout.EAST, box);
        }
        /*
        Label dummy = new Label();
        dummy.setPreferredSize(e.getPreferredSize());
        chatScreen.addComponent(0, dummy);
        e.requestFocus();
        screenForm.revalidate();
        chatScreen.replaceAndWait(dummy, e, CommonTransitions.createSlide(CommonTransitions.SLIDE_VERTICAL, true, 100));
        */
        //chatScreen.addComponent(0, e);
        chatScreen.addComponent(e);
        e.requestFocus();
        //screenForm.revalidate();
    }

    private void alignComponent(int minimumW, int maximumW, Component c) {
        // make sure the width isn't bellow the minimum or above the maximum
        int w = Math.min(c.getPreferredW(), maximumW);
        w = Math.max(w, minimumW);

        if(aligned.size() > 0) {
            Component alignedCmp = (Component)aligned.elementAt(0);
            int alignedW = alignedCmp.getPreferredW();
            aligned.addElement(c);
            if(alignedW >= w) {
                c.setPreferredW(alignedW);
                return;
            }
            if(alignedW < w) {
                for(int iter = 0 ; iter < aligned.size() ; iter++) {
                    ((Component)aligned.elementAt(iter)).setPreferredW(w);
                }
                return;
            }
        } else {
            // first component is a special case:
            c.setPreferredW(w);
            aligned.addElement(c);
        }
    }
    
}
