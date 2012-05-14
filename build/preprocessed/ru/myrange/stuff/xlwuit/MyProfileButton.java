/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ListCellRenderer;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.Users;

/**
 * Button like List with short profile
 *
 * @author Oleg Ponfilenok
 */
public class MyProfileButton extends Container {

    /** Best size of an image. Any image resize to this size. */
    public static int bestUserpicSize = 64;

    private Button actionButton;
    private Label pic = new Label("");
    private MinorLabel info = new MinorLabel("W");

    public MyProfileButton(final Command cmd) {
        /*
        super(new Object[]{Users.getMyProfile()});
        setUIID("MyProfileButton");
        setListCellRenderer(new MyProfileCellRenderer());
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cmd.actionPerformed(ae);
            }
        });
        */

        super (new BorderLayout());
        actionButton = new Button(cmd);

        UserInfo profile = Users.getMyProfile();
        if (profile == null) return;

         // Set style
        setUIID("MenuButton");
        actionButton.setUIID("MenuButton");
        final MyProfileButton menuButton = this;
        final Style unselectedStyle = this.getUnselectedStyle();
        final Style selectedStyle = this.getSelectedStyle();
        final Style pressedStyle = actionButton.getPressedStyle();

        // Add components
        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        actionButton.setAlignment(Button.LEFT);
        actionButton.getStyle().setBorder(Border.createEmpty());
        actionButton.getSelectedStyle().setBorder(Border.createEmpty());
        actionButton.getStyle().setBgTransparency(0);
        actionButton.getSelectedStyle().setBgTransparency(0);
        pic.getStyle().setBgTransparency(0);
        info.getStyle().setBgTransparency(0);
        cnt.getStyle().setBgTransparency(0);
        cnt.addComponent(actionButton);
        cnt.addComponent(info);
        addComponent(BorderLayout.WEST, pic);
        addComponent(BorderLayout.CENTER, cnt);

        // Calc bestPicSize
        bestUserpicSize = cnt.getPreferredH();

        // Fill components
        actionButton.setText((profile.getFullName().length()>0) ? profile.getFullName() : " " );
        info.setText((profile.getInfo().length()>0) ? profile.getInfo() : " " );
        // resize image
        Image img = profile.getPic(bestUserpicSize, bestUserpicSize);
        if (img == null) img = Users.getNullUserpic(bestUserpicSize, bestUserpicSize);
        pic.setIcon(img);

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

    public void setTop() {
        actionButton.setNextFocusUp(actionButton);
    }


    /**
     * List cell renderer for my short profile
     *
     * @author Oleg Ponfilenok
     */
    /*
    public class MyProfileCellRenderer extends Container implements ListCellRenderer {

        private MenuContainer mcnt;
        private Label pic = new Label("");
        private Label name = new Label("W");
        private Label info = new Label("W");
        private FocusLabel focus = new FocusLabel();


        public MyProfileCellRenderer() {
            mcnt = new MenuContainer(new BorderLayout());
            //mcnt.getStyle().setBgTransparency(0);
            Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            pic.getStyle().setBgTransparency(0);
            name.getStyle().setBgTransparency(0);
            info.getStyle().setBgTransparency(0);
            mcnt.getStyle().setBgTransparency(0);
            cnt.addComponent(name);
            cnt.addComponent(info);

            mcnt.addComponent(BorderLayout.WEST, pic);
            mcnt.addComponent(BorderLayout.CENTER, cnt);
            addComponent(mcnt);

            focus.setFocus(true);

            // Calc bestPicSize
            bestUserpicSize = cnt.getPreferredH();
        }

        public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
            UserInfo profile = (UserInfo) value;
            name.setText((profile.getFullName().length()>0) ? profile.getFullName() : " " );
            info.setText((profile.getTextStatus().length()>0) ? profile.getTextStatus() : " " );

            // resize image
            Image img = profile.getPic();
            if (img == null) img = Users.NULL_USERPIC;
            pic.setIcon(img.scaled(bestUserpicSize, bestUserpicSize));

            // Set cell width to fill all width of screen
            mcnt.setWidth(UserCellRenderer.CELL_WIDTH);
            mcnt.setPreferredW(UserCellRenderer.CELL_WIDTH);

            return this;
        }

        public Component getListFocusComponent(List list) {
            return focus;
        }

    }
    */

}
