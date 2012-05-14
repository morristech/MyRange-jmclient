/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */
package ru.myrange.screens;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import ru.myrange.MyRangeMIDlet;
import java.util.Enumeration;
import java.util.Vector;
import ru.myrange.bluetooth.Bluetooth;
import ru.myrange.bluetooth.BluetoothDevice;
import ru.myrange.soap.Message;
import ru.myrange.stuff.StringConsts;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.Companies;
import ru.myrange.stuff.Messages;
import ru.myrange.stuff.MyRoutines;
import ru.myrange.stuff.StaticActions;
import ru.myrange.stuff.Users;
import ru.myrange.stuff.xlwuit.HeaderText;
import ru.myrange.stuff.xlwuit.MenuButton;
import ru.myrange.stuff.xlwuit.MinorLabel;
import ru.myrange.stuff.xlwuit.ProfileLabel;
import ru.myrange.stuff.xlwuit.TextComponent;

/**
 * User profile screen
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class UserProfileScreen extends Screen {

    /** Best size of an userpic image */
    public static int bestUserpicSize = 144;
    /** Best size of an image on the button */
    public static int bestImageButtonSize = 60;
    private UserInfo profile;
//    private boolean isFavourite = false;
//    private boolean isSignal = false;

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.USER_PROFILE_SCREEN];
    }

    /**
     * Overload run method
     *
     * @param backCommand - back to previous screen command
     * @param profile - user profile to show
     */
    public final void run(final Command backCommand, final UserInfo profile) {
        this.profile = profile;
        createAll(backCommand);
        show();
    }

    protected void execute(final Form f) {
        if (profile == null) {
            return;
        }

        // Set best image sizes
        bestUserpicSize = 64;//((Display.getInstance().getDisplayWidth()
                //< Display.getInstance().getDisplayHeight()) ? Display.getInstance().getDisplayWidth()
                //: Display.getInstance().getDisplayHeight()) * 3 / 5;
        bestImageButtonSize = (Display.getInstance().getDisplayWidth() - StringConsts.S_PIC.length()*StringConsts.CH_SMALL_MAW_WIDTH)/4 - 2;

        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        HeaderText ht = new HeaderText(profile.getFullName());
        ht.setFocusable(true);
        ht.setNextFocusUp(ht);
        f.addComponent(ht);


        // Add picture
        Label avatar = new Label();
        if (profile.getPic() != null) {
            avatar.setIcon((profile.getPic().scaled(bestUserpicSize, bestUserpicSize)));
        } else {
            avatar.setIcon(Users.getNullUserpic(bestUserpicSize, bestUserpicSize));
        }

        // Add "favourite" and "signal" buttons

//        final Image favouriteEnableImage = MyRangeMIDlet.res.getImage("star-le").scaled(bestImageButtonSize, bestImageButtonSize);
//        final Image favouriteDisableImage = MyRangeMIDlet.res.getImage("star-ld").scaled(bestImageButtonSize, bestImageButtonSize);
//
        // "favourite" button
//        final ImageButton favouriteButton = new ImageButton("", favouriteDisableImage);
//        if (Favourites.isFavourite(profile.getUserId())) {
//            isFavourite = true;
//            favouriteButton.setIcon(favouriteEnableImage);
//            favouriteButton.setRolloverIcon(favouriteEnableImage);
//            favouriteButton.setPressedIcon(favouriteEnableImage);
//        } else {
//            isFavourite = false;
//        }
//        favouriteButton.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent evt) {
//                (new Thread() {
//
//                    public void run() {
//                        Dialog progress = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);
//                        try {
//                            Favourites.changeUser(profile.getUserId(), !isFavourite);
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                            MyRangeMIDlet.showFailAlert("Exception", ex.toString());
//                        }
//                        isFavourite = !isFavourite;
//                        if (isFavourite) {
//                            favouriteButton.setIcon(favouriteEnableImage);
//                            favouriteButton.setRolloverIcon(favouriteEnableImage);
//                            favouriteButton.setPressedIcon(favouriteEnableImage);
//                        } else {
//                            favouriteButton.setIcon(favouriteDisableImage);
//                            favouriteButton.setRolloverIcon(favouriteDisableImage);
//                            favouriteButton.setPressedIcon(favouriteDisableImage);
//                        }
//                        progress.dispose();
//                    }
//                }).start();
//            }
//        });

        // "signal" button

//        final Image signalEnableImage = MyRangeMIDlet.res.getImage("bell-le").scaled(bestImageButtonSize, bestImageButtonSize);
//        final Image signalDisableImage = MyRangeMIDlet.res.getImage("bell-ld").scaled(bestImageButtonSize, bestImageButtonSize);
//        final ImageButton signalButton = new ImageButton("", signalDisableImage);
//        if (Signals.isSignal(profile.getUserId())) {
//            isSignal = true;
//            signalButton.setIcon(signalEnableImage);
//            signalButton.setRolloverIcon(signalEnableImage);
//            signalButton.setPressedIcon(signalEnableImage);
//        } else {
//            isSignal = false;
//        }
//        signalButton.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent evt) {
//                (new Thread() {
//
//                    public void run() {
//                        Dialog progress = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);
//                        try {
//                            Signals.changeUser(profile.getUserId(), !isSignal);
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                            MyRangeMIDlet.showFailAlert("Exception", ex.toString());
//                        }
//                        isSignal = !isSignal;
//                        if (isSignal) {
//                            signalButton.setIcon(signalEnableImage);
//                            signalButton.setRolloverIcon(signalEnableImage);
//                            signalButton.setPressedIcon(signalEnableImage);
//                        } else {
//                            signalButton.setIcon(signalDisableImage);
//                            signalButton.setRolloverIcon(signalDisableImage);
//                            signalButton.setPressedIcon(signalDisableImage);
//                        }
//                        progress.dispose();
//                    }
//                }).start();
//            }
//        });

        Container c1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        c1.addComponent(avatar);
        //c1.addComponent(favouriteButton);
        //c1.addComponent(signalButton);
        f.addComponent(c1);

        // Add online status
        Container onlineCnt = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Label onlinePic = new Label();
        TextComponent onlineText = new TextComponent("");
        //onlineText.setTickerEnabled(true);
        if (profile.getOnlineStatus().equals(UserInfo.ONLINE_STATUS_ONLINE)) {
            onlinePic.setIcon(MyRangeMIDlet.res.getImage("online-s"));
            onlineText.setText(StringConsts.S_ONLINE);
        } else if (profile.getOnlineStatus().equals(UserInfo.ONLINE_STATUS_OFFLINE)) {
            onlinePic.setIcon(MyRangeMIDlet.res.getImage("offline-s"));
            onlineText.setText(StringConsts.S_OFFLINE + MyRoutines.timeToString(profile.getLastOnlineTime().longValue()));

        } else if (profile.getOnlineStatus().equals(UserInfo.ONLINE_STATUS_NEVER)) {
            onlinePic.setIcon(MyRangeMIDlet.res.getImage("never-s"));
            onlineText.setText(StringConsts.S_DONT_USE_MYRANGE);
        }
        onlineCnt.addComponent(onlinePic);
        onlineCnt.addComponent(onlineText);
        f.addComponent(onlineCnt);

        // Add last meet time
        Container lastMeetCnt = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Label lastMeetPic = new Label();
        TextComponent lastMeetTime = new TextComponent("");
        lastMeetTime.setUIID("BlueLabel");
        lastMeetTime.setFocusable(false);
        lastMeetTime.getStyle().setBgTransparency(0);
//        if (profile.getLastSeenTime() > 0) {
//            lastMeetPic.setIcon(MyRangeMIDlet.res.getImage("bell-se"));
//            String seenTime = MyRoutines.timeToString(profile.getLastSeenTime());
//            if (profile.isNear()) {
//                seenTime = StringConsts.S_IS_NEAR_ME_NOW;
//            }
//            lastMeetTime.setText((seenTime.length() > 0) ? seenTime : " ");
//        } else {
//            lastMeetPic.setIcon(MyRangeMIDlet.res.getImage("bell-sd"));
//            lastMeetTime.setText(StringConsts.S_NEVER_MEET);
//        }
        lastMeetCnt.addComponent(lastMeetPic);
        lastMeetCnt.addComponent(lastMeetTime);
        f.addComponent(lastMeetCnt);
//        f.addComponent(createPair(StringConsts.S_NAME, name, largestW));
//        f.addComponent(createPair(StringConsts.S_PERSONAL_STATUS, textStatus, largestW));
//        f.addComponent(createPair(StringConsts.S_RESIDENTCITY, residentCity, largestW));
//#if FP2009 == "true"
//#         // Add region and fp2009-nomination
//#         final TextComponent region = new TextComponent("");
//#         final TextComponent fp2009Nomination = new TextComponent("");
//#         for (int i=0; i<profile.getCustomParams().size(); i++) {
//#            final CustomParam p = (CustomParam) profile.getCustomParams().elementAt(i);
//#            if (p.getAttribute().equals(CustomParam.ATTRIBUTE_REGION)) {
//#                region.setText(p.getValue());
//#                f.addComponent(createPair(StringConsts.S_REGION, region, largestW));
//#            } else if (p.getAttribute().equals(CustomParam.ATTRIBUTE_FP2009_NOMINATION)) {
//#                fp2009Nomination.setText(p.getValue());
//#                f.addComponent(createPair(StringConsts.S_FP2009_NOMINATION, fp2009Nomination, largestW));
//#            }
//#         }
//#endif
//        f.addComponent(createPair(StringConsts.S_EDUCATION, education, largestW));
//        f.addComponent(largest);
//        f.addComponent(createPair(StringConsts.S_GENDER, gender, largestW));

//        if (profile.getBirthday().longValue() != 0) {  // If user has set birthday
//            Calendar c = Calendar.getInstance();
//            c.setTime(new Date(profile.getBirthday().longValue()));
//            final TextComponent birthday = new TextComponent(c.get(Calendar.DAY_OF_MONTH)
//                    + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR));
//            f.addComponent(createPair(StringConsts.S_BIRTHDAY, birthday, largestW));
//        }
//

          /*
           * Company section
           */
          Button b = new Button(new Command(Companies.getCompany(profile.getCompId()).getName()) {

                    public void actionPerformed(ActionEvent ae) {
                        StaticActions.showWaitDialog();
                        CompanyProfileScreen cps =
                                        (CompanyProfileScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.COMPANY_PROFILE_SCREEN];
                        System.out.println("ok");
                        cps.run(backToThisCommand, profile.getCompId());
                    }
                });
          b.setUIID("Hyperlink");

          f.addComponent(createPair(StringConsts.S_WORK_PLACE, b));
          f.addComponent(createPair(StringConsts.S_WORK_POST, new MinorLabel(profile.getWorkPost())));
          /*
           * End of company section
           */


          // Contacts
        f.addComponent(new HeaderText(StringConsts.S_CONTACT_INFORMATION));
//        for (int i = 0; i < profile.getContact().size(); i++) {
//            final ContactType c = (ContactType) profile.getContact().elementAt(i);
//            if (c.getName().equals(ContactType.CONTACT_NAME_PHONE)) {
//                // Add phone call button
//                f.addComponent(new MenuButton(new Command(StringConsts.S_PHONECALL_TO + c.getUid()) {
//
//                    public void actionPerformed(ActionEvent evt) {
//                        MyRangeMIDlet.staticMidlet.callNumber(c.getUid());
//                    }
//                }));
//            } else {
//                f.addComponent(createPair(c.getName() + ":", new TextComponent(c.getUid()), largestW));
//            }
//        }

        // Links TODO
        /*
        f.addComponent(new Label(StringConsts.S_LINKS));
        for (int i=0; i<profile.getLink().size(); i++) {
        LinkType l = (LinkType) profile.getLink().elementAt(i);
        f.addComponent(createPair(l.getSite(), new Hyperlink(l.getUrl(), l.getUrl()), largestW));
        }
         */

        // Add send private message command
        f.addComponent(new MenuButton(new Command(StringConsts.S_SEND_PRIVATE_MSG) {

            public void actionPerformed(ActionEvent evt) {
                Vector messages = Messages.getMessagesFromUsers(profile.getUserId());
                if (messages.size() == 0) {
                    Message emptyMsg = new Message();
                    emptyMsg.setSenderId(profile.getUserId());
                    messages.addElement(emptyMsg);
                }
                ((UserMsgScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.USER_MSG_SCREEN]).run(backToThisCommand, messages);
            }
        }));

        // Add send SMS command
        if(profile.getCompId().byteValue()==-1)
                f.addComponent(new MenuButton(new Command(StringConsts.S_SEND_SMS) {

                    public void actionPerformed(ActionEvent evt) {
                        ((SmsMsgScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.SMS_MSG_SCREEN]).run(backToThisCommand, profile.getUserId(), profile.getFullName());
                    }
                }));



        if (profile.getHavePhoneNumber().booleanValue()) {
     
            // add Call Button
            f.addComponent(new MenuButton(new Command(StringConsts.S_PHONECALL_TO) {

                public void actionPerformed(ActionEvent evt) {
                    try{
                        StaticActions.callUser(profile.getUserId());
                    }catch(Exception e){
                         MyRangeMIDlet.showFailAlert("Звонок невозможен", StringConsts.S_PHONE_UNREGISTRED_ALERT);

                    }
                }
            }));

        }

        // Add send bluetooth message command
        if (profile.isNear()) {
            f.addComponent(new MenuButton(new Command(StringConsts.S_SEND_BT_MSG) {

                public void actionPerformed(ActionEvent evt) {
                    // Find the device with the same btAddress
                    for (Enumeration e = MyRangeMIDlet.bluetooth.deviceVec.elements(); e.hasMoreElements();) {
                        BluetoothDevice selected = (BluetoothDevice) e.nextElement();
                        if (selected.getBluetoothMac().equals(profile.getBtAddress())) {
                            Bluetooth.searchServicesThread = new Bluetooth.SearchServicesThread(MyRangeMIDlet.bluetooth, selected.getRemoteDevice());
                            Bluetooth.searchServicesThread.start();
                            // show waiting dialog
                            final Command searchCancelCommand = new Command(StringConsts.S_CANCEL) {

                                public void actionPerformed(ActionEvent ae) {
                                    if (Bluetooth.searchServicesThread != null) {
                                        Bluetooth.searchServicesThread.cancel();
                                    }
                                    Bluetooth.searchServicesProgress.dispose();
                                }
                            };
                            Bluetooth.searchServicesProgress = MyRangeMIDlet.showAlert(
                                    StringConsts.S_SEARCH_SERV, StringConsts.S_WAIT, Dialog.TYPE_INFO,
                                    new Command[]{searchCancelCommand}, true);

                            return;
                        }
                    }

                    // Can't find the device with the same btAddress
                    MyRangeMIDlet.showFailAlert("Fail", StringConsts.S_NO_DEVICE_FAIL_ALERT);
                }
            }));
        }

        //add user's goals
        f.addComponent(new ProfileLabel(StringConsts.S_PERSONAL_GOALS));
        final TextComponent goals = new TextComponent(profile.getAbout());
        goals.setEditable(false);
        goals.setFocusable(true);
        goals.setNextFocusDown(goals);
        f.addComponent(goals);

    }
}
