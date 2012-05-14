/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:element name="registerRequestElement">
    <xs:complexType>
        <xs:all>
            <xs:element name="login" type="xs:string"/>
            <xs:element name="firstName" type="xs:string"/>
            <xs:element name="lastName" type="xs:string"/>
            <xs:element name="confId" type="xs:int" minOccurs="0"/>
        </xs:all>
    </xs:complexType>
</xs:element>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * This class represetns the registerRequestElement element from myrange.wsdl
 *
 * @author Oleg Ponfilenok
 */
public class RegisterRequestElement implements KvmSerializable {

    private static final String loginTag = "n0:login";
    private static final String firstNameTag = "n0:firstName";
    private static final String lastNameTag = "n0:lastName";
    private static final String confIdTag = "n0:confId";

    private Vector objects = new Vector();
    private String login = null;
    private String firstName = null;
    private String lastName = null;
    private Vector confIds = null;

	/* Used by KSOAP Implementation methods */

    public RegisterRequestElement(String login, String firstName, String lastName, Vector confIds) {
		setLogin(login);
        setFirstName(firstName);
        setLastName(lastName);
        setConfIds(confIds);
	}

	public String getLogin() { return login; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Vector getConfIds() { return confIds; }

	public void setLogin(String login) {
        this.login = login;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setConfIds(Vector ids) {
        this.confIds = ids;
    }

    // Pack element befor sending
    public void pack() {
        // Pack
        objects.removeAllElements();
        if (getLogin() != null) objects.addElement(new PropertyObject(loginTag, PropertyInfo.STRING_CLASS, getLogin()));
        if (getFirstName() != null) objects.addElement(new PropertyObject(firstNameTag, PropertyInfo.STRING_CLASS, getFirstName()));
        if (getLastName() != null) objects.addElement(new PropertyObject(lastNameTag, PropertyInfo.STRING_CLASS, getLastName()));
        for (int i=0; i < getConfIds().size(); i++) {
            if (getConfIds().elementAt(i) != null) objects.addElement(new PropertyObject(confIdTag, PropertyInfo.INTEGER_CLASS, getConfIds().elementAt(i)));
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
