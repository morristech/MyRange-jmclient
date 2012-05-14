/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */
package ru.myrange.screens;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import java.util.Calendar;
import java.util.Date;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.soap.EventInfoType;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.Users;
import ru.myrange.stuff.xlwuit.Spacer;
import ru.myrange.stuff.xlwuit.TextComponent;

/**
 * Event info screen
 *
 * @author Yegorov Nickolay
 */
public class EventInfoScreen extends Screen {

    private EventInfoType evt;

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.EVENT_INFO_SCREEN];
    }

    /**
     * Overload run method
     *
     * @param backCommand - back to previous screen command
     * @param evt - event in to show
     */
    public final void run(final Command backCommand, final EventInfoType evt) {
        this.evt = evt;
        createAll(backCommand);
        show();
    }

    protected void execute(final Form f) {
        if (evt == null) {
            return;
        }

        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Container c1 = new Container(new BorderLayout());
        Container c2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        c1.addComponent(BorderLayout.NORTH, c2);
        f.addComponent(c1);

        TextComponent bl = new TextComponent(evt.getSection());
        bl.setUIID("HeaderText");
        c2.addComponent(bl);

        String headerText = (evt.getPlace().length() > 0) ? evt.getPlace() : " ";

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(new Date(evt.getStartTime().longValue()));
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(new Date(evt.getEndTime().longValue()));

        headerText = calStart.get(Calendar.HOUR_OF_DAY) + ":" + calStart.get(Calendar.MINUTE) + " - "
                + calEnd.get(Calendar.HOUR_OF_DAY) + ":" + calEnd.get(Calendar.MINUTE) + " "
                + headerText;

        TextComponent mnl = new TextComponent(headerText);
        mnl.setUIID("MinorText");
        c2.addComponent(mnl);
        //c2.addComponent(new Spacer(Spacer.CENTER));

        Button mjl1 = new Button(new Command(MyRangeMIDlet.SCREENS[MyRangeMIDlet.USER_PROFILE_SCREEN].getName()) {

            public void actionPerformed(ActionEvent ae) {
                final Integer userId = evt.getSpeakerUserId();
                if (userId != null && userId.intValue() > 0) {
                    Users.showUserProfile(userId, backToThisCommand);
                }
            }
        });
        mjl1.setText((evt.getSpeakerName().length() > 0) ? evt.getSpeakerName() : "");
        mjl1.setUIID("Hyperlink");
        c2.addComponent(mjl1);

        TextComponent mnl1 = new TextComponent((evt.getSpeakerInfo().length() > 0) ? evt.getSpeakerInfo() : "");
        c2.addComponent(mnl1);

        //c2.addComponent(new Spacer(Spacer.CENTER));

        TextComponent bl2 = new TextComponent("Подробнее на");
        bl2.setUIID("MajorText");
        c2.addComponent(bl2);
        final String url = evt.getInfo();
        Button bl23 = new Button(new Command(StringConsts.S_GO_TO_EVENT_URL) {

            public void actionPerformed(ActionEvent ae) {
                if (url.length() > 0) {
                    (new Thread() {

                        public void run() {
                            try {
                                if (MyRangeMIDlet.midlet.platformRequest(url)) {  // Returns true if the MIDlet suite MUST first exit before start web browser.
                                    MyRangeMIDlet.exitApp();
                                } else {
                                    MyRangeMIDlet.exitApp();     // Exit po lubomu :)
                                }
                            } catch (Exception e) {
                                MyRangeMIDlet.showFailAlert("Exception", e.toString());
                            }
                        }
                    }).start();
                }

            }
        });
        bl23.setText((url.length() > 0) ? evt.getInfo() : "");
        bl23.setUIID("Hyperlink");
        c2.addComponent(bl23);

        c2.addComponent(new Spacer(Spacer.BOTTOM));


        f.revalidate();


    }
}
