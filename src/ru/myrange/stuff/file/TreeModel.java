package ru.myrange.stuff.file;

import java.util.Vector;

/**
 * Arranges tree node objects, a node can essentially be anything
 *
 * @author Shai Almog
 */
public interface TreeModel {
    /**
     * Returns the child objects representing the given parent, null should return
     * the root objects
     */
    public Vector getChildren(Object parent);

    /**
     * Converts a tree child to a label
     */
    public String childToDisplayLabel(Object child);

    /**
     * Is the node a leaf or a folder
     */
    public boolean isLeaf(Object node);
}
