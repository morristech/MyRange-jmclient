/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import javax.microedition.io.ConnectionNotFoundException;
import ru.myrange.MyRangeMIDlet;

/**
 * Hyperlink helper class
 *
 * @author Olrg Ponfilenok
 */
public class Hyperlink extends Button {

    public Hyperlink (final String url, final String text) {
        super(text);
        setUIID("Hyperlink");
        setAlignment(Button.LEFT);

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (url != null && url.length()>0)
                    try {
                        if (MyRangeMIDlet.staticMidlet.platformRequest(url)) { // Returns true if the MIDlet suite MUST first exit before start web browser.
                            //MyRangeMIDlet.exitApp();
                        } else {
                        }
                    } catch (ConnectionNotFoundException ex) {
                        MyRangeMIDlet.showFailAlert("Exception", ex.toString());
                    }
            }
        });
    }

    public Hyperlink(Command cmd) {
        super(cmd);
        setUIID("Hyperlink");
        setAlignment(Button.LEFT);
    }

}
