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
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;
import ru.myrange.soap.CompInfoType;

/**
 * Button like List with short company profile
 *
 * @author Nickolay Yegorov
 */
public class CompanyButton extends Container {

    /** Best size of an image. Any image resize to this size. */
    public static int bestUserpicSize = 48;

    private Button actionButton;
    protected TextArea info = new TextArea("W");
    protected Label pic = new Label("");

    public CompanyButton(final CompInfoType c, final Command cmd) {

        super (new BorderLayout());
        actionButton = new Button(cmd);

        if (c == null) return;

         // Set style
        this.setUIID("MenuButton");
        actionButton.setUIID("MenuMajorText");
        final CompanyButton menuButton = this;
        final Style pressedStyle = actionButton.getPressedStyle();
        final Style SelectedStyle = menuButton.getSelectedStyle();
        final Style UnselectedStyle = menuButton.getUnselectedStyle();

        // Add components
        actionButton.setAlignment(Button.LEFT);
        actionButton.getStyle().setBorder(Border.createEmpty());
        actionButton.getStyle().setBgTransparency(0);
        actionButton.setSelectedStyle(actionButton.getStyle());
        
        info.setUIID("MenuMinorText");
        info.getStyle().setBgTransparency(0);
        
        Container cont1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container cont2 = new Container(new BorderLayout());
        Container cont3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        cont2.addComponent(BorderLayout.WEST, actionButton);       
        cont3.addComponent(info);
        cont1.addComponent(cont2);
        cont1.addComponent(cont3);
        addComponent(BorderLayout.EAST, pic);
        addComponent(BorderLayout.CENTER, cont1);

        // Calc bestPicSize
		bestUserpicSize = cont1.getPreferredH();
        int maxSize = ( (Display.getInstance().getDisplayWidth() <
            Display.getInstance().getDisplayHeight()) ? Display.getInstance().getDisplayWidth() :
                Display.getInstance().getDisplayHeight() )/4;
        if (bestUserpicSize > maxSize) bestUserpicSize = maxSize;
        

        // Fill components        
        Image img = c.getLogo();
        if (img != null) {
            img = img.scaled(bestUserpicSize,bestUserpicSize);
            pic.setIcon(img);
        }

        String headerText = (c.getName().length()>0) ? c.getName() : " ";
        actionButton.setText(headerText);

        info.setText((c.getAbout().length()>0) ? c.getAbout() : " " );
        info.setEditable(false);
        if(c.getAbout().length() < 25)
            info.setSingleLineTextArea(true);
        info.setFocusable(false);
        info.setGrowByContent(false);

        // Set focused style
        actionButton.addFocusListener(new FocusListener() {
            public void focusGained(Component arg0) {
                menuButton.setStyle(SelectedStyle);
                menuButton.repaint();
            }
            public void focusLost(Component arg0) {
                menuButton.setStyle(UnselectedStyle);
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
