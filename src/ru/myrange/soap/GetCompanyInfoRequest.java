package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import ru.myrange.util.Password;

/*
 *          <xs:element name="getCompanyInfoRequest">
725	        <xs:complexType>
726	          <xs:sequence>
727	            <xs:element name="header" type="mr:headerType" />
728	            <xs:element minOccurs="0" maxOccurs="unbounded" name="compId" type="xs:int" />
729	            <xs:element minOccurs="0" name="picSize" type="xs:int" />
730	          </xs:sequence>
731	        </xs:complexType>
732	      </xs:element>
 */

/**
 *
 * @author Nickolay Yegorov
 */
public class GetCompanyInfoRequest implements KvmSerializable{
    private static final String headerTag = "n0:header";
    private static final String compIdTag = "n0:compId";
    private static final String picSizeTag = "n0:picSize";

    private Vector objects = new Vector();
    private HeaderType header = null;
    private Integer picSize = null;
    private Integer[] compIds = null;


    public GetCompanyInfoRequest(HeaderType header, Integer[] compIDs, Integer picSize) {
        setHeader(header);
        setCompIds(compIDs);
        setPicSize(picSize);
    }

//    public GetCompanyInfoRequest(HeaderType header, Integer[] compIDs) {
//        setHeader(header);
//        setCompIds(compIDs);
//    }

    public GetCompanyInfoRequest(HeaderType header) {
        setHeader(header);
    }

    public HeaderType getHeader() { return header; }
    public Integer[] getCompIds() { return compIds; }

    public void setHeader(HeaderType header) {
        this.header = header;
    }
    public void setCompIds(Integer[] compIds) {
        this.compIds = compIds;
    }

    // Sign and Pack element befor sending
    public void pack() throws Exception {
        // Pack for calculate XML string
        objects.removeAllElements();
        if (compIds != null){
            for(int i = 0; i < compIds.length; i++)
                if(compIds[i] != null)
                    objects.addElement(new PropertyObject(compIdTag, PropertyInfo.INTEGER_CLASS, compIds[i]));
        }
        if(picSize != null)
           objects.addElement(new PropertyObject(picSizeTag, PropertyInfo.INTEGER_CLASS, picSize));

        // Calculate XML string
        String xmlString = "";
        // Transform all propertyes to string
        for (int i=0; i<getPropertyCount(); i++){
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
        if (header != null) objects.addElement(new PropertyObject(headerTag, PropertyInfo.OBJECT_CLASS, header));
        //if (getCompIds() != null) objects.addElement(new PropertyObject(compIdTag, PropertyInfo.INTEGER_CLASS, getCompIds()));
        if (compIds != null){
            for(int i = 0; i < compIds.length; i++)
                if(compIds[i] != null)
                    objects.addElement(new PropertyObject(compIdTag, PropertyInfo.INTEGER_CLASS, compIds[i]));
        }
        if(picSize != null)
           objects.addElement(new PropertyObject(picSizeTag, PropertyInfo.INTEGER_CLASS, picSize));
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
     * @return the picSize
     */
    public Integer getPicSize() {
        return picSize;
    }

    /**
     * @param picSize the picSize to set
     */
    public void setPicSize(Integer picSize) {
        this.picSize = picSize;
    }

}
