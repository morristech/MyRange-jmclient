/*
<xs:element name="getPrivateMessagesResponseElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="privateMessage" minOccurs="0" maxOccurs="unbounded">
                ...
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

public class GetPrivateMessagesResponseElement implements KvmSerializable {

    private static final String smsMessageTag = "privateMessage";

    private Vector objects = new Vector();
	private Vector privateMessages = new Vector();
    private Boolean preview = null;

	/* Used by KSOAP Implementation methods */

	public GetPrivateMessagesResponseElement() { }

    public GetPrivateMessagesResponseElement(SoapObject soapObj, final Boolean preview) throws Exception {	// Create new class from SoapObject
        setPreview(preview);
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(smsMessageTag)) {
                PrivateMessage newMsg = new PrivateMessage((SoapObject) soapObj.getProperty(i));
                newMsg.setPreview(preview);
                addPrivateMessage(newMsg);
            }
        }
	}

	public Vector getPrivateMessages() { return privateMessages; }
    public Boolean getPreview() { return preview; }

	public void setPrivateMessage(Vector privateMessages) {
        this.privateMessages = privateMessages;
    }
    public void addPrivateMessage(PrivateMessage newPrivateMessage) {
        privateMessages.addElement(newPrivateMessage);
    }
    public void setPreview(Boolean preview) {
        this.preview = preview;
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