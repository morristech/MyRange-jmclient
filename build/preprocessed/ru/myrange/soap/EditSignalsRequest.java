/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:element name="editSignalsRequest">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="header" type="mr:headerType"/>
            <xs:element name="add" minOccurs="0">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="userId" type="xs:int" minOccurs="0" maxOccurs="unbounded"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>
            <xs:element name="remove" minOccurs="0">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="userId" type="xs:int" minOccurs="0" maxOccurs="unbounded"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>
        </xs:sequence>
    </xs:complexType>
</xs:element>
*/

package ru.myrange.soap;

import java.util.Vector;
import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import ru.myrange.util.Password;

/**
 * This class represetns the editSignalsRequest element from myrange.wsdl
 * @author Oleg Ponfilenok
 */
public class EditSignalsRequest implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String addTag = "n0:add";
    private static final String removeTag = "n0:remove";

    private Vector objects = new Vector();
    private HeaderType header = null;
    private UserIds idsToAdd = new UserIds(null);
    private UserIds idsToRemove = new UserIds(null);

    /**
     * true - add to favourite
     * false - remove from favourites
     *
     */
    private boolean addOrRemove = false;

    /* Used by KSOAP Implementation methods */

    public EditSignalsRequest(HeaderType header, Vector userIdToAdd, Vector userIdToRemove) {
        setHeader(header);
        setIdsToAdd(userIdToAdd);
        setIdsToRemove(userIdToRemove);
    }

    private HeaderType getHeader() { return header; }
    private UserIds getIdsToAdd() { return idsToAdd; }
    private UserIds getIdsToRemove() { return idsToRemove; }

    private void setHeader(HeaderType header) {
        this.header = header;
    }
    private void setIdsToAdd(Vector ids) {
        if (ids != null) this.idsToAdd = new UserIds(ids);
    }
    private void setIdsToRemove(Vector ids) {
        if (ids != null) this.idsToRemove = new UserIds(ids);
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        getIdsToAdd().pack();
        for (int j=0; j< getIdsToAdd().getPropertyCount(); j++) {
            PropertyInfo info = new PropertyInfo();
            getIdsToAdd().getPropertyInfo(j, new Hashtable(), info);
            if (getIdsToAdd().getProperty(j) != null) objects.addElement(new PropertyObject(info.name, info.type.getClass(), getIdsToAdd().getProperty(j)));
        }
        getIdsToRemove().pack();
        for (int j=0; j< getIdsToRemove().getPropertyCount(); j++) {
            PropertyInfo info = new PropertyInfo();
            getIdsToRemove().getPropertyInfo(j, new Hashtable(), info);
            if (getIdsToRemove().getProperty(j) != null) objects.addElement(new PropertyObject(info.name, info.type.getClass(), getIdsToRemove().getProperty(j)));
        }

        // Calculate XML string
        String xmlString = "";
        // Transform all propertyes to string
        for (int i=0; i<getPropertyCount(); i++){
            xmlString += getProperty(i).toString();
        }

        //Sign
        String login = getHeader().getLogin();
        String pass = getHeader().getHash();
        String entity = pass + xmlString;
        String hashResult = (Password.fromPlainText(entity)).toString();
        setHeader(new HeaderType(login, hashResult));

        // Pack
        objects.removeAllElements();
        if (header != null) objects.addElement(new PropertyObject(headerTag, PropertyInfo.OBJECT_CLASS, header));
        getIdsToAdd().pack();
        objects.addElement(new PropertyObject(addTag, PropertyInfo.OBJECT_CLASS, getIdsToAdd()));
        getIdsToRemove().pack();
        objects.addElement(new PropertyObject(removeTag, PropertyInfo.OBJECT_CLASS, getIdsToRemove()));
    }

    /* Implementation Logic for KSOAP */
    public int getPropertyCount() {
        return objects.size();
    }

    public Object getProperty(int param) {
        if (param <= getPropertyCount()) {
            return ((PropertyObject) objects.elementAt(param)).getObj();
        } else {
            return null;
        }
    }

    public void setProperty(int param, Object obj) {
        if (param < getPropertyCount()) {
            objects.insertElementAt(obj, param);
        }
    }

    public void getPropertyInfo(int param, Hashtable ht, PropertyInfo info) {
        info.clear();
        if (param < getPropertyCount()) {
            info.name = ((PropertyObject) objects.elementAt(param)).getInfo().name;
            info.type = ((PropertyObject) objects.elementAt(param)).getInfo().type;
        }
    }

    /**
     * Helper class for pack users Ids
     */
    public class UserIds implements KvmSerializable {

        private static final String userIdTag = "n0:userId";

        private Vector objects = new Vector();
        private Vector userId = new Vector();

        /* Used by KSOAP Implementation methods */

        public UserIds(Vector userID) {
            setUserID(userID);
        }

        public Vector getUserID() { return userId; }

        public void setUserID(Vector userID) {
            if (userID == null) return;
            this.userId = userID;
        }

        // Pack element befor sending
        public void pack() {
            // Pack
            objects.removeAllElements();
            for (int i=0; i<userId.size(); i++) {
                if (userId.elementAt(i) != null) objects.addElement(new PropertyObject(userIdTag, PropertyInfo.OBJECT_CLASS, userId.elementAt(i)));
            }
        }

        /* Implementation Logic for KSOAP */
        public int getPropertyCount() {
            return objects.size();
        }

        public Object getProperty(int param) {
            if (param <= getPropertyCount()) {
                return ((PropertyObject) objects.elementAt(param)).getObj();
            } else {
                return null;
            }
        }

        public void setProperty(int param, Object obj) {
            if (param < getPropertyCount()) {
                objects.insertElementAt(obj, param);
            }
        }

        public void getPropertyInfo(int param, Hashtable ht, PropertyInfo info) {
            info.clear();
            if (param < getPropertyCount()) {
                info.name = ((PropertyObject) objects.elementAt(param)).getInfo().name;
                info.type = ((PropertyObject) objects.elementAt(param)).getInfo().type;
            }
        }

    }

}