/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff;

import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import ru.myrange.bluetooth.Bluetooth;
import ru.myrange.stuff.xlwuit.Hyperlink;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.util.EmailAddress;
import ru.myrange.util.PhoneNumber;

/**
* Some useful routines.
* @author Oleg Ponfilenok
*/

public class MyRoutines {

    public static final char[] C_HEX_CHARS = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

	// This routine gets int from the String
	public static final int strToInt(final String str) 
	{
		char[] c = str.toCharArray();
		int size = c.length;
		
		int l = -1;
		for( int i = 0; i < size; ++i ){
			int n = -1;
			switch (c[i]) {		//'\u0030' through '\u0039', ISO-LATIN-1 digits ('0' through '9')
				case '\u0030' :
					n = 0;
					break;
				case '\u0031' :
					n = 1;
					break;
				case '\u0032' :
					n = 2;
					break;
				case '\u0033' :
					n = 3;
					break;
				case '\u0034' :
					n = 4;
					break;
				case '\u0035' :
					n = 5;
					break;
				case '\u0036' :
					n = 6;
					break;
				case '\u0037' :
					n = 7;
					break;
				case '\u0038' :
					n = 8;
					break;
				case '\u0039' :
					n = 9;
					break;
				default :
					n = -1;
					break;
			}
			
			if ( (l == -1) && (n > -1) ) {
				l = n;
			} else if (n > -1) {
				if (l > 3275) return -2;	// the number is too big
				l = l*10 + n;
			}
			else {
				return -1;	// If there is alfa between numbers get out
			}
		}
		return l;
	}
	
	// This routine gets long from the String
	public static final long strToLong(final String str) 
	{
		char[] c = str.	toCharArray();
		int size = c.length;
		
		long l = -1;
		for( int i = 0; i < size; ++i ){
			long n = -1;
			switch (c[i]) {		//'\u0030' through '\u0039', ISO-LATIN-1 digits ('0' through '9')
				case '\u0030' :
					n = 0;
					break;
				case '\u0031' :
					n = 1;
					break;
				case '\u0032' :
					n = 2;
					break;
				case '\u0033' :
					n = 3;
					break;
				case '\u0034' :
					n = 4;
					break;
				case '\u0035' :
					n = 5;
					break;
				case '\u0036' :
					n = 6;
					break;
				case '\u0037' :
					n = 7;
					break;
				case '\u0038' :
					n = 8;
					break;
				case '\u0039' :
					n = 9;
					break;
				default :
					n = -1;
					break;
			}
			
			if ( (l == -1) && (n > -1) ) {
				l = n;
			} else if (n > -1) {
				if (l > 2147483647) return -2;	// the number is too big
				l = l*10 + n;
			}
			else {
				return -1;	// If there is alfa between numbers get out
			}
		}
		return l;
	}

    /*
	// This routine converts int to 4 bytes
	public static final byte[] intToBytes(final int num) 
	{
		byte[] b = new byte[4];
		int n = num;
		int m;
		for (int i = 0; i < 4; i++) {
			m = n;
			n = n/256;
			m = m - n*256;
			b[3-i] = (byte) (m & 0xff);
		}
		return b;
	}
	
	// This routine converts bytes to int
	public static final int bytesToInt(final byte[] b) 
	{
		int n = 0;
		int l = (b.length > 4) ? 4 : b.length;
		for (int i = 0; i < l; i++) {
			n = n*256 + ( (int) (b[i] & 0xff) );
		}
		return n;
	}
	
	// This routine converts long to 6 bytes
	public static final byte[] longToBytes(final long num) 
	{
		byte[] b = new byte[6];
		long n = num;
		long m;
		for (int i = 0; i < 6; i++) {
			m = n;
			n = n/256;
			m = m - n*256;
			b[5-i] = (byte) (m & 0xff);
		}
		return b;
	}
	
	// This routine converts 6 bytes to long
	public static final long bytesToLong(final byte[] b) 
	{
		long l = 0;
		for (int i = 0; i < 6; i++) {
			l = l*256 + ( (long) (b[i] & 0xff) );
		}
		return l;
	}
	
	// This routine converts long unix time to 4 bytes
	public static final byte[] unixTimeToBytes(final long unixTime) 
	{
		byte[] b = new byte[4];
		long u = unixTime/1000;	// get away the miliseconds
		long t;
		for (int i = 0; i < 4; i++) {
			t = u;
			u = u/256;
			t = t - u*256;
			b[3-i] = (byte) (t & 0xff);
		}
		return b;
	}
	
	// This routine converts 4 bytes to long unix time (in ms)
	public static final long bytesToUnixTime(final byte[] b) 
	{
		long unixTime = 0;
		for (int i = 0; i < 4; i++) {
			unixTime = unixTime*256 + ( (long) (b[i] & 0xff) );
		}
		unixTime = unixTime*1000;
		return unixTime;
	}
	
	// This routine converts hex string to bytes
	public static final byte[] hexToBytes(final String str)
	{
		char buf[] = str.toLowerCase().toCharArray();
		byte[] hash = new byte[buf.length / 2];
		int n, m;
		Integer z;
		for (int i = 0, x = 0; i < hash.length; i++) {
			n = (int) buf[x++];
			if ( (n >= 48) && (n <=57) ) {			// char '0' - '9' corresponds to int 48 - 57
				z= new Integer(( (n - 48) & 0x0f )*0x10); 
				hash[i] = z.byteValue();
			} else if ( (n >= 97) && (n <=102) ) {	// char 'a' - 'f' corresponds to int 97 - 102
				z= new Integer(( (n - 97 + 10) & 0x0f )*0x10); 
				hash[i] = z.byteValue();
			} else {
				return null;
			}
			
			m = (int) buf[x++];
			if ( (m >= 48) && (m <=57) ) { 
				z= new Integer( (m - 48) & 0x0f ); 
				hash[i] += z.byteValue();
			} else if ( (m >= 97) && (m <=102) ) { 
				z= new Integer( (m - 97 + 10) & 0x0f ); 
				hash[i] += z.byteValue();
			} else {
				return null;
			}
		}
		return hash;
	}
	
	// This routine converts bytes to hex String
	public static final String bytesToHex( final byte[] hash ) {
		if (hash == null) return "";
		
		char buf[] = new char[hash.length * 2];
		for (int i = 0, x = 0; i < hash.length; i++) {
			buf[x++] = MyConsts.C_HEX_CHARS[(hash[i] >>> 4) & 0xf];
			buf[x++] = MyConsts.C_HEX_CHARS[hash[i] & 0xf];
		}
		return new String(buf);
	}
	*/

	// This routine returns name of device class
	public static final String devClassToString(final int devClass) {
		String className = "";
		switch (devClass) {
			case Bluetooth.I_CLASS_COMPUTER :
				className = Bluetooth.S_CLASS_COMPUTER;
				break;
			case Bluetooth.I_CLASS_DESKTOP :
				className = Bluetooth.S_CLASS_DESKTOP;
				break;
			case Bluetooth.I_CLASS_LAPTOP :
				className = Bluetooth.S_CLASS_LAPTOP;
				break;
			case Bluetooth.I_CLASS_PDA :
				className = Bluetooth.S_CLASS_PDA;
				break;
			case Bluetooth.I_CLASS_PHONE :
				className = Bluetooth.S_CLASS_PHONE;
				break;
			case Bluetooth.I_CLASS_CELLULAR :
				className = Bluetooth.S_CLASS_CELLULAR;
				break;
			case Bluetooth.I_CLASS_CORDLESS :
				className = Bluetooth.S_CLASS_CORDLESS;
				break;
			case Bluetooth.I_CLASS_SMARTPHONE :
				className = Bluetooth.S_CLASS_SMARTPHONE;
				break;
			default :
				className = "0x" + Integer.toString(devClass, 16);
				break;
		}
		return className;
	}

    // This routine generates MD5 hash
    /*
	public static final String md5Hash(final String str) throws Exception {
		MD5 md5 = new MD5(str.getBytes("UTF-8"));
        String hashResult = MD5.toHex(md5.doFinal());
		return hashResult;
	}
    */

    /*
    // This routine resize image
    public static Image resizeImage(Image image, int newWidth, int newHeight)
    {
        int srcWidth = image.getWidth();
        int srcHeight = image.getHeight();

        Image newImage = Image.createImage(newWidth, newHeight);
        Graphics g = newImage.getGraphics();

        for (int y = 0; y < newHeight; y++)
        {
            for (int x = 0; x < newWidth; x++)
            {
                g.setClip(x, y, 1, 1);
                int dx = x * srcWidth / newWidth;
                int dy = y * srcHeight / newHeight;
                //g.drawImage(image, x - dx, y - dy, Graphics.LEFT | Graphics.TOP);
                g.drawImage(image, x - dx, y - dy);
            }
        }

        //Image immutableImage = Image.createImage(newImage);
        //return immutableImage;
        return newImage;
    }
    */

    public static final String timeToString(final long unixTime) {
		if (unixTime == 0) return " ";
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(unixTime));
        return  c.get(Calendar.HOUR_OF_DAY) + ":" + (c.get(Calendar.MINUTE) > 9 ? "" : "0") + c.get(Calendar.MINUTE) + " " +
                c.get(Calendar.DAY_OF_MONTH) + " " + StringConsts.S_MONTHS[c.get(Calendar.MONTH)] + " " + c.get(Calendar.YEAR);
	}

    /**
     * Make image square (cut the edges)
     */
    public static Image imageToSquare(Image image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        int size = (width < height) ? width : height;

        Image newImage = Image.createImage(size, size);
        Graphics g = newImage.getGraphics();

        if (width > height) {
            //g.drawImage(image, (height - width)/2, 0, Graphics.TOP | Graphics.LEFT);
            g.drawImage(image, (height - width)/2, 0);
        } else {
            //g.drawImage(image, 0, (width - height)/2, Graphics.TOP | Graphics.LEFT);
            g.drawImage(image, 0, (width - height)/2);
        }

        //Image immutableImage = Image.createImage(newImage);
        //return immutableImage;
        return newImage;
    }

    // Creates a new color that is a darker version of this color
    /*
    public static int darkerColor(final int source) {
        int FACTOR = 850;

        int r = (source >> 16) & 0xFF;
        int g = (source >> 8) & 0xFF;
        int b = (source >> 0) & 0xFF;

        r = r*FACTOR/1000;
        g = g*FACTOR/1000;
        b = b*FACTOR/1000;

        return ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);
    }
    */

    /**
     * Is string an e-mail
     *
     * @param str
     * @return true if string is e-mail
     */
    /*
	public static final boolean isEmail(final String str) {
        if (str == null) return false;
		if ( (str.trim().length() < 5) || (str.indexOf('@') <= 0) || (str.indexOf('.') <= 0) )
            return false;
        else return true;
	}
    */

    /**
     * Delete all unusefull chars and return phonenumber in "79261234567" format
     *
     * @param str
     * @return null if string is not phone nuber or phone number in "79261234567" format
     */
    /*
	public static final String stringToPhonenumber(final String source) {
		// Chars to delete
        final char[] TRASH_CHARS = {
                ' ',
                '-',
                '(',
                ')',
                '+',
                '_'
            };

        if (source == null) return null;
        String str = new String(source);
        
        // Delete all unusefull chars
        str = str.trim();
        for (int i=0; i<TRASH_CHARS.length; i++) {
            while (true) {
                int index = str.indexOf(TRASH_CHARS[i]);
                if (index<0) break;
                // Cut char at index
                str = str.substring(0, index) + ((index < str.length()-1) ? (str.substring(index+1)) : "");
            }
        }

        // Is str a phone number?
        if ( (str.trim().length()<10) || (str.trim().length()>11) ) return null;
        if (str.trim().length()==11) {
            if (str.charAt(0) == '8') str = "7" + str.substring(1); // replace "8" to "7"
            else if (str.charAt(0) == '7') ;    // nothing to do
            else return null;
        } else if (str.trim().length()==10) {
            str = "7" + str;
        } else return null;
        String numbers = "0123456789";
        for (int i=0; i<str.length(); i++) {
            if (numbers.indexOf(str.charAt(i)) < 0) return null;
        }

        // str is a phone number in "79261234567" format

        System.out.println("BEFORE: \"" + source + "\"");
        System.out.println("AFTER: \"" + str + "\"");
        System.out.println();

        return str;
	}
     * */

    /**
     * Is string an e-mail or a phone number
     *
     * @param str
     * @return true if string is login
     */
	public static final boolean isLogin(final String str) {
		return EmailAddress.isEmailAddress(str) || PhoneNumber.isPhoneNumber(str);
	}


    /**
     * Transform string to phone number or e-mail
     * or throw IllegalArgumentException
     *
     * @param value to transform
     * @return phone number or e-mail
     * @exception IllegalArgumentException if input string is not an e-mail or phone number
     */
	public static final String transformLogin(final String value) throws IllegalArgumentException {
        // Validation login. Shoulb be a phone number or an e-mail
        try {
            return (PhoneNumber.fromPlainText(value)).toString();
        } catch (IllegalArgumentException ex) {
            try {
                return (EmailAddress.fromPlainText(value)).toString();
            } catch (IllegalArgumentException ex2) {
                throw new IllegalArgumentException(StringConsts.S_EMPTY_PINCODE);
            }
        }
	}

    /**
     * Is string a password
     *
     * @param str
     * @return true if string is a password
     */
    /*
	public static final boolean isPassword(final String str) {
		if ( str.length() < 1) return false;
        return true;
	}
     * */

    /**
     * Parse about information string
     *
     * @param source
     * @return vector of com.sun.lwuit.Component to insert in form
     */
    public static Vector parseAbout(String source)
	{
        if (source == null) return null;
        Vector result = new Vector();

        int index = 0;  // Index of start new tag parse
        while (true) {
            if (index>=source.length()) break;

            int indexImg = source.indexOf("[img=", index);  // Index of "img" tag
            int indexUrl = source.indexOf("[url=", index);  // Index of "url" tag

            if (indexImg<0 && indexUrl<0) break;

            if ((indexImg<0 || indexImg>index) && (indexUrl<0 || indexUrl>index)) {
                // Plain text before tag
                int indexTag = Math.min(indexImg>0 ? indexImg : source.length(),
                        indexUrl>0 ? indexUrl : source.length());
                String text = source.substring(index, indexTag);
                // Parse palin text
                result.addElement(new TextComponent(text));

                index = indexTag;
                continue;
            }

            int indexEnd = source.indexOf("]", index);      // Index of eng of the tag
            if ((indexImg>0 && indexImg<indexUrl) || (indexImg>0 && indexUrl<0)) {
                // Img is first
                String img = source.substring(indexImg+1, indexEnd);
                //Parse img
                int i = img.indexOf("\"")+1;
                String base64 = img.substring(i, img.indexOf("\"", i));
                byte[] out = Base64Coder.decode(base64);
                Image pic = Image.createImage(out, 0, out.length);
                result.addElement(new Label(pic));
            }
            else {
                // Url is first
                String url = source.substring(indexUrl+1, indexEnd);
                //Parse url;
                int i = url.indexOf("\"")+1;
                String link = url.substring(i, url.indexOf("\"", i));
                i = url.indexOf("\"", i)+1;
                i = url.indexOf("\"", i)+1;
                String text = null;
                if (i > 0) text = url.substring(i, url.indexOf("\"", i));
                else text = link;
                result.addElement(new Hyperlink(link, text));
            }
            index = indexEnd+1;
        }
        if (index<source.length()) {
            String text = source.substring(index, source.length());
            // Parse plain text
            result.addElement(new TextComponent(text));
        }

        return result;
	}

}