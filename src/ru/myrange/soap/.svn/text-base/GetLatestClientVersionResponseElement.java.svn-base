/*
This class represetns the getLatestClientVersionResponseElement element from myrange.wsdl

<xs:element name="getLatestClientVersionResponseElement">
    <xs:complexType>
        <xs:all>
            <xs:element name="latestClientVersion" type="xs:string"/>
            <xs:element name="latestClientURL" type="xs:string" minOccurs="0"/>
            <xs:element name="changes" type="xs:string" minOccurs="0"/>
            <xs:element name="criticalChanges" type="xs:string" minOccurs="0"/>
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

public class GetLatestClientVersionResponseElement implements KvmSerializable {

    private static final String latestClientVersionTag = "latestClientVersion";
    private static final String latestClientURLTag = "latestClientURL";
    private static final String changesTag = "changes";
    private static final String criticalChangesTag = "criticalChanges";

    private Vector objects = new Vector();
    private String latestClientVersion = null;
    private String latestClientURL = null;
    private String changes = null;
    private String criticalChanges = null;

	/* Used by KSOAP Implementation methods */

	public GetLatestClientVersionResponseElement() {

	}

	public GetLatestClientVersionResponseElement(SoapObject soapObj) {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
			PropertyInfo soapObjInfo = new PropertyInfo();
			// Get property name of SoapObject
			soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(latestClientVersionTag)) {
                setLatestClientVersion(soapObj.getProperty(i).toString());
            }
            else if (soapObjInfo.name.equals(latestClientURLTag)) {
                setLatestClientURL(soapObj.getProperty(i).toString());
            }
            else if (soapObjInfo.name.equals(changesTag)) {
                setChanges(soapObj.getProperty(i).toString());
            }
            else if (soapObjInfo.name.equals(criticalChangesTag)) {
                setCriticalChanges(soapObj.getProperty(i).toString());
            }
		}
	}

	public String getLatestClientVersion() { return latestClientVersion; }
    public String getLatestClientURL() { return latestClientURL; }
    public String getChanges() { return changes; }
    public String getCriticalChanges() { return criticalChanges; }

	public void setLatestClientVersion(String latestClientVersion) {
        this.latestClientVersion = latestClientVersion;
        objects.addElement(new PropertyObject(latestClientVersionTag, PropertyInfo.STRING_CLASS, latestClientVersion));
    }
    public void setLatestClientURL(String latestClientURL) {
        this.latestClientURL = latestClientURL;
        objects.addElement(new PropertyObject(latestClientURLTag, PropertyInfo.STRING_CLASS, latestClientURL));
    }
    public void setChanges(String changes) {
        this.changes = changes;
        objects.addElement(new PropertyObject(changesTag, PropertyInfo.STRING_CLASS, changes));
    }
    public void setCriticalChanges(String criticalChanges) {
        this.criticalChanges = criticalChanges;
        objects.addElement(new PropertyObject(criticalChangesTag, PropertyInfo.STRING_CLASS, criticalChanges));
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