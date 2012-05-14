/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.Records;
import ru.myrange.stuff.StaticActions;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.xlwuit.HeaderText;
import ru.myrange.stuff.xlwuit.MenuButton;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.util.Password;

/**
 * Phone number verify form
 *
 * @author Oleg Ponfilenok
 */
public class PasswordValidationScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.PASSWORD_VALIDATION_SCREEN];
    }

    protected void execute(Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        f.addComponent(new TextComponent(StringConsts.S_PASSWORD_SEND_INFO));
        f.addComponent(new HeaderText(StringConsts.S_SET_PASSWORD));

        final TextField codeTF = new TextField();
        codeTF.setConstraint(TextField.NUMERIC);   // 4 digits code
        f.addComponent(codeTF);

        f.addCommand(new Command(StringConsts.S_NEXT) {
            public void actionPerformed(ActionEvent evt) {
                final Dialog progress = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);

                String trueCode = "";
                try {
                    trueCode = Records.recSettings.readStringData(Records.REC_VALIDATION_PASSWORD);
                } catch (Exception ignored) {}

                String newCode = codeTF.getText().trim();
                if (newCode != null && newCode.equals(trueCode)) {
                    try {
                         // Registration success!
                        try {
                            String hashResult = (Password.fromPlainText(newCode)).toString();
                            Records.recSettings.eraseStringField(Records.REC_VALIDATION_PASSWORD); // Erase validation code
                            StaticActions.authAndStart(MyRangeMIDlet.login, hashResult, progress);
                        } catch (Exception e) {
                            progress.dispose();
                            MyRangeMIDlet.showFailAlert("Error", e.toString());
                            return;
                        }

                    } catch (Exception e) {
                        progress.dispose();
                        MyRangeMIDlet.showFailAlert("Exception", e.toString());
                    }
                } else {
                    progress.dispose();
                    MyRangeMIDlet.showInfoAlert(StringConsts.S_WRONG_CODE);
                }
            }
        });

        // Hide or Exit
        f.addComponent(new TextComponent(StringConsts.S_CODE_HIDE_OR_EXIT));

        f.addComponent(new MenuButton(new Command(StringConsts.S_HIDE) {
            public void actionPerformed(ActionEvent evt) {
                MyRangeMIDlet.hide();
            }
        }));

        f.addComponent(new MenuButton(new Command(StringConsts.S_CLOSE) {
            public void actionPerformed(ActionEvent evt) {
                MyRangeMIDlet.exitApp();
            }
        }));

    }

    /**
     * Erase validation password before back
     */
    protected void cleanup() {
        try {
            Records.recSettings.eraseStringField(Records.REC_VALIDATION_PASSWORD);
        } catch (Exception ex) {
            MyRangeMIDlet.showFailAlert("Exception", ex.toString());
            ex.printStackTrace();
        }
    }

}
