/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff;

import javax.microedition.pim.*;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.soap.ContactType;
import ru.myrange.soap.UserInfo;

/**
 * Static class to work with the Personal Information Management (Phonebook of mobile device)
 *
 * @author Oleg Ponfilenok
 */
public class PIMAccess {


    public static void addNewContactToPhonebook(UserInfo profile) {
        ContactList contacts;
        try {
            // Открываем заданный по умолчанию список контактов
            contacts = (ContactList) PIM.getInstance().openPIMList(PIM.CONTACT_LIST, PIM.WRITE_ONLY);

            /*
            // Поиск контактов.
            // Метод countValues() возвращает число значений,
            // содержащихся в текущем поле.
            Enumeration contacts = clist.items();
            Contact c = (Contact) contacts.nextElement();
            int phoneNumbers = c.countValues(Contact.TEL);
            for(int i = 0; i < phoneNumbers; i++) {
                try {
                    String tel = c.getString(Contact.TEL, i);
                    System.out.println("[PIM] tel" + Integer.toString(i) + " = " + tel);
                    MyRange.out.println("[PIM] tel" + Integer.toString(i) + " = " + tel);
                } catch (Exception e) {
                    System.out.println("[PIM] Error: " + e.toString());
                    MyRange.showInfoAlert("Error: " + e.toString(), backDisplayable);
                }
            }
            */

            /*
            //Добавляем значение в контакт
            Contact c = clist.createContact();
            int attrs = Contact.ATTR_HOME;
            c.addString(Contact.TEL, attrs, "416-799-1313");
            // Некоторые поля могут быть добавлены без атрибутов
            c.addString(Contact.ORG, PIMItem.ATTR_NONE, "someName Corporation");
            // Заносим элемент в базу данных телефона
            c.commit();
            */

            // Add new contact
            String contactName = "";
            Contact contact = contacts.createContact();
            // firstname
            String[] name = new String[contacts.stringArraySize(Contact.NAME)];
            if (contacts.isSupportedArrayElement(Contact.NAME, Contact.NAME_GIVEN)) {
                name[Contact.NAME_GIVEN] = profile.getFirstName();
                contactName = profile.getFirstName();
            }
            // lastname
            if (profile.getAccountType().equals(UserInfo.ACCOUNT_TYPE_PERSONAL)){
                if (contacts.isSupportedArrayElement(Contact.NAME, Contact.NAME_FAMILY)) {
                    name[Contact.NAME_FAMILY] = profile.getLastName();
                    contactName = profile.getLastName() + " " + contactName;
                }
            }
            contact.addStringArray(Contact.NAME, PIMItem.ATTR_NONE, name);
            // work
            //if (contacts.isSupportedArrayElement(Contact.ORG, PIMItem.ATTR_NONE))
                //contacts.addString(Contact.ORG, PIMItem.ATTR_NONE, "someName Corporation");
            // address
            String[] addr = new String[contacts.stringArraySize(Contact.ADDR)];
            //if (contacts.isSupportedArrayElement(Contact.ADDR, Contact.ADDR_COUNTRY))
                //addr[Contact.ADDR_COUNTRY] = "USA";
            if (contacts.isSupportedArrayElement(Contact.ADDR, Contact.ADDR_LOCALITY))
                addr[Contact.ADDR_LOCALITY] = profile.getResidentCity();
            //if (contacts.isSupportedArrayElement(Contact.ADDR, Contact.ADDR_POSTALCODE))
                //addr[Contact.ADDR_POSTALCODE] = "91921-1234";
            //if (contacts.isSupportedArrayElement(Contact.ADDR, Contact.ADDR_STREET))
                //addr[Contact.ADDR_STREET] = "123 Main Street";
            if (contacts.isSupportedField(Contact.ADDR))
                contact.addStringArray(Contact.ADDR, Contact.ATTR_HOME, addr);
            // contacts
            for (int i=0; i<profile.getContact().size(); i++) {
                ContactType c = (ContactType) profile.getContact().elementAt(i);
                if (c.getName().equals(ContactType.CONTACT_NAME_PHONE)) {
                    if (contacts.isSupportedField(Contact.TEL))
                        contact.addString(Contact.TEL, Contact.ATTR_MOBILE, c.getUid());
                } else if (c.getName().equals(ContactType.CONTACT_NAME_EMAIL)) {
                    if (contacts.isSupportedField(Contact.EMAIL))
                        contact.addString(Contact.EMAIL, Contact.ATTR_HOME | Contact.ATTR_PREFERRED, c.getUid());
                } else if (c.getName().equals(ContactType.CONTACT_NAME_ICQ)) {
                    // nothing to do
                } else if (c.getName().equals(ContactType.CONTACT_NAME_SKYPE)) {
                    // nothing to do
                }
            }
            // birthday
            //if (contacts.isSupportedField(Contact.BIRTHDAY))
                //contact.addDate(Contact.BIRTHDAY, PIMItem.ATTR_NONE, new Date().getTime());


            contact.commit();
            contacts.close();
            MyRangeMIDlet.showInfoAlert("\"" + contactName + "\"" + StringConsts.S_ADD_TO_PHONEBOOK_SUCCESS);
        } catch (PIMException pe) {
            // такого списка не существует
            System.out.println("[PIM] Error: " + pe.toString());
            MyRangeMIDlet.showInfoAlert(StringConsts.S_ADD_TO_PHONEBOOK_PIM_EXCEPTION);
        } catch (SecurityException se) {
            // MIDlet не может получить доступ к требуемому списку
            System.out.println("[PIM] Error: " + se.toString());
            MyRangeMIDlet.showInfoAlert(StringConsts.S_ADD_TO_PHONEBOOK_SECURITY_EXCEPTION);
        } catch (Exception e) {
            // Other exception
            System.out.println("[PIM] Error: " + e.toString());
            MyRangeMIDlet.showFailAlert("Exception", e.toString());
        }
    }


}
