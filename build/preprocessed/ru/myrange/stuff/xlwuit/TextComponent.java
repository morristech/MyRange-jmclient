/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.TextArea;

/**
 * Not editable TextArea
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class TextComponent extends TextArea {

    public TextComponent(String text) {
        super(text);
        setUIID("TextComponent");
        setEditable(false);
        setFocusable(false);
        setBorderPainted(false);
    }

}
