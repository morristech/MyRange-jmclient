package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Label;
import com.sun.lwuit.animations.Animation;
import com.sun.lwuit.animations.Transition;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.plaf.UIManager;

/**
 * Simple border layout container that uses container replace to rotate the components
 * within it based on the given transition
 *
 * @author Shai Almog
 */
public class AutoSwapContainer extends Container implements Animation {
    private boolean active;
    private long lastRotate = System.currentTimeMillis();
    private int timeToRotate;
    private Component[] cmps;
    private int currentCmp;
    private Transition trans;
    public AutoSwapContainer(Component[] cmps, Transition trans, int timeToRotate) {
        super(new BorderLayout());
        this.cmps = cmps;
        this.trans = trans;
        this.timeToRotate = timeToRotate;
        addComponent(BorderLayout.CENTER, cmps[0]);
    }

    public void initComponent() {
        getComponentForm().registerAnimated(this);
    }
    
    public String getUIID() {
        return "AutoSwapContainer";
    }

    protected void refreshTheme(String id) {
        super.refreshTheme(id);
        for(int iter = 0 ; iter < cmps.length ; iter++) {
            cmps[iter].refreshTheme();
        }
    }

    
    protected Dimension calcPreferredSize() {
        int w = 0;
        int h = 0;
        for(int iter = 0 ; iter < cmps.length ; iter++) {
            w = Math.max(w, cmps[iter].getPreferredW());
            h = Math.max(h, cmps[iter].getPreferredH());
        }
        return new Dimension(w + getStyle().getPadding(Component.LEFT) + getStyle().getPadding(Component.RIGHT), 
                h + getStyle().getPadding(Component.TOP) + getStyle().getPadding(Component.BOTTOM));
    }

    public boolean animate() {
        boolean parentAnimating = super.animate();
        if(parentAnimating){
            return parentAnimating;
        }
        long time = System.currentTimeMillis();
        if(isActive()) {
            if(time - lastRotate > timeToRotate) {
                int last = currentCmp;
                // prevent a race condition with the replace method transition
                if(contains(cmps[last])) {
                    lastRotate = time;
                    currentCmp++;
                    if(currentCmp == cmps.length) {
                        currentCmp = 0;
                    }
                    replace(cmps[last], cmps[currentCmp], trans);
                    if(cmps[currentCmp] instanceof Label) {
                        Label l = (Label)cmps[currentCmp];
                        if(l.getText() != null) {
                            if(l.shouldTickerStart() && (!l.isTickerRunning())) {
                                l.startTicker(UIManager.getInstance().getLookAndFeel().getTickerSpeed(), true);
                            }
                        }
                    }
                }
            }
            return true;
        } else {
            if(currentCmp != 0 && contains(cmps[currentCmp])) {
                replace(cmps[currentCmp], cmps[0], trans);
                currentCmp = 0;
            }
            return false;
        }
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
        lastRotate = System.currentTimeMillis();
    }
}
