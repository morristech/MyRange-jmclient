package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import ru.myrange.util.Password;

/**
 *
 * @author Nickolay Yegorov
 */

/*
 * represents SearchCompanyRequest form wsdl
 *                  <xs:element name="searchCompanyRequestElement">
925	                <xs:complexType>
926	                    <xs:sequence>
927	                        <xs:element name="header" type="mr:headerType"/>
928	                        <xs:element name="confId" type="xs:int" minOccurs="0"/>
930	                    </xs:sequence>
931	                </xs:complexType>
932	            </xs:element>
 */
public class SearchCompanyRequest implements KvmSerializable{
    private static final String headerTag = "n0:header";
    private static final String conferenceIdTag = "n0:confId";

    private Vector objects = new Vector();
    private HeaderType header = null;
    private Integer confId = null;

    /* Used by KSOAP Implementation methods */

    public SearchCompanyRequest(HeaderType header, Integer confId) {
        setHeader(header);
        setConfId(confId);
    }

    public HeaderType getHeader() { return header; }
    public Integer getConfId() { return confId; }

    public void setHeader(HeaderType header) {
        this.header = header;
    }
    public void setConfId(Integer confId) {
        this.confId = confId;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (getConfId() != null) objects.addElement(new PropertyObject(conferenceIdTag, PropertyInfo.INTEGER_CLASS, getConfId()));

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
        if (getConfId() != null) objects.addElement(new PropertyObject(conferenceIdTag, PropertyInfo.INTEGER_CLASS, getConfId()));
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
