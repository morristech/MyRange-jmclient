package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 *
 * @author Nickolay Yegorov
 */

/* This class represents getBusinessMeetsResponse object from WSDL
 * 
 *          <xs:element name="getBusinessMeetsResponseElement">
 *	        <xs:complexType>
 *	          <xs:sequence>
 *	            <xs:element minOccurs="0" maxOccurs="unbounded" name="businessMeet" type="mr:businessMeetInfoType" />
 *	          </xs:sequence>
 *	        </xs:complexType>
 *	      </xs:element>
 */
public class GetBusinessMeetsResponse implements KvmSerializable{

    private static final String businessMeetTag = "businessMeet";
    private Vector objects = new Vector();
    private Vector businessMeets = new Vector();

    public GetBusinessMeetsResponse(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i = 0; i < soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(businessMeetTag)) {
                addBusinessMeet(new BusinessMeetInfoType((SoapObject) soapObj.getProperty(i)));
            }
        }
    }

    public Vector getBusinessMeets() {
        return businessMeets;
    }

    public void setBusinessMeets(Vector meets) {
        this.businessMeets = meets;
    }

    public void addBusinessMeet(BusinessMeetInfoType newBM) {
        businessMeets.addElement(newBM);
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
