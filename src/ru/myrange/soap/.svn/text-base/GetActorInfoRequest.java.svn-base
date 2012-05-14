/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:element name="getActorInfoRequest">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="header" type="mr:headerType"/>
            <xs:element name="btAddress" type="xs:string" minOccurs="0" maxOccurs="unbounded"/>
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
 * This class represetns the getActorInfoRequest element from myrange.wsdl
 * @author Oleg Ponfilenok
 */
public class GetActorInfoRequest implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String btAddressTag = "n0:btAddress";

    private Vector objects = new Vector();
    private HeaderType header = null;
    private Vector btAddress = new Vector();

    /* Used by KSOAP Implementation methods */

    public GetActorInfoRequest(HeaderType header, Vector btAddress) {
        setHeader(header);
        setBtAddress(btAddress);
    }

    public HeaderType getHeader() { return header; }
    public Vector getBtAddress() { return btAddress; }

    public void setHeader(HeaderType header) {
        this.header = header;
    }
    public void setBtAddress(Vector btAddress) {
        if (btAddress == null) return;
        this.btAddress = btAddress;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        for (int i=0; i<getBtAddress().size(); i++) {
            if (getBtAddress().elementAt(i) != null) objects.addElement(new PropertyObject(btAddressTag, PropertyInfo.OBJECT_CLASS, getBtAddress().elementAt(i)));
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
        for (int i=0; i<getBtAddress().size(); i++) {
            if (getBtAddress().elementAt(i) != null) objects.addElement(new PropertyObject(btAddressTag, PropertyInfo.OBJECT_CLASS, getBtAddress().elementAt(i)));
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