/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.xlwuit.HeaderText;
import ru.myrange.stuff.xlwuit.MenuButton;

/**
 * Class to show the login or the register screen
 *
 * @author Oleg Ponfilenok
 */
public class StartScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.START_SCREEN];
    }

    protected void execute(final Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        Label logoLabel = new Label(MyRangeMIDlet.logo);
        logoLabel.setAlignment(Label.CENTER);
        f.addComponent(logoLabel);
        
        f.addComponent(new HeaderText(StringConsts.S_INFO));

        /*
        f.addComponent(new MenuButton(new Command(StringConsts.S_PINCODE) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.PINCODE_SCREEN].run(backToThisCommand);
            }
        }));
        */

        f.addComponent(new MenuButton(new Command(StringConsts.S_ENTER_BY_CODE) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.LOGIN_SCREEN].run(backToThisCommand);
            }
        }));

        f.addComponent(new MenuButton(new Command(StringConsts.S_REG) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.REGISTER_SCREEN].run(backToThisCommand);
            }
        }));
    }
	
}
