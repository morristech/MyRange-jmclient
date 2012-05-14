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
import com.sun.lwuit.Label;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;

/**
 * Button with label
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class MenuButton extends Container {

    private Button actionButton;
    private Label altTextLabel;
    private String uiid = "MenuButton";

    public MenuButton(Command cmd) {
        super (new BorderLayout());
        actionButton = new Button(cmd);
        altTextLabel = new MinorLabel();
        init();
    }

    public MenuButton(final Command cmd, final Label altText) {
        super (new BorderLayout());
        actionButton = new Button(cmd);
        altTextLabel = altText;
        init();
    }

    public void resetUIID(String uiid){
        this.removeAll();
        this.uiid = uiid;
        init();
    }

    public void setBottom(){
        actionButton.setNextFocusDown(actionButton);

    }

     public void setTop(){
        actionButton.setNextFocusUp(actionButton);

    }

    private void init() {
        // Set style
        final MenuButton menuButton = this;
        menuButton.setUIID(uiid);
        final Style SelectedStyle = menuButton.getSelectedStyle();
        final Style UnselectedStyle = menuButton.getUnselectedStyle();

        actionButton.setAlignment(Button.LEFT);
        actionButton.getStyle().setBorder(Border.createEmpty());
        actionButton.getStyle().setBgTransparency(0);
        actionButton.setSelectedStyle(actionButton.getStyle());

        altTextLabel.setAlignment(Button.RIGHT);
        if (!(altTextLabel instanceof SignalLabel)) {
            //altTextLabel.getStyle().setBorder(Border.createEmpty());
            altTextLabel.getStyle().setBgTransparency(0);
            altTextLabel.setSelectedStyle(altTextLabel.getStyle());
        }

        // Set width of components
        int dw = Display.getInstance().getDisplayWidth();
        if (actionButton.getPreferredW() > 0 && altTextLabel.getPreferredW() > 0 &&
                actionButton.getPreferredW() + altTextLabel.getPreferredW() > dw) {
            actionButton.setPreferredW(dw*60/100);
            altTextLabel.setPreferredW(dw*35/100);
        }

        // Set focused style
        actionButton.addFocusListener(new FocusListener() {
            public void focusGained(Component arg0) {
                menuButton.setStyle(SelectedStyle);
                menuButton.repaint();
                altTextLabel.setFocus(true);
            }
            public void focusLost(Component arg0) {
                menuButton.setStyle(UnselectedStyle);
                menuButton.repaint();
                altTextLabel.setFocus(false);
            }
        });

        // Add components
        addComponent(BorderLayout.WEST, actionButton);
        addComponent(BorderLayout.EAST, altTextLabel);
    }

}