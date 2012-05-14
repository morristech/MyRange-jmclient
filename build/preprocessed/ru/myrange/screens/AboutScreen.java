/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.layouts.BoxLayout;
import java.util.Calendar;
import java.util.Date;
import ru.myrange.stuff.StringConsts;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.Records;
import ru.myrange.soap.Scrobbler;
import ru.myrange.stuff.xlwuit.HeaderText;
import ru.myrange.stuff.xlwuit.MajorLabel;
import ru.myrange.stuff.xlwuit.MinorText;
import ru.myrange.stuff.xlwuit.TextComponent;

/**
 * About information , send user's feedback  and traffic screen
 *
 * @author Oleg Ponfilenok, Yegorov Nickoaly
 */
public class AboutScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.ABOUT_SCREEN];
    }

    protected void execute(final Form f) {

        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Label logoLabel = new Label(MyRangeMIDlet.logo);
        logoLabel.setAlignment(Label.CENTER);
        f.addComponent(logoLabel);
        f.addComponent(new HeaderText(StringConsts.S_ABOUT_INFO));
        f.addComponent(new MajorLabel(StringConsts.S_ABOUT_VERSION));
        MinorText mt = new MinorText(StringConsts.S_OUR_GOAL);
        mt.setFocusable(true);
        mt.setNextFocusUp(mt);
        f.addComponent(mt);
        TextComponent tc = new TextComponent(StringConsts.S_ABOUT_CONTACTS);
        tc.setFocusable(true);
        f.addComponent(tc);

        /*
         * Traffic
         */
        try {
            Scrobbler.dumpTrafficToRMS();

            int todayTraf = Records.recSettings.readIntData(Records.REC_TRAFFIC_TODAY);
            int totalTraf = Records.recSettings.readIntData(Records.REC_TRAFFIC_TOTAL);
            long days = (System.currentTimeMillis() - Records.recSettings.readLongData(Records.REC_TRAFFIC_TOTAL_SINCE))/86400000 + 1;

            Label trafficLabel = new Label(StringConsts.S_TRAFFIC_LABEL);
            trafficLabel.setFocusable(true);
            f.addComponent(trafficLabel);
            // Traffic of today
            f.addComponent(new Label(StringConsts.S_TRAFFIC_TODAY));
            f.addComponent(new TextComponent(Integer.toString(todayTraf) + StringConsts.S_TRAFFIC_UNIT));
            // Average traffic
            f.addComponent(new Label(StringConsts.S_TRAFFIC_AVERAGE));
            TextComponent t = new TextComponent(Long.toString(totalTraf/days) + StringConsts.S_TRAFFIC_UNIT_AVERAGE);
            t.setNextFocusUp(t);
            f.addComponent(t);
            // Total traffic
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(Records.recSettings.readLongData(Records.REC_TRAFFIC_TOTAL_SINCE)));
            f.addComponent(new Label(StringConsts.S_TRAFFIC_TOTAL +
                    (c.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + c.get(Calendar.DAY_OF_MONTH) : Integer.toString(c.get(Calendar.DAY_OF_MONTH))) + "." +
                    (c.get(Calendar.MONTH)+1 < 10 ? "0" + (c.get(Calendar.MONTH)+1) : Integer.toString(c.get(Calendar.DAY_OF_MONTH)+1)) + "." +
                    c.get(Calendar.YEAR) + ":"));

            TextComponent totalTraff = new TextComponent(Integer.toString(totalTraf) + StringConsts.S_TRAFFIC_UNIT);
            totalTraff.setNextFocusDown(totalTraff);
            totalTraff.setFocusable(true);
            f.addComponent(totalTraff);
        } catch (Exception e) {
            MyRangeMIDlet.showFailAlert("Exception", e.toString());
        }
        /*
         * End of traffic
         */



//        f.addComponent(new TextComponent(StringConsts.S_ABOUT_FEEDBACK));
//        final TextArea feedbackArea = new TextArea();
//        feedbackArea.setFocusable(true);
//        feedbackArea.setEditable(true);
//        feedbackArea.setNextFocusDown(feedbackArea);
//        f.addComponent(feedbackArea);
        //f.setScrollableY(true);
        f.revalidate();

//        f.addCommand(new Command(StringConsts.S_SEND_FEEDBACK) {
//            public void actionPerformed(ActionEvent ae) {
//                (new Thread() {
//                    public void run() {  // Send message to remote device
//                        String msg = feedbackArea.getText().trim();
//                        if (msg.length() < 1) {
//                            // No any text was entered
//                            MyRangeMIDlet.showInfoAlert(StringConsts.S_SEND_MSG_NO_TEXT);
//                            return;
//                        }
//
//                        final Dialog pleaseWait = MyRangeMIDlet.showWaitingAlert(StringConsts.S_SENDING);
//                        try {
//                            // Send feedback message to the actual conference owner
//                            Integer sendId = MyConferences.getFeedbackId();
//                            if (sendId != null) {
//                                StaticActions.sendPrivateMessage(sendId, StringConsts.S_FEEDBACK_SUBJECT, msg);
//                                // Feedback send OK
//                                pleaseWait.dispose();
//                                Command goToMainCommand = new Command(StringConsts.S_OK) {
//                                    public void actionPerformed(ActionEvent evt) {
//                                        if (Display.getInstance().getCurrent() instanceof Dialog)
//                                            ((Dialog) Display.getInstance().getCurrent()).dispose();
//                                        MyRangeMIDlet.SCREENS[MyRangeMIDlet.MAINMENU_SCREEN]
//                                                .run(MyRangeMIDlet.SCREENS[MyRangeMIDlet.MAINMENU_SCREEN].getBackCommand());
//                                    }
//                                };
//                                MyRangeMIDlet.showAlert(null, StringConsts.S_SEND_FEEDBACK_OK,
//                                    Dialog.TYPE_INFO, new Command[] {goToMainCommand}, false);
//                            } else {
//                                // Feedback send Fail
//                                pleaseWait.dispose();
//                                MyRangeMIDlet.showFailAlert(null, StringConsts.S_SEND_FEEDBACK_FAIL);
//                            }
//                        } catch( Exception e ){
//                            pleaseWait.dispose();
//                            MyRangeMIDlet.showFailAlert(StringConsts.S_SEND_MSG_FAIL_ALERT, "[Feedback] Exception: " + e.toString());
//                        }
//                    }
//                }).start();
//            }
//        });

    }

}