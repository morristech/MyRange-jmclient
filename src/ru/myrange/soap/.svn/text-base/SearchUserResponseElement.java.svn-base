/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:element name="searchUserResponseElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="userId" type="xs:int" minOccurs="0" maxOccurs="unbounded"/>
        </xs:sequence>
    </xs:complexType>
</xs:element>
*/

package ru.myrange.soap;

import java.util.Vector;
import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 * SearchUserResponseElement implementation
 *
 * @author Oleg Ponfilenok
 */
public class SearchUserResponseElement implements KvmSerializable {

    private static final String userIdTag = "userId";

    private Vector objects = new Vector();
	private Vector userIdSequence = new Vector();

	/* Used by KSOAP Implementation methods */

    public SearchUserResponseElement(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(userIdTag)) {
                addUserId(new Integer(Integer.parseInt(soapObj.getProperty(i).toString())));
            }
        }
	}

	public Vector getUserIdSequence() { return userIdSequence; }

	public void setUserIdSequence(Vector userIdSequence) {
        this.userIdSequence = userIdSequence;
    }
    public void addUserId(Integer userId) {
        userIdSequence.addElement(userId);
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