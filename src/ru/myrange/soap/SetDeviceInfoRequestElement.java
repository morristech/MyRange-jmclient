/*
This class represetns the setDeviceInfoRequestElement element from myrange.wsdl

<xs:element name="setDeviceInfoRequestElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="header" type="tns:headerType"/>
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
</xs:element>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import ru.myrange.util.Password;

public class SetDeviceInfoRequestElement implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String clientVersionTag = "n0:clientVersion";
    private static final String btAddressTag = "n0:btAddress";
    private static final String devClassTag = "n0:devClass";
    private static final String friendlyNameTag = "n0:friendlyName";
    private static final String imeiTag = "n0:imei";
    private static final String vendorTag = "n0:vendor";
    private static final String modelTag = "n0:model";
    private static final String platformTag = "n0:platform";

    private Vector objects = new Vector();
    private HeaderType header = null;
    private String clientVersion = null;
    private String btAddress = null;
    private Integer devClass = null;
    private String friendlyName = null;
    private String imei = null;
    private String vendor = null;
    private String model = null;
	private String platform = null;

	/* Used by KSOAP Implementation methods */

	public SetDeviceInfoRequestElement() { }

	public SetDeviceInfoRequestElement(HeaderType header, String clientVersion, String btAddress) {
		setHeader(header);
        setClientVersion(clientVersion);
        setBtAddress(btAddress);
	}

    public SetDeviceInfoRequestElement(HeaderType header, String clientVersion, String btAddress, Integer devClass, String friendlyName, String imei, String vendor, String model, String platform) {
		setHeader(header);
        setClientVersion(clientVersion);
        setBtAddress(btAddress);
        setDevClass(devClass);
        setFriendlyName(friendlyName);
        setImei(imei);
        setVendor(vendor);
        setModel(model);
        setPlatform(platform);
	}

	public HeaderType getHeader() { return header; }
    public String getClientVersion() { return clientVersion; }
    public String getBtAddress() { return btAddress; }
    public Integer getDevClass() { return devClass; }
    public String getFriendlyName() { return friendlyName; }
    public String getImei() { return imei; }
    public String getVendor() { return vendor; }
    public String getModel() { return model; }
    public String getPlatform() { return platform; }

	public void setHeader(HeaderType header) {
        this.header = header;
    }
    public void setBtAddress(String btAddress) {
        this.btAddress = btAddress;
    }
    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
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

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (clientVersion != null) objects.addElement(new PropertyObject(clientVersionTag, PropertyInfo.STRING_CLASS, clientVersion));
        if (btAddress != null) objects.addElement(new PropertyObject(btAddressTag, PropertyInfo.STRING_CLASS, btAddress));
        if (devClass != null) objects.addElement(new PropertyObject(devClassTag, PropertyInfo.INTEGER_CLASS, devClass));
        if (friendlyName != null) objects.addElement(new PropertyObject(friendlyNameTag, PropertyInfo.STRING_CLASS, friendlyName));
        if (imei != null) objects.addElement(new PropertyObject(imeiTag, PropertyInfo.STRING_CLASS, imei));
        if (vendor != null) objects.addElement(new PropertyObject(vendorTag, PropertyInfo.STRING_CLASS, vendor));
        if (model != null) objects.addElement(new PropertyObject(modelTag, PropertyInfo.STRING_CLASS, model));
        if (platform != null) objects.addElement(new PropertyObject(platformTag, PropertyInfo.STRING_CLASS, platform));

        // Calculate XML string
        String xmlString = "";
        // Transform all propertyes to string
        for (int i=0; i<getPropertyCount(); i++){
            xmlString += getProperty(i).toString();
        }

        //Sign
        String login = getHeader().getLogin();
        String pass = getHeader().getHash();
        String entity = pass + xmlString;
        String hashResult = (Password.fromPlainText(entity)).toString();
        setHeader(new HeaderType(login, hashResult));

        // Pack
        objects.removeAllElements();
        if (header != null) objects.addElement(new PropertyObject(headerTag, PropertyInfo.OBJECT_CLASS, header));
        if (clientVersion != null) objects.addElement(new PropertyObject(clientVersionTag, PropertyInfo.STRING_CLASS, clientVersion));
        if (btAddress != null) objects.addElement(new PropertyObject(btAddressTag, PropertyInfo.STRING_CLASS, btAddress));
        if (devClass != null) objects.addElement(new PropertyObject(devClassTag, PropertyInfo.INTEGER_CLASS, devClass));
        if (friendlyName != null) objects.addElement(new PropertyObject(friendlyNameTag, PropertyInfo.STRING_CLASS, friendlyName));
        if (imei != null) objects.addElement(new PropertyObject(imeiTag, PropertyInfo.STRING_CLASS, imei));
        if (vendor != null) objects.addElement(new PropertyObject(vendorTag, PropertyInfo.STRING_CLASS, vendor));
        if (model != null) objects.addElement(new PropertyObject(modelTag, PropertyInfo.STRING_CLASS, model));
        if (platform != null) objects.addElement(new PropertyObject(platformTag, PropertyInfo.STRING_CLASS, platform));
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
