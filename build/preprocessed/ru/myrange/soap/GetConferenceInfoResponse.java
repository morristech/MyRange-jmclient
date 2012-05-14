/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */

/*
<xs:element name="getConferenceInfoResponse">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="confInfo" type="mr:confInfoType" minOccurs="0" maxOccurs="unbounded"/>
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
 * This class represetns the getConferenceInfoResponse element from myrange.wsdl
 *
 * @author Nickolay Yegorov
 */
public class GetConferenceInfoResponse implements KvmSerializable {

    private static final String confInfoTag = "confInfo";

    private Vector objects = new Vector();
	private Vector confInfo = new Vector();

	/* Used by KSOAP Implementation methods */

    public GetConferenceInfoResponse(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(confInfoTag)) {
                addConfInfo( new ConfInfo((SoapObject) soapObj.getProperty(i)) );
            }
        }
	}

	public Vector getConfInfo() { return confInfo; }

	public void setConfInfo(Vector confInfo) {
        this.confInfo = confInfo;
    }
    public void addConfInfo(ConfInfo newConfInfo) {
        confInfo.addElement(newConfInfo);
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