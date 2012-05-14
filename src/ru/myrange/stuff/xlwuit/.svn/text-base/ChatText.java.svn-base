package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.TextArea;
import com.sun.lwuit.plaf.UIManager;

public class ChatText extends TextArea {

    private boolean me;
    private AutoSwapContainer box;

    public ChatText(String t, boolean me, AutoSwapContainer box) {
        super(t, 1, 20);
        this.me = me;
        this.box = box;
        // the super class constructor initializes the style before "me" has a chance
        // to initialize the local variable
        refreshTheme(getUIID());
    }

    public void focusGained() {
        box.setActive(true);
    }

    public void focusLost() {
        box.setActive(false);
    }

    public String getUIID() {
        if (me) {
            return "TextAreaMe";
        } else {
            return "TextAreaThem";
        }
    }

    protected void refreshTheme(String id) {
        setUnSelectedStyle(UIManager.getInstance().getComponentStyle(getUIID()));
        setSelectedStyle(UIManager.getInstance().getComponentStyle(getUIID()));
        getSelectedStyle().setFgColor(getUnselectedStyle().getBgColor());
    }
}
