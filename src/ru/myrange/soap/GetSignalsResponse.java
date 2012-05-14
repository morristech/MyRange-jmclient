/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:element name="getSignalsResponse">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="userId" type="xs:int" minOccurs="0" maxOccurs="unbounded"/>
            <!--<xs:element name="compId" type="xs:int" minOccurs="0" maxOccurs="unbounded"/>-->
            <!--<xs:element name="confId" type="xs:int" minOccurs="0" maxOccurs="unbounded"/>-->
            <!--<xs:element name="bboardId" type="xs:int" minOccurs="0" maxOccurs="unbounded"/>-->
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
 * This class represetns the getSignalsResponse element from myrange.wsdl
 * @author Oleg Ponfilenok
 */
public class GetSignalsResponse implements KvmSerializable {

    private static final String userIdTag = "userId";

    private Vector objects = new Vector();
	private Vector userId = new Vector();

	/* Used by KSOAP Implementation methods */

    public GetSignalsResponse(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(userIdTag)) {
                addUserId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
        }
	}

	public Vector getUserId() { return userId; }

	public void setUserId(Vector userId) {
        this.userId = userId;
    }
    public void addUserId(Integer newUserId) {
        userId.addElement(newUserId);
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