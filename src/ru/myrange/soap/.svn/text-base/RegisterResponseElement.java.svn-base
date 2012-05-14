/*
This class represetns the registerResponseElement type from myrange.wsdl

<xs:element name="registerResponseElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="id" type="xs:int"/>
            <xs:element name="password" type="xs:string"/>
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

public class RegisterResponseElement implements KvmSerializable {

    private static final String idTag = "id";
    private static final String passwordTag = "password";

    private Vector objects = new Vector();
    private Integer id = null;
    private String password = "";

	/* Used by KSOAP Implementation methods */

	public RegisterResponseElement() {

	}

    public RegisterResponseElement(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
			PropertyInfo soapObjInfo = new PropertyInfo();
			// Get property name of SoapObject
			soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(idTag)) {
                setId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(passwordTag)) {
                setPassword( soapObj.getProperty(i).toString() );
            }
		}
	}

	public Integer getId() { return id; }
    public String getPassword() { return password; }

	public void setId(Integer id) {
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
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
