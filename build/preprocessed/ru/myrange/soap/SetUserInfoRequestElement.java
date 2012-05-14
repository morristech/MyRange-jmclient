/*
This class represetns the setUserInfoRequestElement element from myrange.wsdl

<xs:element name="setUserInfoRequestElement">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="header" type="mr:headerType"/>
            <xs:element name="firstName" type="xs:string"/>
            <xs:element name="lastName" type="xs:string"/>
            <xs:element name="email" type="xs:string" minOccurs="0"/> <!-- idden field for other users-->
            <xs:element name="phoneNumber" type="xs:string" minOccurs="0"/> <!-- idden field for other users-->
            <xs:element name="gender" type="mr:genderType" minOccurs="0"/>
            <xs:element name="about" type="xs:string" minOccurs="0"/>
            <xs:element name="residentCity" type="xs:string" minOccurs="0"/>
            <xs:element name="birthday" type="xs:long" minOccurs="0"/>
            <xs:element name="eduType" type="mr:eduType" minOccurs="0"/>
            <xs:element name="eduPlace" type="xs:string" minOccurs="0"/>
            <xs:element name="workPlace" type="xs:string" minOccurs="0"/>
            <xs:element name="workPost" type="xs:string" minOccurs="0"/>
            <xs:element name="pic" type="xs:base64Binary" minOccurs="0"/>
            <xs:element name="textStatus" type="xs:string" minOccurs="0"/>
            <xs:element name="contact" type="mr:contactType" minOccurs="0" maxOccurs="unbounded"/>
            <xs:element name="link" type="mr:linkType" minOccurs="0" maxOccurs="unbounded"/>
            <xs:element name="customParam" type="mr:customParamType" minOccurs="0" maxOccurs="unbounded"/>
        </xs:sequence>
    </xs:complexType>
</xs:element>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import ru.myrange.stuff.Base64Coder;
import javax.microedition.lcdui.Image;
import ru.myrange.util.Password;

/**
 * SetUserInfoRequestElement implementation
 *
 * @author Oleg Ponfilenok
 */
public class SetUserInfoRequestElement implements KvmSerializable {

    private static final String headerTag = "n0:header";
    private static final String firstNameTag = "n0:firstName";
    private static final String lastNameTag = "n0:lastName";
    private static final String emailTag = "n0:email";
    private static final String phoneNumberTag = "n0:phoneNumber";
    private static final String genderTag = "n0:gender";
    private static final String aboutTag = "n0:about";
    private static final String residentCityTag = "n0:residentCity";
    private static final String birthdayTag = "n0:birthday";
    private static final String eduTypeTag = "n0:eduType";
    private static final String eduPlaceTag = "n0:eduPlace";
    private static final String workPlaceTag = "n0:workPlace";
    private static final String workPostTag = "n0:workPost";
    private static final String picTag = "n0:pic";
    private static final String textStatusTag = "n0:textStatus";
    private static final String contactTag = "n0:contact";
    private static final String linkTag = "n0:link";
    private static final String customParamTag = "n0:customParam";

    private Vector objects = new Vector();

    private HeaderType header = null;
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String phoneNumber = "";
    private String gender = "";
    private String about = "";
    private String residentCity = "";
    private Long birthday = new Long(0);
    private String eduType = "";
    private String eduPlace = "";
    private String workPlace = "";
    private String workPost = "";
    private Image pic = null;
    private String picBase64 = ""; // pic in Base64 for save in RMS
    private String textStatus = "";
    private Vector contact = new Vector();
    private Vector link = new Vector();
    private Vector customParams = new Vector();

	/* Used by KSOAP Implementation methods */

	public SetUserInfoRequestElement(HeaderType header, UserInfo profile) throws Exception {
        setHeader(header);
        setEmail(profile.getEmail());
        setPhoneNumber(profile.getPhoneNumber());
        setFirstName(profile.getFirstName());
        if (profile.getAccountType().equals(UserInfo.ACCOUNT_TYPE_ENTERPRISE)) {
            // Enterprise user
            setLastName("lastName");
        }
        else {  
            // Personal user
            setLastName(profile.getLastName());
            setGender(profile.getGender());
            setAbout(profile.getAbout());
            setResidentCity(profile.getResidentCity());
            setBirthday(profile.getBirthday());
            setEduPlace(profile.getEduPlace());
            setEduType(profile.getEduType());
            setWorkPlace(profile.getWorkPlace());
            setWorkPost(profile.getWorkPost());
        }
        setPic(profile.getPicBase64());
        setTextStatus(profile.getTextStatus());
        setContact(profile.getContact());
        setLink(profile.getLink());
        setCustomParams(profile.getCustomParams());
	}

	public HeaderType getHeader() { return header; }
    public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
	public String getGender() { return gender; }
    public String getAbout() { return about; }
	public String getResidentCity() { return residentCity; }
    public Long getBirthday() { return birthday; }
	public String getEduType() { return eduType; }
    public String getEduPlace() { return eduPlace; }
    public String getWorkPlace() { return workPlace; }
    public String getWorkPost() { return workPost; }
	public Image getPic() { return pic; }
    public String getPicBase64() { return picBase64; }
    public String getTextStatus() { return textStatus; }
    public Vector getContact() { return contact; }
    public Vector getLink() { return link; }
    public Vector getCustomParams() { return customParams; }

	public void setHeader(HeaderType header) {
        this.header = header;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
    private void setPic(Image pic) {
        this.pic = pic;
    }
    public void setPic(String input) throws Exception {     // Get .PNG image from Base64 string
        if (input == null || input.equals("")) return;
        this.picBase64 = input;
        byte[] out = Base64Coder.decode(input);
        this.pic = Image.createImage(out, 0, out.length);
    }
    public void setContact(Vector contact) {
        this.contact = contact;
    }
    public void setLink(Vector link) {
        this.link = link;
    }
    public void setCustomParams(Vector customParams) {
        this.customParams = customParams;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (!getFirstName().equals("")) objects.addElement(new PropertyObject(firstNameTag, PropertyInfo.STRING_CLASS, getFirstName()));
        if (!getLastName().equals("")) objects.addElement(new PropertyObject(lastNameTag, PropertyInfo.STRING_CLASS, getLastName()));
        if (!getEmail().equals("")) objects.addElement(new PropertyObject(emailTag, PropertyInfo.STRING_CLASS, getEmail()));
        if (!getPhoneNumber().equals("")) objects.addElement(new PropertyObject(phoneNumberTag, PropertyInfo.STRING_CLASS, getPhoneNumber()));
        if (!getGender().equals("")) objects.addElement(new PropertyObject(genderTag, PropertyInfo.STRING_CLASS, getGender()));
        if (!getAbout().equals("")) objects.addElement(new PropertyObject(aboutTag, PropertyInfo.STRING_CLASS, getAbout()));
        if (!getResidentCity().equals("")) objects.addElement(new PropertyObject(residentCityTag, PropertyInfo.STRING_CLASS, getResidentCity()));
        if (getBirthday() != null && getBirthday().longValue() != 0) objects.addElement(new PropertyObject(birthdayTag, PropertyInfo.LONG_CLASS, getBirthday()));
        if (!getEduType().equals("")) objects.addElement(new PropertyObject(eduTypeTag, PropertyInfo.STRING_CLASS, getEduType()));
        if (!getEduPlace().equals("")) objects.addElement(new PropertyObject(eduPlaceTag, PropertyInfo.STRING_CLASS, getEduPlace()));
        if (!getWorkPlace().equals("")) objects.addElement(new PropertyObject(workPlaceTag, PropertyInfo.STRING_CLASS, getWorkPlace()));
        if (!getWorkPost().equals("")) objects.addElement(new PropertyObject(workPostTag, PropertyInfo.STRING_CLASS, getWorkPost()));
        // Do not take MD5(getPicBase64())! It takes to long time!
        //if (!getPicBase64().equals("")) objects.addElement(new PropertyObject(picTag, PropertyInfo.STRING_CLASS, getPicBase64()));
        if (!getTextStatus().equals("")) objects.addElement(new PropertyObject(textStatusTag, PropertyInfo.STRING_CLASS, getTextStatus()));
        if (getContact()!=null){
            for (int i=0; i<getContact().size(); i++) {
                objects.addElement(new PropertyObject(contactTag, PropertyInfo.OBJECT_CLASS, (ContactType) getContact().elementAt(i)));
            }
        }
        if (getLink()!=null){
            for (int i=0; i<getLink().size(); i++) {
                objects.addElement(new PropertyObject(linkTag, PropertyInfo.OBJECT_CLASS, (LinkType) getLink().elementAt(i)));
            }
        }
        if (getCustomParams()!=null){
            for (int i=0; i<getCustomParams().size(); i++) {
                objects.addElement(new PropertyObject(customParamTag, PropertyInfo.OBJECT_CLASS, (CustomParam) getCustomParams().elementAt(i)));
            }
        }

        // Calculate XML string
        String xmlString = "";
        // Transform all propertyes to string
        for (int i=0; i<getPropertyCount(); i++){
            if (getProperty(i) instanceof ContactType)
                xmlString += ((ContactType) getProperty(i)).toString();
            else if (getProperty(i) instanceof LinkType)
                xmlString += ((LinkType) getProperty(i)).toString();
            else
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
        if (!getFirstName().equals("")) objects.addElement(new PropertyObject(firstNameTag, PropertyInfo.STRING_CLASS, getFirstName()));
        if (!getLastName().equals("")) objects.addElement(new PropertyObject(lastNameTag, PropertyInfo.STRING_CLASS, getLastName()));
        if (!getEmail().equals("")) objects.addElement(new PropertyObject(emailTag, PropertyInfo.STRING_CLASS, getEmail()));
        if (!getPhoneNumber().equals("")) objects.addElement(new PropertyObject(phoneNumberTag, PropertyInfo.STRING_CLASS, getPhoneNumber()));
        if (!getGender().equals("")) objects.addElement(new PropertyObject(genderTag, PropertyInfo.STRING_CLASS, getGender()));
        if (!getAbout().equals("")) objects.addElement(new PropertyObject(aboutTag, PropertyInfo.STRING_CLASS, getAbout()));
        if (!getResidentCity().equals("")) objects.addElement(new PropertyObject(residentCityTag, PropertyInfo.STRING_CLASS, getResidentCity()));
        if (getBirthday() != null && getBirthday().longValue() != 0) objects.addElement(new PropertyObject(birthdayTag, PropertyInfo.LONG_CLASS, getBirthday()));
        if (!getEduType().equals("")) objects.addElement(new PropertyObject(eduTypeTag, PropertyInfo.STRING_CLASS, getEduType()));
        if (!getEduPlace().equals("")) objects.addElement(new PropertyObject(eduPlaceTag, PropertyInfo.STRING_CLASS, getEduPlace()));
        if (!getWorkPlace().equals("")) objects.addElement(new PropertyObject(workPlaceTag, PropertyInfo.STRING_CLASS, getWorkPlace()));
        if (!getWorkPost().equals("")) objects.addElement(new PropertyObject(workPostTag, PropertyInfo.STRING_CLASS, getWorkPost()));
        if (!getPicBase64().equals("")) objects.addElement(new PropertyObject(picTag, PropertyInfo.STRING_CLASS, getPicBase64()));
        if (!getTextStatus().equals("")) objects.addElement(new PropertyObject(textStatusTag, PropertyInfo.STRING_CLASS, getTextStatus()));
        if (getContact()!=null){
            for (int i=0; i<getContact().size(); i++) {
                objects.addElement(new PropertyObject(contactTag, PropertyInfo.OBJECT_CLASS, (ContactType) getContact().elementAt(i)));
            }
        }
        if (getLink()!=null){
            for (int i=0; i<getLink().size(); i++) {
                objects.addElement(new PropertyObject(linkTag, PropertyInfo.OBJECT_CLASS, (LinkType) getLink().elementAt(i)));
            }
        }
        if (getCustomParams()!=null){
            for (int i=0; i<getCustomParams().size(); i++) {
                objects.addElement(new PropertyObject(customParamTag, PropertyInfo.OBJECT_CLASS, (CustomParam) getCustomParams().elementAt(i)));
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

}
