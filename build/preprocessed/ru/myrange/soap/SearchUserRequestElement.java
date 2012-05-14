/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 *
 */

/*
<xs:element name="searchUserRequestElement">
<xs:complexType>
<xs:sequence>
<xs:element name="header" type="mr:headerType"/>
<xs:element name="confId" type="xs:int" minOccurs="0"/>
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

/**
 * SearchUserRequestElement implementation
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class SearchUserRequestElement implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String confIdTag = "n0:confId";
    private static final String compIdTag = "n0:companyId";
    private static final String paramTag = "n0:param";

    /**
     * @return the compIdTag
     */
    private Vector objects = new Vector();
    private HeaderType header = null;
    private Integer confId = null;
    private Integer compId = null;
    private Vector params = new Vector();

    /* Used by KSOAP Implementation methods */
    public SearchUserRequestElement(HeaderType header, Integer confId) throws Exception {
        //System.out.println(header.getHash());
        setHeader(header);
        // System.out.println(confId);
        setConfId(confId);
        //setParams(params);
    }

     public SearchUserRequestElement(HeaderType header, Integer confId, Integer compId) throws Exception {
        setHeader(header);
        setConfId(confId);
        setCompId(compId);
        //setParams(params);
    }

    public HeaderType getHeader() {
        return header;
    }

    public Integer getConfId() {
        return confId;
    }

    public Vector getParams() {
        return params;
    }

    public void setHeader(HeaderType header) {
        this.header = header;
    }

    public void setConfId(Integer confId) {
        this.confId = confId;
    }

    public void setParams(Vector params) {
        this.params = params;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (getConfId() != null) {
            objects.addElement(new PropertyObject(confIdTag, PropertyInfo.INTEGER_CLASS, getConfId()));
        if (getCompId() != null)
            objects.addElement(new PropertyObject(compIdTag, PropertyInfo.INTEGER_CLASS, getCompId()));
        }
        if (getParams() != null) {
            for (int i = 0; i < getParams().size(); i++) {
                if (params.elementAt(i) != null) {
                    objects.addElement(new PropertyObject(paramTag, PropertyInfo.OBJECT_CLASS, params.elementAt(i)));
                }
            }
        }

        // Calculate XML string
        String xmlString = "";
        // Transform all propertyes to string
        for (int i = 0; i < getPropertyCount(); i++) {
            if (getProperty(i) instanceof ContactType) {
                xmlString += ((ContactType) getProperty(i)).toString();
            } else if (getProperty(i) instanceof LinkType) {
                xmlString += ((LinkType) getProperty(i)).toString();
            } else {
                xmlString += getProperty(i).toString();
            }
        }

        //Sign
        String login = getHeader().getLogin();
        String pass = getHeader().getHash();
        String entity = pass + xmlString;
        String hashResult = (Password.fromPlainText(entity)).toString();
        setHeader(new HeaderType(login, hashResult));

        // Pack
        objects.removeAllElements();
        if (getHeader() != null) {
            objects.addElement(new PropertyObject(headerTag, PropertyInfo.OBJECT_CLASS, getHeader()));
        }
        if (getConfId() != null) {
            objects.addElement(new PropertyObject(confIdTag, PropertyInfo.INTEGER_CLASS, getConfId()));
        }
        if (getCompId() != null) {
            objects.addElement(new PropertyObject(compIdTag, PropertyInfo.INTEGER_CLASS, getCompId()));
        }
        if (getParams() != null) {
            for (int i = 0; i < getParams().size(); i++) {
                if (params.elementAt(i) != null) {
                    objects.addElement(new PropertyObject(paramTag, PropertyInfo.OBJECT_CLASS, params.elementAt(i)));
                }
            }
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

    /**
     * @return the compId
     */
    public Integer getCompId() {
        return compId;
    }

    /**
     * @param compId the compId to set
     */
    public void setCompId(Integer compId) {
        this.compId = compId;
    }
}
