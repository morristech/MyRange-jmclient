/*
This class represetns the getDeviceInfoResponseElement element from myrange.wsdl

<xs:element name="getDeviceInfoResponseElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="deviceInfo" type="tns:deviceInfoType" minOccurs="0" maxOccurs="unbounded"/>
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

public class GetDeviceInfoResponseElement implements KvmSerializable {

    private static final String deviceInfoTag = "deviceInfo";

    private Vector objects = new Vector();
	private Vector deviceInfo = new Vector();

	/* Used by KSOAP Implementation methods */

    public GetDeviceInfoResponseElement(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(deviceInfoTag)) {
                addDevicesInfo( new DeviceInfoType((SoapObject) soapObj.getProperty(i)) );
            }
        }
	}

	public Vector getDevicesInfo() { return deviceInfo; }

	public void setDevicesInfo(Vector userInfo) {
        this.deviceInfo = userInfo;
    }
    public void addDevicesInfo(DeviceInfoType newDeviceInfo) {
        deviceInfo.addElement(newDeviceInfo);
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