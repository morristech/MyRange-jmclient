/*
This class represetns the btMeet type from myrange.wsdl

<xs:element name="btMeet">
    <xs:complexType>
        <xs:sequence>
            <xs:element name="btAddress" type="xs:string"/>
            <xs:element name="devClass" type="xs:int" minOccurs="0"/>
            <xs:element name="friendlyName" type="xs:string" minOccurs="0"/>
            <xs:element name="startTime" type="xs:dateTime"/>
            <xs:element name="endTime" type="xs:dateTime"/>
        </xs:sequence>
    </xs:complexType>
</xs:element>
*/

package ru.myrange.soap;

import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.RMS;

public class BtMeet implements KvmSerializable {

    private static final String btAddressTag = "n0:btAddress";
    private static final String devClassTag = "n0:devClass";
    private static final String friendlyNameTag = "n0:friendlyName";
    private static final String startTimeTag = "n0:startTime";
    private static final String endTimeTag = "n0:endTime";

    private Vector objects = new Vector();
    private String btAddress = "";
    private Integer devClass = new Integer(0);
	private String friendlyName = "";
    private Long startTime = new Long(0);
    private Long endTime = new Long(0);

	/* Used by KSOAP Implementation methods */

	public BtMeet() { }

	public BtMeet(String btAddress, Long startTime, Long endTime) {
		setBtAddress(btAddress);
		setStartTime(startTime);
        setEndTime(endTime);
	}

    public BtMeet(String btAddress, Integer devClass, String friendlyName, Long startTime, Long endTime) {
		setBtAddress(btAddress);
        setDevClass(devClass);
        setFriendlyName(friendlyName);
		setStartTime(startTime);
        setEndTime(endTime);
	}

    public BtMeet(final String btAddress, int devClass, String friendlyName, long time)
	{
		this.btAddress = btAddress.toLowerCase();
		this.devClass = new Integer(devClass);
		this.friendlyName = friendlyName;
        this.startTime = new Long(time - MyRangeMIDlet.mergeIntervalVar/2);
		this.endTime = new Long(time + MyRangeMIDlet.mergeIntervalVar/2);
	}

	public BtMeet(final byte[] record) throws Exception {	// BtMeet object from RMS
        String recordString = new String(record, "UTF-8");

        setBtAddress( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setDevClass( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setFriendlyName( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setStartTime( new Long ( Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setEndTime( new Long ( Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
	}

    public byte[] toRecord() throws Exception {		// Create the byte[] record to add to the record store
        return (getBtAddress() + RMS.SPACER + getDevClass().toString() + RMS.SPACER + getFriendlyName() +
                RMS.SPACER + getStartTime().toString() + RMS.SPACER + getEndTime().toString() + RMS.SPACER).getBytes("UTF-8");
	}
    
	public int merge(BtMeet log) {	// Merge to intevals (expand the time interval)
		if (this.btAddress.compareTo(log.btAddress) != 0) {
			return 1;	// Device dddresses are different
		} else if (this.endTime.longValue() > log.endTime.longValue()) {
			return 2;	// Wrong order
		} else if ( (log.endTime.longValue() - this.endTime.longValue()) > MyRangeMIDlet.mergeIntervalVar ) {
			return 3;	// Too much time interval
		} else {
			this.endTime = log.endTime;
			return 0;	// OK
		}
	}

    public String getBtAddress() { return btAddress; }
    public Integer getDevClass() { return devClass; }
    public String getFriendlyName() { return friendlyName; }
    public Long getStartTime() { return startTime; }
    public Long getEndTime() { return endTime; }

    public void setBtAddress(String btAddress) {
        if (btAddress != null) this.btAddress = btAddress;
    }
    public void setDevClass(Integer devClass) {
        if (devClass != null) this.devClass = devClass;
    }
    public void setFriendlyName(String friendlyName) {
        if (friendlyName != null) this.friendlyName = friendlyName;
    }
    public void setStartTime(Long startTime) {
        if (startTime != null) this.startTime = startTime;
    }
    public void setEndTime(Long endTime) {
        if (endTime != null) this.endTime = endTime;
    }

    // Pack element befor sending
    public void pack() {
        // Pack
        objects.removeAllElements();
        if (btAddress != null) objects.addElement(new PropertyObject(btAddressTag, PropertyInfo.STRING_CLASS, btAddress));
        if (devClass != null) objects.addElement(new PropertyObject(devClassTag, PropertyInfo.INTEGER_CLASS, devClass));
        if (friendlyName != null) objects.addElement(new PropertyObject(friendlyNameTag, PropertyInfo.STRING_CLASS, friendlyName));
        if (startTime != null) objects.addElement(new PropertyObject(startTimeTag, PropertyInfo.STRING_CLASS, startTime));
        if (endTime != null) objects.addElement(new PropertyObject(endTimeTag, PropertyInfo.STRING_CLASS, endTime));
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
