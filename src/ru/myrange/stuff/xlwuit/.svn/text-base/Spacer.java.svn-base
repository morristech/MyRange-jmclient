/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Label;

/**
 * Component for set some empty space
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class Spacer extends Label {

    public Spacer(String text, int spacerType){
        super(text);

        if(spacerType == BOTTOM){
            this.setNextFocusDown(this);
            this.setSelectedStyle(this.getUnselectedStyle());
        }

        if(spacerType == TOP){
            this.setNextFocusUp(this);
            this.setSelectedStyle(this.getUnselectedStyle());
        }
        
    }

    public Spacer(int spacerType){
        super(" ");
        this.setFocusable(true);
        this.setSelectedStyle(this.getUnselectedStyle());

        if(spacerType == BOTTOM){
            this.setNextFocusDown(this);
        }

        if(spacerType == TOP){
            this.setNextFocusUp(this);
        }

    }

}
