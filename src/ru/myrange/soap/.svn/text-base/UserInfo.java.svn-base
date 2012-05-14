/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:complexType name="userInfoType">
    <xs:sequence>
        <xs:element name="userId" type="xs:int"/>
        <xs:element name="accountType" type="mr:accountEnum" minOccurs="0"/>
        <xs:element name="email" type="xs:string" minOccurs="0"/>
        <xs:element name="havePhoneNumber" type="xs:boolean" minOccurs="0"/>
        <xs:element name="phoneNumber" type="xs:string" minOccurs="0"/>
        <xs:element name="btAddress" type="xs:string" minOccurs="0"/>
        <xs:element name="regDate" type="xs:long" minOccurs="0"/>
        <xs:element name="lastOnlineTime" type="xs:long" minOccurs="0"/>
        <xs:element name="firstName" type="xs:string" minOccurs="0"/>
        <xs:element name="lastName" type="xs:string" minOccurs="0"/>
        <xs:element name="gender" minOccurs="0" type="mr:genderEnum"/>
        <xs:element name="about" type="xs:string" minOccurs="0"/>
        <xs:element name="residentCity" type="xs:string" minOccurs="0"/>
        <xs:element name="birthday" type="xs:long" minOccurs="0"/>
        <xs:element name="eduType" type="mr:eduEnum" minOccurs="0"/>
        <xs:element name="eduPlace" type="xs:string" minOccurs="0"/>
        <xs:element name="workPlace" type="xs:string" minOccurs="0"/>
        <xs:element name="workPost" type="xs:string" minOccurs="0"/>
        <xs:element name="textStatus" type="xs:string" minOccurs="0"/>
        <xs:element name="contact" type="mr:contactType" minOccurs="0" maxOccurs="unbounded"/>
        <xs:element name="link" type="mr:linkType" minOccurs="0" maxOccurs="unbounded"/>
        <xs:element name="onlineStatus" type="mr:onlineStatusEnum" minOccurs="0"/>
        <xs:element name="lastOnline" type="xs:long" minOccurs="0"/>
        <xs:element name="customParam" type="mr:customParamType" minOccurs="0" maxOccurs="unbounded"/>
        <xs:element name="havePic" type="xs:boolean" minOccurs="0"/>
        <xs:element name="pic" type="xs:base64Binary" minOccurs="0"/>
    </xs:sequence>
</xs:complexType>
*/

package ru.myrange.soap;

import com.sun.lwuit.Image;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import ru.myrange.datastore.RMS;
import ru.myrange.stuff.Base64Coder;

/**
 * This class represetns the userInfoType from myrange.wsdl
 * @author Oleg Ponfilenok
 */
public class UserInfo extends Actor implements KvmSerializable {

    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";
    public static final String ACCOUNT_TYPE_PERSONAL = "personal";
    public static final String ACCOUNT_TYPE_ENTERPRISE = "enterprise";
    public static final String ONLINE_STATUS_ONLINE = "online";
    public static final String ONLINE_STATUS_OFFLINE = "offline";
    public static final String ONLINE_STATUS_NEVER = "never";

    private static final String userIdTag = "userId";
    private static final String emailTag = "email";
    private static final String accountTypeTag = "accountType";
    private static final String havePhoneNumberTag = "havePhoneNumber";
    private static final String phoneNumberTag = "phoneNumber";
    private static final String btAddressTag = "btAddress";
    private static final String regDateTag = "regDate";
    private static final String firstNameTag = "firstName";
    private static final String lastNameTag = "lastName";
    private static final String genderTag = "gender";
    private static final String textStatusTag = "textStatus";
    private static final String aboutTag = "about";
    private static final String residentCityTag = "residentCity";
    private static final String birthdayTag = "birthday";
    private static final String eduTypeTag = "eduType";
    private static final String eduPlaceTag = "eduPlace";
    private static final String workPlaceTag = "workPlace";
    private static final String workPostTag = "workPost";
    private static final String contactTag = "contact";
    private static final String linkTag = "link";
    private static final String onlineStatusTag = "onlineStatus";
    private static final String lastOnlineTag = "lastOnline";
    private static final String customParamTag = "customParam";
    private static final String havePicTag = "havePic";
    private static final String picTag = "pic";
    private static final String companyIdTag = "companyId";

    private Vector objects = new Vector();

    private Integer userId = new Integer(0);
    private Integer compId = new Integer(0);
    private String accountType = "";
    private String email = "";
    private Boolean havePhoneNumber = null;
    private String phoneNumber = "";
    private Long regDate = new Long(0);
    private String firstName = "";
    private String lastName = "";
    private String gender = "";
    private String textStatus = "";
    private String about = "";
    private String residentCity = "";
    private Long birthday = new Long(0);
    private String eduType = "";
    private String eduPlace = "";
    private String workPlace = "";
    private String workPost = "";
    private Boolean havePic = null;
    private Image pic = null;
    private String picBase64 = ""; // pic in Base64 for save in RMS
    private Vector scaledPics = new Vector();  // save scaled pictures
    private Vector contact = new Vector();
    private Vector link = new Vector();
    private String onlineStatus = "";
    private Long lastOnline = new Long(0);
    private Vector customParams = new Vector();
    private long lastSeenTime = 0;		    // Time of the last meeting with user (lastTime)
    //private String lastSeenTimeHR = "";     // Time of last meet in the human readable format
    private long downloadTime = 0;			// When this profile was get from server


    public UserInfo() {}

    public UserInfo(UserInfo user) {
        super(user);
        setUserId(user.getUserId());
        setAccountType(user.getAccountType());
        setEmail(user.getEmail());
        setHavePhoneNumber(user.getHavePhoneNumber());
        setPhoneNumber(user.getPhoneNumber());
        setRegDate(user.getRegDate());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setGender(user.getGender());
        setTextStatus(user.getTextStatus());
        setAbout(user.getAbout());
        setResidentCity(user.getResidentCity());
        setBirthday(user.getBirthday());
        setEduPlace(user.getEduPlace());
        setEduType(user.getEduType());
        setWorkPlace(user.getWorkPlace());
        setWorkPost(user.getWorkPost());
        setHavePic(user.getHavePic());
        setPic(user.getPic());
        setContact(user.getContact());
        setLink(user.getLink());
        setOnlineStatus(user.getOnlineStatus());
        setLastOnlineTime(user.getLastOnlineTime());
        setCustomParams(user.getCustomParams());
        setLastSeenTime(user.getLastSeenTime());
        setDownloadTime(user.getDownloadTime());
        setCompId(user.getCompId());
    }

    /* Used by KSOAP Implementation methods */

    public UserInfo(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(userIdTag)) {
                setUserId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }else if (soapObjInfo.name.equals(companyIdTag)) {
                setCompId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(accountTypeTag)) {
                setAccountType( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(emailTag)) {
                setEmail( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(havePhoneNumberTag)) {
                setHavePhoneNumber( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(phoneNumberTag)) {
                setPhoneNumber( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(btAddressTag)) {
                setBtAddress( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(regDateTag)) {
                setRegDate( new Long(Long.parseLong(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(firstNameTag)) {
                setFirstName( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(lastNameTag)) {
                setLastName( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(genderTag)) {
                setGender( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(textStatusTag)) {
                setTextStatus( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(aboutTag)) {
                setAbout( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(residentCityTag)) {
                setResidentCity( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(birthdayTag)) {
                setBirthday( new Long(Long.parseLong(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(eduTypeTag)) {
                setEduType( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(eduPlaceTag)) {
                setEduPlace( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(workPlaceTag)) {
                setWorkPlace( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(workPostTag)) {
                setWorkPost( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(havePicTag)) {
                setHavePic( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(picTag)) {
                setPic( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(contactTag)) {
                addContact( new ContactType((SoapObject) soapObj.getProperty(i)) );
            }
            else if (soapObjInfo.name.equals(linkTag)) {
                addLink( new LinkType((SoapObject) soapObj.getProperty(i)) );
            }
            else if (soapObjInfo.name.equals(onlineStatusTag)) {
                setOnlineStatus(soapObj.getProperty(i).toString());
            }
            else if (soapObjInfo.name.equals(lastOnlineTag)) {
                setLastOnlineTime( new Long(Long.parseLong(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(customParamTag)) {
                addCustomParam( new CustomParam((SoapObject) soapObj.getProperty(i)) );
            }
        }
        setDownloadTime(System.currentTimeMillis());   // Set time of when this profile was get from server
	}

	public Integer getUserId() { return new Integer(userId.intValue()); }
    public String getAccountType() { return new String(accountType); }
    public String getEmail() { return new String(email); }
    public Boolean getHavePhoneNumber() { return havePhoneNumber; }
    private String getHavePhoneNumberStr() {
        if (havePhoneNumber != null) return havePhoneNumber.toString();
        else return "";
    }
    public String getPhoneNumber() { return new String(phoneNumber); }
	public Long getRegDate() { return new Long(regDate.longValue()); }
	public String getFirstName() { return new String(firstName); }
	public String getLastName() { return new String(lastName); }
	public String getGender() { return new String(gender); }
    public String getTextStatus() { return new String(textStatus); }
    public String getAbout() { return new String(about); }
	public String getResidentCity() { return new String(residentCity); }
    public Long getBirthday() { return new Long(birthday.longValue()); }
	public String getEduType() { return new String(eduType); }
    public String getEduPlace() { return new String(eduPlace); }
    public String getWorkPlace() { return new String(workPlace); }
    public String getWorkPost() { return new String(workPost); }
    public String getFullWorkPosition() { return new String(getWorkPost() + ", " + getWorkPost()); }
	private String getHavePicStr() {
        if (havePic != null) return havePic.toString();
        else return "";
    }
    public Boolean getHavePic() { return havePic; }
    public Image getPic() { return pic; }
    public String getPicBase64() { return picBase64; }
    public Vector getContact() { return contact; }
    public Vector getLink() { return link; }
    public Vector getCustomParams() { return customParams; }
    public long getLastSeenTime() { return lastSeenTime; }
    public String getOnlineStatus() { return onlineStatus; }
    public Long getLastOnlineTime() { return lastOnline; }
    //public String getLastSeenTimeHR() { return new String(lastSeenTimeHR); }
    public long getDownloadTime() { return downloadTime; }

    public String getFullName() {
        return new String(lastName + " " + firstName);
    }

    /**
     * Get text status or enother information about user
     */
    public String getInfo() {
        if (getTextStatus() != null & getTextStatus().length() > 0) {
            return getTextStatus();
        } else if (getEduPlace() != null & getEduPlace().length() > 0) {
            return getEduPlace();
        } else if (getWorkPost() != null & getWorkPost().length() > 0) {
            if (getWorkPlace() != null & getWorkPlace().length() > 0) {
                return getWorkPost() + ", " + getWorkPlace();
            } else return getWorkPost();
        } else if (getWorkPlace() != null & getWorkPlace().length() > 0) {
            return getWorkPlace();
        } else if (getResidentCity() != null & getResidentCity().length() > 0) {
            return getResidentCity();
        } else return "";
    }


    /**
     * Get pic by size
     * Save scaled image in memory
     */
    public Image getPic(int sizeX, int sizeY) {
        int MAX_DEVIATION = 10; // 10 pixels
        int MAX_PICS = 4; // save max 4 scaled pics
        
        if (getPic() == null) return null;
        if (getPic().getWidth()-MAX_DEVIATION < sizeX &&  getPic().getWidth()+MAX_DEVIATION > sizeX &&
                getPic().getHeight()-MAX_DEVIATION < sizeY &&  getPic().getHeight()+MAX_DEVIATION > sizeY) {
            return getPic();
        }
        for (Enumeration e = scaledPics.elements() ; e.hasMoreElements() ;) {
            Image i = (Image) e.nextElement();
            if (i.getWidth()-MAX_DEVIATION < sizeX &&  i.getWidth()+MAX_DEVIATION > sizeX &&
                i.getHeight()-MAX_DEVIATION < sizeY &&  i.getHeight()+MAX_DEVIATION > sizeY) {
            return i;
        }
        }
        // Scale new image
        if (scaledPics.size() >= MAX_PICS) scaledPics.removeElementAt(0);   // remove oldest element
        Image newImg = getPic().scaled(sizeX, sizeY);
        scaledPics.addElement(newImg);
        return newImg;
    }

    
    public boolean isNear() {
        if (getLastSeenTime() > System.currentTimeMillis()) return true;
        return false;
    }

	private void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    private void setHavePhoneNumber(Boolean havePhoneNumber) {
        this.havePhoneNumber = havePhoneNumber;
    }
    private void setHavePhoneNumber(String havePhoneNumberStr) {
        if (havePhoneNumberStr.equals("true")){
            setHavePhoneNumber( new Boolean(true));
        } else if (havePhoneNumberStr.equals("false")){
            setHavePhoneNumber( new Boolean(false));
        }
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setRegDate(Long regDate) {
        this.regDate = regDate;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setTextStatus(String textStatus) {
        this.textStatus = textStatus;
    }
    public void setAbout(String about) {
        this.about = about;
    }
    public void setResidentCity(String residentCity) {
        this.residentCity = residentCity;
    }
    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }
    public void setEduType(String eduType) {
        this.eduType = eduType;
    }
    public void setEduPlace(String eduPlace) {
        this.eduPlace = eduPlace;
    }
    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }
    public void setWorkPost(String workPost) {
        this.workPost = workPost;
    }
    private void setHavePic(String havePicStr) {
        if (havePicStr.equals("true")){
            setHavePic( new Boolean(true));
        } else if (havePicStr.equals("false")){
            setHavePic( new Boolean(false));
        }
    }
    private void setHavePic(Boolean havePic) {
        this.havePic = havePic;
    }
    public void setPic(Image pic) {
        // change picture
        this.pic = pic;
        // erase scaled pictures
        scaledPics = new Vector();
    }
    public void setPic(String input) throws Exception {     // Get .PNG image from Base64 string
        if (input == null || input.equals("")) return;
        this.picBase64 = input;
        byte[] out = Base64Coder.decode(input);
        setPic(Image.createImage(out, 0, out.length));
    }
    public void setContact(Vector contact) {
        this.contact = contact;
    }
    public void addContact(ContactType contactType) {
        this.contact.addElement(contactType);
    }
    public void setLink(Vector link) {
        this.link = link;
    }
    public void addLink(LinkType linkType) {
        this.link.addElement(linkType);
    }
    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    public void setLastOnlineTime(Long lastOnline) {
        this.lastOnline = lastOnline;
    }
    public void setCustomParams(Vector customParams) {
        this.customParams = customParams;
    }
    public void addCustomParam(CustomParam p) {
        this.customParams.addElement(p);
    }
    public void setLastSeenTime(long unixTime){
		this.lastSeenTime = unixTime;
        /*
        if (unixTime > 0) {
            // Set lastSeenTimeHR
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(unixTime));
            this.lastSeenTimeHR =  c.get(Calendar.DAY_OF_MONTH) + " " + StringConsts.S_MONTHS[c.get(Calendar.MONTH)] + " " +
                    c.get(Calendar.HOUR_OF_DAY) + ":" + (c.get(Calendar.MINUTE) > 9 ? "" : "0") + c.get(Calendar.MINUTE);
        }
        */
	}
    /*
    public void setLastSeenTimeHR(String lastSeenTimeHR) {
        this.lastSeenTimeHR = lastSeenTimeHR;
    }
    */
    public void setDownloadTime(long downloadTime) {
        this.downloadTime = downloadTime;
    }

    /* Work with RMS */
	public UserInfo(final byte[] record) throws Exception {	// UserProfile object from RMS
        String recordString = new String(record, "UTF-8");

        setUserId( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setAccountType( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setEmail( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setHavePhoneNumber( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setPhoneNumber( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setBtAddress( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setRegDate( new Long( Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setFirstName( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setLastName( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setGender( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setTextStatus( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setAbout( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setResidentCity( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setBirthday( new Long( Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setEduType( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setEduPlace( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setWorkPlace( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setWorkPost( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setHavePic( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setPic( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setContact( ContactType.vectorFromString(recordString.substring(0, recordString.indexOf(RMS.SPACER))) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setLink( LinkType.vectorFromString(recordString.substring(0, recordString.indexOf(RMS.SPACER))) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setOnlineStatus( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setLastOnlineTime( new Long( Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setCustomParams( CustomParam.vectorFromString(recordString.substring(0, recordString.indexOf(RMS.SPACER))) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setLastSeenTime( (new Long(Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))))).longValue() );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setDownloadTime( (new Long(Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))))).longValue() );
	recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setCompId( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        }

    public byte[] toRecord() throws Exception {		// Create the byte[] record to add to the record store
        return (getUserId().toString() + RMS.SPACER + getAccountType() +
                RMS.SPACER + getEmail() + 
                RMS.SPACER + getHavePhoneNumberStr() + RMS.SPACER + getPhoneNumber() +
                RMS.SPACER + getBtAddress() + RMS.SPACER + getRegDate().toString() +
                RMS.SPACER + getFirstName() + RMS.SPACER + getLastName() +
                RMS.SPACER + getGender() +
                RMS.SPACER + getTextStatus() + RMS.SPACER + getAbout() +
                RMS.SPACER + getResidentCity() + RMS.SPACER + getBirthday().toString() +
                RMS.SPACER + getEduType() + RMS.SPACER + getEduPlace() +
                RMS.SPACER + getWorkPlace() + RMS.SPACER + getWorkPost() +
                RMS.SPACER + getHavePicStr() + RMS.SPACER + getPicBase64() +
                RMS.SPACER + ContactType.vectorToString(getContact()) +
                RMS.SPACER + LinkType.vectorToString(getLink()) +
                RMS.SPACER + getOnlineStatus() + RMS.SPACER + getLastOnlineTime().toString() +
                RMS.SPACER + CustomParam.vectorToString(getCustomParams()) +
                RMS.SPACER + (new Long(getLastSeenTime())).toString() +
                RMS.SPACER + (new Long(getDownloadTime())).toString() +
                RMS.SPACER + getCompId().toString() +
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

}