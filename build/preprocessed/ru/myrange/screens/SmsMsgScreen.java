package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.Records;
import ru.myrange.stuff.StringConsts;
import ru.myrange.soap.SmsServiceInfo;
import ru.myrange.stuff.xlwuit.TextComponent;

/**
 * Class for shows a SMS entered textbox
 * and realise the sending SMS.
 */
public class SmsMsgScreen extends Screen {

    private Integer receiverId;             // Receiver's ID
    private String receiverName = "";       // Receiver's Name


    public String getName() {
        //return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.SMS_MSG_SCREEN];
        return StringConsts.S_SMS_TO_USERNAME + receiverName;
    }

    /**
     * Overload run method
     *
     * @param backCommand - back to previous screen command
     * @param receiverId - receiver's userId
     * @param receiverName - receiver's name
     */
    public final void run(final Command backCommand, Integer receiverId, String receiverName) {
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        createAll(backCommand);
        //show();
    }


    protected void execute(final Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        if (receiverId == null) {
            // No receiver
            f.addComponent(new TextComponent(StringConsts.S_SMS_NO_USER_FAIL_ALERT));
            return;
        }

        try {
            // Update information about SMS cost
            final String smsCost = (new SmsServiceInfo(Records.recSettings.readByteData((Records.REC_SMS_U2U_INFO)))).getCost();
            // Billing phone number
            String shortCode = (new SmsServiceInfo(Records.recSettings.readByteData((Records.REC_SMS_U2U_INFO)))).getShortCode();
            final String smsURL = "sms://" + shortCode;
            // Hide string in the beginnig of message (short code + Bluetooth address)
            final String hideString = (new SmsServiceInfo(Records.recSettings.readByteData((Records.REC_SMS_U2U_INFO)))).getKeyWord() +
                        " " + receiverId + " ";

            // Check last sms service info update
            if ( (new SmsServiceInfo(Records.recSettings.readByteData((Records.REC_SMS_U2U_INFO)))).getUpdateTime().longValue() <
                    (System.currentTimeMillis() - StringConsts.L_SMS_SERVICE_MUST_UPDATE_MS) ) {
                MyRangeMIDlet.showInfoAlert(StringConsts.S_SMS_SERVICE_MUST_UPDATE);
                return;
            }

            Command smsOkCommand = new Command(StringConsts.S_YES) {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        int msgLength = StringConsts.I_SMS_LENGTH - hideString.length();   // Length of the available sms string
                        final TextArea msgArea = new TextArea();
                        // TODO. Set max len text size
                        //TextArea.setDefaultMaxSize(msgLength);
                        f.addComponent(msgArea);

                        f.addCommand(new Command(StringConsts.S_SEND) {
                            public void actionPerformed(ActionEvent ae) {
                                final Dialog progress = MyRangeMIDlet.showWaitingAlert(StringConsts.S_SENDING);
                                (new Thread() {
                                    public void run(){  // Send SMS
                                        String msg = msgArea.getText().trim();
                                        if (msg.length() < 1) {
                                            // No any text was entered
                                            progress.dispose();
                                            MyRangeMIDlet.showInfoAlert(StringConsts.S_SEND_MSG_NO_TEXT);
                                            return;
                                        }

                                        MessageConnection conn;
                                        try {
                                            // Open connection
                                            conn = (MessageConnection) Connector.open(smsURL);
                                            // Send SMS
                                            TextMessage textMsg = (TextMessage) conn.newMessage(MessageConnection.TEXT_MESSAGE);
                                            String text = hideString + msg;
                                            textMsg.setPayloadText(text);
                                            conn.send(textMsg);
                                            // Close connection
                                            conn.close();

                                            MyRangeMIDlet.out.println("[SmsMsg] Send sms: url=" + smsURL + " text=" + text + " ...OK");
                                        }
                                        catch( Exception e ){
                                            progress.dispose();
                                            MyRangeMIDlet.showFailAlert(StringConsts.S_SEND_MSG_FAIL_ALERT, "[SMS] Exception: " + e.toString());
                                            return;
                                        }
                                        // Finished OK
                                        progress.dispose();
                                        Command goToUserProfileCommand = new Command(StringConsts.S_OK) {
                                            public void actionPerformed(ActionEvent evt) {
                                                MyRangeMIDlet.SCREENS[MyRangeMIDlet.USER_PROFILE_SCREEN]
                                                        .run(MyRangeMIDlet.SCREENS[MyRangeMIDlet.USER_PROFILE_SCREEN].getBackCommand());
                                            }
                                        };
                                        MyRangeMIDlet.showAlert(null, StringConsts.S_SEND_MSG_OK_ALERT,
                                                Dialog.TYPE_INFO, new Command[] {goToUserProfileCommand}, false);
                                    }
                                }).start();
                            }
                        });

                    } catch (Exception e) {
                        MyRangeMIDlet.showFailAlert("Exception", e.toString());
                    }
                }
            };
            // Show form
            show();
            // Show alert
            MyRangeMIDlet.showAlert("", StringConsts.S_SMS_COST_WARNING_1 + smsCost + StringConsts.S_SMS_COST_WARNING_2,
                    Dialog.TYPE_WARNING, new Command[] {smsOkCommand, backCommand}, false);

        } catch (Exception e) {
            MyRangeMIDlet.showInfoAlert(StringConsts.S_SMS_SERVICE_MUST_UPDATE + " \n[SMS] Error: " + e.toString());
        }

    }
    
}

