/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:complexType name="smsType">
    <xs:sequence>
        <xs:element name="smsId" type="xs:int"/>
        <xs:element name="senderId" type="xs:int"/>
        <xs:element name="time" type="xs:long"/>
        <xs:element name="message" type="xs:string"/>
    </xs:sequence>
</xs:complexType>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 * This class represetns the smsMessage type from myrange.wsdl
 * @author Oleg Ponfilenok
 */
public class SmsMessage extends Message implements KvmSerializable {

    private static final String smsIdTag = "smsId";
    private static final String senderIdTag = "senderId";
    private static final String timeTag = "time";
    private static final String messageTag = "message";

    private Vector objects = new Vector();
    
    /* Used by KSOAP Implementation methods */

    public SmsMessage(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(smsIdTag)) {
                setMsgId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(senderIdTag)) {
                setSenderId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(timeTag)) {
                setTime( new Long(Long.parseLong(soapObj.getProperty(i).toString())).longValue() );
            }
            else if (soapObjInfo.name.equals(messageTag)) {
                setMessage( soapObj.getProperty(i).toString() );
            }
        }
        setType(TYPE_SMS);
        setDirection(DIRECTION_INBOX);
        setStatus(STATUS_READ);
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