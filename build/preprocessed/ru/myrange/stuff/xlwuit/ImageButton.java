/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Button;
import com.sun.lwuit.Image;

/**
 * Modified com.sun.lwuit.Button
 * Use it when it's need to create button with image
 *
 * @author Oleg Ponfilenok
 */
public class ImageButton extends Button {

    public ImageButton(String text, Image icon) {
        super(text, icon);
        setUIID("ImageButton");
        setAlignment(Button.CENTER);
        setTextPosition(Button.BOTTOM);
    }

}
