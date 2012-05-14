/*
<xs:complexType name="deviceInfoType">
    <xs:sequence>
        <xs:element name="clientVersion" type="xs:string"/>
        <xs:element name="btAddress" type="xs:string"/>
        <xs:element name="devClass" type="xs:int" minOccurs="0"/>
        <xs:element name="friendlyName" type="xs:string" minOccurs="0"/>
        <xs:element name="imei" type="xs:string" minOccurs="0"/>
        <xs:element name="vendor" type="xs:string" minOccurs="0"/>
        <xs:element name="model" type="xs:string" minOccurs="0"/>
        <xs:element name="platform" type="xs:string" minOccurs="0"/>
    </xs:sequence>
</xs:complexType>
*/

package ru.myrange.soap;

import java.util.Vector;
import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 * This class represetns the deviceInfoType element from myrange.wsdl
 *
 * @author Oleg Ponfilenok
 */
public class DeviceInfoType implements KvmSerializable {

    private static final String clientVersionTag = "clientVersion";
    private static final String btAddressTag = "btAddress";
    private static final String devClassTag = "devClass";
    private static final String friendlyNameTag = "friendlyName";
    private static final String imeiTag = "imei";
    private static final String vendorTag = "vendor";
    private static final String modelTag = "model";
    private static final String platformTag = "platform";

    private Vector objects = new Vector();

    private String clientVersion = null;
    private String btAddress = null;
    private Integer devClass = null;
    private String friendlyName = null;
    private String imei = null;
    private String vendor = null;
    private String model = null;
	private String platform = null;

    /* Used by KSOAP Implementation methods */

    public DeviceInfoType(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(clientVersionTag)) {
                setClientVersion( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(btAddressTag)) {
                setBtAddress( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(devClassTag)) {
                setDevClass( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(friendlyNameTag)) {
                setFriendlyName( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(imeiTag)) {
                setImei( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(vendorTag)) {
                setVendor( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(modelTag)) {
                setModel( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(platformTag)) {
                setPlatform( soapObj.getProperty(i).toString() );
            }
        }
	}

	public String getClientVersion() { return clientVersion; }
    public String getBtAddress() { return btAddress; }
    public Integer getDevClass() { return devClass; }
    public String getFriendlyName() { return friendlyName; }
    public String getImei() { return imei; }
    public String getVendor() { return vendor; }
    public String getModel() { return model; }
    public String getPlatform() { return platform; }
    
    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
    public void setBtAddress(String btAddress) {
        this.btAddress = btAddress;
    }
    public void setDevClass(Integer devClass) {
        this.devClass = devClass;
    }
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }
    public void setImei(String imei) {
        this.imei = imei;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
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