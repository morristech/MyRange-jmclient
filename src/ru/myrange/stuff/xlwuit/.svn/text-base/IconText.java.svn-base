/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Image;

/**
 * Simple class.
 * Icon + text for menu lists
 *
 * @author Oleg Ponfilenok
 */
public class IconText {
    private Image icon;
    private String text;

    public IconText(Image icon, String text) {
        this.text = text;
        this.icon = icon;
    }

    public Image getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

    public static IconText[] createArray(Image[] imgs, String[] texts) {
        int imgsLen = 0;
        int textsLen = 0;
        if (imgs != null) imgsLen = imgs.length;
        if (texts != null) textsLen = texts.length;

        IconText[] itArray = new IconText[Math.max(imgsLen, textsLen)];
        for (int i = 0; i < itArray.length; i++) {
            Image img = null;
            String txt = "";
            if (imgs!=null && imgs.length>i) img = imgs[i];
            if (texts!=null && texts.length>i) txt = texts[i];
            
            itArray[i] = new IconText(img, txt);
        }
        return itArray;
    }

}
