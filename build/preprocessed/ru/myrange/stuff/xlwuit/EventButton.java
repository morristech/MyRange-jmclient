/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import ru.myrange.soap.EventInfoType;
import java.util.Calendar;
import java.util.Date;
import ru.myrange.stuff.Users;

/**
 * Button like List with short profile
 *
 * @author Nickolay Yegorov
 */
public class EventButton extends Container {

    /** Best size of an image. Any image resize to this size. */
    public static int bestUserpicSize = 64;

    private Button actionButton;
    protected TextArea info = new TextArea("W");

    public EventButton(final EventInfoType evt, final Command cmd) {

        super (new BorderLayout());
        actionButton = new Button(cmd);

        if (evt == null) return;

         // Set style
        setUIID("MenuButton");
        actionButton.setUIID("MenuMinorText");
        final EventButton menuButton = this;
        final Style unselectedStyle = this.getUnselectedStyle();
        final Style selectedStyle = this.getSelectedStyle();
        final Style pressedStyle = actionButton.getPressedStyle();

        // Add components
        actionButton.setAlignment(Button.LEFT);
        actionButton.getStyle().setBorder(Border.createEmpty());
        actionButton.getStyle().setBgTransparency(0);
        actionButton.setSelectedStyle(actionButton.getStyle());
        
        info.setUIID("MenuMajorText");
        info.getStyle().setBgTransparency(0);
        info.setSelectedStyle(info.getStyle());
        info.setText((evt.getSection().length()>0) ? evt.getSection() : " " );
        info.setEditable(false);
        if(evt.getSection().length() < 25)
            info.setSingleLineTextArea(true);

        info.setFocusable(false);

        
        Container cont1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container cont3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        addComponent(BorderLayout.SOUTH, actionButton);
        cont3.addComponent(info);
        cont1.addComponent(cont3);
        addComponent(BorderLayout.CENTER, cont1);

        Label speakerLabel = new Label("");
        addComponent(BorderLayout.EAST, speakerLabel);

        /*
         * Fill components
         */

        // Calc bestPicSize
		bestUserpicSize = cont1.getPreferredH();
        int maxSize = ( (Display.getInstance().getDisplayWidth() <
            Display.getInstance().getDisplayHeight()) ? Display.getInstance().getDisplayWidth() :
                Display.getInstance().getDisplayHeight() )/4;
        if (bestUserpicSize > maxSize) bestUserpicSize = maxSize;

        if(Users.isUser(evt.getSpeakerUserId())){
            try {
                Users.getUserWithPic(evt.getSpeakerUserId());
                speakerLabel.setIcon(Users.getUser(evt.getSpeakerUserId()).getPic(bestUserpicSize, bestUserpicSize));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        String headerText = (evt.getPlace().length()>0) ? evt.getPlace() : " ";

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(new Date(evt.getStartTime().longValue()));
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(new Date(evt.getEndTime().longValue()));

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

}
