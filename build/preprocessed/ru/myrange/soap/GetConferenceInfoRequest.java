/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:element name="getConferenceInfoRequest">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="header" type="mr:headerType"/>
            <xs:element name="confId" type="xs:int" minOccurs="0" maxOccurs="unbounded"/>
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
 * This class represetns the getConferenceInfoRequest element from myrange.wsdl
 *
 * @author Oleg Ponfilenok
 */
public class GetConferenceInfoRequest implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String confIdTag = "n0:confId";

    private Vector objects = new Vector();
    private HeaderType header = null;
    private Integer confId = null;

    /* Used by KSOAP Implementation methods */

    public GetConferenceInfoRequest(HeaderType header) {
        setHeader(header);
    }
    
    public GetConferenceInfoRequest(HeaderType header, Integer confId) {
        setHeader(header);
        setConfId(confId);
    }

    public HeaderType getHeader() { return header; }
    public Integer getConfId() { return confId; }

    public void setHeader(HeaderType header) {
        this.header = header;
    }
    public void setConfId(Integer confId) {
        this.confId = confId;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (getConfId() != null) objects.addElement(new PropertyObject(confIdTag, PropertyInfo.INTEGER_CLASS, getConfId()));

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
        if (getConfId() != null) objects.addElement(new PropertyObject(confIdTag, PropertyInfo.INTEGER_CLASS, getConfId()));
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