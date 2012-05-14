/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

/* THis class represents getEventsResponse object from WSDL
 * 
 *  <xs:element name="getEventsResponse">
 *  <xs:complexType>
 *      <xs:sequence>
 *          <xs:element name="event" minOccurs="0" maxOccurs="unbounded" type="mr:eventInfoType"/>
 *      </xs:sequence>
 *  </xs:complexType>
 *  </xs:element>
 */
public class GetEventsResponse implements KvmSerializable{

    private static final String eventTag = "event";
    private Vector objects = new Vector();
    private Vector events = new Vector();

    public GetEventsResponse(SoapObject soapObj) throws Exception {	// Create new class from SoapObject
        for (int i = 0; i < soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(eventTag)) {
                addEvent(new EventInfoType((SoapObject) soapObj.getProperty(i)));
            }
        }
    }

    public Vector getEvents() {
        return events;
    }

    public void setEvents(Vector events) {
        this.events = events;
    }

    public void addEvent(EventInfoType newEvent) {
        events.addElement(newEvent);
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
