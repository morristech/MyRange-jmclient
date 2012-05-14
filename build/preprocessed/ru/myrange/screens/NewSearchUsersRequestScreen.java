/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.ButtonGroup;
import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.RadioButton;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.MyConferences;
import ru.myrange.stuff.SearchUsers;
import ru.myrange.stuff.StringConsts;

/**
 * Class to show a new search users request screen
 *
 * @author Oleg Ponfilenok
 */
public class NewSearchUsersRequestScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.NEW_SEARCH_USERS_REQUEST_SCREEN];
    }

    protected void execute(final Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        // Search conf choise
        f.addComponent(new Label(StringConsts.S_USER_SEARCH_CONF));
        RadioButton rb;
        final ButtonGroup confGroup = new ButtonGroup();
        rb = new RadioButton(StringConsts.S_USER_SEARCH_CONF_GLOBAL);
        confGroup.add(rb);
        confGroup.setSelected(0);
        f.addComponent(rb);
        if (MyConferences.getActualConf() != null) {
            rb = new RadioButton(StringConsts.S_USER_SEARCH_CONF_LOCAL + MyConferences.getActualConf().getName());
            confGroup.add(rb);
            confGroup.setSelected(1);
            f.addComponent(rb);
        }
        
        f.addCommand(new Command(StringConsts.S_USER_SEARCH_START) {
            public void actionPerformed(ActionEvent ae) {
                (new Thread() {
                    public void run(){
                        final Dialog pleaseWait = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);
                        // Set search conf
                        Integer confId = null;
                        switch (confGroup.getSelectedIndex()) {
                            case 0 :
                                confId = null;
                                break;
                            case 1 :
                                confId = MyConferences.getActualConf().getConfId();
                                break;
                            default :
                                break;
                        }
                        try {
                            // Start search
                            SearchUsers.newSearch(confId);
                            // Show search result
                            pleaseWait.dispose();
                            // back to UsersChoiseScreen
                            final Command backToUserChoiseScreenCommand = new Command(StringConsts.S_BACK){
                                public void actionPerformed(ActionEvent ae) {
                                    MyRangeMIDlet.SCREENS[MyRangeMIDlet.USERS_CHOICSE_SCREEN].run(MyRangeMIDlet.SCREENS[MyRangeMIDlet.USERS_CHOICSE_SCREEN].getBackCommand());
                                }
                            };
                            MyRangeMIDlet.SCREENS[MyRangeMIDlet.SEARCH_USERS_SCREEN].run(backToUserChoiseScreenCommand);
                        } catch (Exception ex) {
                            pleaseWait.dispose();
                            ex.printStackTrace();
                            MyRangeMIDlet.showFailAlert("Exception", ex.toString());
                        }
                    }
                }).start();
            }
        });

    }

}
