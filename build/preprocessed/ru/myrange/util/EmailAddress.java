package ru.myrange.util;

/**
 * E-mail
 * User: serkin, ponfil
 * Date: 08.11.2009
 * Time: 23:39
 */
public final class EmailAddress {
    private String value;

    private EmailAddress(String value) {
        this.value = value;
    }

    public static EmailAddress fromPlainText(String value) throws IllegalArgumentException {
        // Validation
        if ((value == null) || (value.trim().length() < 5) ||
                (value.indexOf('@') <= 0) || (value.indexOf('.') <= 0) )
            throw new IllegalArgumentException("Argument it not an e-mail");
        return new EmailAddress(value);
    }

    public static boolean isEmailAddress(String value) {
        try {
            EmailAddress.fromPlainText(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailAddress that = (EmailAddress) o;

        return value.equals(that.value);
    }

    public int hashCode() {
        return value.hashCode();
    }

    public String toString() {
        return value;
    }
}