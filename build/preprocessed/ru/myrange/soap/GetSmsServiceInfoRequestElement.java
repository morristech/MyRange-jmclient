/*
This class represetns the getSmsServiceInfoRequestElement element from myrange.wsdl

<xs:element name="getSmsServiceInfoRequestElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="header" type="tns:headerType"/>
            <xs:element name="codeName" type="xs:string"/>
            <xs:element name="senderBtAddress" type="xs:string"/>
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

public class GetSmsServiceInfoRequestElement implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String codeNameTag = "n0:codeName";
    private static final String senderBtAddressTag = "n0:senderBtAddress";

    private Vector objects = new Vector();
    private HeaderType header = null;
	private String codeName = null;
    private String senderBtAddress = null;

	/* Used by KSOAP Implementation methods */

	public GetSmsServiceInfoRequestElement() {

	}

	public GetSmsServiceInfoRequestElement(HeaderType header, String codeName, String senderBtAddress) {
		setHeader(header);
		setCodeName(codeName);
        setSenderBtAddress(senderBtAddress);
	}

	public HeaderType getHeader() { return header; }
	public String getCodeName() { return codeName; }
    public String getSenderBtAddress() { return senderBtAddress; }

	public void setHeader(HeaderType header) {
        this.header = header;
    }
	public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
    public void setSenderBtAddress(String senderBtAddress) {
        this.senderBtAddress = senderBtAddress;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (getCodeName() != null) objects.addElement(new PropertyObject(codeNameTag, PropertyInfo.STRING_CLASS, getCodeName()));
        if (getSenderBtAddress() != null) objects.addElement(new PropertyObject(senderBtAddressTag, PropertyInfo.STRING_CLASS, getSenderBtAddress()));

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
        if (getCodeName() != null) objects.addElement(new PropertyObject(codeNameTag, PropertyInfo.STRING_CLASS, getCodeName()));
        if (getSenderBtAddress() != null) objects.addElement(new PropertyObject(senderBtAddressTag, PropertyInfo.STRING_CLASS, getSenderBtAddress()));
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