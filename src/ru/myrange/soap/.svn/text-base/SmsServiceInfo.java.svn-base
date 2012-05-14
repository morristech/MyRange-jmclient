/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
This class represetns the getSmsServiceInfoResponseElement element from myrange.wsdl

<xs:element name="getSmsServiceInfoResponseElement">
    <xs:complexType>
        <xs:all>
            <xs:element name="shortCode" type="xs:string"/>
            <xs:element name="keyWord" type="xs:string"/>
            <xs:element name="cost" type="xs:string"/>
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
import ru.myrange.datastore.RMS;

/**
 * SmsServiceInfo class
 *
 * @author Oleg Ponfilenok
 */
public class SmsServiceInfo implements KvmSerializable {

    private static final String shortCodeTag = "shortCode";
    private static final String keyWordTag = "keyWord";
    private static final String costTag = "cost";

    private Vector objects = new Vector();

    private String shortCode = null;
    private String keyWord = null;
    private String cost = null;
    private Long updateTime = new Long(0);

	/* Used by KSOAP Implementation methods */

	public SmsServiceInfo(SoapObject soapObj) {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
			PropertyInfo soapObjInfo = new PropertyInfo();
			// Get property name of SoapObject
			soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(shortCodeTag)) {
                setShortCode(soapObj.getProperty(i).toString());
            }
            else if (soapObjInfo.name.equals(keyWordTag)) {
                setKeyWord(soapObj.getProperty(i).toString());
            }
            else if (soapObjInfo.name.equals(costTag)) {
                setCost(soapObj.getProperty(i).toString());
            }
		}
        // Set update time
        setUpdateTime(new Long(System.currentTimeMillis()));
	}

	public String getShortCode() { return shortCode; }
    public String getKeyWord() { return keyWord; }
    public String getCost() { return cost; }
    public Long getUpdateTime() { return updateTime; }

	public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
        objects.addElement(new PropertyObject(shortCodeTag, PropertyInfo.STRING_CLASS, shortCode));
    }
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
        objects.addElement(new PropertyObject(keyWordTag, PropertyInfo.STRING_CLASS, keyWord));
    }
    public void setCost(String cost) {
        this.cost = cost;
        objects.addElement(new PropertyObject(costTag, PropertyInfo.STRING_CLASS, cost));
    }
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /* Work with RMS */
	public SmsServiceInfo(final byte[] record) throws Exception {	// SmsServiceInfo object from RMS
        String recordString = new String(record, "UTF-8");

        setShortCode( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setKeyWord( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setCost( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setUpdateTime( new Long( Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
	}

    public byte[] toRecord() throws Exception {		// Create the byte[] record to add to the record store
        return (getShortCode() + RMS.SPACER + getKeyWord() + RMS.SPACER + getCost() +
                RMS.SPACER + getUpdateTime().toString() + RMS.SPACER).getBytes("UTF-8");
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