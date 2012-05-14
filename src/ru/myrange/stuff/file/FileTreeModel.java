package ru.myrange.stuff.file;

import com.sun.lwuit.Dialog;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;

/**
 * Exposes the file system as a tree model hierarchy
 *
 * @author Shai Almog
 */
public class FileTreeModel implements TreeModel {

    public Vector getChildren(Object parent) {
        Vector response = new Vector();
        try {
            if(parent == null) {
                Enumeration e = FileSystemRegistry.listRoots();
                while(e.hasMoreElements()) {
                    response.addElement("file:///" + e.nextElement());
                }
            } else {
                String name = (String)parent;
                FileConnection c = (FileConnection)Connector.open(name, Connector.READ);
                Enumeration e = c.list();
                while(e.hasMoreElements()) {
                    response.addElement(name + e.nextElement());
                }
                c.close();
            }
        } catch(IOException err) {
            err.printStackTrace();
            Dialog.show(null, "Error while accessing the file system: " + err, "OK", null);
        }
        return response;
    }

    public String childToDisplayLabel(Object child) {
        if(((String)child).endsWith("/")) {
            return ((String)child).substring(((String)child).lastIndexOf('/', ((String)child).length() - 2));
        }
        return ((String)child).substring(((String)child).lastIndexOf('/'));
    }

    public boolean isLeaf(Object node) {
        boolean d = true;
        try {
            FileConnection c = (FileConnection)Connector.open((String)node, Connector.READ);
            d = c.isDirectory();
            c.close();
        } catch(IOException err) {
            err.printStackTrace();
            Dialog.show(null, "Error while accessing the file system node: " + node + "\n" + err, "OK", null);
        }
        return !d;
        //return !((String)node).endsWith("/");
    }

}
