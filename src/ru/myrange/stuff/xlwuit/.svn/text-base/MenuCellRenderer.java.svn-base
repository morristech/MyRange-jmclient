/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ListCellRenderer;
import ru.myrange.stuff.StringConsts;

/**
 * List cell renderer for simple menu list.
 * Image and one string
 *
 * @author Oleg Ponfilenok
 */
public class MenuCellRenderer extends Container implements ListCellRenderer {

    /** Best size of an image. Any image resize to this size. */
    public static int bestPicSize = StringConsts.I_ICON_SIZE;

    private MenuContainer mcnt;
    private Label icon = new Label("");
    private Label text = new Label("W");
    protected FocusLabel focus = new FocusLabel();

    public MenuCellRenderer() {
        mcnt = new MenuContainer(new BoxLayout(BoxLayout.X_AXIS));
        mcnt.getStyle().setBgTransparency(0);
        icon.getStyle().setBgTransparency(0);
        text.getStyle().setBgTransparency(0);
        mcnt.addComponent(icon);
        mcnt.addComponent(text);
        addComponent(mcnt);
        focus.setFocus(true);
    }

    public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
        /*
        if (list.getSelectedItem().equals(value) && list.hasFocus()) {
            mcnt.getStyle().setBgImage(null);
            mcnt.getStyle().setBgTransparency(0);
        } else {
            mcnt.getStyle().setBgImage(MenuContainer.bgImage);
        }
        */

        IconText it = (IconText) value;
        text.setText(it.getText());
        icon.setIcon(it.getIcon());
        //System.out.println("1. " +Display.getInstance().getDisplayWidth() + ", " + focus.getPreferredW()  + ", " + focus.getWidth());
        mcnt.setWidth(UserCellRenderer.CELL_WIDTH);
        mcnt.setPreferredW(UserCellRenderer.CELL_WIDTH);
        return this;
    }

    public Component getListFocusComponent(List list) {
        return focus;
    }

}
