/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.xlwuit.MenuButton;
import ru.myrange.stuff.xlwuit.TextComponent;

/**
 * My profile or Main menu choise
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class FirstScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.FIRST_SCREEN];
    }

    protected void execute(final Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
         Label logoLabel = new Label(MyRangeMIDlet.logo);
         logoLabel.setAlignment(Label.CENTER);
         f.addComponent(logoLabel);
        

        TextComponent tc = new TextComponent(StringConsts.S_FIRST_GUIDE);
        f.addComponent(tc);

        f.addComponent(new MenuButton(new Command(StringConsts.S_FIRST_MY_PROFILE) {
            public void actionPerformed(ActionEvent ae) {
                // Show my profile
                final Command backProfileCommand = new Command(StringConsts.S_BACK){
                    public void actionPerformed(ActionEvent ae) {
                        MyRangeMIDlet.SCREENS[MyRangeMIDlet.MAINMENU_SCREEN].run(MyRangeMIDlet.staticMidlet.hideCommand);
                    }
                };
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.MY_PROFILE_SCREEN].run(backProfileCommand);
            }
        }));

        f.addComponent(new MenuButton (new Command(StringConsts.S_FIRST_MAIN_MENU) {
            public void actionPerformed(ActionEvent ae) {
                MyRangeMIDlet.SCREENS[MyRangeMIDlet.MAINMENU_SCREEN].run(MyRangeMIDlet.staticMidlet.hideCommand);
            }
        }));

    }

}
