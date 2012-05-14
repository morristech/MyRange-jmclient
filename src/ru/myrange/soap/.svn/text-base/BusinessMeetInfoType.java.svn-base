/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */


package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import ru.myrange.datastore.RMS;

/**
 * This class represetns the businessMeetInfoType type from myrange.wsdl
 *
 * @author Nickolay Yegorov
 */
public class BusinessMeetInfoType implements EventInterface {

    private static final String businessMeetIdTag = "businessMeetId";
    private static final String partnerIdTag = "partnerId";
    private static final String titleTag = "title";
    private static final String infoTag = "info";
    private static final String placeTag = "place";
    private static final String startTimeTag = "startTime";
    private static final String endTimeTag = "endTime";

    private Vector objects = new Vector();

    private Integer businessMeetId;
    private Integer partnerId;
    private String title;
    private String info;
    private String place;
    private Long startTime;
    private Long endTime;

    public BusinessMeetInfoType(BusinessMeetInfoType bm) {
        setBusinessMeetId(bm.getBusinessMeetId());
        setPartnerId(bm.getPartnerId());
        setTitle(bm.getTitle());
        setInfo(bm.getInfo());
        setPlace(bm.getPlace());
        setStartTime(bm.getStartTime());
        setEndTime(bm.getEndTime());
    }

    /* Used by KSOAP Implementation methods */

    /*
     * Create new class from SoapObject
     */
    public BusinessMeetInfoType(SoapObject soapObj) throws Exception {
        for (int i=0; i<soapObj.getPropertyCount(); i++) {
            PropertyInfo soapObjInfo = new PropertyInfo();
            // Get property name of SoapObject
            soapObj.getPropertyInfo(i, new java.util.Hashtable(), soapObjInfo);
            if (soapObjInfo.name.equals(businessMeetIdTag)) {
                setBusinessMeetId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(partnerIdTag)) {
                setPartnerId( new Integer(Integer.parseInt(soapObj.getProperty(i).toString())) );
            }
            else if (soapObjInfo.name.equals(titleTag)) {
                setTitle( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(infoTag)) {
                setInfo( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(placeTag)) {
                setPlace( soapObj.getProperty(i).toString() );
            }
            else if (soapObjInfo.name.equals(startTimeTag)) {
                setStartTime( new Long(Long.parseLong(soapObj.getProperty(i).toString())) );
            }else if (soapObjInfo.name.equals(endTimeTag)) {
                setEndTime( new Long(Long.parseLong(soapObj.getProperty(i).toString())) );
            }
        }
	}

    public BusinessMeetInfoType(Integer businessMeetId, Integer partnerId, String title, String info, String place, Long startTime, Long endTime){
        this.businessMeetId = businessMeetId;
        this.partnerId = partnerId;
        this.info = info;
        this.place = place;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    /* Work with RMS */
	public BusinessMeetInfoType(final byte[] record) throws Exception {	// UserProfile object from RMS
        String recordString = new String(record, "UTF-8");

        setBusinessMeetId( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setPartnerId( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setTitle( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setInfo( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setPlace( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setStartTime( new Long( Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setEndTime( new Long( Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

	}

    public byte[] toRecord() throws Exception {		// Create the byte[] record to add to the record store
        return (getBusinessMeetId().toString() + RMS.SPACER + getPartnerId().toString() +
                RMS.SPACER + getTitle() +
                RMS.SPACER + getInfo() +
                RMS.SPACER + getPlace() +
                RMS.SPACER + getStartTime() +
                RMS.SPACER + getEndTime() +
                RMS.SPACER).getBytes("UTF-8");
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
     * @return the businessMeetId
     */
    public Integer getBusinessMeetId() {
        return businessMeetId;
    }

    /**
     * @param businessMeetId the businessMeetId to set
     */
    public void setBusinessMeetId(Integer businessMeetId) {
        this.businessMeetId = businessMeetId;
    }

    /**
     * @return the partnerId
     */
    public Integer getPartnerId() {
        return partnerId;
    }

    /**
     * @param partnerId the partnerId to set
     */
    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
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

    public String getHeaderText() {
        return (getPlace().length()>0) ? getPlace() : " ";
    }

}