/*
This class represetns the echoStringInElement element from myrange.wsdl

<xs:element name="echoStringInElement">
    <xs:complexType>
        <xs:all>
            <xs:element name="header" type="tns:headerType"/>
            <xs:element name="stringIn" type="xs:string"/>
        </xs:all>
    </xs:complexType>
</xs:element>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import ru.myrange.util.Password;

public class EchoStringInElement implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String stringInTag = "n0:stringIn";

    private Vector objects = new Vector();
    private HeaderType header = null;
	private String stringIn = null;
	
	/* Used by KSOAP Implementation methods */
	
	public EchoStringInElement() {
		
	}
	
	public EchoStringInElement(HeaderType header, String stringIn) {
		setHeader(header);
		setStringIn(stringIn);
	}
		
	public HeaderType getHeader() { return header; }
	
	public String getStringIn() { return stringIn; }
	
	public void setHeader(HeaderType header) {
        this.header = header;
    }
	
	public void setStringIn(String stringIn) {
        this.stringIn = stringIn;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (getStringIn() != null) objects.addElement(new PropertyObject(stringInTag, PropertyInfo.STRING_CLASS, getStringIn()));

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
        if (getStringIn() != null) objects.addElement(new PropertyObject(stringInTag, PropertyInfo.STRING_CLASS, getStringIn()));
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
