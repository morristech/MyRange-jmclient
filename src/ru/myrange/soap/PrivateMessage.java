/*
<xs:element name="privateMessage" minOccurs="0" maxOccurs="unbounded">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="privateMsgId" type="xs:int"/>
                <xs:element name="privateMsgIndex" type="xs:int" minOccurs="0"/>
                <xs:element name="userId" type="xs:int"/>
                <xs:element name="time" type="xs:long"/>
                <xs:element name="type" type="tns:privateMessageType"/>
                <xs:element name="status" type="tns:messageStatusType"/>
                <xs:element name="subject" type="xs:string" minOccurs="0"/>
                <xs:element name="body" type="xs:string"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>
</xs:sequence>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class PrivateMessage extends Message implements KvmSerializable {

    private static final String privateMsgIdTag = "privateMsgId";
    private static final String privateMsgIndexTag = "privateMsgIndex";
    private static final String userIdTag = "userId";
    private static final String timeTag = "time";
    private static final String directionTag = "type";
    private static final String statusTag = "status";
    private static final String subjectTag = "subject";
    private static final String bodyTag = "body";

    private Vector objects = new Vector();
    private Integer privateMsgIndex = new Integer(0);   // Don't know why it's need?

    /* Used by KSOAP Implementation methods */

    public PrivateMessage(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(privateMsgIdTag)) {
                setMsgId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(privateMsgIndexTag)) {
                setPrivateMsgIndex( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(userIdTag)) {
                setSenderId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(timeTag)) {
                setTime( new Long(Long.parseLong(soapObj.getProperty(i).toString())).longValue() );
            }
            else if (soapObjInfo.name.equals(directionTag)) {
                setDirection( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(statusTag)) {
                setStatus( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(subjectTag)) {
                setSubject( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(bodyTag)) {
                setMessage( soapObj.getProperty(i).toString() );
            }
        }
        setType(Message.TYPE_PRIVATE);
	}

    private Integer getPrivateMsgIndex() { return privateMsgIndex; }

    private void setPrivateMsgIndex(Integer privateMsgIndex) {
        if (privateMsgIndex != null) this.privateMsgIndex = privateMsgIndex;
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