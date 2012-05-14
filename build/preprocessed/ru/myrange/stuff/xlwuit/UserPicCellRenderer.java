/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ListCellRenderer;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.Favourites;
import ru.myrange.stuff.MyRoutines;
import ru.myrange.stuff.Signals;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.Users;

/**
 * User profile with picture list cell renderer
 * Avatar and 3 strings
 *
 * @author Oleg Ponfilenok
 */
public class UserPicCellRenderer extends Container implements ListCellRenderer {

    /** Best size of an image. Any image resize to this size. */
    public static int bestUserpicSize = 64;

    protected MenuContainer mcnt;
    protected Label pic = new Label("");
    protected Label online = new Label();
    protected Label favourite = new Label("");
    protected Label signal = new Label("");
    protected Label name = new Label("W");
    protected MinorLabel info = new MinorLabel("W");
    protected Label lastMeetTime = new Label("W");
    protected FocusLabel focus = new FocusLabel();

    public UserPicCellRenderer() {
        mcnt = new MenuContainer(new BorderLayout());
        name.getStyle().setBgTransparency(0);
        info.getStyle().setBgTransparency(0);
        lastMeetTime.setUIID("BlueLabel");
        lastMeetTime.getStyle().setBgTransparency(0);
        online.getStyle().setBgTransparency(0);
        favourite.getStyle().setBgTransparency(0);
        signal.getStyle().setBgTransparency(0);

        Container cont1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container cont2 = new Container(new BorderLayout());
        Container cont3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        cont3.addComponent(online);
        cont3.addComponent(favourite);
        cont3.addComponent(signal);
        cont2.addComponent(BorderLayout.WEST, cont3);
        cont2.addComponent(BorderLayout.CENTER, lastMeetTime);
        cont1.addComponent(name);
        cont1.addComponent(info);
        cont1.addComponent(cont2);

        pic.getStyle().setBgTransparency(0);
        mcnt.addComponent(BorderLayout.WEST, pic);
        mcnt.addComponent(BorderLayout.CENTER, cont1);
        addComponent(mcnt);
        focus.setFocus(true);

        // Calc bestPicSize
		bestUserpicSize = mcnt.getPreferredH();
        int maxSize = ( (Display.getInstance().getDisplayWidth() <
            Display.getInstance().getDisplayHeight()) ? Display.getInstance().getDisplayWidth() :
                Display.getInstance().getDisplayHeight() )/4;
        if (bestUserpicSize > maxSize) bestUserpicSize = maxSize;
    }

    public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
        /*
        if (list.getSelectedItem().equals(value) && list.hasFocus()) {
            mcnt.getStyle().setBgImage(null);
        } else {
            mcnt.getStyle().setBgImage(MenuContainer.BG_IMAGE);
        }
        */
        UserInfo profile = (UserInfo) value;
        name.setText((profile.getFullName().length()>0) ? profile.getFullName() : " " );
        info.setText((profile.getInfo().length()>0) ? profile.getInfo() : " " );
        String seenTime = MyRoutines.timeToString(profile.getLastSeenTime());
        if (profile.isNear()) seenTime = StringConsts.S_IS_NEAR_ME_NOW;
        lastMeetTime.setText((seenTime.length()>0) ? seenTime : " " );
        if (Favourites.isFavourite(profile.getUserId())) {
            favourite.setIcon(MyRangeMIDlet.res.getImage("star-se"));
        } else {
            favourite.setIcon(null);
        }
        if (Signals.isSignal(profile.getUserId())) {
            signal.setIcon(MyRangeMIDlet.res.getImage("bell-se"));
        } else {
            signal.setIcon(null);
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

        // resize image
        Image img = profile.getPic(bestUserpicSize, bestUserpicSize);
        if (img == null) img = Users.getNullUserpic(bestUserpicSize, bestUserpicSize);
        pic.setIcon(img);

        // Set cell width to fill all width of screen
        mcnt.setWidth(UserCellRenderer.CELL_WIDTH);
        mcnt.setPreferredW(UserCellRenderer.CELL_WIDTH);
        
        return this;
    }

    public Component getListFocusComponent(List list) {
        return focus;
    }

}

