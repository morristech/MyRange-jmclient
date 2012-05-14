/*
This class represetns the UploadSimpleLog element from http://alexbobkov.studenthost.ru/LogService.asmx

<s:element name="UploadSimpleLog">
    <s:complexType>
        <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="btAddress" type="s:string"/>
            <s:element minOccurs="0" maxOccurs="1" name="entry" type="s:string"/>
        </s:sequence>
    </s:complexType>
</s:element>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class UploadSimpleLogRequestElement implements KvmSerializable {

    private static final String btAddressTag = "n0:btAddress";
    private static final String entryTag = "n0:entry";

    private Vector objects = new Vector();
    private String btAddress = null;
	private String entry = null;

	/* Used by KSOAP Implementation methods */

	public UploadSimpleLogRequestElement() {

	}

	public UploadSimpleLogRequestElement(String btAddress, String entry) {
		setBtAddress(btAddress);
		setEntry(entry);
	}

	public String getBtAddress() { return btAddress; }
	public String getEntry() { return entry; }

	public void setBtAddress(String btAddress) {
        this.btAddress = btAddress;
        objects.addElement(new PropertyObject(btAddressTag, PropertyInfo.STRING_CLASS, btAddress));
    }
	public void setEntry(String entry) {
        this.entry = entry;
        objects.addElement(new PropertyObject(entryTag, PropertyInfo.STRING_CLASS, entry));
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        /*
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
        String hashResult = MyRoutines.md5Hash(entity);
        setHeader(new HeaderType(login, hashResult));
        */

        // Pack
        objects.removeAllElements();
        if (getBtAddress() != null) objects.addElement(new PropertyObject(btAddressTag, PropertyInfo.STRING_CLASS, getBtAddress()));
        if (getEntry() != null) objects.addElement(new PropertyObject(entryTag, PropertyInfo.STRING_CLASS, getEntry()));
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