/*
This class represetns the sendBtMeetsRequestElement element from myrange.wsdl

<xs:element name="sendBtMeetsRequestElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="header" type="tns:headerType"/>
            <xs:element name="btAddress" type="xs:string"/>
            <xs:element name="devClass" type="xs:int" minOccurs="0"/>
            <xs:element name="friendlyName" type="xs:string" minOccurs="0"/>
            <xs:element name="btMeet" maxOccurs="unbounded">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="btAddress" type="xs:string"/>
                        <xs:element name="devClass" type="xs:int" minOccurs="0"/>
                        <xs:element name="friendlyName" type="xs:string" minOccurs="0"/>
                        <xs:element name="startTime" type="xs:long"/>
                        <xs:element name="endTime" type="xs:long"/>
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
import ru.myrange.util.Password;

public class SendBtMeetsRequestElement implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String btAddressTag = "n0:btAddress";
    private static final String devClassTag = "n0:devClass";
    private static final String friendlyNameTag = "n0:friendlyName";
    private static final String btMeetTag = "n0:btMeet";

    private Vector objects = new Vector();
    private HeaderType header = null;
    private String btAddress = null;
    private Integer devClass = null;
    private String friendlyName = null;
    private Vector btMeetVector = null;

    /* Used by KSOAP Implementation methods */

    public SendBtMeetsRequestElement() { }

    public SendBtMeetsRequestElement(HeaderType header, String btAddress, Vector btMeetVector) {
        setHeader(header);
        setBtAddress(btAddress);
        setBtMeetVector(btMeetVector);
    }

    public SendBtMeetsRequestElement(HeaderType header, String btAddress, Integer devClass, String friendlyName, Vector btMeetVector) {
        setHeader(header);
        setBtAddress(btAddress);
        setDevClass(devClass);
        setFriendlyName(friendlyName);
        setBtMeetVector(btMeetVector);
    }

    public HeaderType getHeader() { return header; }
    public String getBtAddress() { return btAddress; }
    public Integer getDevClass() { return devClass; }
    public String getFriendlyName() { return friendlyName; }
    public Vector getBtMeetVector() { return btMeetVector; }

    public void setHeader(HeaderType header) {
        this.header = header;
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
    public void setBtMeetVector(Vector btMeetVector) {
        if (btMeetVector == null) return;
        this.btMeetVector = btMeetVector;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (btAddress != null) objects.addElement(new PropertyObject(btAddressTag, PropertyInfo.STRING_CLASS, btAddress));
        if (devClass != null) objects.addElement(new PropertyObject(devClassTag, PropertyInfo.INTEGER_CLASS, devClass));
        if (friendlyName != null) objects.addElement(new PropertyObject(friendlyNameTag, PropertyInfo.STRING_CLASS, friendlyName));
        for (int i=0; i<btMeetVector.size(); i++) {
            BtMeet btMeet = (BtMeet) btMeetVector.elementAt(i);
            btMeet.pack();
            for (int j=0; j<btMeet.getPropertyCount(); j++) {
                PropertyInfo info = new PropertyInfo();
                btMeet.getPropertyInfo(j, new Hashtable(), info);
                if (btMeet.getProperty(j) != null) objects.addElement(new PropertyObject(info.name, info.type.getClass(), btMeet.getProperty(j)));
            }
        }
        // Calculate XML string
        String xmlString = "";
        // Transform all propertyes to string
        for (int i=0; i<getPropertyCount(); i++){
            xmlString += getProperty(i).toString();
        }

        /* *** DEBUG PRINT ***
        byte[] bin = xmlString.getBytes("UTF-8");
        MyMIDlet.out.println("START");
        int n = 0;
        for (int i=0; i<bin.length; i++) {
            n++;

            int ch = (int) bin[i];
            if (ch < 0) {
                ch = ch + 256;    
            }
            MyMIDlet.out.print(Integer.toString(ch) + " ");
            if (n >= 10) {
                MyMIDlet.out.println("    ");
                n = 0;
            }
        }
        MyMIDlet.out.println("    ");
        MyMIDlet.out.println("END");
        */


        //Sign
        String login = getHeader().getLogin();
        String pass = getHeader().getHash();
        String entity = pass + xmlString;
        String hashResult = (Password.fromPlainText(entity)).toString();
        setHeader(new HeaderType(login, hashResult));

        // Pack
        objects.removeAllElements();
        if (header != null) objects.addElement(new PropertyObject(headerTag, PropertyInfo.OBJECT_CLASS, header));
        if (btAddress != null) objects.addElement(new PropertyObject(btAddressTag, PropertyInfo.STRING_CLASS, btAddress));
        if (devClass != null) objects.addElement(new PropertyObject(devClassTag, PropertyInfo.INTEGER_CLASS, devClass));
        if (friendlyName != null) objects.addElement(new PropertyObject(friendlyNameTag, PropertyInfo.STRING_CLASS, friendlyName));
        for (int i=0; i<btMeetVector.size(); i++) {
            BtMeet btMeet = (BtMeet) btMeetVector.elementAt(i);
            btMeet.pack();
            objects.addElement(new PropertyObject(btMeetTag, PropertyInfo.OBJECT_CLASS, btMeet));
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

}