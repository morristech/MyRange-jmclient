/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.StringConsts;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.MeetUsers;
import ru.myrange.stuff.Users;
import ru.myrange.stuff.xlwuit.ImageMenuButton;
import ru.myrange.stuff.xlwuit.MinorLabel;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.stuff.xlwuit.UserPicCellRenderer;
import ru.myrange.stuff.xlwuit.XList;

/**
 * Show and work with list of meet users
 *
 * @author Oleg Ponfilenok
 */
public class MeetUsersScreen extends Screen {

    public String getName() {
        String name = MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.MEET_USERS_SCREEN];
        if (MeetUsers.numNearUsers() > 0) {
            name = StringConsts.S_USERS_NEAR + Integer.toString(MeetUsers.numNearUsers());
        }
        return name;


    }

    protected void execute(final Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        // Create the users list
        final XList usersList = new XList(Users.getUsers(MeetUsers.getIds()));
        usersList.setListCellRenderer(new UserPicCellRenderer());
        final ActionListener defaultActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Show selected user's profile
                UserInfo selection = (UserInfo) usersList.getSelectedItem();
                final Integer userId = selection == null ? null : selection.getUserId();
                if (userId != null)
                    Users.showUserProfile(userId, backToThisCommand);
            }
        };
        usersList.addActionListener(defaultActionListener);
        f.addComponent(usersList);

        f.addCommand(new Command(StringConsts.S_REFRESH) {
            public void actionPerformed(ActionEvent evt) {
                MeetUsers.unreg.removeAllElements();  // Full erase ureg user list
                // activated the refresh mode
                MeetUsers.isRefreshMode = true;
                MeetUsers.refreshModeCount = 0;
                // start new bluetooth scan
                try {
                    MyRangeMIDlet.bluetooth.reDiscover();
                } catch (Exception ex){
                    MyRangeMIDlet.showFailAlert(StringConsts.S_ERROR, ex.getMessage());
                }
            }
        });

        if (usersList.size() > 0) {   // if there are any users in the list
            // Add delete all users from the list command
            f.addCommand(new Command(StringConsts.S_ERASE_USERLIST) {
                public void actionPerformed(ActionEvent evt) {
                    // Full erase userIds and ureg user list
                    MeetUsers.getIds().removeAllElements();
                    MeetUsers.unreg.removeAllElements();
                    MyRangeMIDlet.staticMidlet.updateShow();
                }
            });
        } else {    // No users
            f.addComponent(new TextComponent(StringConsts.S_MEET_NO_USERS));
        }

        // Add show found devices button
        Image btImg;
        if (MyRangeMIDlet.bluetooth.isScanning) btImg = MyRangeMIDlet.icons[StringConsts.I_ICON_BT_ON];
        else btImg = MyRangeMIDlet.icons[StringConsts.I_ICON_BT_OFF];
        f.addComponent(new ImageMenuButton(btImg, new Command(StringConsts.S_DEVICES) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.DEVICES_SCREEN].run(backToThisCommand);
            }
        }, new MinorLabel("(" + StringConsts.S_DEVICES_NEAR + Integer.toString(MyRangeMIDlet.bluetooth.numDevicesNow()) + ")")));

    }
	
}