/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:element name="editFavoritesResponse">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="status" type="xs:int"/>
        </xs:sequence>
    </xs:complexType>
</xs:element>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 * This class represetns the editFavoritesResponse type from myrange.wsdl
 * @author Oleg Ponfilenok
 */
public class EditFavoritesResponse implements KvmSerializable {

    private static final String responseTag = "response";

    private Vector objects = new Vector();
    private Integer response = null;

	/* Used by KSOAP Implementation methods */

    // Create new class from SoapObject
    public EditFavoritesResponse(SoapObject soapObj) throws Exception {
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
			PropertyInfo soapObjInfo = new PropertyInfo();
			// Get property name of SoapObject
			soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(responseTag)) {
                setResponse( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
		}
	}

	public Integer getResponse() { return response; }

	public void setResponse(Integer response) {
        this.response = response;
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
