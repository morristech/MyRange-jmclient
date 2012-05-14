/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:element name="getUserInfoRequestElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="header" type="mr:headerType"/>
            <xs:element name="param" type="mr:userInfoParamEnum" minOccurs="0" maxOccurs="unbounded"/>
            <xs:element name="picSize" type="xs:int" minOccurs="0"/>
            <xs:element name="userID" type="xs:int" maxOccurs="unbounded"/>
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
 * This class represetns the getUserInfoRequestElement element from myrange.wsdl
 * @author Oleg Ponfilenok
 */
public class GetUserInfoRequestElement implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String paramTag = "n0:param";
    private static final String picSizeTag = "n0:picSize";
    private static final String userIDTag = "n0:userID";

    private Vector objects = new Vector();
    private HeaderType header = null;
    private Vector params = new Vector();
    private Integer picSize = null;
    private Vector userID = new Vector();

    /* Used by KSOAP Implementation methods */

    public GetUserInfoRequestElement(HeaderType header, Vector params, Integer picSize, Vector userID) {
        setHeader(header);
        setParams(params);
        setPicSize(picSize);
        setUserID(userID);
    }

    public HeaderType getHeader() { return header; }
    public Vector getParams() { return params; }
    public Integer getPicSize() { return picSize; }
    public Vector getUserID() { return userID; }

    public void setHeader(HeaderType header) {
        this.header = header;
    }
    public void setParams(Vector params) {
        this.params = params;
    }
    public void setPicSize(Integer picSize) {
        this.picSize = picSize;
    }
    public void setUserID(Vector userID) {
        if (userID == null) return;
        this.userID = userID;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        for (int i=0; i<params.size(); i++) {
            if (params.elementAt(i) != null) objects.addElement(new PropertyObject(paramTag, PropertyInfo.OBJECT_CLASS, params.elementAt(i)));
        }
        if (picSize != null) objects.addElement(new PropertyObject(picSizeTag, PropertyInfo.INTEGER_CLASS, picSize));
        for (int i=0; i<userID.size(); i++) {
            if (userID.elementAt(i) != null) objects.addElement(new PropertyObject(userIDTag, PropertyInfo.OBJECT_CLASS, userID.elementAt(i)));
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
        for (int i=0; i<params.size(); i++) {
            if (params.elementAt(i) != null) objects.addElement(new PropertyObject(paramTag, PropertyInfo.OBJECT_CLASS, params.elementAt(i)));
        }
        if (picSize != null) objects.addElement(new PropertyObject(picSizeTag, PropertyInfo.INTEGER_CLASS, picSize));
        for (int i=0; i<userID.size(); i++) {
            if (userID.elementAt(i) != null) objects.addElement(new PropertyObject(userIDTag, PropertyInfo.OBJECT_CLASS, userID.elementAt(i)));
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