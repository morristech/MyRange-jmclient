/*
This class represetns the contactType type from myrange.wsdl

<xs:complexType name="contactType">
    <xs:sequence>
        <xs:element name="name" type="mr:contactNameType"/>
        <xs:element name="uid" type="xs:string"/>
    </xs:sequence>
</xs:complexType>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class ContactType implements KvmSerializable {

    private static final String CONTACT_SPACER = "<#*\u0004\u0019%>"; // To split name and uid

    public static final String CONTACT_NAME_ICQ = "icq";
    public static final String CONTACT_NAME_SKYPE = "skype";
    public static final String CONTACT_NAME_PHONE = "Телефон";
    public static final String CONTACT_NAME_EMAIL = "Email";

    private static final String nameTag = "name";
    private static final String uidTag = "uid";
    private static final String nameTagRequest = "n0:name";
    private static final String uidTagRequest = "n0:uid";

    private Vector objects = new Vector();

    private String name = "";
	private String uid = "";
	
	/* Used by KSOAP Implementation methods */
	
	public ContactType() {
		
	}
	
	public ContactType(String name, String uid) {
		setName(name);
		setUid(uid);
	}

    public ContactType(SoapObject soapObj) {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
			PropertyInfo soapObjInfo = new PropertyInfo();
			// Get property name of SoapObject
			soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            String s = soapObjInfo.name;
            if (soapObjInfo.name.equals(nameTag)) {
                setName( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(uidTag)) {
                setUid( soapObj.getProperty(i).toString() );
            }
		}
	}
		
	public String getName() { return name; }
	public String getUid() { return uid; }
	
	public void setName(String name) {
        this.name = name;
        objects.addElement(new PropertyObject(nameTagRequest, PropertyInfo.STRING_CLASS, name));
    }
	public void setUid(String uid) {
        this.uid = uid;
        objects.addElement(new PropertyObject(uidTagRequest, PropertyInfo.STRING_CLASS, uid));
    }

    public String toString() {
        String res = "";
        for (int i=0; i<getPropertyCount(); i++){
            res += getProperty(i).toString();
        }
        return res;
    }

    /**
     * Conver vector of ContactType's to string
     *
     * @param contacts
     * @return
     */
    static public String vectorToString(Vector contacts) throws Exception {
        String result = "";
        if (contacts == null) return result;
        for (int i=0; i<contacts.size(); i++) {
            if (contacts.elementAt(i) instanceof ContactType) {
                ContactType c = (ContactType) contacts.elementAt(i);
                result += c.getName() + CONTACT_SPACER + c.getUid() + CONTACT_SPACER;
            }
        }
        return result;
    }

    /**
     * Get vector of ContactType's from string
     *
     * @param source
     * @return
     */
    static public Vector vectorFromString(String  source) throws Exception {
        Vector result = new Vector();
        if (source == null) return result;

        int startIndex = 0;
        while (true) {
            ContactType c = new ContactType();
            // Set name
            int endIndex = source.indexOf(CONTACT_SPACER, startIndex);
            if (endIndex<0) break;
            c.setName(source.substring(startIndex, endIndex));
            startIndex = endIndex + CONTACT_SPACER.length();
            // Set uid
            endIndex = source.indexOf(CONTACT_SPACER, startIndex);
            if (endIndex<0) break;
            c.setUid(source.substring(startIndex, endIndex));
            startIndex = endIndex + CONTACT_SPACER.length();
            // Add
            result.addElement(c);
        }
        return result;
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
