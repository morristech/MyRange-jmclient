/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange
 */

package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Component;
import com.sun.lwuit.Display;
import com.sun.lwuit.Font;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.plaf.Style;

/**
* Simple progress indicator component that fills out the progress made.
* Progress is assumed to always be horizontal in this widget
*
* @author Shai Almog
*/
public class Progress extends Component {
    private byte percent;
    private Image unfilled;
    private Image filled;

    /**
    * The default constructor uses internal rendering to draw the progress
    */
    public Progress() {
        setFocusable(false);
    }

    /**
    * Allows indicating the progress using a filled/unfilled images.
    * The unfilled image is always drawn and the filled image is drawn on top with
    * clipping to indicate the amount of progress made.
    *
    * @param unfilled an image containing the progress bar without any of its
    * content being filled (with the progress color)
    * @param filled an image identicall to unfilled in every way except that progress
    * is completed in this bar.
    */
    public Progress(Image unfilled, Image filled) {
        this();
        this.unfilled = unfilled;
        this.filled = filled;
    }

    /**
    * Indicate to LWUIT the component name for theming in this case "Progress"
    */
    public String getUIID() {
        return "Progress";
    }

    /**
    * Indicates the percent of progress made
    */
    public byte getProgress() {
        return percent;
    }

    /**
    * Indicates the percent of progress made, this method is thread safe and
    * can be invoked from any thread although discression should still be kept
    * so one thread doesn't regress progress made by another thread...
    */
    public void setProgress(byte percent) {
        this.percent = percent;
        repaint();
    }

    public void setContinuosRunning(final boolean run) {
        new BackgroundTask() {
            public void performTask() {
                byte b=0;
                while (run) {
                    try {
                        setProgress(b);
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } finally {
                        b++;
                        if (b>100) b = 0;
                    }
                }
            }
        }.start();
    }

    /**
    * Return the size we would generally like for the component
    */
    protected Dimension calcPreferredSize() {
        if(filled != null) {
            return new Dimension(filled.getWidth(), filled.getHeight());
        } else {
            // we don't really need to be in the font height but this provides
            // a generally good indication for size expectations
            return new Dimension(Display.getInstance().getDisplayWidth(),
            Font.getDefaultFont().getHeight());
        }
    }

    /**
    * Paint the progress indicator
    */
    public void paint(Graphics g) {
        int width = (int)((((float)percent) / 100.0f) * getWidth());
        if(filled != null) {
            if(filled.getWidth() != getWidth()) {
                filled = filled.scaled(getWidth(), getHeight());
                unfilled = unfilled.scaled(getWidth(), getHeight());
            }

            // draw based on two user supplied images
            g.drawImage(unfilled, getX(), getY());
            g.clipRect(getX(), getY(), width, getHeight());
            g.drawImage(filled, getX(), getY());
        } else {
            // draw based on simple graphics primitives
            Style s = getStyle();
            g.setColor(s.getBgColor());
            int curve = getHeight() / 2 - 1;
            g.fillRoundRect(getX(), getY(), getWidth() - 1, getHeight() - 1, curve, curve);
            g.setColor(s.getFgColor());
            g.drawRoundRect(getX(), getY(), getWidth() - 1, getHeight() - 1, curve, curve);
            g.clipRect(getX(), getY(), width - 1, getHeight() - 1);
            g.setColor(s.getBgColor());// getBgSelectionColor());
            g.fillRoundRect(getX(), getY(), getWidth() - 1, getHeight() - 1, curve, curve);
        }
    }


    /**
    * A tool allowing to respond to an event in the background possibly with
    * progress indication inspired by Swings "SwingWorker" tool. This class
    * should be used from event dispatching code to prevent the UI from blocking.
    * State can be stored in this class the separate thread and it can be used by
    * the finish method which will be invoked after running.
    *
    * @author Shai Almog
    */
    public abstract class BackgroundTask {
        /**
        * Start this task
        */
        public final void start() {
            if(Display.getInstance().isEdt()) {
                taskStarted();
            } else {
                Display.getInstance().callSeriallyAndWait(new Runnable() {
                    public void run() {
                        taskStarted();
                    }
                });
            }
            new Thread(new Runnable() {
                public void run() {
                    if(Display.getInstance().isEdt()) {
                        taskFinished();
                    } else {
                        performTask();
                        Display.getInstance().callSerially(this);
                    }
                }
            }).start();
        }

        /**
        * Invoked on the LWUIT EDT before spawning the background thread, this allows
        * the developer to perform initialization easily.
        */
        public void taskStarted() {
        }

        /**
        * Invoked on a separate thread in the background, this task should not alter
        * UI except to indicate progress.
        */
        public abstract void performTask();

        /**
        * Invoked on the LWUIT EDT after the background thread completed its
        * execution.
        */
        public void taskFinished() {
        }
    }


}
