/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.soap;

import java.util.Calendar;
import java.util.Date;
import ru.myrange.datastore.RMS;
import ru.myrange.stuff.StringConsts;

/**
 * Helper Message class
 *
 * @author Oleg Ponfilenok
 */
public class Message {

    public static final String TYPE_PRIVATE = "private";    // Private messages
    public static final String TYPE_SMS = "sms";            // SMS messages
    public static final String DIRECTION_INBOX = "inbox";    // Direction for request inbox private messages
    public static final String DIRECTION_OUTBOX = "outbox";  // Direction for request outbox private messages
    public static final String DIRECTION_BOTH = "both";      // Direction for request inbox and outbox private messages
    public static final String STATUS_READ = "read";    // Status of read messages
    public static final String STATUS_UNREAD = "unread";// Type of unread messages

    private Integer msgId = new Integer(0);
    private Integer senderId = new Integer(0);
    private long time = 0;              // Time of message
    private String timeHR = "";         // Time of message in human readable format
    private String type = "";           // private/sms
    private String direction = "";      // inbox/outbox/both
    private String status = "";         // read/unread
    private String subject = "";
    private String message = "";
    private Boolean preview = null;

    public Message() { }

    public Message(Message msg) {
        setMsgId(msg.getMsgId());
        setSenderId(msg.getSenderId());
        setTime(msg.getTime());
        setType(msg.getType());
        setDirection(msg.getDirection());
        setStatus(msg.getStatus());
        setSubject(msg.getSubject());
        setMessage(msg.getMessage());
        setPreview(msg.getPreview());
    }

    public Integer getMsgId() { return msgId; }
    public Integer getSenderId() { return senderId; }
    public long getTime() { return time; }
    public String getTimeHR() { return timeHR; }
    public String getType() { return type; }
    public String getDirection() { return direction; }
    public String getStatus() { return status; }
    public String getSubject() { return subject; }
    public String getMessage() { return message; }
    public Boolean getPreview() { return preview; }
    private String getPreviewStr() {
        if (preview != null) return preview.toString();
        else return "";
    }

    public void setMsgId(Integer msgId) {
        if (msgId != null) this.msgId = msgId;
    }
    public void setSenderId(Integer senderId) {
        if (senderId != null) this.senderId = senderId;
    }
    public void setTime(long unixTime){
		this.time = unixTime;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(unixTime));
		setTimeHR(c.get(Calendar.DAY_OF_MONTH) + " " + StringConsts.S_MONTHS[c.get(Calendar.MONTH)] + " " +
                c.get(Calendar.HOUR_OF_DAY) + ":" + (c.get(Calendar.MINUTE) > 9 ? "" : "0") + c.get(Calendar.MINUTE));
	}
    public void setTimeHR(String timeHR) {
        if (timeHR != null) this.timeHR = timeHR;
    }
    public void setType(String type) {
        if (type != null) this.type = type;
    }
    public void setDirection(String direction) {
        if (direction != null) this.direction = direction;
    }
    public void setStatus(String status) {
        if (status != null) this.status = status;
    }
    public void setSubject(String subject) {
        if (subject != null) this.subject = subject;
    }
    public void setMessage(String message) {
        if (message != null) this.message = message;
    }
    public void setPreview(Boolean preview) {
        this.preview = preview;
    }
    private void setPreview(String previewStr) {
        if (previewStr.equals("true")){
            setPreview( new Boolean(true));
        } else if (previewStr.equals("false")){
            setPreview( new Boolean(false));
        }
    }

    /* Work with RMS */
	public Message(final byte[] record) throws Exception {	// PrivateMessage object from RMS
        String recordString = new String(record, "UTF-8");

        setMsgId( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setSenderId( new Integer( Integer.parseInt(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setTime( new Long( Long.parseLong(recordString.substring(0, recordString.indexOf(RMS.SPACER))) ).longValue() );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setType( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setDirection( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setStatus( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setSubject( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setMessage( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
        recordString = recordString.substring(recordString.indexOf(RMS.SPACER) + RMS.SPACER.length());

        setPreview( recordString.substring(0, recordString.indexOf(RMS.SPACER)) );
	}

    public byte[] toRecord() throws Exception {		// Create the byte[] record to add to the record store
        return (getMsgId().toString() + RMS.SPACER +
                getSenderId().toString() + RMS.SPACER +
                (new Long(getTime())).toString() + RMS.SPACER + getType() + RMS.SPACER +
                getDirection() + RMS.SPACER + getStatus() + RMS.SPACER +
                getSubject() + RMS.SPACER + getMessage() + RMS.SPACER +
                getPreviewStr() + RMS.SPACER).getBytes("UTF-8");
    }

}
