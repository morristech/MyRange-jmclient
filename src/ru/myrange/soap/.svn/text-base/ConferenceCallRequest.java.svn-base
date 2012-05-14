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

/* represents
 * <xs:element name="conferenceCallRequestElement">
962                 <xs:complexType>
963                     <xs:sequence>
964                         <xs:element name="header" type="mr:headerType"/>
965                         <xs:choice>
966                             <xs:element name="userId" type="xs:int" minOccurs="0"/>
967                             <xs:element name="companyId" type="xs:int" minOccurs="0"/>
968                         </xs:choice>
969                     </xs:sequence>
970                 </xs:complexType>
971             </xs:element>

 */
public class ConferenceCallRequest implements KvmSerializable{
    private static final String headerTag = "n0:header";
    private static final String userIdTag = "n0:userId";
    private static final String compIdTag = "n0:companyId";

    private Vector objects = new Vector();
    private HeaderType header = null;
    private Integer userId = null;
    private Integer compId = null;

    /* Used by KSOAP Implementation methods */

    public ConferenceCallRequest(HeaderType header, Integer userId) {
        setHeader(header);
        setUserId(userId);
    }

    public ConferenceCallRequest(HeaderType header, Integer userId, Integer compId) {
        setHeader(header);
        setCompId(compId);
        setUserId(userId);
    }

    public HeaderType getHeader() { return header; }
    public Integer getCompId() { return compId; }

    public void setHeader(HeaderType header) {
        this.header = header;
    }
    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (getCompId() != null) objects.addElement(new PropertyObject(compIdTag, PropertyInfo.INTEGER_CLASS, getCompId()));
        if (getUserId() != null) objects.addElement(new PropertyObject(userIdTag, PropertyInfo.INTEGER_CLASS, getUserId()));

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
        if (getCompId() != null) objects.addElement(new PropertyObject(compIdTag, PropertyInfo.INTEGER_CLASS, getCompId()));
        if (getUserId() != null) objects.addElement(new PropertyObject(userIdTag, PropertyInfo.INTEGER_CLASS, getUserId()));

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

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
