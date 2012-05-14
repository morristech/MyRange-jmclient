/*
<xs:element name="getPrivateMessagesRequestElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="header" type="tns:headerType"/>
            <xs:element name="preview" type="xs:boolean"/>
            <xs:choice>
                <xs:element name="privateMsgId" type="xs:int" maxOccurs="unbounded"/>
                <xs:sequence>
                    <xs:element name="userId" type="xs:int" minOccurs="0"/>
                    <xs:element name="type" type="tns:privateMessageType"/>
                    <xs:element name="fromIndex" type="xs:int" minOccurs="0"/>
                    <xs:element name="number" type="xs:int" minOccurs="0"/>
                    <xs:element name="lastUpdatedTime" type="xs:long" minOccurs="0"/>
                </xs:sequence>
            </xs:choice>
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

public class GetPrivateMessagesRequestElement implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String privateMsgIdTag = "n0:privateMsgId";
    private static final String userIdTag = "n0:userId";
    private static final String typeTag = "n0:type";
    private static final String previewTag = "n0:preview";
    private static final String fromIndexTag = "n0:fromIndex";
    private static final String numberTag = "n0:number";
    private static final String lastUpdatedTimeTag = "n0:lastUpdatedTime";

    private Vector objects = new Vector();
    private HeaderType header = null;
    private Vector privateMsgId = new Vector();
	private Integer userId = null;
    private String type = null;
    private Boolean preview = null;
    private Integer fromIndex = null;
    private Integer number = null;
    private Long lastUpdatedTime = null;

	/* Used by KSOAP Implementation methods */

	public GetPrivateMessagesRequestElement(HeaderType header, Boolean preview, Vector privateMsgId) {
        setHeader(header);
        setPreview(preview);
        setPrivateMsgId(privateMsgId);
	}

	public GetPrivateMessagesRequestElement(HeaderType header, Integer userId, Boolean preview, long lastUpdatedTime, Integer number) {
		setHeader(header);
        setUserId(userId);
        setType(PrivateMessage.DIRECTION_BOTH);
        setPreview(preview);
        setLastUpdatedTime(lastUpdatedTime);
        setNumber(number);
	}

    public GetPrivateMessagesRequestElement(HeaderType header, Integer userId, Boolean preview, Integer fromIndex, Integer number) {
        setHeader(header);
        setUserId(userId);
        setType(PrivateMessage.DIRECTION_BOTH);
        setPreview(preview);
        setFromIndex(fromIndex);
        setNumber(number);
	}

	public HeaderType getHeader() { return header; }
    public Vector getPrivateMsgId() { return privateMsgId; }
    public Integer getUserId() { return userId; }
    public String getType() { return type; }
    public Boolean getPreview() { return preview; }
    public Integer getFromIndex() { return fromIndex; }
    public Integer getNumber() { return number; }
    public Long getLastUpdatedTime() { return lastUpdatedTime; }

	public void setHeader(HeaderType header) {
        this.header = header;
    }
    public void setPrivateMsgId(Vector privateMsgId) {
        this.privateMsgId = privateMsgId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setPreview(Boolean preview) {
        this.preview = preview;
    }
    public void setFromIndex(Integer fromIndex) {
        this.fromIndex = fromIndex;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public void setLastUpdatedTime(long lastUpdatedTime) {
        this.lastUpdatedTime = new Long(lastUpdatedTime);
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (getPreview() != null) objects.addElement(new PropertyObject(previewTag, PropertyInfo.BOOLEAN_CLASS, getPreview()));
        if (getPrivateMsgId() != null) {
            for (int i=0; i<getPrivateMsgId().size(); i++) {
                if (getPrivateMsgId().elementAt(i) != null) objects.addElement(new PropertyObject(privateMsgIdTag, PropertyInfo.INTEGER_CLASS, getPrivateMsgId().elementAt(i)));
            }
        }
        if (getUserId() != null) objects.addElement(new PropertyObject(userIdTag, PropertyInfo.INTEGER_CLASS, getUserId()));
        if (getType() != null) objects.addElement(new PropertyObject(typeTag, PropertyInfo.STRING_CLASS, getType()));
        if (getFromIndex() != null) objects.addElement(new PropertyObject(fromIndexTag, PropertyInfo.INTEGER_CLASS, getFromIndex()));
        if (getNumber() != null) objects.addElement(new PropertyObject(numberTag, PropertyInfo.INTEGER_CLASS, getNumber()));
        if (getLastUpdatedTime() != null) objects.addElement(new PropertyObject(lastUpdatedTimeTag, PropertyInfo.LONG_CLASS, getLastUpdatedTime()));

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
        if (getHeader() != null) objects.addElement(new PropertyObject(headerTag, PropertyInfo.OBJECT_CLASS, getHeader()));
        if (getPreview() != null) objects.addElement(new PropertyObject(previewTag, PropertyInfo.BOOLEAN_CLASS, getPreview()));
        if (getPrivateMsgId() != null) {
            for (int i=0; i<getPrivateMsgId().size(); i++) {
                if (getPrivateMsgId().elementAt(i) != null) objects.addElement(new PropertyObject(privateMsgIdTag, PropertyInfo.INTEGER_CLASS, getPrivateMsgId().elementAt(i)));
            }
        }
        if (getUserId() != null) objects.addElement(new PropertyObject(userIdTag, PropertyInfo.INTEGER_CLASS, getUserId()));
        if (getType() != null) objects.addElement(new PropertyObject(typeTag, PropertyInfo.STRING_CLASS, getType()));
        if (getFromIndex() != null) objects.addElement(new PropertyObject(fromIndexTag, PropertyInfo.INTEGER_CLASS, getFromIndex()));
        if (getNumber() != null) objects.addElement(new PropertyObject(numberTag, PropertyInfo.INTEGER_CLASS, getNumber()));
        if (getLastUpdatedTime() != null) objects.addElement(new PropertyObject(lastUpdatedTimeTag, PropertyInfo.LONG_CLASS, getLastUpdatedTime()));
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