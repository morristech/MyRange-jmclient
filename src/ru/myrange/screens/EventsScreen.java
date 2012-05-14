/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */
package ru.myrange.screens;

import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.Command;
import java.util.Calendar;
import java.util.Date;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.soap.BusinessMeetInfoType;
import ru.myrange.soap.EventInfoType;
import ru.myrange.soap.EventInterface;
import ru.myrange.stuff.BusinessMeets;
import ru.myrange.stuff.Events;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.xlwuit.BusinessMeetButton;
import ru.myrange.stuff.xlwuit.EventButton;
import ru.myrange.stuff.xlwuit.InfiniteProgressIndicator;
import ru.myrange.stuff.xlwuit.DateLabel;
import ru.myrange.stuff.xlwuit.MenuButton;
import ru.myrange.stuff.xlwuit.Spacer;

/**
 * List of events
 *
 * @author Nickolay Yegorov
 */
public class EventsScreen extends Screen {

    private static boolean onlyMeets = false;

    public String getName() {
        String name = MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.EVENTS_SCREEN];
        return name;
    }

    protected void execute(final Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        (new Thread() {

            public void run() {
                Container pleaseWaitCnt = new Container();
                InfiniteProgressIndicator pleaseWait = new InfiniteProgressIndicator(MyRangeMIDlet.waitCircle);
                pleaseWaitCnt.addComponent(pleaseWait);
                f.addComponent(pleaseWaitCnt);
                f.revalidate();

                Container topButtonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
                MenuButton bnAll = new MenuButton(new Command(StringConsts.S_SHOW_ALL) {

                                public void actionPerformed(ActionEvent ae) {
                                    onlyMeets = false;
                                    MyRangeMIDlet.SCREENS[MyRangeMIDlet.EVENTS_SCREEN].run(getBackCommand());
                                }
                            });

                if(!onlyMeets) {
                    bnAll.resetUIID("SelectedButton");
                     f.revalidate();
                }

                MenuButton bnMeets = new MenuButton(new Command(StringConsts.S_SHOW_MEETS) {

                                public void actionPerformed(ActionEvent ae) {
                                    onlyMeets = true;
                                    MyRangeMIDlet.SCREENS[MyRangeMIDlet.EVENTS_SCREEN].run(getBackCommand());
                                }
                            });

                  if(onlyMeets) {
                      bnMeets.resetUIID("SelectedButton");
                       f.revalidate();
                  }

//                MenuButton bnWhatSelected = new MenuButton(new Command(" ") {
//
//                                public void actionPerformed(ActionEvent ae) {
//                                    //TODO:
//                                }
//                            });

                topButtonContainer.addComponent(bnAll);

                topButtonContainer.addComponent(bnMeets);

                //topButtonContainer.addComponent(BorderLayout.CENTER, bnWhatSelected);

                f.addComponent(topButtonContainer);
                //bnAll.setWidth(120);
                //bnMeets.setWidth(120);
                f.revalidate();

                Object[] events00 = Events.myEvents();
                Object[] meetings00 = BusinessMeets.myBusinessMeets();
                Object[] events0;

                if(!onlyMeets){
                    events0 = new Object[events00.length + meetings00.length];
                    for (int i = 0; i < events00.length; i++) {
                        events0[i] = events00[i];
                    }
                    for (int i = 0; i < meetings00.length; i++) {
                        events0[events00.length + i] = meetings00[i];
                    }
                } else{
                    events0 = meetings00;
                }

                Object[] events = new Object[events0.length];
                boolean[] printed = new boolean[events0.length];

                for (int i = 0; i < events0.length; i++) {
                    printed[i] = false;
                }
                

                /*
                 *  sorting events. very simple algorithm)
                 */
                long min;
                int minIndex = 0;

                for (int i = 0; i < events0.length; i++) {

                    min = Long.MAX_VALUE;

                    for (int j = 0; j < events0.length; j++) {
                        if (!printed[j]) {
                            if (events0.length <= i || events0[i] == null || !(events0[i] instanceof EventInterface)) {
                                continue;
                            }
                            EventInterface evt = (EventInterface) events0[i];

                            if (evt.getStartTime().longValue() < min) {
                                min = evt.getStartTime().longValue();
                                minIndex = j;
                            }
                        }
                    }

                    events[i] = events0[minIndex];

                    printed[minIndex] = true;

                }

                events0 = null;//deleting useless array
                /*
                 * end of sort
                 */

                if (events.length > 0) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date(((EventInterface) events[0]).getStartTime().longValue()));
                    int currentDay = cal.get(Calendar.DAY_OF_MONTH);
                    int newDay = currentDay;
                    f.addComponent(new DateLabel(cal));

                    for (int i = 0; i < events.length; i++) {
                        if (events.length <= i || events[i] == null || !(events[i] instanceof EventInterface)) {
                            continue;
                        }
                        final EventInterface evt = (EventInterface) events[i];

                        cal.setTime(new Date(evt.getStartTime().longValue()));
                        newDay = cal.get(Calendar.DAY_OF_MONTH);
                        if (newDay != currentDay) {
                            currentDay = newDay;
                            f.addComponent(new DateLabel(cal));
                        }

                        if (evt instanceof EventInfoType) {
                            final EventInfoType ev = (EventInfoType) evt;

                            f.addComponent(new EventButton(ev, new Command(StringConsts.S_EVENT_CLICK) {

                                public void actionPerformed(ActionEvent ae) {
                                    EventInfoScreen eis =
                                            (EventInfoScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.EVENT_INFO_SCREEN];
                                    eis.run(backToThisCommand, ev);
                                }
                            }));
                        }

                        if (evt instanceof BusinessMeetInfoType) {
                            final BusinessMeetInfoType bm = (BusinessMeetInfoType) evt;
                            f.addComponent(new BusinessMeetButton(bm, new Command(StringConsts.S_EVENT_CLICK) {

                                public void actionPerformed(ActionEvent ae) {
                                    BusinessMeetScreen bs =
                                            (BusinessMeetScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.BUSINESS_MEET_VIEW_SCREEN];
                                    bs.run(backToThisCommand, bm);
                                }
                            }));
                        }

                        f.revalidate();
                    }
                }

                f.addComponent(new Spacer(Spacer.BOTTOM));

                pleaseWaitCnt.removeComponent(pleaseWait);
                f.revalidate();
            }
        }).start();
    }
}
