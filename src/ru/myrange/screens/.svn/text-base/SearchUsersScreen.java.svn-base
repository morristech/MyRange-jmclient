/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.DataChangedListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import java.util.Enumeration;
import ru.myrange.MyRangeMIDlet;
import java.util.Vector;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.MeetUsers;
import ru.myrange.stuff.SearchUsers;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.Users;
import ru.myrange.stuff.xlwuit.MenuButton;
import ru.myrange.stuff.xlwuit.MinorLabel;
import ru.myrange.stuff.xlwuit.UserCellRenderer;
import ru.myrange.stuff.xlwuit.XList;

/**
 * Search users
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class SearchUsersScreen extends Screen {

    //private boolean onlyHavePic = false;
    //private boolean onlyIsOnline = false;
    private static boolean onlyNear = false;
    private String nameFilter = "";
    private boolean isNameFilterFieldFocused = false;
    private int numUsers = 0;

    public String getName() {
        String name = null;//SearchUsers.getTitle();
        if (name == null) name = MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.SEARCH_USERS_SCREEN];
        return name;
    }

    public int getNumUsers() {
        return numUsers;
    }

    protected void execute(final Form f) {
        Dialog dl = null;
        if(!SearchUsers.isReady())
            dl = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);

        // Show filter panel
//        final CheckBox havePicCheck = new CheckBox(MyRangeMIDlet.res.getImage("photo-s"));
//        havePicCheck.setSelected(onlyHavePic);
//        havePicCheck.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ae) {
//                if (havePicCheck.isSelected()) onlyHavePic = true;
//                else onlyHavePic = false;
//                run(getBackCommand());
//            }
//        });
//        final CheckBox onlineCheck = new CheckBox(MyRangeMIDlet.res.getImage("online-s"));
//        onlineCheck.setSelected(onlyIsOnline);
//        onlineCheck.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ae) {
//                if (onlineCheck.isSelected()) onlyIsOnline = true;
//                else onlyIsOnline = false;
//                run(getBackCommand());
//            }
//        });
        
                  
                        //final Dialog pleaseWait = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);
        //f.addComponent(new Spacer(Spacer.TOP));
                

        final TextField fname = new TextField(nameFilter);
        fname.addDataChangeListener(new DataChangedListener() {
            public void dataChanged(int arg0, int arg1) {
                if (arg1 >= 0) {
                    nameFilter = fname.getText();
                    run(getBackCommand());
                }
            }
        });
        fname.addFocusListener(new FocusListener() {
            public void focusGained(Component arg0) {
                isNameFilterFieldFocused = true;
            }
            public void focusLost(Component arg0) {
                isNameFilterFieldFocused = false;
            }
        });
        //ImageButton searchButton = new ImageButton("", MyRangeMIDlet.res.getImage("search-s"));

        /*
         * Top buttons
         */
            //container for top buttons
        Container cnt1 = new Container(new BoxLayout(BoxLayout.X_AXIS));

        //cnt1.setWidth(bWidth*2);
       

        // Add all users button
        MenuButton bnAll = new MenuButton(new Command(StringConsts.S_USER_SEARCH_GLOBAL_ALL_TITLE) {
            public void actionPerformed(ActionEvent ae) {
                onlyNear = false;
                run(getBackCommand());
            }
        }, new MinorLabel(Integer.toString(SearchUsers.getIds().size())));

        if(!onlyNear){
           bnAll.resetUIID("SelectedButton");
        }
        
        //bnAll.setWidth(bWidth);
        cnt1.addComponent(bnAll);

        // Add near users button
        MenuButton bnNear = new MenuButton(new Command(StringConsts.S_USERS_NEAR_HERE) {
            public void actionPerformed(ActionEvent ae) {                
                onlyNear = true;
                run(getBackCommand());

            }
        }, new MinorLabel(Integer.toString(MeetUsers.numNearUsers())));

        if(onlyNear){
            bnNear.resetUIID("SelectedButton");
        }
        //bnNear.setWidth(bWidth);
        cnt1.addComponent(bnNear);

        /*
         * End of top buttons
         */
       


        Container cnt2 = new Container(new BorderLayout());
        //cnt2.setNextFocusDown(cnt2);
        //cnt1.addComponent(havePicCheck);
        //cnt1.addComponent(onlineCheck);
        cnt2.addComponent(BorderLayout.NORTH, cnt1);
        cnt2.addComponent(BorderLayout.CENTER, createPair("поиск:", fname));
        //cnt2.addComponent(BorderLayout.EAST, searchButton);
        f.addComponent(cnt2);
        if (isNameFilterFieldFocused) {
            fname.requestFocus();
            fname.setCursorPosition(fname.getText().length());
        }

        // Show the last search result
        Vector ids = SearchUsers.getIds();
        Vector result = Users.getUsers(ids);
           
        Vector filteredResult = new Vector();
        for (Enumeration e = result.elements() ; e.hasMoreElements() ;) {
            UserInfo next = (UserInfo) e.nextElement();
            boolean toAdd = true;
            
            if(Users.getMyProfile().getCompId().intValue() == next.getUserId().intValue()) toAdd = false;

            if (nameFilter != null && nameFilter.length() > 0 &&
                    next.getFullName().toLowerCase().indexOf(nameFilter.toLowerCase()) == -1) toAdd = false;
            if(onlyNear && !next.isNear()) toAdd = false;

            if (toAdd) filteredResult.addElement(next);
        }

        final XList usersList = new XList(filteredResult);
        usersList.setListCellRenderer(new UserCellRenderer());

        final ActionListener defaultActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Show selected user's profile
                UserInfo selection = (UserInfo) usersList.getSelectedItem();
                final Integer userId = selection == null ? null : selection.getUserId();
                Users.showUserProfile(userId, backToThisCommand);
            }
        };

        usersList.addActionListener(defaultActionListener);
        f.addComponent(usersList);
        
        //f.addComponent(new Spacer(Spacer.BOTTOM));


        numUsers = usersList.size();
        
        f.setTitle(getNumUsers() + StringConsts.S_USERS);

     
            while(!SearchUsers.isReady()){
               try{
                    Thread.sleep(1000);
                }catch(InterruptedException i){}
            }
            if(dl != null)
                dl.dispose();
            //this.run(getBackCommand());
    }

}