/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Label;

/**
 * Major text label
 *
 * @author Oleg Ponfilenok
 */
public class MajorLabel extends Label {

    public MajorLabel(String text) {
        super(text);
        setUIID("MajorLabel");
    }

}
