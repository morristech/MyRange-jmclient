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
 *             <xs:element name="conferenceCallResponseElement">
974                 <xs:complexType>
975                     <xs:sequence>
976                         <xs:element name="callStatus" type="xs:int"/>
977                     </xs:sequence>
978                 </xs:complexType>
979             </xs:element>

 */
public class ConferenceCallResponse implements KvmSerializable{

    private static final String callStatusTag = "callStatus";
    private Vector objects = new Vector();
    private Integer callStatus = null;

    public ConferenceCallResponse(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i = 0; i < soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(callStatusTag)) {
               setCallStatus(new Integer(Integer.parseInt(soapObj.getProperty(i).toString())));
            }
        }
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

    /**
     * @return the callStatus
     */
    public Integer getCallStatus() {
        return callStatus;
    }

    /**
     * @param callStatus the callStatus to set
     */
    public void setCallStatus(Integer callStatus) {
        this.callStatus = callStatus;
    }
}
