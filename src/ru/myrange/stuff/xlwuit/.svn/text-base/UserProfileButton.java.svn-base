/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.Favourites;
import ru.myrange.stuff.Messages;
import ru.myrange.stuff.Signals;
import ru.myrange.stuff.Users;

/**
 * Button like List with short profile
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class UserProfileButton extends Container {

    /** Best size of an image. Any image resize to this size. */
    public static int bestUserpicSize = 64;

    private Button actionButton;
    protected Label pic = new Label("");
    protected Label online = new Label();
    protected Label favourite = new Label("");
    protected Label signal = new Label("");
    protected MinorLabel info = new MinorLabel("W");
    protected SignalLabel numMsg = new SignalLabel("0");

    public UserProfileButton(final UserInfo profile, final Command cmd) {
        super (new BorderLayout());
        actionButton = new Button(cmd);

        if (profile == null) return;

         // Set style
        setUIID("MenuButton");
        actionButton.setUIID("Button");
        final UserProfileButton menuButton = this;
        final Style unselectedStyle = this.getUnselectedStyle();
        final Style selectedStyle = this.getSelectedStyle();
        final Style pressedStyle = actionButton.getPressedStyle();

        // Add components
        actionButton.setAlignment(Button.LEFT);
        actionButton.getStyle().setBorder(Border.createEmpty());
        actionButton.getSelectedStyle().setBorder(Border.createEmpty());
        actionButton.getStyle().setBgTransparency(0);
        actionButton.getSelectedStyle().setBgTransparency(0);
        pic.getStyle().setBgTransparency(0);
        info.getStyle().setBgTransparency(0);
        info.setSelectedStyle(info.getStyle());
        online.getStyle().setBgTransparency(0);
        favourite.getStyle().setBgTransparency(0);
        signal.getStyle().setBgTransparency(0);
        numMsg.setAlignment(Label.RIGHT);
        Container cont1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container cont2 = new Container(new BorderLayout());
        Container cont3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        addComponent(BorderLayout.SOUTH, actionButton);
        cont2.addComponent(BorderLayout.EAST, numMsg);
        cont3.addComponent(online);
        cont3.addComponent(favourite);
        cont3.addComponent(signal);
        cont3.addComponent(info);
        /*
         * Magic!
         */
        cont1.addComponent(cont3);
        cont1.addComponent(cont2);        
        addComponent(BorderLayout.WEST, pic);
        addComponent(BorderLayout.CENTER, cont1);

        // Calc bestPicSize
		bestUserpicSize = cont1.getPreferredH();
        int maxSize = ( (Display.getInstance().getDisplayWidth() <
            Display.getInstance().getDisplayHeight()) ? Display.getInstance().getDisplayWidth() :
                Display.getInstance().getDisplayHeight() )/4;
        if (bestUserpicSize > maxSize) bestUserpicSize = maxSize;

        // Fill components
        actionButton.setText((profile.getFullName().length()>0) ? profile.getFullName() : " " );
        info.setText((profile.getInfo().length()>0) ? profile.getInfo() : " " );
        int numM = Messages.numNewInboxMsg(profile.getUserId());
        if (numM > 0) numMsg.setText(Integer.toString(numM));
        else numMsg.setText("");
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

        // Set focused style
        actionButton.addFocusListener(new FocusListener() {
            public void focusGained(Component arg0) {
                menuButton.setStyle(selectedStyle);
                //menuButton.setFocus(true);
                menuButton.repaint();
                info.setFocus(true);
            }

            public void focusLost(Component arg0) {
                menuButton.setStyle(unselectedStyle);
                //menuButton.setFocus(false);
                menuButton.repaint();
                info.setFocus(false);
            }
        });
        actionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                menuButton.setStyle(pressedStyle);
                menuButton.repaint();

            }
        });
    }

     public void setBottom(){
        actionButton.setNextFocusDown(actionButton);

    }

     public void setTop(){
        actionButton.setNextFocusUp(actionButton);

    }


    /**
     * Short user profile list cell renderer
     * Avatar and 3 strings
     *
     * @author Oleg Ponfilenok
     */
    /*
    public class UserButtonCellRenderer extends UserPicCellRenderer implements ListCellRenderer {

        public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
            UserInfo profile = (UserInfo) value;
            text1.setText((profile.getFullName().length()>0) ? profile.getFullName() : " " );
            text2.setText((profile.getTextStatus().length()>0) ? profile.getTextStatus() : " " );
            String seenTime = MyRoutines.timeToString(profile.getLastSeenTime());//profile.getLastSeenTimeHR();
            if (profile.isNear()) seenTime = StringConsts.S_NEAR_ME_NOW;
            text3.setText((seenTime.length()>0) ? seenTime : " " );
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
            Image img = profile.getPic();
            if (img == null) img = Users.NULL_USERPIC;
            pic.setIcon(img.scaled(UserPicCellRenderer.bestUserpicSize, UserPicCellRenderer.bestUserpicSize));

            // Set cell width to fill all width of screen
            mcnt.setWidth(UserCellRenderer.CELL_WIDTH);
            mcnt.setPreferredW(UserCellRenderer.CELL_WIDTH);

            return this;
        }

    }
    */

}
