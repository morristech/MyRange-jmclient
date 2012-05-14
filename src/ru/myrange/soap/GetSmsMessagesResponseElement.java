/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:element name="getSmsMessagesResponseElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="users" minOccurs="0">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="sms" type="mr:smsType" minOccurs="0" maxOccurs="unbounded"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>
            <xs:element name="companies" minOccurs="0">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="sms" type="mr:smsType" minOccurs="0" maxOccurs="unbounded"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>
            <xs:element name="conferences" minOccurs="0">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="sms" type="mr:smsType" minOccurs="0" maxOccurs="unbounded"/>
                    </xs:sequence>
                </xs:complexType>
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
 * This class represetns the getSmsMessagesResponseElement element from myrange.wsdl
 * @author Oleg Ponfilenok
 */
public class GetSmsMessagesResponseElement implements KvmSerializable {

    private static final String usersTag = "users";
    private static final String companiesTag = "companies";
    private static final String conferencesTag = "conferences";
    private static final String smsTag = "sms";

    private Vector objects = new Vector();
	private Vector smsMessages = new Vector();

	/* Used by KSOAP Implementation methods */

    public GetSmsMessagesResponseElement(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            // TODO. Now get SMSes only from users!
            if (soapObjInfo.name.equals(usersTag)) {
                ParseSmsMessages((SoapObject) soapObj.getProperty(i));
            }
        }
	}

    public void ParseSmsMessages(SoapObject soapObj) throws Exception {
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(smsTag)) {
                addSmsMessage( new SmsMessage((SoapObject) soapObj.getProperty(i)) );
            }
        }
	}

	public Vector getSmsMessages() { return smsMessages; }

	public void setSmsMessage(Vector smsMessages) {
        this.smsMessages = smsMessages;
    }
    public void addSmsMessage(SmsMessage newSmsMessage) {
        smsMessages.addElement(newSmsMessage);
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