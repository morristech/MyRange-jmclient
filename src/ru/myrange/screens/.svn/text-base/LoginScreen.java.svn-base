/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.DataChangedListener;
import com.sun.lwuit.layouts.BoxLayout;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.StaticActions;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.xlwuit.HeaderText;
import ru.myrange.stuff.xlwuit.MinorLabel;
import ru.myrange.stuff.xlwuit.Spacer;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.util.Password;

/**
 * Enter by e-mail or phone number and password form
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class LoginScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.LOGIN_SCREEN];
    }

    protected void execute(final Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

         Label logoLabel = new Label(MyRangeMIDlet.logo);
         logoLabel.setAlignment(Label.CENTER);
         f.addComponent(logoLabel);

        f.addComponent(new HeaderText(StringConsts.S_LOGIN_PINCODE_INFO));

        final TextField loginTF = new TextField();
        loginTF.setConstraint(TextField.INITIAL_CAPS_WORD);
        loginTF.addDataChangeListener(new DataChangedListener() {
            public void dataChanged(int arg0, int arg1) {
                if (arg1 >= 0) {
                    loginTF.setText(loginTF.getText().toUpperCase());
                }
            }
        });

        loginTF.setNextFocusUp(loginTF);
        
        f.addComponent(loginTF);

       
        //loginTF.setMaxSize(5);
        //loginTF.setInputMode("UCB_BASIC_LATIN");
        
        MinorLabel exampleLabel1 = new MinorLabel(StringConsts.S_LOGIN_INFO_EXAMPLE_1);
//        exampleLabel1.setAlignment(Label.RIGHT);
//        MinorLabel exampleLabel2 = new MinorLabel(StringConsts.S_LOGIN_INFO_EXAMPLE_2);
//        exampleLabel2.setAlignment(Label.RIGHT);
        f.addComponent(exampleLabel1);
//        f.addComponent(exampleLabel2);

        //f.addComponent(new HeaderText(StringConsts.S_PASSWORD_INFO));
//        final TextField passwordTF = new TextField();
//        passwordTF.setConstraint(TextField.PASSWORD);
        //passwordTF.setMaxSize(5);
        //f.addComponent(passwordTF);

//#if RELEASE == "true"
//#        //loginTF.setText("ponfil@myrange.ru");
//#        //passwordTF.setText("7698");
//#else
        loginTF.setText("8RQ2");
        //passwordTF.setText("123");
//#endif

        // Add recovery password button
//        f.addComponent(new Hyperlink(new Command(StringConsts.S_PASSWORD_RECOVERY) {
//            public void actionPerformed(ActionEvent ae) {
//                MyRangeMIDlet.SCREENS[MyRangeMIDlet.PASSWORD_RECOVERY_SCREEN].run(backToThisCommand);
//            }
//        }));

        if (MyRangeMIDlet.login.length() > 1) {
            loginTF.setText(MyRangeMIDlet.login);
        }

        f.addCommand(new Command(StringConsts.S_LOGIN) {
            public void actionPerformed(ActionEvent evt) {
                (new Thread() {
                    public void run(){
                        String login = loginTF.getText().trim();
                        //String login = "test4@myrange.ru";
                        // Validation and transform login. Shoulb be a code, a phone number or an e-mail
//                        try {
//                            login = MyRoutines.transformLogin(login);
//                        } catch (IllegalArgumentException e) {
//                            MyRangeMIDlet.showInfoAlert(e.getMessage());
//                            return;
//                        }

                        String password = login;//passwordTF.getText().trim();

                        String hashResult;
                        try {
                            // Validation password and get md5 hash
                            hashResult = (Password.fromPlainText(password)).toString();
                        } catch (IllegalArgumentException e) {
                            MyRangeMIDlet.showInfoAlert(StringConsts.S_EMPTY_PINCODE);
                            return;
                        }

                        final Dialog pleaseWait = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);
                        try {
                            // Check online email and password and start!
                            StaticActions.authAndStart(login, hashResult, pleaseWait);
                        } catch (Exception e) {
                            pleaseWait.dispose();
                            //MyRangeMIDlet.showFailAlert("[Auth and Start: ]Exception", e.toString());
                            MyRangeMIDlet.showFailAlert(StringConsts.S_WRONG_CODE);
                        }
                    }
                }).start();
            }
        });

        f.addComponent(new TextComponent(StringConsts.S_LOGIN_PINCODE_HELP));

        TextComponent tc = new TextComponent(StringConsts.S_INFO);
        tc.setUIID("MajorText");
        f.addComponent(tc);

        f.addComponent(new Spacer(Spacer.BOTTOM));

        f.revalidate();
    }

}
