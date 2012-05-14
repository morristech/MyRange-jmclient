/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import javax.obex.HeaderSet;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.StaticActions;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.MyRoutines;
import ru.myrange.stuff.xlwuit.HeaderText;
import ru.myrange.stuff.xlwuit.MinorLabel;
import ru.myrange.stuff.xlwuit.TextComponent;

/**
 * Password recovery
 *
 * @author Oleg Ponfilenok
 */
public class PasswordRecoveryScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.PASSWORD_RECOVERY_SCREEN];
    }

    protected void execute(final Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        f.addComponent(new HeaderText(StringConsts.S_LOGIN_INFO));
        final TextField loginTF = new TextField();
        loginTF.setConstraint(TextField.ANY);
        //loginTF.setMaxSize(5);
        //loginTF.setInputMode("UCB_BASIC_LATIN");
        f.addComponent(loginTF);
        MinorLabel exampleLabel1 = new MinorLabel(StringConsts.S_LOGIN_INFO_EXAMPLE_1);
        exampleLabel1.setAlignment(Label.RIGHT);
        MinorLabel exampleLabel2 = new MinorLabel(StringConsts.S_LOGIN_INFO_EXAMPLE_2);
        exampleLabel2.setAlignment(Label.RIGHT);
        f.addComponent(exampleLabel1);
        f.addComponent(exampleLabel2);
        f.addComponent(new TextComponent(StringConsts.S_PASSWORD_RECOVERY_HELP));

        if (MyRangeMIDlet.login.length() > 1) {
            loginTF.setText(MyRangeMIDlet.login);
        }

        f.addCommand(new Command(StringConsts.S_SEND_NEW_PASSWORD) {
            public void actionPerformed(ActionEvent evt) {
                (new Thread() {
                    public void run(){
                        final Dialog pleaseWait = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);

                        String login = loginTF.getText().trim();
                        // Validation and transform login. Shoulb be a code, a phone number or an e-mail
                        try {
                            login = MyRoutines.transformLogin(login);
                        } catch (IllegalArgumentException e) {
                            pleaseWait.dispose();
                            MyRangeMIDlet.showInfoAlert(e.getMessage());
                            return;
                        }

                        try {
                            // Get new password
                            StaticActions.recoverPassword(login);

                            // Go to login
                            pleaseWait.dispose();
                            MyRangeMIDlet.login = login;
                            Command goToLoginCommand = new Command(StringConsts.S_LOGIN) {
                                public void actionPerformed(ActionEvent evt) {
                                    if (Display.getInstance().getCurrent() instanceof Dialog)
                                        ((Dialog) Display.getInstance().getCurrent()).dispose();
                                    MyRangeMIDlet.SCREENS[MyRangeMIDlet.LOGIN_SCREEN]
                                            .run(MyRangeMIDlet.SCREENS[MyRangeMIDlet.LOGIN_SCREEN].getBackCommand());
                                }
                            };
                            MyRangeMIDlet.showAlert(null, StringConsts.S_PASSWORD_RECOVERY_DONE + "+" + login,
                                    Dialog.TYPE_INFO, new Command[] {goToLoginCommand}, false);

                        } catch (Exception ex) {
                            pleaseWait.dispose();
                            ex.printStackTrace();
                            MyRangeMIDlet.showFailAlert("Exception", ex.toString());
                        }
                    }
                }).start();
            }
        });
    }

}
