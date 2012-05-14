/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.soap.ConfInfo;
import ru.myrange.stuff.Companies;
import ru.myrange.stuff.MeetUsers;
import ru.myrange.stuff.Messages;
import ru.myrange.stuff.MyConferences;
import ru.myrange.stuff.SearchUsers;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.xlwuit.HeaderText;
import ru.myrange.stuff.xlwuit.MenuButton;
import ru.myrange.stuff.xlwuit.MinorLabel;
import ru.myrange.stuff.xlwuit.MyProfileButton;
import ru.myrange.stuff.xlwuit.SignalLabel;

/**
 * The Main menu
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class MainMenuScreen extends Screen
{
	//private Image[] imgMainMenu = new Image[StringConsts.S_MAIN_MENU.length];

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.MAINMENU_SCREEN];
    }

    protected void execute(final Form f) {

        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        // Add conference info
        ConfInfo ac = MyConferences.getActualConf();
        if (ac != null) {
            f.addComponent(new HeaderText(ac.getName()));
        }


        // Add my profile button
        MyProfileButton mpb = new MyProfileButton(new Command(StringConsts.S_MY_PROFILE) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.MY_PROFILE_SCREEN].run(backToThisCommand);
            }
        });

        mpb.setTop();
        f.addComponent(mpb);

        // Create main menu list
        /*
        imgMainMenu[StringConsts.CMD_M_USERS] = MyRangeMIDlet.icons[StringConsts.I_ICON_USERS];
        imgMainMenu[StringConsts.CMD_M_ENTERPRISE_USERS] = MyRangeMIDlet.icons[StringConsts.I_ICON_ENTERPRISE];
		imgMainMenu[StringConsts.CMD_M_DEVICES] = MyRangeMIDlet.icons[StringConsts.I_ICON_DEVICES];
        imgMainMenu[StringConsts.CMD_M_MESSAGES] = MyRangeMIDlet.icons[StringConsts.I_ICON_PRIVATEMSG];
        //imgMainMenu[StringConsts.CMD_M_PROFILE] = MyRangeMIDlet.icons[StringConsts.I_ICON_PROFILE];
		imgMainMenu[StringConsts.CMD_M_OPTIONS] = MyRangeMIDlet.icons[StringConsts.I_ICON_OPTIONS];
        */

         // Add my events button
        f.addComponent(new MenuButton(new Command(MyRangeMIDlet.SCREENS[MyRangeMIDlet.EVENTS_SCREEN].getName()) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.EVENTS_SCREEN].run(backToThisCommand);
            }
        }));


         // Add my meets button
//        f.addComponent(new MenuButton(new Command(MyRangeMIDlet.SCREENS[MyRangeMIDlet.BUSINESS_MEET_SCREEN].getName()) {
//            public void actionPerformed(ActionEvent ae) {
//                MyRangeMIDlet.SCREENS[MyRangeMIDlet.BUSINESS_MEET_SCREEN].run(backToThisCommand);
//            }
//        }));

        // Add show messages choise button
        String numNewMsgs = "";
        int num = Messages.numNewInboxMsg();
        if (num > 0) numNewMsgs = Integer.toString(num);
        f.addComponent(new MenuButton(new Command(MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.MESSAGES_SCREEN]) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.MESSAGES_SCREEN].run(backToThisCommand);
            }
        }, new SignalLabel(numNewMsgs)));

        // Add company list
        f.addComponent(new MenuButton(new Command(MyRangeMIDlet.SCREENS[MyRangeMIDlet.COMPANY_LIST_SCREEN].getName()) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.COMPANY_LIST_SCREEN].run(backToThisCommand);
            }
        }, new MinorLabel("(" + String.valueOf(Companies.myCompanies().length) + ")")));

        // Add show users search button
        f.addComponent(new MenuButton(new Command(MyRangeMIDlet.SCREENS[MyRangeMIDlet.SEARCH_USERS_SCREEN].getName()) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.SEARCH_USERS_SCREEN].run(backToThisCommand);
            }
        }, new MinorLabel("(рядом " +  Integer.toString(MeetUsers.numNearUsers()) + " из " + SearchUsers.getIds().size() +")")));

        // Add show settings button
        f.addComponent(new MenuButton(new Command(MyRangeMIDlet.SCREENS[MyRangeMIDlet.SETTINGS_SCREEN].getName()) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.SETTINGS_SCREEN].run(backToThisCommand);
            }
        }));

        // Add exit button
        MenuButton mbe =  new MenuButton(MyRangeMIDlet.staticMidlet.askExitCommand);
        mbe.setBottom();
        f.addComponent(mbe);


    }
	
}
