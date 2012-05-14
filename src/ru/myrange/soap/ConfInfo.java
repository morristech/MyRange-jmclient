/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */

/*
<xs:complexType name="confInfoType">
    <xs:sequence>
        <xs:element name="confId" type="xs:int"/>
        <xs:element name="name" type="xs:string" minOccurs="0"/>
        <xs:element name="userId" type="xs:int" minOccurs="0"/>
        <xs:element name="startTime" type="xs:long" minOccurs="0"/>
        <xs:element name="endTime" type="xs:long" minOccurs="0"/>
        <xs:element name="place" type="xs:string" minOccurs="0"/>
        <xs:element name="info" type="xs:string" minOccurs="0"/>
        <xs:element name="logo" type="xs:base64Binary" minOccurs="0"/>
    </xs:sequence>
</xs:complexType>
*/

package ru.myrange.soap;

import com.sun.lwuit.Image;
import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import ru.myrange.datastore.RMS;
import ru.myrange.stuff.Base64Coder;

/**
 * This class represetns the confInfoType type from myrange.wsdl
 *
 * @author Nickolay Yegorov
 */
public class ConfInfo implements KvmSerializable {

    private static final String confIdTag = "confId";
    private static final String nameTag = "name";
    private static final String userIdTag = "userId";
    private static final String startTimeTag = "startTime";
    private static final String endTimeTag = "endTime";
    private static final String placeTag = "place";
    private static final String infoTag = "info";
    private static final String logoTag = "logo";

    private Vector objects = new Vector();

    private Integer confId = new Integer(0);
    private String name = "";
    private Integer userId = new Integer(0);
    private Long startTime = new Long(0);
    private Long endTime = new Long(0);
    private String place = "";
    private String info = "";
    private Image logo = null;
    private String logoBase64 = ""; // pic in Base64 for save in RMS

    public ConfInfo(ConfInfo conf) {
        setConfId(conf.getConfId());
        setName(conf.getName());
        setUserId(conf.getUserId());
        setStartTime(conf.getStartTime());
        setEndTime(conf.getEndTime());
        setPlace(conf.getPlace());
        setInfo(conf.getInfo());
        setLogo(conf.getLogo());
    }

    /* Used by KSOAP Implementation methods */

    /*
     * Create new class from SoapObject
     */
    public ConfInfo(SoapObject soapObj) throws Exception {
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(confIdTag)) {
                setConfId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(nameTag)) {
                setName( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(userIdTag)) {
                setUserId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(startTimeTag)) {
                setStartTime( new Long(Long.parseLong(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(endTimeTag)) {
                setEndTime( new Long(Long.parseLong(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(placeTag)) {
                setPlace( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(infoTag)) {
                setInfo( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(logoTag)) {
                setLogo( soapObj.getProperty(i).toString() );
            }
        }
	}

	public Integer getConfId() { return new Integer(confId.intValue()); }
    public String getName() { return new String(name); }
    public Integer getUserId() { return new Integer(userId.intValue()); }
    public Long getStartTime() { return new Long(startTime.longValue()); }
    public Long getEndTime() { return new Long(endTime.longValue()); }
    public String getPlace() { return new String(place); }
    public String getInfo() { return new String(info); }
    public Image getLogo() { return logo; }
    public String getLogoBase64() { return new String(logoBase64); }

	private void setConfId(Integer confId) {
        this.confId = new Integer(confId.intValue());
    }
    public void setName(String name) {
        this.name = new String(name);
    }
    private void setUserId(Integer userId) {
        this.userId = new Integer(userId.intValue());
    }
    public void setStartTime(Long startTime) {
        this.startTime = new Long(startTime.longValue());
    }
    public void setEndTime(Long endTime) {
        this.endTime = new Long(endTime.longValue());
    }
    public void setPlace(String place) {
        this.place = new String(place);
    }
    public void setInfo(String info) {
        this.info = new String(info);
    }
    private void setLogo(Image logo) {
        this.logo = logo;
    }
    public void setLogo(String input) throws Exception {     // Get .PNG image from Base64 string
        if (input == null || input.equals("")) return;
        this.logoBase64 = input;
        byte[] out = Base64Coder.decode(input);
        this.logo = Image.createImage(out, 0, out.length);
    }


    /* Work with RMS */
	public ConfInfo(final byte[] record) throws Exception {	// UserProfile object from RMS
        String recordString = new String(record, "UTF-8");

        setConfId( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setName( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setUserId( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setStartTime( new Long( Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setEndTime( new Long( Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setPlace( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setInfo( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setLogo( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

	}

    public byte[] toRecord() throws Exception {		// Create the byte[] record to add to the record store
        return (getConfId().toString() + RMS.SPACER + getName() +
                RMS.SPACER + getUserId().toString() +
                RMS.SPACER + getStartTime().toString() + RMS.SPACER + getEndTime().toString() +
                RMS.SPACER + getPlace() + RMS.SPACER + getInfo() +
                RMS.SPACER + getLogoBase64() +
                RMS.SPACER).getBytes("UTF-8");
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