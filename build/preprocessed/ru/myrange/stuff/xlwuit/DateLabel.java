/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */
package ru.myrange.stuff.xlwuit;

import com.sun.lwuit.Label;
import java.util.Calendar;
import ru.myrange.stuff.StringConsts;

/**
 * Component for set some empty space
 *
 * @author Yegorov Nickoaly
 */
public class DateLabel extends Label {

    public DateLabel(Calendar cal) {

        super(cal.get(Calendar.DAY_OF_MONTH) + " "
                + StringConsts.S_MONTHS[cal.get(Calendar.MONTH) - 1] + " "
                + cal.get(Calendar.YEAR) + " ("
                + StringConsts.S_DAY_OF_WEEKS[cal.get(Calendar.DAY_OF_WEEK) - 1] + ")");
        this.setUIID("DateLabel");

    }
}
