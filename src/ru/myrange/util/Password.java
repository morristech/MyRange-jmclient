package ru.myrange.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Date;

/**
 * Пароль
 * User: serkin, ponfil
 * Date: 08.11.2009
 * Time: 23:43
 */
public final class Password {

    /**
     * Пароль хранится в виде (MD5-hash) строки
     * в ?нижнем? регистре
     */
    private String hash;

    /**
     * Конструирование пароля по plain-text строке
     * @param plainText plain-text строка
     * @return сконструированный пароль
     * @throws IllegalArgumentException - пустая (либо null) plain-text строка
     */
    public static Password fromPlainText(String plainText) throws IllegalArgumentException {
        // Validation
        if (plainText==null || (plainText.length()==0))
            throw new IllegalArgumentException("Password cannot be null or empty");

        // Generate MD5 hash
		MD5 md5;
        try {
            md5 = new MD5(plainText.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            md5 = new MD5(plainText.getBytes());
        }
        String hashResult = MD5.toHex(md5.doFinal());
        return Password.fromHash(hashResult);
    }

    public static boolean isPassword(String value) {
        try {
            Password.fromPlainText(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Конструирование пароля по хеш-коду
     * @param passwordHash хеш-код пароля
     * @return сконструированный пароль
     * @throws IllegalArgumentException - недопустимый хеш-код
     */
    public static Password fromHash(String passwordHash) throws IllegalArgumentException {
        // TODO: check <passwordHash>
        if (passwordHash==null || (passwordHash.length()==0))
            throw new IllegalArgumentException("Password hash cannot be null or empty");
        passwordHash = passwordHash.toLowerCase();
        return new Password(passwordHash);
    }

    /**
     * Генерация пароля псевдослучайным образом
     * @return сгенрированный пароль
     */
    public static Password generate() {
        String passwordText = generateAsPlainText();
        return fromPlainText(passwordText);
    }

    public static String generateAsPlainText() {
        return String.valueOf(1111 + (int) (RANDOM.nextFloat() * 8888.0f));
    }

    private Password(String hash) {
        this.hash = hash;
    }

    private static final Random RANDOM = new Random(new Date().getTime());

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Password password = (Password) o;

        return hash.equals(password.hash);
    }

    public int hashCode() {
        return hash.hashCode();
    }

    public String toString() {
        return hash;
    }
}