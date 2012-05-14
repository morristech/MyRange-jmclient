/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:element name="getUserInfoResponseElement">
    <xs:complexType>
        <xs:sequence minOccurs="0" maxOccurs="unbounded">
            <xs:element name="userInfo" >
                ...
            </xs:element>
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
 * This class represetns the GetUserInfoResponseElement element from myrange.wsdl
 *
 * @author Oleg Ponfilenok
 */
public class GetUserInfoResponseElement implements KvmSerializable {

    private static final String userInfoTag = "userInfo";

    private Vector objects = new Vector();
	private Vector usersInfo = new Vector();

	/* Used by KSOAP Implementation methods */

	public GetUserInfoResponseElement() { }

    public GetUserInfoResponseElement(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(userInfoTag)) {
                addUsersInfo( new UserInfo((SoapObject) soapObj.getProperty(i)) );
            }
        }
	}

	public Vector getUsersInfo() { return usersInfo; }

	public void setUsersInfo(Vector userInfo) {
        this.usersInfo = userInfo;
    }
    public void addUsersInfo(UserInfo newUserInfo) {
        usersInfo.addElement(newUserInfo);
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