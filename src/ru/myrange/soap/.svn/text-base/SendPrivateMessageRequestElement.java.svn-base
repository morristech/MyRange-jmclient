/*
<xs:element name="sendPrivateMessageRequestElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="header" type="tns:headerType"/>
            <xs:element name="to" type="xs:int"/>
            <xs:element name="subject" minOccurs="0"/>
            <xs:element name="body" type="xs:string"/>
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

public class SendPrivateMessageRequestElement implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String toTag = "n0:to";
    private static final String subjectTag = "n0:subject";
    private static final String bodyTag = "n0:body";

    private Vector objects = new Vector();
    private HeaderType header = null;
    private Integer to = null;
    private String subject = null;
    private String body = null;

	/* Used by KSOAP Implementation methods */

	public SendPrivateMessageRequestElement() { }

    public SendPrivateMessageRequestElement(HeaderType header, Integer to, String subject, String body) {
		setHeader(header);
        setSubject(subject);
        setTo(to);
        setBody(body);
	}

	public HeaderType getHeader() { return header; }
    public Integer getTo() { return to; }
    public String getSubject() { return subject; }
    public String getBody() { return body; }

	public void setHeader(HeaderType header) {
        this.header = header;
    }
    public void setTo(Integer to) {
        this.to = to;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setBody(String body) {
        this.body = body;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (getTo() != null) objects.addElement(new PropertyObject(toTag, PropertyInfo.INTEGER_CLASS, getTo()));
        if (getSubject() != null) objects.addElement(new PropertyObject(subjectTag, PropertyInfo.STRING_CLASS, getSubject()));
        if (getBody() != null) objects.addElement(new PropertyObject(bodyTag, PropertyInfo.STRING_CLASS, getBody()));

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
        if (getTo() != null) objects.addElement(new PropertyObject(toTag, PropertyInfo.INTEGER_CLASS, getTo()));
        if (getSubject() != null) objects.addElement(new PropertyObject(subjectTag, PropertyInfo.STRING_CLASS, getSubject()));
        if (getBody() != null) objects.addElement(new PropertyObject(bodyTag, PropertyInfo.STRING_CLASS, getBody()));
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
