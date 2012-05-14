/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Label;

/**
 * Modified com.sun.lwuit.Label
 * Use it like a focus element in the lists
 *
 * @author Oleg Ponfilenok
 */
public class FocusLabel extends Label {

    public FocusLabel() {
        super("");
        setUIID("FocusLabel");
    }

}

