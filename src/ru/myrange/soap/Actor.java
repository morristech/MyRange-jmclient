/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

/*
<xs:complexType name="ActorIdStruct">
    <xs:sequence>
        <xs:element name="id" type="xs:int"/>
        <xs:element name="btAddress" type="xs:string"/>
    </xs:sequence>
</xs:complexType>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 * This class represetns the ActorIdStruct from myrange.wsdl
 * @author Oleg Ponfilenok
 */
public class Actor implements KvmSerializable {

    private static final String idTag = "id";
    private static final String btAddressTag = "btAddress";

    private Vector objects = new Vector();
    private Integer actorId = new Integer(0);
    private String btAddress = "";

    public Actor() {}

    public Actor(Actor actor) {
        setActorId(actor.getActorId());
        setBtAddress(actor.getBtAddress());
    }

    /* Used by KSOAP Implementation methods */

    public Actor(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(idTag)) {
                setActorId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(btAddressTag)) {
                setBtAddress( soapObj.getProperty(i).toString() );
            }
        }
	}

    public Integer getActorId() { return new Integer(actorId.intValue()); }
    public String getBtAddress() { return new String(btAddress); }

    private void setActorId(Integer actorId) {
        this.actorId = actorId;
    }
    public void setBtAddress(String btAddress) {
        this.btAddress = btAddress;
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