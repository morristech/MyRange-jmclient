/*
 * This class represetns the registerResponseElement type from myrange.wsdl
 *
 *                 <xs:complexType name="eventInfoType">
197	                <xs:sequence>
198	                    <xs:element name="eventId" type="xs:int"/>
199	                    <xs:element name="section" type="xs:string" minOccurs="0"/>
200	                    <xs:element name="title" type="xs:string" minOccurs="0"/>
201	                    <xs:element name="info" type="xs:string" minOccurs="0"/>
202	                    <xs:element name="place" type="xs:string" minOccurs="0"/>
203	                    <xs:element name="startTime" type="xs:long" minOccurs="0"/>
204	                    <xs:element name="endTime" type="xs:long" minOccurs="0"/>
205	                    <xs:element name="speakerName" type="xs:string" minOccurs="0"/>
206	                    <xs:element name="speakerInfo" type="xs:string" minOccurs="0"/>
207	                    <xs:element name="speakerUserId" type="xs:int" minOccurs="0"/>
208	                </xs:sequence>
209	            </xs:complexType>
 */
package ru.myrange.soap;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import ru.myrange.datastore.RMS;

/**
 *
 * @author Nickolay Yegorov
 */
public class EventInfoType implements EventInterface {

    private static final String eventIdTag = "eventId";
    private static final String sectionTag = "section";
    private static final String titleTag = "title";
    private static final String infoTag = "info";
    private static final String placeTag = "place";
    private static final String startTimeTag = "startTime";
    private static final String endTimeTag = "endTime";
    private static final String speakerNameTag = "speakerName";
    private static final String speakerInfoTag = "speakerInfo";
    private static final String speakerUserIdTag = "speakerUserId";
    private Integer eventId = new Integer(0);
    private String section = "";
    private String title = "title";
    private String info = "";
    private String place = "";
    private Long startTime = new Long(0);
    private Long endTime = new Long(0);
    private String speakerName = "";
    private String speakerInfo = "";
    private Integer speakerUserId = new Integer(0);
    private Vector objects = new Vector();

    public EventInfoType(EventInfoType event) {
        setEndTime(event.getEndTime());
        setEventId(event.getEventId());
        setInfo(event.getInfo());
        setPlace(event.getPlace());
        setSection(event.getSection());
        setSpeakerInfo(event.getSpeakerInfo());
        setSpeakerName(event.getSpeakerName());
        setSpeakerUserId(event.getSpeakerUserId());
        setStartTime(event.getStartTime());
        setTitle(event.getTitle());
    }

    public EventInfoType(SoapObject soapObj) throws Exception {
        for (int i = 0; i < soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);

            if (soapObjInfo.name.equals(eventIdTag)) {
                setEventId(new Integer(Integer.parseInt(soapObj.getProperty(i).toString())));
            } else if (soapObjInfo.name.equals(sectionTag)) {
                setSection(soapObj.getProperty(i).toString());
            } else if (soapObjInfo.name.equals(titleTag)) {
                setSection(soapObj.getProperty(i).toString());
            } else if (soapObjInfo.name.equals(infoTag)) {
                setInfo(soapObj.getProperty(i).toString());
            } else if (soapObjInfo.name.equals(placeTag)) {
                setPlace(soapObj.getProperty(i).toString());
            } else if (soapObjInfo.name.equals(startTimeTag)) {
                setStartTime(new Long(Long.parseLong(soapObj.getProperty(i).toString())));
            } else if (soapObjInfo.name.equals(endTimeTag)) {
                setEndTime(new Long(Long.parseLong(soapObj.getProperty(i).toString())));
            } else if (soapObjInfo.name.equals(speakerNameTag)) {
                setSpeakerName(soapObj.getProperty(i).toString());
            } else if (soapObjInfo.name.equals(speakerInfoTag)) {
                setSpeakerInfo(soapObj.getProperty(i).toString());
            } else if (soapObjInfo.name.equals(speakerUserIdTag)) {
                setSpeakerUserId(new Integer(Integer.parseInt(soapObj.getProperty(i).toString())));
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

    /* Work with RMS */
	public EventInfoType(final byte[] record) throws Exception {	// EventInfoType object from RMS
        String recordString = new String(record, "UTF-8");

        setEventId( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setInfo( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setPlace( recordString.substring(0, recordString.indexOf(RMS.SPACER) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setSection( recordString.substring(0, recordString.indexOf(RMS.SPACER) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setSpeakerInfo( recordString.substring(0, recordString.indexOf(RMS.SPACER) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setSpeakerName( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setTitle( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setEndTime( new Long(Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER)))) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setSpeakerUserId( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setStartTime( new Long(Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER)))) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

	}

    public byte[] toRecord() throws Exception {		// Create the byte[] record to add to the record store
        return (getEventId().toString() + RMS.SPACER + getInfo().toString() +
                RMS.SPACER + getPlace().toString() +
                RMS.SPACER + getSection().toString() + RMS.SPACER + getSpeakerInfo().toString() +
                RMS.SPACER + getSpeakerName() + RMS.SPACER + getTitle() +
                RMS.SPACER + getEndTime() + RMS.SPACER +
                RMS.SPACER + getSpeakerUserId() + RMS.SPACER + getStartTime()
               ).getBytes("UTF-8");
    }

    /**
     * @return the eventId
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return the place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * @return the startTime
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the speakerName
     */
    public String getSpeakerName() {
        return speakerName;
    }

    /**
     * @param speakerName the speakerName to set
     */
    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    /**
     * @return the speakerInfo
     */
    public String getSpeakerInfo() {
        return speakerInfo;
    }

    /**
     * @param speakerInfo the speakerInfo to set
     */
    public void setSpeakerInfo(String speakerInfo) {
        this.speakerInfo = speakerInfo;
    }

    /**
     * @return the speakerUserId
     */
    public Integer getSpeakerUserId() {
        return speakerUserId;
    }

    /**
     * @param speakerUserId the speakerUserId to set
     */
    public void setSpeakerUserId(Integer speakerUserId) {
        this.speakerUserId = speakerUserId;
    }

    public String getHeaderText() {
        String headerText = (getPlace().length()>0) ? getPlace() : " ";

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(new Date(getStartTime().longValue()));
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(new Date(getEndTime().longValue()));

        headerText = calStart.get(Calendar.HOUR_OF_DAY) + ":" + calStart.get(Calendar.MINUTE) + " - " +
                     calEnd.get(Calendar.HOUR_OF_DAY) + ":" + calEnd.get(Calendar.MINUTE) + " " +
                     headerText;
        
        return headerText;
    }
}
