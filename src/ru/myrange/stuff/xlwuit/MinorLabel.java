/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Label;

/**
 * Minor text label
 *
 * @author Oleg Ponfilenok
 */
public class MinorLabel extends Label {

    public MinorLabel() {
        super();
        setUIID("MinorLabel");
    }

    public MinorLabel(String text) {
        super(text);
        setUIID("MinorLabel");
    }

}
