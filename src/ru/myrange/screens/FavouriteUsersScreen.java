/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import java.util.Enumeration;
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.StringConsts;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.Favourites;
import ru.myrange.stuff.Users;
import ru.myrange.stuff.xlwuit.InfiniteProgressIndicator;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.stuff.xlwuit.UserPicCellRenderer;
import ru.myrange.stuff.xlwuit.XList;

/**
 * Show and work with list of favourite users
 *
 * @author Oleg Ponfilenok
 */
public class FavouriteUsersScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.FAVOURITE_USERS_SCREEN];
    }

    protected void execute(final Form f) {
        // Create the users list
        final XList usersList = new XList();
        usersList.setListCellRenderer(new UserPicCellRenderer());
        final ActionListener defaultActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (usersList.size() <= 0) return;
                // Show selected user's profile
                UserInfo selection = (UserInfo) usersList.getSelectedItem();
                final Integer userId = selection == null ? null : selection.getUserId();
                if (userId != null)
                    Users.showUserProfile(userId, backToThisCommand);
            }
        };
        usersList.addActionListener(defaultActionListener);
        f.addComponent(usersList);

        // Add favourite users
        (new Thread() {
            public void run(){
                Container pleaseWaitCnt = new Container();
                InfiniteProgressIndicator pleaseWait = new InfiniteProgressIndicator(MyRangeMIDlet.waitCircle);
                pleaseWaitCnt.addComponent(pleaseWait);
                f.addComponent(pleaseWaitCnt);
                f.revalidate();

                Vector users = new Vector();
                for (Enumeration e = Favourites.getIds().elements() ; e.hasMoreElements() ;) {
                    Integer id = (Integer) e.nextElement();
                    // Add user
                    UserInfo nextUser = null;
                    try {
                        // try to get user with pic
                        nextUser = Users.getUserWithPic(id);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        // try to get user without pic
                        nextUser = Users.getUser(id);
                    }
                    if (nextUser != null) {
                        users.addElement(nextUser);
                        usersList.addItem(nextUser);
                        f.revalidate();
                    }
                }
                
                pleaseWaitCnt.removeComponent(pleaseWait);
                if (usersList.size() <= 0) {   // No users
                    f.addComponent(new TextComponent(StringConsts.S_FAVOURITE_NO_USERS));
                }
                f.revalidate();
            }
        }).start();
    }

}