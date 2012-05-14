/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import java.util.Calendar;
import java.util.Date;
import ru.myrange.soap.BusinessMeetInfoType;

/**
 *
 * @author Nickolay Yegorov
 */
public class BusinessMeetButton extends Container {

    private Button actionButton;
    protected TextArea info = new TextComponent("W");

    public BusinessMeetButton(final BusinessMeetInfoType bt, final Command cmd) {

        super (new BorderLayout());
        actionButton = new Button(cmd);

        if (bt == null) return;

         // Set style
        setUIID("MenuButton");
        actionButton.setUIID("MenuMinorText");
        final BusinessMeetButton menuButton = this;
        final Style unselectedStyle = this.getUnselectedStyle();
        final Style selectedStyle = this.getSelectedStyle();
        final Style pressedStyle = actionButton.getPressedStyle();

        // Add components
        actionButton.setAlignment(Button.LEFT);
        actionButton.getStyle().setBorder(Border.createEmpty());
        actionButton.getStyle().setBgTransparency(0);
        actionButton.setSelectedStyle(this.getStyle());
        
        info.setUIID("MenuMajorText");
        info.getStyle().setBgTransparency(0);
        info.setSelectedStyle(info.getStyle());
        info.setText((bt.getInfo().length()>0) ? bt.getInfo() : " " );
        info.setEditable(false);
        if(bt.getInfo().length() < 25)
            info.setSingleLineTextArea(true);

        info.setFocusable(false);
        
        Container cont1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container cont2 = new Container(new BorderLayout());
        Container cont3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        cont2.addComponent(BorderLayout.WEST, actionButton);       
        cont3.addComponent(info);
        cont1.addComponent(cont2);
        cont1.addComponent(cont3);
        addComponent(BorderLayout.CENTER, cont1);


        // Fill components
        String headerText = (bt.getPlace().length()>0) ? bt.getPlace() : " ";

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(new Date(bt.getStartTime().longValue()));
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(new Date(bt.getEndTime().longValue()));

        headerText = calStart.get(Calendar.HOUR_OF_DAY) + ":" + calStart.get(Calendar.MINUTE) + " - " +
                     calEnd.get(Calendar.HOUR_OF_DAY) + ":" + calEnd.get(Calendar.MINUTE) + " " +
                     headerText;

        actionButton.setText(headerText);

        // Set focused style
        actionButton.addFocusListener(new FocusListener() {
            public void focusGained(Component arg0) {
                menuButton.setStyle(selectedStyle);
                menuButton.repaint();
            }
            public void focusLost(Component arg0) {
                menuButton.setStyle(unselectedStyle);
                menuButton.repaint();
            }
        });
        
        actionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                menuButton.setStyle(pressedStyle);
                menuButton.repaint();
            }
        });
    }


    /**
     * EventInfoType renderer
     *
     * @author Yegorov Nickolay
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
