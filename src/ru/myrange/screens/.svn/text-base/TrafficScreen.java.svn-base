/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BoxLayout;
import java.util.Calendar;
import java.util.Date;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.Records;
import ru.myrange.soap.Scrobbler;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.xlwuit.TextComponent;

/**
 * Show information about traffic
 * @author Oleg Ponfilenok
 */
public class TrafficScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.TRAFFIC_SCREEN];
    }

    protected void execute(final Form f) {

        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        // Dump traffic to RMS
        try {
            Scrobbler.dumpTrafficToRMS();

            int todayTraf = Records.recSettings.readIntData(Records.REC_TRAFFIC_TODAY);
            int totalTraf = Records.recSettings.readIntData(Records.REC_TRAFFIC_TOTAL);
            long days = (System.currentTimeMillis() - Records.recSettings.readLongData(Records.REC_TRAFFIC_TOTAL_SINCE))/86400000 + 1;

            f.addComponent(new Label(StringConsts.S_TRAFFIC_LABEL));
            // Traffic of today
            f.addComponent(new Label(StringConsts.S_TRAFFIC_TODAY));
            f.addComponent(new TextComponent(Integer.toString(todayTraf) + StringConsts.S_TRAFFIC_UNIT));
            // Average traffic
            f.addComponent(new Label(StringConsts.S_TRAFFIC_AVERAGE));
            TextComponent t = new TextComponent(Long.toString(totalTraf/days) + StringConsts.S_TRAFFIC_UNIT_AVERAGE);
            t.setNextFocusUp(t);
            f.addComponent(t);
            // Total traffic
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(Records.recSettings.readLongData(Records.REC_TRAFFIC_TOTAL_SINCE)));
            f.addComponent(new Label(StringConsts.S_TRAFFIC_TOTAL +
                    (c.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + c.get(Calendar.DAY_OF_MONTH) : Integer.toString(c.get(Calendar.DAY_OF_MONTH))) + "." +
                    (c.get(Calendar.MONTH)+1 < 10 ? "0" + (c.get(Calendar.MONTH)+1) : Integer.toString(c.get(Calendar.DAY_OF_MONTH)+1)) + "." +
                    c.get(Calendar.YEAR) + ":"));
            f.addComponent(new TextComponent(Integer.toString(totalTraf) + StringConsts.S_TRAFFIC_UNIT));
        } catch (Exception e) {
            MyRangeMIDlet.showFailAlert("Exception", e.toString());
        }

    }

}
