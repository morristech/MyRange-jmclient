/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

/**
 * Headet text
 *
 * @author Yegorov Nickolay
 */
public class HeaderText extends TextComponent {

    public HeaderText(String text) {
        super(text);
        setUIID("HeaderText");
        this.setSelectedStyle(this.getUnselectedStyle());
        this.setFocusable(false);
        setBorderPainted(false);
    }

}