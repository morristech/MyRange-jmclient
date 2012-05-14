/*
This class represetns the headerType type from myrange.wsdl

<xs:complexType name="headerType">
    <xs:all>
        <xs:element name="login" type="xs:string"/>
        <xs:element name="hash" type="xs:string"/>
        <!--<xs:element name="time" type="xs:dateTime"/>-->
    </xs:all>
</xs:complexType>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class HeaderType implements KvmSerializable {

    private static final String loginTag = "n0:login";
    private static final String hashTag = "n0:hash";

    private Vector objects = new Vector();
    private String login = null;
	private String hash = null;
	
	/* Used by KSOAP Implementation methods */
	
	public HeaderType() {
		
	}
	
	public HeaderType(String login, String hash) {
		setLogin(login);
		setHash(hash);
	}
		
	public String getLogin() { return login; }
	
	public String getHash() { return hash; }
	
	public void setLogin(String login) {
        this.login = login;
        objects.addElement(new PropertyObject(loginTag, PropertyInfo.STRING_CLASS, login));
    }
	
	public void setHash(String hash) {
        this.hash = hash;
        objects.addElement(new PropertyObject(hashTag, PropertyInfo.STRING_CLASS, hash));
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
