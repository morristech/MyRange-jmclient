package ru.myrange.util;

import java.util.Date;
import java.util.Random;

/**
 * Пинкод для авторизации пользователя
 * В моменте реализация подразумевает 4х-символьную строку,
 * содержащую арабские цифры и латинские буквы в верхнем регистре
 * User: serkin
 * Date: 04.11.2009
 * Time: 2:21:41
 */
public final class Pincode {

    /**
     * целочисленное представление пин-кода (индекс в бд)
     */
    private final int intValue;

    /**
     * строковое представление пин-кода
     */
    private final transient String stringValue;

    /**
     * Максимальная длина пин-кода в строковом представлении
     */
    private static final int LENGTH = 4;

    /**
     * Допустимые символы пин-кода
     */
    private static final String CHARS = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";

    /**
     * Основание для преобразования из строкого представления в целочисленное и обратно
     */
    private static final int RADIX = CHARS.length();

    /**
     * Максимальное значение целочисленного представления
     */
    private static final int MAX_INTEGER = RADIX*RADIX*RADIX*RADIX;

    /**
     * Конструирование пин-кода из числа
     * @param intValue - положительное целочисленное представление пин-кода
     * @return сконструированный пин-код
     */
    public static Pincode valueOf(int intValue) {
        if (intValue <= 0)
            throw new IllegalArgumentException("Pincode' integaer-value must be positive");
        if (intValue > MAX_INTEGER)
            throw new IllegalArgumentException("Too big pincode' integer-value");

        return new Pincode (intValue, int2string(intValue));
    }

    /**
     * Конструирование пин-кода из строки
     * @param stringValue строковое представление пин-кода
     * @return сконструированный пин-код
     */
    public static Pincode valueOf(String stringValue) throws NullPointerException, IllegalArgumentException   {
        if (stringValue == null)
            throw new NullPointerException("Pincode string-value cannot be null");
        if (stringValue.length() != LENGTH)
            throw new IllegalArgumentException("Pincode string-value must be of length " + LENGTH);

        return new Pincode(string2int(stringValue), stringValue);
    }

    /**
     * Является ли строка пинкодом
     * @param stringValue строковое представление пин-кода
     * @return true, если строка является пинкодом
     */
    public static boolean isPincode(String stringValue) {
        try {
            valueOf(stringValue);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Генерация случайного пин-кода
     * @return случайного пинкод, не обязательно уникальный в рамках БД
     */
    public static Pincode random() {
        int intValue = 1 + randomizer.nextInt(MAX_INTEGER);
        return valueOf(intValue);
    }

    /**
     * Возвращает строковое представление пин-кода
     * @return строковое представление пин-кода
     *  (never null)
     */
    public String toString() {
        return stringValue;
    }

    /**
     * Возвращает целочисленное представление пин-кода
     * @return целочисленное представление пин-кода
     */
    public int toInteger() {
        return intValue;
    }

    ///////////////////////////////////////////////////////////////////////////

    private Pincode(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue.toUpperCase();
    }

    private static final Random randomizer = new Random(new Date().getTime());

    private static String int2string(int intValue) {
        String result = "";
        while (intValue > 0) {
            int d = intValue % RADIX;
            char c = CHARS.charAt(d);
            result = c + result;
            intValue /= RADIX;
        }
        return result;
    }

    private static int string2int(String stringValue) {
        int intValue = 0;
        String stringValueUC = stringValue.toUpperCase();
        for (int i=0; i<stringValueUC.length(); i++) {
            char c = stringValueUC.charAt(i);
            int d = CHARS.indexOf(c);
            if (-1 == d)
                throw new IllegalArgumentException("Pincode string-value contains invalid char: " + c);
            intValue = intValue*RADIX + d;
        }
        return intValue;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pincode pincode = (Pincode) o;

        return intValue == pincode.intValue;
    }

    public int hashCode() {
        return intValue;
    }
}