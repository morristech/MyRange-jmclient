/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:element name="getActorInfoResponse">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="user" type="mr:ActorIdStruct" minOccurs="0" maxOccurs="unbounded"/>
            <xs:element name="comp" type="mr:ActorIdStruct" minOccurs="0" maxOccurs="unbounded"/>
            <xs:element name="conf" type="mr:ActorIdStruct" minOccurs="0" maxOccurs="unbounded"/>
            <xs:element name="bboard" type="mr:ActorIdStruct" minOccurs="0" maxOccurs="unbounded"/>
        </xs:sequence>
    </xs:complexType>
</xs:element>
*/

package ru.myrange.soap;

import java.util.Vector;
import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 * This class represetns the GetActorInfoResponse element from myrange.wsdl
 * @author Oleg Ponfilenok
 */
public class GetActorInfoResponse implements KvmSerializable {

    private static final String usersTag = "user";
    private static final String companiesTag = "comp";
    private static final String conferencesTag = "conf";
    private static final String bboardTag = "bboard";

    private Vector objects = new Vector();
	private Vector userActors = new Vector();
    private Vector companyActors = new Vector();
    private Vector conferenceActors = new Vector();
    private Vector bboardActors = new Vector();

	/* Used by KSOAP Implementation methods */

    public GetActorInfoResponse(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(usersTag)) {
                addUserActor( new Actor((SoapObject) soapObj.getProperty(i)) );
            }
            else if (soapObjInfo.name.equals(companiesTag)) {
                addCompanyActor( new Actor((SoapObject) soapObj.getProperty(i)) );
            }
            else if (soapObjInfo.name.equals(conferencesTag)) {
                addConferenceActor( new Actor((SoapObject) soapObj.getProperty(i)) );
            }
            else if (soapObjInfo.name.equals(bboardTag)) {
                addBboardActor( new Actor((SoapObject) soapObj.getProperty(i)) );
            }
        }
	}

	public Vector getUserActors() { return userActors; }

	public void setUserActors(Vector userActors) {
        this.userActors = userActors;
    }
    public void addUserActor(Actor newUserActor) {
        userActors.addElement(newUserActor);
    }
    public void setCompanyActors(Vector companyActors) {
        this.companyActors = companyActors;
    }
    public void addCompanyActor(Actor newCompanyActor) {
        companyActors.addElement(newCompanyActor);
    }
    public void setConferenceActors(Vector conferenceActors) {
        this.conferenceActors = conferenceActors;
    }
    public void addConferenceActor(Actor newConferenceActor) {
        conferenceActors.addElement(newConferenceActor);
    }
    public void setBboardActors(Vector bboardActors) {
        this.bboardActors = bboardActors;
    }
    public void addBboardActor(Actor newBboardActor) {
        bboardActors.addElement(newBboardActor);
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