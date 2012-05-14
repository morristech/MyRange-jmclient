/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
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
import ru.myrange.soap.UserInfo;

/**
 * User profile without picture list cell renderer
 * Avatar and 3 strings
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class UserCellRenderer extends Container implements ListCellRenderer {

    /** Cell width indent to fill width of screen */
    public static final int CELL_WIDTH = Display.getInstance().getDisplayWidth() - 0;

    protected Container mcnt, namecnt;
    protected Label favourite = new Label();
    protected Label signal = new Label();
    protected Label havePicture = new Label();
    protected Label online = new Label();
    protected Label name = new Label("W");
    protected MinorLabel info = new MinorLabel("W");
    protected FocusLabel focus = new FocusLabel();

    public UserCellRenderer() {
        name.getStyle().setBgTransparency(0);
        info.getStyle().setBgTransparency(0);
        //text3.getStyle().setBgTransparency(0);
        //favourite.getStyle().setBgTransparency(0);
        signal.getStyle().setBgTransparency(0);
        havePicture.getStyle().setBgTransparency(0);
        online.getStyle().setBgTransparency(0);
        
        /*
        mcnt = new MenuContainer(new BoxLayout(BoxLayout.Y_AXIS));
        Container cont2 = new Container(new BorderLayout());
        Container cont3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        cont3.addComponent(favourite);
        cont3.addComponent(signal);
        cont3.addComponent(havePicture);
        cont3.addComponent(online);
        cont2.addComponent(BorderLayout.WEST, cont3);
        //cont2.addComponent(BorderLayout.CENTER, text3);
        mcnt.addComponent(text1);
        //mcnt.addComponent(text2);
        mcnt.addComponent(cont2);
        */
        namecnt = new Container(new BoxLayout(BoxLayout.X_AXIS));

        mcnt = new Container(new BoxLayout(BoxLayout.X_AXIS));
        mcnt.addComponent(info);
        namecnt.addComponent(name);
        namecnt.addComponent(online);

        //mcnt.addComponent(havePicture);
       // mcnt.addComponent(favourite);
        //mcnt.addComponent(signal);
        Container left = new Container(new BoxLayout(BoxLayout.Y_AXIS));     
        left.addComponent(namecnt);
        left.addComponent(mcnt);

        setLayout(new BorderLayout());
        
        addComponent(BorderLayout.CENTER, left);
        addComponent(BorderLayout.EAST, havePicture);
        focus.setFocus(true);
    }

    public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
        
        
        
        UserInfo profile = (UserInfo) value;
        name.setText((profile.getFullName().length()>0) ? profile.getFullName() : " " );
        info.setText((profile.getInfo().length()>0) ? profile.getInfo() : " " );

        this.setUIID("ListItem");

//        if (list.getSelectedItem().equals(value) && list.hasFocus()) {
//           this.setStyle(this.getSelectedStyle());
//        } else {
//             this.setStyle(this.getUnselectedStyle());
//        }
//        this.repaint();
//        if (Favourites.isFavourite(profile.getUserId())) {
//            favourite.setIcon(MyRangeMIDlet.res.getImage("star-se"));
//        } else {
//            favourite.setIcon(null);
//        }
//        if (Signals.isSignal(profile.getUserId())) {
//            signal.setIcon(MyRangeMIDlet.res.getImage("bell-se"));
//        } else {
//            signal.setIcon(null);
//        }
        if (profile.getHavePic().booleanValue()) {
            havePicture.setIcon(MyRangeMIDlet.res.getImage("photo-s"));
        } else {
            havePicture.setIcon(null);
        }
        if (profile.getOnlineStatus().equals(UserInfo.ONLINE_STATUS_ONLINE)) {
            online.setIcon(MyRangeMIDlet.res.getImage("online-s"));
        } else if (profile.getOnlineStatus().equals(UserInfo.ONLINE_STATUS_OFFLINE)) {
            online.setIcon(MyRangeMIDlet.res.getImage("offline-s"));
        } else if (profile.getOnlineStatus().equals(UserInfo.ONLINE_STATUS_NEVER)) {
            online.setIcon(MyRangeMIDlet.res.getImage("never-s"));
        } else  {
            online.setIcon(null);
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

