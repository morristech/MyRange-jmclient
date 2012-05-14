/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */
package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import java.util.Calendar;
import java.util.Date;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.soap.BusinessMeetInfoType;
import ru.myrange.stuff.xlwuit.Spacer;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.Users;
import ru.myrange.stuff.xlwuit.Hyperlink;
import ru.myrange.stuff.xlwuit.UserProfileButton;

/**
 * Company info screen
 *
 * @author Yegorov Nickolay
 */
public class BusinessMeetScreen extends Screen {

    private BusinessMeetInfoType c;

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.BUSINESS_MEET_VIEW_SCREEN];
    }

    /**
     * Overload run method
     *
     * @param backCommand - back to previous screen command
     * @param c - event in to show
     */
    public final void run(final Command backCommand, final BusinessMeetInfoType evt) {
        this.c = evt;
        createAll(backCommand);
        show();
    }

    public final void run(final BusinessMeetInfoType evt) {
        this.c = evt;
        createAll(backCommand);
        show();
    }

    protected void execute(final Form f) {
        if (c == null) {
            return;
        }

        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Container c1 = new Container(new BorderLayout());
        Container c2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        c1.addComponent(BorderLayout.NORTH, c2);
        f.addComponent(c1);

        Command cmd = new Command("профиль партнера") {

                            public void actionPerformed(ActionEvent ae) {
                                Users.showUserProfile(c.getBusinessMeetId(), backToThisCommand);
                            }
                        };

        c2.addComponent(new UserProfileButton(Users.getUser(c.getPartnerId()), cmd));

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(new Date(c.getStartTime().longValue()));

        String headerText = calStart.get(Calendar.DAY_OF_MONTH)+ " " + StringConsts.S_MONTHS[calStart.get(Calendar.MONTH)] +
                " " + calStart.get(Calendar.HOUR_OF_DAY) + ":" + calStart.get(Calendar.MINUTE)
                + "\n" + c.getPlace();

        TextComponent bl = new TextComponent(headerText);
        bl.setUIID("HeaderText");
        c2.addComponent(bl);
        //c2.addComponent(new Spacer(Spacer.CENTER));

        TextComponent description = new TextComponent(c.getInfo());
        c2.addComponent(description);

        cmd = new Command("Отправить сообщение") {

            public void actionPerformed(ActionEvent ae) {
                final Integer userId = c.getPartnerId();
                //TODO!
            }
        };

        c2.addComponent(new Hyperlink(cmd));

        

        c2.addComponent(new Spacer(Spacer.BOTTOM));


        f.revalidate();


    }
}
