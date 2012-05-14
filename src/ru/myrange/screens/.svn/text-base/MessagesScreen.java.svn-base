/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.soap.Message;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.Messages;
import ru.myrange.stuff.MyConferences;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.Users;
import ru.myrange.stuff.xlwuit.InfiniteProgressIndicator;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.stuff.xlwuit.UserProfileButton;

/**
 * List of opponents (messages users)
 *
 * @author Oleg Ponfilenok, Yegorov Nickoaly
 */
public class MessagesScreen extends Screen {

    public String getName() {
        String name = MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.MESSAGES_SCREEN];
        if (Messages.numNewInboxMsg() > 0)
                name =Integer.toString(Messages.numNewInboxMsg()) + StringConsts.S_MESSAGES_NEW;
        return name;
    }

    protected void execute(final Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        // Add opponents
        (new Thread() {
            public void run(){
                Container pleaseWaitCnt = new Container();
                InfiniteProgressIndicator pleaseWait = new InfiniteProgressIndicator(MyRangeMIDlet.waitCircle);
                pleaseWaitCnt.addComponent(pleaseWait);
                f.addComponent(pleaseWaitCnt);
                f.revalidate();

                Vector users = new Vector();
                Object[] messages = Messages.getMessages();
                for (int i=0; i < messages.length; i++) {
                    if (messages.length <= i || messages[i] == null || !(messages[i] instanceof Message)) continue;
                    Message msg = (Message) messages[i];

                    int j=0;
                    while (j < users.size()) {
                        if ( msg.getSenderId().intValue() == ((UserInfo)users.elementAt(j)).getUserId().intValue() )
                            break;
                        j++;
                    }
                    // Add user
                    // TODO: But don't add the feebdack to send user!
                    if (j >= users.size() && msg.getSenderId().intValue() != MyConferences.getFeedbackId().intValue()) {
                        UserInfo nUser;
                        try {
                            // try to get user with pic
                            nUser = Users.getUserWithPic(msg.getSenderId());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            // try to get user without pic
                            nUser = Users.getUser(msg.getSenderId());
                        }
                        final UserInfo nextUser = nUser;
                        users.addElement(nextUser);
                        // Add user button
                        UserProfileButton upb = new UserProfileButton(nextUser, new Command(StringConsts.S_PROFILE) {
                            public void actionPerformed(ActionEvent ae) {
                                // Open the chat
                                ((UserMsgScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.USER_MSG_SCREEN]).run(backToThisCommand,
                                        Messages.getMessagesFromUsers(nextUser.getUserId()));
                            }
                        });

                        if(i == 0)
                            upb.setNextFocusUp(upb);

                        if(i + 1 == messages.length)
                            upb.setNextFocusDown(upb);

                        f.addComponent(upb);

                        f.revalidate();
                    }
                }
                pleaseWaitCnt.removeComponent(pleaseWait);
                if (users.size() <= 0) {   // No messages
                    f.addComponent(new TextComponent(StringConsts.S_PRIVATE_NO_MESSAGES));
                }
                f.revalidate();
            }
        }).start();
    }

}
