/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */
package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.Users;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.soap.CompInfoType;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.Companies;
import ru.myrange.stuff.SearchUsers;
import ru.myrange.stuff.StaticActions;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.xlwuit.HeaderText;
import ru.myrange.stuff.xlwuit.MenuButton;
import ru.myrange.stuff.xlwuit.ProfileLabel;
import ru.myrange.stuff.xlwuit.UserProfileButton;

/**
 * Company info screen
 *
 * @author Yegorov Nickolay
 */
public class CompanyProfileScreen extends Screen {

    private CompInfoType c;
    private final static int bestPicSize = 140;

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.COMPANY_PROFILE_SCREEN];
    }

    /**
     * Overload run method
     *
     * @param backCommand - back to previous screen command
     * @param c - event in to show
     */
    public final void run(final Command backCommand, final CompInfoType evt) {
        this.c = evt;
        createAll(backCommand);
        show();
    }

    public final void run(final CompInfoType evt) {
        this.c = evt;
        //createAll(backCommand);
        show();
    }

    protected void execute(final Form f) {
        if (c == null) {
            return;
        }

        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Label pic = new Label("");

        Container c1 = new Container(new BorderLayout());
        Container c2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        c1.addComponent(BorderLayout.NORTH, c2);
        f.addComponent(c1);

        c2.addComponent(pic);

        //add company logo
        Image img = c.getLogo();
        //Display.getInstance().getDisplayWidth() / 2;
        if (img == null) {
            img = CompInfoType.getNullLogo().scaled(bestPicSize, bestPicSize);
        } else {
            img = img.scaled(bestPicSize, bestPicSize);
        }
        pic.setIcon(img);

        c2.addComponent(new HeaderText(c.getName()));

        c2.addComponent(new TextComponent(c.getIndustry()));

        TextComponent description = new TextComponent(c.getAbout());
        description.setFocusable(true);
        description.setNextFocusUp(description);
        description.setGrowByContent(true);
        description.setSingleLineTextArea(false);
        c2.addComponent(description);



        f.revalidate();
        int a = 0;
        while(!SearchUsers.isReady()){
            a++;
        }


        /*
         * Users of company section
         */
        UserInfo predstavitel = Users.getUser(c.getUserId());

        if(predstavitel != null)
            try{

                if (predstavitel.getHavePhoneNumber().booleanValue()) {
                    MenuButton bnConferenceCall = new MenuButton(new Command(StringConsts.S_PHONECALL_TO) {

                        public void actionPerformed(ActionEvent ae) {
                            try {
                                StaticActions.callCompany(c.getCompId());
                            } catch (Exception e) {
                                MyRangeMIDlet.showFailAlert("Звонок невозможен", StringConsts.S_PHONE_UNREGISTRED_ALERT);
                            }
                        }
                    });

                    bnConferenceCall.setNextFocusUp(bnConferenceCall);
                    c2.addComponent(bnConferenceCall);
                }
        }catch(Exception ignored){}

        c2.addComponent(new ProfileLabel(StringConsts.S_USER_OF_COMPANY));

        Vector ids = SearchUsers.getIds(); //if(ids == null)System.err.println("OAOAOAOAOA!!");
        Vector result = Users.getUsers(ids);

        UserProfileButton upb = null;

        for (int i = 0; i < result.size(); i++) {
            UserInfo next = (UserInfo) result.elementAt(i);

            if (next.getCompId().longValue() == c.getCompId().longValue()) {

                try {
                    next = Users.getUserWithPic(next.getUserId());
                } catch (Exception ex) {
                }

                final UserInfo nextUser = next;

                upb = new UserProfileButton(nextUser, new Command(StringConsts.S_PROFILE) {

                    public void actionPerformed(ActionEvent ae) {
                        // Show user profile
                        Users.showUserProfile(nextUser.getUserId(), backCommand);
                    }
                });

                if(i + 1 == result.size()){
                    upb.setBottom();
                    System.out.println("YEEEES");
                }

                f.addComponent(upb);

                f.revalidate();
            }

        }

        if(upb != null) upb.setBottom();
        /*
         * End users of company
         */

        f.revalidate();


    }

    void run(Command backCommand, Integer compId) {
        run(backCommand, Companies.getCompany(compId, new Integer(bestPicSize) ));
    }
}
