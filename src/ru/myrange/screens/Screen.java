/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.geom.Dimension;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.xlwuit.MinorLabel;

/**
 * Base class for a screen contains common code for all screen pages
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public abstract class Screen {
    
    protected Form screenForm;
    protected Command backCommand;

    protected final Command backToThisCommand = new Command(StringConsts.S_BACK){
        public void actionPerformed(ActionEvent ae) {
            //cleanup();
            //show();
            run(backCommand);
        }
    };

    /*
    protected final Command codeScreenBackCommand = new Command(StringConsts.S_BACK){
        public void actionPerformed(ActionEvent ae) {
            try {
                Records.recSettings.eraseStringField(Records.REC_VALIDATION_PASSWORD); // Erase validation passeord
            } catch (Exception ex) {
                MyRangeMIDlet.showFailAlert("Exception", ex.toString());
                ex.printStackTrace();
            }
            screenForm.show();
        }
    };
    */

    /*
    protected final Command captureScreenBackCommand = new Command(StringConsts.S_BACK){
        public void actionPerformed(ActionEvent ae) {
            ((CaptureScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.CAPTURE_SCREEN]).stopPlayer();
            run(backCommand);
        }
    };
    */

    /**
     * returns the name of the screen to display in the list
     */
    public abstract String getName();

    /**
     * Create new Form and call the execute
     */
    public final void createAll(final Command backCommand) {
        this.backCommand = backCommand;
        screenForm = new Form(getName());
        if (backCommand != null) {
            Command cleanBackCommand = new Command(backCommand.getCommandName()) {
                public void actionPerformed(ActionEvent ae) {
                    cleanup();
                    backCommand.actionPerformed(ae);
                }
            };
            screenForm.addCommand(cleanBackCommand);
            screenForm.setBackCommand(cleanBackCommand);
        }

        //although calling directly to execute() will work on
        //most devices, a good coding practice will be to allow the midp
        //thread to return and to do all the UI on the EDT.
        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                screenForm.removeAll();
                execute(screenForm);
                screenForm.revalidate();
            }
        });
    }

    /**
     * Show the form on the screen
     */
    public final void show() {
        if (screenForm != null) {
            Display.getInstance().callSerially(new Runnable() {
                public void run() {
                    if (Display.getInstance().getCurrent() instanceof Dialog) {
                        ((Dialog) Display.getInstance().getCurrent()).dispose();
                    }
                    screenForm.show();
                }
            });
        }
    }

    /**
     * Invoked by the main code to start the screen
     */
    public void run(final Command backCommand) {
        createAll(backCommand);
        show();
    }


    /**
     * The demo should place its UI into the given form
     */
    protected abstract void execute(Form f);

    /**
     * Get the form
     */
    public final Form getForm() {
        return screenForm;
    }

    /**
     * Get the back command
     */
    public final Command getBackCommand() {
        return backCommand;
    }


    /**
     * Helper method that allows us to create a pair of components label and the given
     * component in a horizontal layout with a minimum label width
     */
    protected Container createPair(String label, Component c, int minWidth) {
        Container pair = new Container(new BorderLayout());
        Label l =  new MinorLabel(label);
        l.setUIID("pairLabel");
        Dimension d = l.getPreferredSize();
        d.setWidth(Math.max(d.getWidth(), minWidth));
        l.setPreferredSize(d);
        pair.addComponent(BorderLayout.WEST,l);
        pair.addComponent(BorderLayout.CENTER, c);
        return pair;
    }

    protected Container createPair(String label, Component c, String labelUUID, int minWidth) {
        Container pair = new Container(new BorderLayout());
        Label l =  new MinorLabel(label);
        l.setUIID(labelUUID);
        Dimension d = l.getPreferredSize();
        d.setWidth(Math.max(d.getWidth(), minWidth));
        l.setPreferredSize(d);
        pair.addComponent(BorderLayout.WEST,l);
        pair.addComponent(BorderLayout.CENTER, c);
        return pair;
    }

    /**
     * Helper method that allows us to create a pair of components label and the given
     * component in a horizontal layout
     */
     protected Container createPair(String label, Component c) {
         return createPair(label,c,0);
     }

     /**
      * Do some actions just before leave the screen
      */
     protected void cleanup() {

        // for series 40 devices
        //System.gc();
        //System.gc();
     }
}
