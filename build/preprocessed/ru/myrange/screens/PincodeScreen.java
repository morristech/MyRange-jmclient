/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.StaticActions;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.util.Password;
import ru.myrange.util.Pincode;

/**
 * Enter by pincode form
 *
 * @author Oleg Ponfilenok
 */
public class PincodeScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.PINCODE_SCREEN];
    }

    protected void execute(final Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Label logoLabel = new Label(MyRangeMIDlet.logo);
        logoLabel.setAlignment(Label.CENTER);
        f.addComponent(logoLabel);
//#if CONF == "true"
//#         f.addComponent(new TextComponent(StringConsts.S_INFO));
//#endif
        f.addComponent(new TextComponent(StringConsts.S_LOGIN_PINCODE_INFO));
        final TextField pincodeTF = new TextField();
        pincodeTF.setConstraint(TextField.ANY);
        //loginTF.setMaxSize(4);
        //loginTF.setInputMode("UCB_BASIC_LATIN");
        f.addComponent(pincodeTF);
        f.addComponent(new TextComponent(StringConsts.S_LOGIN_PINCODE_HELP));

//#if RELEASE == "false"
        //pincodeTF.setText("LTRE");
//#endif

        if (MyRangeMIDlet.login.length() > 1) {
            pincodeTF.setText(MyRangeMIDlet.login);
        }

        f.addCommand(new Command(StringConsts.S_LOGIN) {
            public void actionPerformed(ActionEvent evt) {
                (new Thread() {
                    public void run(){
                        final Dialog progress = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);

                        String pincode = pincodeTF.getText().trim().toUpperCase();
                        // Validation pincode
                        if (!Pincode.isPincode(pincode)) {
                            progress.dispose();
                            MyRangeMIDlet.showInfoAlert(StringConsts.S_EMPTY_PINCODE);
                            return;
                        }

                        String password = pincode;
                        String hashResult;
                        try {
                            // Validation password and get md5 hash
                            hashResult = (Password.fromPlainText(password)).toString();
                        } catch (IllegalArgumentException e) {
                            progress.dispose();
                            MyRangeMIDlet.showInfoAlert(StringConsts.S_EMPTY_PINCODE);
                            return;
                        }

                        try {
                            // Check online email and password and Start!
                            StaticActions.authAndStart(pincode, hashResult, progress);
                        } catch (Exception e) {
                            progress.dispose();
                            MyRangeMIDlet.showFailAlert("Exception", e.toString());
                        }
                    }
                }).start();
            }
        });
    }

}
