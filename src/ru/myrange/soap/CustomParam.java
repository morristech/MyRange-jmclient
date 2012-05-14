/*
<xs:complexType name="customParamType">
    <xs:sequence>
        <xs:element name="attribute" type="xs:string"/>
        <xs:element name="value" type="xs:string"/>
    </xs:sequence>
</xs:complexType>
*/

package ru.myrange.soap;

import java.util.Vector;
import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 * This class represetns the customParams element from myrange.wsdl
 *
 * @author Oleg Ponfilenok
 */
public class CustomParam implements KvmSerializable {

    private static final String CUSTOM_PARAM_SPACER = "<**\u0004\u0019%>"; // To split attribute and value
    public static final String ATTRIBUTE_FP2009_NOMINATION = "fp2009-nomination";
    public static final String ATTRIBUTE_REGION = "region";

    private static final String attributeTag = "attribute";
    private static final String valueTag = "value";
    private static final String attributeTagRequest = "n0:attribute";
    private static final String valueTagRequest = "n0:value";

    private Vector objects = new Vector();
    private String attribute = null;
	private String value = null;

	/* Used by KSOAP Implementation methods */

    public CustomParam() {}

	public CustomParam(String attribute, String value) {
		setAttribute(attribute);
		setValue(value);
	}

    public CustomParam(SoapObject soapObj) {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
			PropertyInfo soapObjInfo = new PropertyInfo();
			// Get property name of SoapObject
			soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(attributeTag)) {
                setAttribute( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(valueTag)) {
                setValue( soapObj.getProperty(i).toString() );
            }
		}
	}

	public String getAttribute() { return attribute; }
	public String getValue() { return value; }

	public void setAttribute(String attribute) {
        this.attribute = attribute;
        objects.addElement(new PropertyObject(attributeTagRequest, PropertyInfo.STRING_CLASS, attribute));
    }
	public void setValue(String value) {
        this.value = value;
        objects.addElement(new PropertyObject(valueTagRequest, PropertyInfo.STRING_CLASS, value));
    }

    public String toString() {
        String res = "";
        for (int i=0; i<getPropertyCount(); i++){
            res += getProperty(i).toString();
        }
        return res;
    }

    /**
     * Conver vector of CustomParam's to string
     *
     * @param params
     * @return
     */
    static public String vectorToString(Vector params) throws Exception {
        String result = "";
        if (params == null) return result;
        for (int i=0; i<params.size(); i++) {
            if (params.elementAt(i) instanceof CustomParam) {
                CustomParam p = (CustomParam) params.elementAt(i);
                result += p.getAttribute() + CUSTOM_PARAM_SPACER + p.getValue() + CUSTOM_PARAM_SPACER;
            }
        }
        return result;
    }

    /**
     * Get vector of CustomParam's from string
     *
     * @param source
     * @return
     */
    static public Vector vectorFromString(String  source) throws Exception {
        Vector result = new Vector();
        if (source == null) return result;

        int startIndex = 0;
        while (true) {
            CustomParam p = new CustomParam();
            // Set site
            int endIndex = source.indexOf(CUSTOM_PARAM_SPACER, startIndex);
            if (endIndex<0) break;
            p.setAttribute(source.substring(startIndex, endIndex));
            startIndex = endIndex + CUSTOM_PARAM_SPACER.length();
            // Set url
            endIndex = source.indexOf(CUSTOM_PARAM_SPACER, startIndex);
            if (endIndex<0) break;
            p.setValue(source.substring(startIndex, endIndex));
            startIndex = endIndex + CUSTOM_PARAM_SPACER.length();
            // Add
            result.addElement(p);
        }
        return result;
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
