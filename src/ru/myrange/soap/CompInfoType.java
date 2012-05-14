/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */

/*
 <xs:sequence>
170	                    <xs:element name="compId" type="xs:int" minOccurs="0"/>
171	                    <xs:element name="name" type="xs:string" minOccurs="0"/>
172	                    <xs:element name="industry" type="xs:string" minOccurs="0"/>
173	                    <xs:element name="about" type="xs:string" minOccurs="0"/>
174	                    <xs:element name="userId" type="xs:int" minOccurs="0"/>
175	                    <xs:element name="havePic" type="xs:boolean" minOccurs="0"/>
176	                    <xs:element name="logo" type="xs:base64Binary" minOccurs="0"/>
 </xs:sequence>
*/

package ru.myrange.soap;

import com.sun.lwuit.Image;
import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.RMS;
import ru.myrange.stuff.Base64Coder;

/**
 * This class represetns the compInfoType type from myrange.wsdl
 *
 * @author Nickolay Yegorov
 */
public class CompInfoType implements KvmSerializable {

    private static final String compIdTag = "compId";
    private static final String nameTag = "name";
    private static final String industryTag = "industry";
    private static final String aboutTag = "about";
    private static final String userIdTag = "userId";
    private static final String logoTag = "logo";
    private static final String havePicTag = "havePic";

    private static final Image nullLogo = MyRangeMIDlet.res.getImage("nullLogo");

    /**
     * @return the nullLogo
     */
    public static Image getNullLogo() {
        return nullLogo;
    }

    private Vector objects = new Vector();

    private Integer compId = new Integer(0);
    private String name = "";
    private String industry = "";
    private String about = "";
    private Integer userId = new Integer(0);
    private Image logo = null;
    private Boolean havePic = new Boolean(false);
    private String logoBase64 = ""; // pic in Base64 for save in RMS

    public CompInfoType(CompInfoType company) {
        setCompId(company.getCompId());
        setName(company.getName());
        setIndustry(company.getIndustry());
        setAbout(company.getAbout());
        setUserId(company.getUserId());
        setLogo(company.getLogo());
        setHavePic(company.getHavePic());
    }

    /* Used by KSOAP Implementation methods */

    /*
     * Create new class from SoapObject
     */
    public CompInfoType(SoapObject soapObj) throws Exception {
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(compIdTag)) {
                setCompId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(nameTag)) {
                setName( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(industryTag)) {
                setIndustry( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(aboutTag)) {
                setAbout( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(userIdTag)) {
                setUserId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(havePicTag)) {
                setHavePic( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(logoTag)) {
                setLogo( soapObj.getProperty(i).toString() );
                System.out.println("logo OK");
            }
        }
	}

    public CompInfoType(Integer compId, String name, String description, String about, Integer userId, Boolean havePic, Image logo) {
        this.compId = compId;
        this.name = name;
        this.industry = description;
        this.about = about;
        this.userId = userId;
        this.havePic = havePic;
        this.logo = logo;
    }

	
    public String getName() { return new String(name); }
    public Integer getUserId() { return new Integer(userId.intValue()); }
    public Image getLogo() { return logo; }

    public String getLogoBase64() { return new String(logoBase64); }

    public void setName(String name) {
        this.name = new String(name);
    }
    private void setUserId(Integer userId) {
        this.userId = new Integer(userId.intValue());
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
    public CompInfoType(final byte[] record) throws Exception {	// UserProfile object from RMS
        String recordString = new String(record, "UTF-8");

        setCompId( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setName( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setIndustry( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setAbout( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setUserId( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setHavePic(recordString.substring(0, recordString.indexOf(RMS.SPACER)));
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setLogo( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

	}

    public byte[] toRecord() throws Exception {		// Create the byte[] record to add to the record store
        return (getCompId().toString() + RMS.SPACER + getName() +
                RMS.SPACER + getIndustry() +
                RMS.SPACER + getAbout() +
                RMS.SPACER + getUserId().toString() +
                RMS.SPACER + getHavePic().toString() + 
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

    /**
     * @return the industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * @param industry the industry to set
     */
    public void setIndustry(String description) {
        this.industry = description;
    }

    /**
     * @return the about
     */
    public String getAbout() {
        return about;
    }

    /**
     * @param about the about to set
     */
    public void setAbout(String about) {
        this.about = about;
    }

    /**
     * @return the havePic
     */
    public Boolean getHavePic() {
        return havePic;
    }

    /**
     * @param havePic the havePic to set
     */
    public void setHavePic(Boolean havePic) {
        this.havePic = havePic;
    }

     private void setHavePic(String havePicStr) {
        if (havePicStr.equals("true")){
            setHavePic( new Boolean(true));
        } else if (havePicStr.equals("false")){
            setHavePic( new Boolean(false));
        }
    }

}