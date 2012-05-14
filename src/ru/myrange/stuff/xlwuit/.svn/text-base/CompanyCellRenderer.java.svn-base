/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
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
import ru.myrange.MyRangeMIDlet;
import ru.myrange.soap.CompInfoType;

/**
 * Company profile without picture list cell renderer
 * Logo and 3 strings
 *
 * @author Yegorov Nickolay
 */
public class CompanyCellRenderer extends Container implements ListCellRenderer {

    /** Cell width indent to fill width of screen */
    public static final int CELL_WIDTH = Display.getInstance().getDisplayWidth() - 0;

    protected Container mcnt, namecnt;
    protected Label havePicture = new Label();
    protected Label name = new Label("W");
    protected MinorLabel info = new MinorLabel("W");
    protected FocusLabel focus = new FocusLabel();

    public CompanyCellRenderer() {
        name.getStyle().setBgTransparency(0);
        info.getStyle().setBgTransparency(0);        
        havePicture.getStyle().setBgTransparency(0);
        namecnt = new Container(new BoxLayout(BoxLayout.X_AXIS));

        mcnt = new Container(new BoxLayout(BoxLayout.X_AXIS));
        mcnt.addComponent(info);
        namecnt.addComponent(name);

        Container left = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        left.addComponent(namecnt);
        left.addComponent(mcnt);

        setLayout(new BorderLayout());

        addComponent(BorderLayout.CENTER, left);
        addComponent(BorderLayout.EAST, havePicture);
        focus.setFocus(true);
    }

    public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {


        CompInfoType profile = (CompInfoType) value;
        name.setText((profile.getName().length()>0) ? profile.getName() : " " );
        info.setText((profile.getIndustry().length()>0) ? profile.getIndustry() : " " );

        this.setUIID("ListItem");

//        if (list.getSelectedItem().equals(value) && list.hasFocus()) {
//           this.setStyle(this.getSelectedStyle());
//        } else {
//             this.setStyle(this.getUnselectedStyle());
//        }

        if (profile.getHavePic().booleanValue()) {
            havePicture.setIcon(MyRangeMIDlet.res.getImage("photo-s"));
        } else {
            havePicture.setIcon(null);
        }

        // Set cell width to fill all width of screen
        mcnt.setWidth(CELL_WIDTH);// 9*list.getBorderGap());
        mcnt.setPreferredW(CELL_WIDTH);// - 6*list.getBorderGap());

        return this;
    }

    public Component getListFocusComponent(List list) {
        return focus;
    }

}

