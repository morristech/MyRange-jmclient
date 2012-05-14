/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */

/*
             <xs:element name="getCompanyInfoResponse">
734	        <xs:complexType>
735	          <xs:sequence>
736	            <xs:element minOccurs="0" maxOccurs="unbounded" name="company" type="mr:compTag" />
737	          </xs:sequence>
738	        </xs:complexType>
739	      </xs:element>

*/

package ru.myrange.soap;

import java.util.Vector;
import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 * This class represetns the getConferenceInfoResponse element from myrange.wsdl
 *
 * @author Nickolay Yegorov
 */
public class GetCompanyInfoResponse implements KvmSerializable {

    private static final String compTag = "company";

    private Vector objects = new Vector();
    private Vector companies = new Vector();

	/* Used by KSOAP Implementation methods */

    public GetCompanyInfoResponse(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(compTag)) {
                addCompany( new CompInfoType((SoapObject) soapObj.getProperty(i)) );
            }
        }
	}

	public Vector getCompanies() { return companies; }

	public void setCompanies(Vector companies) {
        this.companies = companies;
    }
    public void addCompany(CompInfoType newCompany) {
        companies.addElement(newCompany);
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