package ru.myrange.stuff.file;

import com.sun.lwuit.Button;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Style;
import com.sun.lwuit.util.EventDispatcher;
import java.io.IOException;
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;

/**
 * LWUIT Tree component sample
 *
 * @author Shai Almog
 */
public class TreeComponent extends Container {
    private static final String KEY_OBJECT = "TREE_OBJECT";
    private static final String KEY_PARENT = "TREE_PARENT";
    private static final String KEY_EXPANDED = "TREE_NODE_EXPANDED";
    private static final String KEY_DEPTH = "TREE_DEPTH";
    private EventDispatcher leafListener = new EventDispatcher();

    private ActionListener expansionListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            Component c = (Component)evt.getSource();
            Object e = c.getClientProperty(KEY_EXPANDED);
            if(e != null && e.equals("true")) {
                collapseNode(c);
            } else {
                expandNode(c);
            }
        }
    };
    private TreeModel model;
    private static Image folder;
    private static Image openFolder;
    private static Image nodeImage;
    private int depthIndent = 15;

    public TreeComponent(TreeModel model) {
        this.model = model;
        if(folder == null) {
            folder = MyRangeMIDlet.res.getImage("folder.png");
            nodeImage = MyRangeMIDlet.res.getImage("page_white.png");
            openFolder = MyRangeMIDlet.res.getImage("folder_page.png");
        }
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        buildBranch(null, 0, this);
        setScrollableY(true);
    }

    private void expandNode(Component c) {
        c.putClientProperty(KEY_EXPANDED, "true");
        ((Button)c).setIcon(openFolder);
        int depth = ((Integer)c.getClientProperty(KEY_DEPTH)).intValue();
        Container parent = c.getParent();
        Object o = c.getClientProperty(KEY_OBJECT);
        Container dest = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label dummy = new Label();
        parent.addComponent(BorderLayout.CENTER, dummy);
        buildBranch(o, depth, dest);
        parent.replace(dummy, dest, CommonTransitions.createSlide(CommonTransitions.SLIDE_VERTICAL, true, 300));
    }

    private void collapseNode(Component c) {
        c.putClientProperty(KEY_EXPANDED, null);
        ((Button)c).setIcon(folder);
        Container p = c.getParent();
        for(int iter = 0 ; iter < p.getComponentCount() ; iter++) {
            if(p.getComponentAt(iter) != c) {
                Label dummy = new Label();
                p.replaceAndWait(p.getComponentAt(iter), dummy, CommonTransitions.createSlide(CommonTransitions.SLIDE_VERTICAL, false, 300));
                p.removeComponent(dummy);
            }
        }
    }

    /**
     * Returns the currently selected item in the tree
     */
    public Object getSelectedItem() {
        Component c = getComponentForm().getFocused();
        if(c != null) {
            return c.getClientProperty(KEY_OBJECT);
        }
        return null;
    }

    /**
     * Adds the child components of a tree branch to the given container.nt
     */
    protected void buildBranch(Object parent, int depth, Container destination) {
        Vector children = model.getChildren(parent);
        int size = children.size();
        Integer depthVal = new Integer(depth + 1);
        for(int iter = 0 ; iter < size ; iter++) {
            final Object current = children.elementAt(iter);
            Button nodeComponent = createNodeComponent(current, depth);
            if(model.isLeaf(current)) {
                destination.addComponent(nodeComponent);
                nodeComponent.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        leafListener.fireActionEvent(new ActionEvent(current));
                    }
                });
            } else {
                Container componentArea = new Container(new BorderLayout());
                componentArea.addComponent(BorderLayout.NORTH, nodeComponent);
                destination.addComponent(componentArea);
                nodeComponent.addActionListener(expansionListener);
            }
            nodeComponent.putClientProperty(KEY_OBJECT, current);
            nodeComponent.putClientProperty(KEY_PARENT, parent);
            nodeComponent.putClientProperty(KEY_DEPTH, depthVal);
        }
    }

    protected Button createNodeComponent(Object node, int depth) {
        Button cmp = new Button(model.childToDisplayLabel(node));
        if(model.isLeaf(node)) {
            cmp.setIcon(nodeImage);
        } else {
            cmp.setIcon(folder);
        }
        updateNodeComponentStyle(cmp.getSelectedStyle(), depth);
        updateNodeComponentStyle(cmp.getUnselectedStyle(), depth);
        return cmp;
    }

    protected void updateNodeComponentStyle(Style s, int depth) {
        s.setBorder(null);
        s.setMargin(LEFT, depth * depthIndent);
    }

    public void addLeafListener(ActionListener l) {
        leafListener.addListener(l);
    }

    public void removeLeafListener(ActionListener l) {
        leafListener.removeListener(l);
    }

    /**
     * Make sure height should be no shorter than 6 elements for expansion room
     */
    protected Dimension calcPreferredSize() {
        Dimension d = super.calcPreferredSize();
        int size = model.getChildren(null).size();
        if(size < 6) {
            return new Dimension(Math.max(d.getWidth(), Display.getInstance().getDisplayWidth() / 4 * 3),
                    d.getHeight() / size * 6);
        }
        return d;
    }
}
