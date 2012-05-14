/*
This class represetns the validatePhoneNumberRequestElement element from myrange.wsdl

<xs:element name="validatePhoneNumberRequestElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="header" type="tns:headerType"/>
            <xs:element name="btAddress" type="xs:string"/>
            <xs:element name="phoneNumber" type="xs:string"/>
        </xs:sequence>
    </xs:complexType>
</xs:element>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import ru.myrange.util.Password;

public class ValidatePhoneNumberRequestElement implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String phoneNumberTag = "n0:phoneNumber";

    private Vector objects = new Vector();
    private HeaderType header = null;
    private String phoneNumber = null;

	/* Used by KSOAP Implementation methods */

	public ValidatePhoneNumberRequestElement(HeaderType header, String phoneNumber) {
		setHeader(header);
        setPhoneNumber(phoneNumber);
	}

	public HeaderType getHeader() { return header; }
    public String getPhoneNumber() { return phoneNumber; }

	public void setHeader(HeaderType header) {
        this.header = header;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (getPhoneNumber() != null) objects.addElement(new PropertyObject(phoneNumberTag, PropertyInfo.STRING_CLASS, getPhoneNumber()));

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
        if (getHeader() != null) objects.addElement(new PropertyObject(headerTag, PropertyInfo.OBJECT_CLASS, getHeader()));
        if (getPhoneNumber() != null) objects.addElement(new PropertyObject(phoneNumberTag, PropertyInfo.STRING_CLASS, getPhoneNumber()));
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