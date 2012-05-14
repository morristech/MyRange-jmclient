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
import ru.myrange.stuff.StringConsts;
import ru.myrange.datastore.Records;
import ru.myrange.stuff.Users;
import ru.myrange.stuff.xlwuit.MenuButton;

/**
 * Class to show the settings menu form
 * and work with it.
 */
public class SettingsScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.SETTINGS_SCREEN];
    }

    protected void execute(final Form f) {
        try {
            f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

            // Choise scan timeout and merge interval
            //final List scanChoiceList = new List(StringConsts.S_SET_SCAN);
            // Detect the current scan time out
//            long scanTimeOut = Records.recSettings.readLongData(Records.REC_SCAN_TO);
//            int i = 0;
//            while (i < StringConsts.L_SET_SCANTO.length) {
//                if (scanTimeOut == StringConsts.L_SET_SCANTO[i]) {
//                    scanChoiceList.setSelectedIndex(i);
//                    break;
//                }
//                i++;
//            }
//            if (i >= StringConsts.L_SET_SCANTO.length) {
                // add custom value
//                String item = (new Long(scanTimeOut)).toString();
//                scanChoiceList.addItem(item);
//                scanChoiceList.setSelectedItem(item);
//            }
//            f.addComponent(new Label(StringConsts.S_SET_SCAN_TITLE));
//            ComboBox scanChoiceBox = new ComboBox(scanChoiceList.getModel());
//            f.addComponent(scanChoiceBox);

            // Bluetooth address
//            f.addComponent(new Label(StringConsts.S_SET_BTADDRESS));
//            f.addComponent(new TextComponent(MyRangeMIDlet.bluetooth.getBtAddress()));

            // Login
//            String loginLabel = "";
//            if (EmailAddress.isEmailAddress(MyRangeMIDlet.login)) {
//                loginLabel = StringConsts.S_SET_EMAIL;
//            } else if (PhoneNumber.isPhoneNumber(MyRangeMIDlet.login)) {
//                loginLabel = StringConsts.S_SET_PHONE;
//            }
//            f.addComponent(new Label(loginLabel));
//            f.addComponent(new TextComponent(MyRangeMIDlet.login));

            /*
            // Choise internet connection
            f.addComponent(new Label(StringConsts.S_SET_INET_CONNECTION_TITLE));
            RadioButton inetConnectionRB;
            final ButtonGroup inetConnectionGroup = new ButtonGroup();
            inetConnectionRB = new RadioButton(StringConsts.S_SET_INET_CONNECTION[0]);
            inetConnectionGroup.add(inetConnectionRB);
            f.addComponent(inetConnectionRB);
            inetConnectionRB = new RadioButton(StringConsts.S_SET_INET_CONNECTION[1]);
            inetConnectionGroup.add(inetConnectionRB);
            f.addComponent(inetConnectionRB);
            if (MyRangeMIDlet.allowConnection) inetConnectionGroup.setSelected(0);
            else inetConnectionGroup.setSelected(1);
             */

            // Choise download pictures
            f.addComponent(new Label(StringConsts.S_SET_DOWNLOAD_PIC_TITLE));
            RadioButton downloadPicRB;
            final ButtonGroup downloadPicGroup = new ButtonGroup();
            downloadPicRB = new RadioButton(StringConsts.S_SET_DOWNLOAD_PIC[0]);
            downloadPicGroup.add(downloadPicRB);
            downloadPicRB.setNextFocusUp(downloadPicRB);
            f.addComponent(downloadPicRB);
            downloadPicRB = new RadioButton(StringConsts.S_SET_DOWNLOAD_PIC[1]);
            downloadPicGroup.add(downloadPicRB);
            f.addComponent(downloadPicRB);
            if (Records.recSettings.readIntData(Records.REC_DOWNLOAD_PIC) == StringConsts.I_DOWNLOAD_PIC_YES) {
                downloadPicGroup.setSelected(0);
            } else if (Records.recSettings.readIntData(Records.REC_DOWNLOAD_PIC) == StringConsts.I_DOWNLOAD_PIC_NO) {
                downloadPicGroup.setSelected(1);
            }

            // Add show the About button
            f.addComponent(new MenuButton(new Command(MyRangeMIDlet.SCREENS[MyRangeMIDlet.ABOUT_SCREEN].getName()) {

                public void actionPerformed(ActionEvent ae) {
                    MyRangeMIDlet.SCREENS[MyRangeMIDlet.ABOUT_SCREEN].run(backToThisCommand);
                }
            }));

            // Add show the Traffic button
            if(Users.isUser(new Integer(-1)))
            f.addComponent(new MenuButton(new Command(MyRangeMIDlet.SCREENS[MyRangeMIDlet.TRAFFIC_SCREEN].getName()) {

                public void actionPerformed(ActionEvent ae) {
                    MyRangeMIDlet.SCREENS[MyRangeMIDlet.TRAFFIC_SCREEN].run(backToThisCommand);
                }
            }));

            /*
            // Choise Bluetooth Discoverable
            discovChoice = new ChoiceGroup (MyConsts.S_SET_DISCOV_TITLE, Choice.EXCLUSIVE, MyConsts.S_SET_DISCOV, null);
            discovFlags[MyConsts.CMD_M_SET_DISCOV_GIAC] = true;	// Stat with active GIAC
            discovChoice.setSelectedFlags(discovFlags);
            append(discovChoice);
            // Set correct discovChoice flag
            for (int i=0; i<MyConsts.L_SET_MERGEINT.length; i++) {
            discovFlags[i] = false;
            }
            int mode = MyMIDlet.bluetooth.getDiscoverable();
            switch (mode) {
            case DiscoveryAgent.GIAC :
            discovFlags[MyConsts.CMD_M_SET_DISCOV_GIAC] = true;
            break;
            case DiscoveryAgent.LIAC :
            discovFlags[MyConsts.CMD_M_SET_DISCOV_LIAC] = true;
            break;
            case DiscoveryAgent.NOT_DISCOVERABLE :
            discovFlags[MyConsts.CMD_M_SET_DISCOV_NOT] = true;
            break;
            default :
            break;
            }
            discovChoice.setSelectedFlags(discovFlags);
             */

            f.addCommand(new Command(StringConsts.S_SAVE) {

                public void actionPerformed(ActionEvent ae) {
                    try {
                        /*
                        // Change Bluetooth Discoverable
                        int discovIndex = discovChoice.getSelectedIndex();
                        discovChoice.getSelectedFlags(discovFlags);
                        switch (discovIndex) {
                        case MyConsts.CMD_M_SET_DISCOV_GIAC :
                        MyMIDlet.bluetooth.setDiscoverable(DiscoveryAgent.GIAC);
                        break;
                        case MyConsts.CMD_M_SET_DISCOV_LIAC :
                        MyMIDlet.bluetooth.setDiscoverable(DiscoveryAgent.LIAC);
                        break;
                        case MyConsts.CMD_M_SET_DISCOV_NOT :
                        MyMIDlet.bluetooth.setDiscoverable(DiscoveryAgent.NOT_DISCOVERABLE);
                        break;
                        default :
                        break;
                        }
                         */

                        // Change scan timeout and merge interval
                        //int scanIndex = scanChoiceList.getSelectedIndex();
//                        long oldScanTimeOut = StringConsts.L_SCAN_TO;
//                        try {
//                            oldScanTimeOut = Records.recSettings.readLongData(Records.REC_SCAN_TO);
//                        } catch (Exception ignored) {
//                        }
//                        if (scanIndex < StringConsts.L_SET_SCANTO.length) {
//                            long newScanTimeOut = StringConsts.L_SET_SCANTO[scanIndex];
//                            long newMergeInt = StringConsts.L_SET_MERGEINT[scanIndex];
//                            if (newScanTimeOut != oldScanTimeOut) { // if scan timeout interval has changed
//                                // change the scan time out
//                                Records.recSettings.addLongData(newScanTimeOut, Records.REC_SCAN_TO);
//                                // change the merge interval
//                                Records.recSettings.addLongData(newMergeInt, Records.REC_MERGE_INTERVAL);
//                                MyRangeMIDlet.mergeIntervalVar = newMergeInt;	// ! Set the merge interval
//                            }
//                        }

                        /*
                        // Choise internet connection
                        switch (inetConnectionGroup.getSelectedIndex()) {
                        case 0 :
                        Records.recSettings.addIntData(StringConsts.I_INET_CONNECTION_ALWAYS, Records.REC_INET_CONNECTION);
                        MyRangeMIDlet.allowConnection = true;
                        break;
                        case 1 :
                        Records.recSettings.addIntData(StringConsts.I_INET_CONNECTION_ONDEMAND, Records.REC_INET_CONNECTION);
                        MyRangeMIDlet.allowConnection = false;
                        break;
                        default :
                        break;
                        }
                         */

                        // Choise download pictures
                        switch (downloadPicGroup.getSelectedIndex()) {
                            case 0:
                                Records.recSettings.addIntData(StringConsts.I_DOWNLOAD_PIC_YES, Records.REC_DOWNLOAD_PIC);
                                break;
                            case 1:
                                Records.recSettings.addIntData(StringConsts.I_DOWNLOAD_PIC_NO, Records.REC_DOWNLOAD_PIC);
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        MyRangeMIDlet.showInfoAlert("Error: " + e.toString());
                    }

                    /* CHANGE PHONE NUMBER */
                    /*
                    String oldPhone = "";
                    try {
                    oldPhone = Records.recSettings.readStringData(Records.REC_PHONE);
                    } catch (Exception ignored) {}
                    oldPhone = cutSeven(oldPhone);
                    String newPhone = phoneTF.getString().trim();
                    if (!oldPhone.equals(newPhone)) {
                    if (newPhone.length() != 10) {	// Validation phone number
                    MyRange.showInfoAlert(MyConsts.S_WRONG_PHONE, d);
                    return;
                    }

                    // Validate phone number
                    Thread validateThread = new Thread() {
                    public void run(){
                    try {
                    // Validate phone number
                    String validationCode = MyAction.validatePhoneNumber(phone);
                    if (validationCode==null) throw new Exception("validationCode=null");
                    // Save phone and code in RMS for continue to enter the code after exit of application
                    Records.recSettings.addStringData(phone, Records.REC_VALIDATION_PHONE);
                    Records.recSettings.addStringData(validationCode, Records.REC_VALIDATION_CODE);     // Save code in RMS
                    // Show the form for entering the code
                    new CodeForm(d, false, false);
                    } catch (Exception e) {
                    MyRange.showInfoAlert("Error: " + e.toString(), d);
                    }
                    }
                    };
                    MyRange.showWaitingAlert(MyConsts.S_CONNECTING_TO_THE_SERVER, d);
                    validateThread.start();
                    }
                    else {
                    // Exit
                    MyRange.mainmenu.options.show();
                    }
                     */

                    // OK
                    MyRangeMIDlet.showInfoAlert(StringConsts.S_SETTINGS_CHANGED_OK);
                }
            });

        } catch (Exception e) {
            MyRangeMIDlet.showFailAlert("Exception", e.toString());
        }


        /*** OPTIONS ***/

        // Add start the synchronizing button
//        f.addComponent(new MenuButton(new Command(StringConsts.S_OPTIONS_MENU[StringConsts.CMD_M_SYNC]) {
//
//            public void actionPerformed(ActionEvent ae) {
//                (new Thread() {
//
//                    public void run() {
//                        // Synchronize to the server and show report
//                        final Dialog progress = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);
//                        MyRangeMIDlet.staticMidlet.synchronize(true, progress);
//                    }
//                }).start();
//            }
//        }));

        // Add show logs button
//        f.addComponent(new MenuButton(new Command(StringConsts.S_OPTIONS_MENU[StringConsts.CMD_M_PROGRAMM_LOG]) {
//
//            public void actionPerformed(ActionEvent ae) {
//                //MyRangeMIDlet.console.show(backCommand);
//                /*
//                TextLabel logs = new TextLabel(MyRangeMIDlet.out.getAll());
//                logs.setFocusable(true);
//                Dialog.show("", logs, new Command[] {new Command(StringConsts.S_BACK)});
//                 */
//                MyRangeMIDlet.showInfoAlert(MyRangeMIDlet.out.getAll());
//            }
//        }));

        // Add log out button
        MenuButton mbLogout = new MenuButton(new Command(StringConsts.S_OPTIONS_MENU[StringConsts.CMD_M_LOGOUT]) {

            public void actionPerformed(ActionEvent ae) {
                Command logoutOkCommand = new Command(StringConsts.S_YES) {     // Agree Logout

                    public void actionPerformed(ActionEvent evt) {
                        try {
                            // Logout
                            MyRangeMIDlet.staticMidlet.logout();
                        } catch (Exception e) {
                            MyRangeMIDlet.showFailAlert("Exception", e.getMessage());
                        }
                    }
                };
                MyRangeMIDlet.showAlert("", StringConsts.S_LOGOUT_ALERT,
                        Dialog.TYPE_WARNING, new Command[]{logoutOkCommand,
                            new Command(StringConsts.S_CANCEL)}, false);
            }
        });

        mbLogout.setBottom();
        f.addComponent(mbLogout);


    }
}
