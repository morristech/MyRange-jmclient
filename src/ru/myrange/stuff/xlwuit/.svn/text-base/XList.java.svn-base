/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.List;
import java.util.Vector;

/**
 * Modified com.sun.lwuit.List
 * Use it everywhere instead of originaly com.sun.lwuit.List
 *
 * @author Oleg Ponfilenok
 */
public class XList extends List {

    public XList() {
        super();
        init();
    }

    public XList(Object[] items) {
        super(items);
        init();
    }

    public XList(Vector items) {
        super(items);
        init();
    }

    private void init() {
        getStyle().setBgTransparency(90);
        setOrientation(List.VERTICAL);
        setPaintFocusBehindList(true);
        setIgnoreFocusComponentWhenUnfocused(true);
    }

    /*
    protected void focusLost() {
        repaint();
    }
    */

    public boolean animate() {
        boolean val = super.animate();

        // return true of animate only if there is data loading, this saves battery and CPU
        /*
        if(MessageCellRenderer.indicator.animate()) {
            if(getModel().getItemAt(getSelectedIndex()) == MessageCellRenderer.LOADING_MARKER) {
                return true;
            }
            /*
            int index = getSelectedIndex();
            index = Math.max(0, index - 4);
            ListModel model = getModel();
            int dest = Math.min(index + 4, model.getSize());
            for(int iter = index ; iter < dest ; iter++) {
                if(model.getItemAt(index) == LOADING_MARKER) {
                    return true;
                }
            }
            */
        //}
        return val;
    }

}
