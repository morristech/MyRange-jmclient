/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
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
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;

/**
 * Button with image and label
 *
 * @author Oleg Ponfilenok
 */
public class ImageMenuButton extends Container {

    private Button actionButton;
    private Label pic = new Label("");
    private Label altTextLabel;

    public ImageMenuButton(final Image img, final Command cmd, final Label altText) {
        super (new BorderLayout());
        actionButton = new Button(cmd);
        altTextLabel = altText;
        pic.setIcon(img);

         // Set style
        setUIID("MenuButton");
        actionButton.setUIID("MenuButton");
        final ImageMenuButton menuButton = this;
        final Style unselectedStyle = this.getUnselectedStyle();
        final Style selectedStyle = this.getSelectedStyle();
        final Style pressedStyle = actionButton.getPressedStyle();

        // Set width of components
        int dw = Display.getInstance().getDisplayWidth() - pic.getPreferredW();
        if (actionButton.getPreferredW() > 0 && altTextLabel.getPreferredW() > 0 &&
                actionButton.getPreferredW() + altTextLabel.getPreferredW() > dw) {
            actionButton.setPreferredW(dw*60/100);
            altTextLabel.setPreferredW(dw*35/100);
        }

        // Add components
        Container cnt = new Container(new BorderLayout());
        actionButton.setAlignment(Button.LEFT);
        actionButton.getStyle().setBorder(Border.createEmpty());
        actionButton.getSelectedStyle().setBorder(Border.createEmpty());
        actionButton.getStyle().setBgTransparency(0);
        actionButton.getSelectedStyle().setBgTransparency(0);
        pic.getStyle().setBgTransparency(0);
        altTextLabel.setAlignment(Button.RIGHT);
        if (!(altTextLabel instanceof SignalLabel)) {
            altTextLabel.getStyle().setBorder(Border.createEmpty());
            altTextLabel.getStyle().setBgTransparency(0);
        }
        cnt.getStyle().setBgTransparency(0);
        cnt.addComponent(BorderLayout.WEST, actionButton);
        cnt.addComponent(BorderLayout.EAST, altTextLabel);
        addComponent(BorderLayout.WEST, pic);
        addComponent(BorderLayout.CENTER, cnt);

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
