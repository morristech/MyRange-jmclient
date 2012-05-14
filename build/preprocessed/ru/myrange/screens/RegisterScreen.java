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
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.Records;
import ru.myrange.stuff.StaticActions;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.xlwuit.HeaderText;
import ru.myrange.stuff.xlwuit.MinorLabel;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.util.PhoneNumber;

/**
 * User registration form. Phone number, first and last name.
 *
 * @author Oleg Ponfilenok
 */
public class RegisterScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.REGISTER_SCREEN];
    }

    protected void execute(Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        f.addComponent(new HeaderText(StringConsts.S_SET_FIRST_NAME));
        final TextField firstNameTF = new TextField();
        firstNameTF.setConstraint(TextField.ANY);
        f.addComponent(firstNameTF);

        f.addComponent(new HeaderText(StringConsts.S_SET_LAST_NAME));
        final TextField lastNameTF = new TextField();
        lastNameTF.setConstraint(TextField.ANY);
        f.addComponent(lastNameTF);

        f.addComponent(new HeaderText(StringConsts.S_PHONE_INFO));
        final TextField phoneTF = new TextField();
        phoneTF.setConstraint(TextField.PHONENUMBER);
        f.addComponent(phoneTF);
        MinorLabel exampleLabel = new MinorLabel(StringConsts.S_PHONE_INFO_EXAMPLE);
        exampleLabel.setAlignment(Label.RIGHT);
        f.addComponent(exampleLabel);

        f.addComponent(new TextComponent(StringConsts.S_REGISTER_HELP));

        f.addCommand(new Command(StringConsts.S_REG_NEXT) {
            public void actionPerformed(ActionEvent evt) {
                (new Thread() {
                public void run(){
                    String phone = phoneTF.getText().trim();
                    try {
                        phone = (PhoneNumber.fromPlainText(phone)).toString();
                    } catch (IllegalArgumentException ex) {
                        MyRangeMIDlet.showInfoAlert(StringConsts.S_WRONG_PHONE);
                        return;
                    }

                    String firstName = firstNameTF.getText().trim();
                    if (firstName.length() <= 0) {	// Validation first name
                        MyRangeMIDlet.showInfoAlert(StringConsts.S_EMPTY_FIRST_NAME);
                        return;
                    }
                    String lastName = lastNameTF.getText().trim();
                    if (lastName.length() <= 0) {	// Validation last name
                        MyRangeMIDlet.showInfoAlert(StringConsts.S_EMPTY_LAST_NAME);
                        return;
                    }

                    // Online register
                    final Dialog progress = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);
                    try {
//#if FP2009 == "true"
//#                         // Registered on the global and fp2009 conferences
//#                         Vector confIds = new Vector();
//#                         confIds.addElement(StringConsts.I_GLOBAL_CONF_ID);
//#                         confIds.addElement(StringConsts.I_CONF_ID);
//#                         String password = StaticActions.register(phone, firstName, lastName, confIds);
//#else
                        //Disabled for time
                        String password = "";//StaticActions.register(phone, firstName, lastName, null);
//#endif

                        // Set login
                        MyRangeMIDlet.login = phone;
                        // Save in RMS
                        Records.recSettings.addStringData(phone, Records.REC_LOGIN);
                        Records.recSettings.eraseStringField(Records.REC_PASSWORD);
                        Records.recSettings.addStringData(password, Records.REC_VALIDATION_PASSWORD);
                        // Show the screen for entering the password
                        progress.dispose();
                        MyRangeMIDlet.SCREENS[MyRangeMIDlet.PASSWORD_VALIDATION_SCREEN].run(backToThisCommand);
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
