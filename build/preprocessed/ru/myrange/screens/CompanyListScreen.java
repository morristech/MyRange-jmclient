/*
 * myRange J2ME client application
 * Copyright © 2010 MyRange Group
 */
package ru.myrange.screens;

import ru.myrange.stuff.xlwuit.CompanyCellRenderer;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import ru.myrange.MyRangeMIDlet;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionListener;
import java.util.Vector;
import ru.myrange.soap.CompInfoType;
import ru.myrange.stuff.Companies;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.xlwuit.InfiniteProgressIndicator;
import ru.myrange.stuff.xlwuit.XList;

/**
 * List of companies
 *
 * @author Yegorov Nickolay
 */
public class CompanyListScreen extends Screen {

    public String getName() {
        String name = MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.COMPANY_LIST_SCREEN];
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

                //f.addComponent(new Spacer(Spacer.TOP));

                while(!Companies.isReady()){
                    f.revalidate();
                }

                Vector companies = Companies.getMyCompanies();


                final XList companyList = new XList(companies);
                companyList.setListCellRenderer(new CompanyCellRenderer());

                final ActionListener defaultActionListener = new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        // Show selected compamie's profile
                        CompInfoType selection = (CompInfoType) companyList.getSelectedItem();
                        final Integer compId = selection == null ? null : selection.getCompId();
                        if (compId != null){
                            MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);
                            CompanyProfileScreen cps =
                                            (CompanyProfileScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.COMPANY_PROFILE_SCREEN];
                                    cps.run(backToThisCommand, selection);
                        }
                            
                    }
                };

                companyList.addActionListener(defaultActionListener);
                f.addComponent(companyList);

                pleaseWaitCnt.removeComponent(pleaseWait);
//                if (users.size() <= 0) {   // No messages
//                    f.addComponent(new TextComponent(StringConsts.S_PRIVATE_NO_MESSAGES));
//                }
           

                f.revalidate();

            }
        }).start();
    }

    private void run(Command backToThisCommand, CompInfoType c) {
    }
}
