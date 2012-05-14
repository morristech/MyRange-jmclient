/*
This class represetns the linkType type from myrange.wsdl

<xs:complexType name="linkType">
    <xs:sequence>
        <xs:element name="site" type="xs:string" minOccurs="0"/>
        <xs:element name="url" type="xs:string"/>
    </xs:sequence>
</xs:complexType>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class LinkType implements KvmSerializable {

    private static final String LINK_SPACER = "<**\u0004\u0019%>"; // To split site and url

    private static final String siteTag = "site";
    private static final String urlTag = "url";
    private static final String siteTagRequest = "n0:site";
    private static final String urlTagRequest = "n0:url";

    private Vector objects = new Vector();
    private String site = null;
	private String url = null;
	
	/* Used by KSOAP Implementation methods */
	
	public LinkType() {}
	
	public LinkType(String site, String url) {
		setSite(site);
		setUrl(url);
	}

    public LinkType(SoapObject soapObj) {	// Create new class from SoapObject
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
			PropertyInfo soapObjInfo = new PropertyInfo();
			// Get property name of SoapObject
			soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(siteTag)) {
                setSite( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(urlTag)) {
                setUrl( soapObj.getProperty(i).toString() );
            }
		}
	}
		
	public String getSite() { return site; }
	public String getUrl() { return url; }
	
	public void setSite(String site) {
        this.site = site;
        objects.addElement(new PropertyObject(siteTagRequest, PropertyInfo.STRING_CLASS, site));
    }
	public void setUrl(String url) {
        this.url = url;
        objects.addElement(new PropertyObject(urlTagRequest, PropertyInfo.STRING_CLASS, url));
    }

    public String toString() {
        String res = "";
        for (int i=0; i<getPropertyCount(); i++){
            res += getProperty(i).toString();
        }
        return res;
    }

    /**
     * Conver vector of LinkType's to string
     *
     * @param contacts
     * @return
     */
    static public String vectorToString(Vector  contacts) throws Exception {
        String result = "";
        if (contacts == null) return result;
        for (int i=0; i<contacts.size(); i++) {
            if (contacts.elementAt(i) instanceof LinkType) {
                LinkType l = (LinkType) contacts.elementAt(i);
                result += l.getSite() + LINK_SPACER + l.getUrl() + LINK_SPACER;
            }
        }
        return result;
    }

    /**
     * Get vector of LinkType's from string
     *
     * @param source
     * @return
     */
    static public Vector vectorFromString(String  source) throws Exception {
        Vector result = new Vector();
        if (source == null) return result;

        int startIndex = 0;
        while (true) {
            LinkType l = new LinkType();
            // Set site
            int endIndex = source.indexOf(LINK_SPACER, startIndex);
            if (endIndex<0) break;
            l.setSite(source.substring(startIndex, endIndex));
            startIndex = endIndex + LINK_SPACER.length();
            // Set url
            endIndex = source.indexOf(LINK_SPACER, startIndex);
            if (endIndex<0) break;
            l.setUrl(source.substring(startIndex, endIndex));
            startIndex = endIndex + LINK_SPACER.length();
            // Add
            result.addElement(l);
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
