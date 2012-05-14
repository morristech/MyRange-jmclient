/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Container;
import com.sun.lwuit.layouts.Layout;

/**
 * Modified com.sun.lwuit.Container for use in Menu Lists
 *
 * @author Oleg Ponfilenok
 */
public class MenuContainer  extends Container {

    //public static final Image BG_IMAGE = MyRangeMIDlet.res.getImage("b3-1");

    public MenuContainer(Layout layout) {
        super(layout);
        setUIID("MenuContainer");
        //getStyle().setBgTransparency(255);
    }

}