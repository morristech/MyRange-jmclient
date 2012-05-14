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
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.MeetUsers;
import ru.myrange.stuff.SearchUsers;
import ru.myrange.stuff.xlwuit.MenuButton;
import ru.myrange.stuff.xlwuit.MinorLabel;

/**
 * The choise of lists of users
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class UsersChoiseScreen extends Screen {


    public String getName() {
        String name = MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.USERS_CHOICSE_SCREEN];
        return name;
    }

    /**
     * Choise list of users
	 * <ol>
	 * <li>Search</li>
	 * <li>Favourites</li>
	 * <li>Near users</li>
     * <li>Near devices</li>
	 * </ol>
     */
    protected void execute(final Form f) {

        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        /*
        String[] strUsersChoise = new String[StringConsts.S_USERS_MENU.length];
        System.arraycopy(StringConsts.S_USERS_MENU, 0, strUsersChoise, 0, StringConsts.S_USERS_MENU.length);
        strUsersChoise[StringConsts.CMD_M_NEAR_USERS] = Integer.toString(MeetUsers.numNearUsers()) +
                " (" + Integer.toString(MeetUsers.getIds().size()) + ")" + StringConsts.S_USERS_FOUND;
        */

        // Add all users button
        String searchResultTitle = SearchUsers.getTitle();
        if (searchResultTitle == null || searchResultTitle.length()<1)
            searchResultTitle = StringConsts.S_ALL_USERS_ON_CONFERENCE;
        String searchNum = "";
        if (((SearchUsersScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.SEARCH_USERS_SCREEN]).getNumUsers() > 0)
            searchNum = Integer.toString(((SearchUsersScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.SEARCH_USERS_SCREEN]).getNumUsers());
        f.addComponent(new MenuButton(new Command(searchResultTitle) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.SEARCH_USERS_SCREEN].run(backToThisCommand);
            }
        }, new MinorLabel(searchNum)));

        // Add show favourites users button
//        f.addComponent(new MenuButton(new Command(StringConsts.S_USERS_MENU[StringConsts.CMD_M_FAVOURITES]) {
//            public void actionPerformed(ActionEvent ae) {
//                MyRangeMIDlet.SCREENS[MyRangeMIDlet.FAVOURITE_USERS_SCREEN].run(backToThisCommand);
//            }
//        }, new MinorLabel(Integer.toString(Favourites.getIds().size()))));

        // Add show found users button
        f.addComponent(new MenuButton(new Command(StringConsts.S_USERS_MENU[StringConsts.CMD_M_NEAR_USERS]) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.MEET_USERS_SCREEN].run(backToThisCommand);
            }
        }, new MinorLabel(StringConsts.S_USERS_NEAR + Integer.toString(MeetUsers.numNearUsers()))));

        /*
        // Add new search button
        f.addComponent(new MenuButton(new Command(strUsersChoise[StringConsts.CMD_M_NEW_SEARCH]) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.NEW_SEARCH_USERS_REQUEST_SCREEN].run(backToThisCommand);
            }
        }));
        */
    }


}
