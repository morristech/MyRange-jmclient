/*
This class represetns the getSmsMessagesRequestElement element from myrange.wsdl

<xs:element name="getSmsMessagesRequestElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="header" type="tns:headerType"/>
            <xs:element name="startTime" type="xs:long"/>
            <xs:element name="endTime" type="xs:long"/>
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

public class GetSmsMessagesRequestElement implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String startTimeTag = "n0:startTime";
    private static final String endTimeTag = "n0:endTime";

    private Vector objects = new Vector();
    private HeaderType header = null;
	private Long startTime = new Long(0);
    private Long endTime = new Long(0);

	/* Used by KSOAP Implementation methods */

	public GetSmsMessagesRequestElement() {

	}

	public GetSmsMessagesRequestElement(HeaderType header, Long startTime, Long endTime) {
		setHeader(header);
		setStartTime(startTime);
        setEndTime(endTime);
	}

	public HeaderType getHeader() { return header; }
    public Long getStartTime() { return startTime; }
    public Long getEndTime() { return endTime; }

	public void setHeader(HeaderType header) {
        this.header = header;
    }
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (getStartTime() != null) objects.addElement(new PropertyObject(startTimeTag, PropertyInfo.LONG_CLASS, getStartTime()));
        if (getEndTime() != null) objects.addElement(new PropertyObject(endTimeTag, PropertyInfo.LONG_CLASS, getEndTime()));

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
        if (getStartTime() != null) objects.addElement(new PropertyObject(startTimeTag, PropertyInfo.LONG_CLASS, getStartTime()));
        if (getEndTime() != null) objects.addElement(new PropertyObject(endTimeTag, PropertyInfo.LONG_CLASS, getEndTime()));
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