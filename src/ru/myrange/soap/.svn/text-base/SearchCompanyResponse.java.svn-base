package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 *
 * @author Nickolay Yegorov
 */

/*
 * Represents SearchCompanyResponse from WSDL
 *
 *                  <xs:element name="searchCompanyResponseElement">
935	                <xs:complexType>
936	                    <xs:sequence>
937	                        <xs:element name="companyId" type="xs:int" minOccurs="0" maxOccurs="unbounded"/>
938	                    </xs:sequence>
939	                </xs:complexType>
940	            </xs:element>
941
 */
public class SearchCompanyResponse implements KvmSerializable {

    private static final String compTag = "companyId";

    private Vector objects = new Vector();
    private Vector companies = new Vector();

	/* Used by KSOAP Implementation methods */

    public SearchCompanyResponse(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(compTag)) {
                addCompany( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
        }
	}

	public Vector getCompanyIDs() { return companies; }

	public void setCompanies(Vector companyIDs) {
        this.companies = companyIDs;
    }
    public void addCompany(Integer newCompanyID) {
        companies.addElement(newCompanyID);
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