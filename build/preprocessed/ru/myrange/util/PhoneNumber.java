package ru.myrange.util;

/**
 * Номер телефона
 * User: serkin, ponfil
 * Date: 09.11.2009
 * Time: 0:01
 */
public final class PhoneNumber {
    private String value;

    private PhoneNumber(String value) {
        this.value = value;
    }

    /**
     * Delete all unnecessary chars and convert phonenumber to the "79261234567" format
     */
    public static PhoneNumber fromPlainText(String value) throws IllegalArgumentException {
        // Chars to delete
        final char[] UNNUCESSARY_CHARS = {
                ' ',
                '-',
                '(',
                ')',
                '+',
                '_'
            };

        if (value == null)
            throw new IllegalArgumentException("Argument cannot be null");

        String result = new String(value);

        // Delete all unnecessary chars
        result = result.trim();
        for (int i=0; i<UNNUCESSARY_CHARS.length; i++) {
            while (true) {
                int index = result.indexOf(UNNUCESSARY_CHARS[i]);
                if (index<0) break;
                // Cut char at index
                result = result.substring(0, index) + ( (index < result.length()-1)
                        ? (result.substring(index+1)) : "" );
            }
        }

        // Is result a phone number?
        if ( (result.trim().length()<10) || (result.trim().length()>11) )
            throw new IllegalArgumentException("Argument is not a phone number");
        if (result.trim().length()==11) {
            if (result.charAt(0) == '8') result = "7" + result.substring(1); // replace "8" to "7"
            else if (result.charAt(0) == '7') ;    // nothing to do
            else
                throw new IllegalArgumentException("Argument is not a phone number");
        } else if (result.trim().length()==10) {
            result = "7" + result;
        } else
            throw new IllegalArgumentException("Argument is not a phone number");

        String numbers = "0123456789";
        for (int i=0; i<result.length(); i++) {
            if (numbers.indexOf(result.charAt(i)) < 0)
                throw new IllegalArgumentException("Argument is not a phone number");
        }

        // result is a phone number in the "79261234567" format
        return new PhoneNumber(result);
    }

    public static boolean isPhoneNumber(String value) {
        try {
            PhoneNumber.fromPlainText(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        return value.equals(that.value);
    }

    public int hashCode() {
        return value.hashCode();
    }

    public String toString() {
        return value;
    }
}