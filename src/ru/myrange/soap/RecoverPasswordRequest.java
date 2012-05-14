/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:element name="recoverPasswordRequest">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="login" type="xs:string"/>
        </xs:sequence>
    </xs:complexType>
</xs:element>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * This class represetns the recoverPasswordRequest element from myrange.wsdl
 *
 * @author Oleg Ponfilenok
 */
public class RecoverPasswordRequest implements KvmSerializable {

    private static final String loginTag = "n0:login";

    private Vector objects = new Vector();
    private String login = null;

	/* Used by KSOAP Implementation methods */

	public RecoverPasswordRequest(String login) {
		setLogin(login);
	}

	public String getLogin() { return login; }

	public void setLogin(String login) {
        this.login = login;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack
        objects.removeAllElements();
        if (getLogin() != null) objects.addElement(new PropertyObject(loginTag, PropertyInfo.STRING_CLASS, getLogin()));
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
