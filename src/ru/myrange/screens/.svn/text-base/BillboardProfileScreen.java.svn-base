/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BoxLayout;
import ru.myrange.MyRangeMIDlet;
import java.util.Vector;
import ru.myrange.soap.ContactType;
import ru.myrange.soap.LinkType;
import ru.myrange.stuff.StringConsts;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.MyRoutines;
import ru.myrange.stuff.xlwuit.Hyperlink;
import ru.myrange.stuff.xlwuit.TextComponent;

/**
 * Billboard profile screen
 *
 * @author Oleg Ponfilenok
 */
public class BillboardProfileScreen extends Screen {

    /**
     * Best size of an userpic image
     */
    public static int bestUserpicSize = 120;

    private UserInfo profile;


    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.BILLBOARD_PROFILE_SCREEN];
    }

    /**
     * Overload run method
     *
     * @param backCommand - back to previous screen command
     * @param profile - billboard profile to show
     */
    public final void run(final Command backCommand, final UserInfo profile) {
        this.profile = profile;
        createAll(backCommand);
        show();
    }

    protected void execute(final Form f) {
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        if (profile.getPic() != null) {
			f.addComponent(new Label(profile.getPic()));
		}

        final TextComponent name = new TextComponent(profile.getFirstName());
        final TextComponent textStatus = new TextComponent(profile.getTextStatus());

        Container largest = createPair(StringConsts.S_ENTERPRISE_NAME, name, 30);
        int largestW = largest.getComponentAt(0).getPreferredW();
        f.addComponent(createPair(StringConsts.S_ENTERPRISE_NAME, name, largestW));
        f.addComponent(createPair(StringConsts.S_ENTERPRISE_STATUS, textStatus, largestW));

        // About
        f.addComponent(new Label(StringConsts.S_ENTERPRISE_ABOUT_MSG));
        Vector aboutComponents = MyRoutines.parseAbout(profile.getAbout());
        for (int i=0; i<aboutComponents.size(); i++) {
            f.addComponent((Component) aboutComponents.elementAt(i));
        }

        // Contacts
        f.addComponent(new Label(StringConsts.S_CONTACTS));
        for (int i=0; i<profile.getContact().size(); i++) {
            ContactType c = (ContactType) profile.getContact().elementAt(i);
            f.addComponent(createPair(c.getName() + ":", new TextComponent(c.getUid()), largestW));
        }

        // Links
        f.addComponent(new Label(StringConsts.S_LINKS));
        for (int i=0; i<profile.getLink().size(); i++) {
            LinkType l = (LinkType) profile.getLink().elementAt(i);
            f.addComponent(createPair(l.getSite(), new Hyperlink(l.getUrl(), l.getUrl()), largestW));
        }

    }

}
