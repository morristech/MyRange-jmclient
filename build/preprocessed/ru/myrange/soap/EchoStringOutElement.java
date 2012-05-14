/*
This class represetns the echoStringOutElement element from myrange.wsdl

<xs:element name="echoStringOutElement">
    <xs:complexType>
        <xs:all>
            <xs:element name="stringOut" type="xs:string"/>
        </xs:all>
    </xs:complexType>
</xs:element>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class EchoStringOutElement implements KvmSerializable {

    private static final String stringOutTag = "stringOut";

    private Vector objects = new Vector();
    private String stringOut = null;
	
	/* Used by KSOAP Implementation methods */
	
	public EchoStringOutElement() {
		
	}
	
	public EchoStringOutElement(SoapObject soapObj) {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
			PropertyInfo soapObjInfo = new PropertyInfo();
			// Get property name of SoapObject
			soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(stringOutTag)) {
                setStringOut(soapObj.getProperty(i).toString());
            }
			//objects.addElement(new PropertyObject(soapObjInfo.name, soapObjInfo.type.getClass(), soapObj.getProperty(i)));
		}
	}
	
	public EchoStringOutElement(String stringOut) {
		setStringOut(stringOut);
	}
	
	public String getStringOut() { return stringOut; }
	
	public void setStringOut(String stringOut) {
        this.stringOut = stringOut;
        objects.addElement(new PropertyObject(stringOutTag, PropertyInfo.STRING_CLASS, stringOut));
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
