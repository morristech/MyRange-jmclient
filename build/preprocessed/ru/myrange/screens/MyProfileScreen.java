package ru.myrange.screens;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import javax.microedition.io.Connector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.Records;
import ru.myrange.soap.ContactType;
import ru.myrange.soap.UserInfo;
import ru.myrange.stuff.Base64Coder;
import ru.myrange.stuff.MyRoutines;
import ru.myrange.stuff.PNG;
import ru.myrange.stuff.StaticActions;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.Users;
import ru.myrange.stuff.file.FileTreeModel;
import ru.myrange.stuff.file.TreeComponent;
import ru.myrange.stuff.xlwuit.HeaderText;
import ru.myrange.stuff.xlwuit.ImageButton;
import ru.myrange.stuff.xlwuit.MinorLabel;
import ru.myrange.stuff.xlwuit.MinorText;
import ru.myrange.stuff.xlwuit.ProfileLabel;
import ru.myrange.stuff.xlwuit.Spacer;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.util.EmailAddress;
import ru.myrange.util.PhoneNumber;
import ru.myrange.util.Pincode;

/**
 * My profile show screen class
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class MyProfileScreen extends Screen
{
    /** Best size of an userpic */
    public static int bestUserpicSize = 144;

    /** Best size of an image on the button */
    public static int bestImageButtonSize = 60;

    private Image newPic = null;        // New pic to change my avatar
    private String newPicBase64 = "";   // New pic in Base64 for save in RMS

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.MY_PROFILE_SCREEN];
    }

    /**
     * Overload run method
     *
     * @param inputPic new pic
     */
    public final void runWithNewPic(final Image inputPic) {
        if (inputPic != null) {
            newPic = MyRoutines.imageToSquare(inputPic).scaled(bestImageButtonSize, bestImageButtonSize);
            byte[] byteArray = PNG.imageToPNG(newPic);
            newPicBase64 = new String(Base64Coder.encode(byteArray));
        }
        createAll(this.backCommand);
        show();
    }

    protected void execute(final Form f) {
        // Get my profile
        if (Users.getMyProfile() == null) {
            MyRangeMIDlet.out.println("[Profile] Error: Cannot get my profile.");
            MyRangeMIDlet.showFailAlert("Exception", "Cannot get my profile");
            return;
        }
        final UserInfo profile = Users.getMyProfile();

        // ONLY FOR PERSONAL USERS
        if (!profile.getAccountType().equals(UserInfo.ACCOUNT_TYPE_PERSONAL)) {
            MyRangeMIDlet.showInfoAlert(StringConsts.S_ONLY_FOR_PERSONAL);
            return;
        }
        
        // Set best image sizes
        bestUserpicSize = ( (Display.getInstance().getDisplayWidth() <
            Display.getInstance().getDisplayHeight()) ? Display.getInstance().getDisplayWidth() :
                Display.getInstance().getDisplayHeight() )*3/5;
        bestImageButtonSize = (Display.getInstance().getDisplayWidth() - StringConsts.S_PIC.length()*StringConsts.CH_SMALL_MAW_WIDTH)/4 - 2;

        // Show my profile
        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        //add username
        f.addComponent(new HeaderText(profile.getFirstName() + " " + profile.getLastName()));

        // Add pincode
        if (Pincode.isPincode(MyRangeMIDlet.login)) {
            Label pncl = new Label(StringConsts.S_SHOW_PINCODE + MyRangeMIDlet.login);
            pncl.setNextFocusUp(pncl);
            f.addComponent(pncl);
        }
        
        // Add picture        
        Label avatar = new Label();
        if (this.newPic != null) avatar.setIcon(this.newPic);
        else if (profile.getPic() != null) avatar.setIcon((profile.getPic(bestImageButtonSize, bestImageButtonSize)));
        else avatar.setIcon(Users.getNullUserpic(bestImageButtonSize, bestImageButtonSize));

        // Add "take a picture" and "set avatar from file" button
        final Image photoImage = MyRangeMIDlet.res.getImage("photo-l").scaled(bestImageButtonSize, bestImageButtonSize);
        final Image folderImage = MyRangeMIDlet.res.getImage("folder-l").scaled(bestImageButtonSize, bestImageButtonSize);
        
        ImageButton photoButton = new ImageButton("", photoImage);
        photoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                //MyRangeMIDlet.SCREENS[MyRangeMIDlet.CAPTURE_SCREEN].run(backToThisCommand);
                new OldCaptureForm();
            }
        });
        ImageButton folderButton = new ImageButton("", folderImage);
        folderButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                FileTreeModel m = new FileTreeModel();
                final TreeComponent t = new TreeComponent(m);
                t.addLeafListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (Display.getInstance().getCurrent() instanceof Dialog)
                            ((Dialog)Display.getInstance().getCurrent()).dispose();
                        pickImage(t);
                    }
                });
                Command ok = new Command("OK");
                Command cancel = new Command("Cancel");
                if(ok == Dialog.show(StringConsts.S_PIC_PICTURE, t, new Command[] {ok, cancel})) {
                    pickImage(t);
                }
            }
        });

        Container c1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        c1.addComponent(new ProfileLabel(StringConsts.S_PIC));
        c1.addComponent(avatar);
        folderButton.setNextFocusUp(folderButton);
        c1.addComponent(folderButton);
        c1.addComponent(photoButton);
        f.addComponent(c1);

        Button b = new Button(new Command(profile.getWorkPlace()) {

                    public void actionPerformed(ActionEvent ae) {
                        StaticActions.showWaitDialog();
                        CompanyProfileScreen cps =
                                        (CompanyProfileScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.COMPANY_PROFILE_SCREEN];
                                cps.run(backToThisCommand, profile.getCompId());
                    }
                });
          b.setUIID("Hyperlink");
        final TextField workPost = new TextField(profile.getWorkPost());

        Container largest = createPair(StringConsts.S_WORK_PLACE, b, 30);
        int largestW = largest.getComponentAt(0).getPreferredW();
//#if FP2009 == "true"
//#         // Add region and fp2009-nomination
//#         final TextField region = new TextField("");
//#         final TextField fp2009Nomination = new TextField("");
//#         for (int i=0; i<profile.getCustomParams().size(); i++) {
//#            final CustomParam p = (CustomParam) profile.getCustomParams().elementAt(i);
//#            if (p.getAttribute().equals(CustomParam.ATTRIBUTE_REGION)) {
//#                region.setText(p.getValue());
//#            } else if (p.getAttribute().equals(CustomParam.ATTRIBUTE_FP2009_NOMINATION)) {
//#                fp2009Nomination.setText(p.getValue());
//#            }
//#         }
//#         f.addComponent(createPair(StringConsts.S_REGION, region, largestW));
//#         f.addComponent(createPair(StringConsts.S_FP2009_NOMINATION, fp2009Nomination, largestW));
//#endif
        //f.addComponent(createPair(StringConsts.S_EDUCATION, eduPlace, largestW));
        f.addComponent(largest);
        f.addComponent(createPair(StringConsts.S_WORK_POST, workPost, "ProfileLabel", largestW));

        // Gender. disabled
//        f.addComponent(new Label(StringConsts.S_GENDER));
//        RadioButton rb;
//        final ButtonGroup genderGroup = new ButtonGroup();
//        rb = new RadioButton(StringConsts.S_SET_GENDER[0]);
//        genderGroup.add(rb);
//        f.addComponent(rb);
//        rb = new RadioButton(StringConsts.S_SET_GENDER[1]);
//        genderGroup.add(rb);
//        f.addComponent(rb);
//        if (profile.getGender().equals(UserInfo.GENDER_MALE)) genderGroup.setSelected(0);
//        else if (profile.getGender().equals(UserInfo.GENDER_FEMALE)) genderGroup.setSelected(1);

        // Birthday choice. disabled
//        final List birthdayDayList = new List(StringConsts.S_BIRTHDAY_DAYS);
//        final List birthdayMonthList = new List(StringConsts.S_BIRTHDAY_MONTHS);
//        final List birthdayYearList = new List(StringConsts.S_BIRTHDAY_YEARS);
//        if (profile.getBirthday().longValue() > 0) {    // If user has set birthday
//            Calendar c = Calendar.getInstance();
//            c.setTime(new Date(profile.getBirthday().longValue()));
//            birthdayDayList.setSelectedIndex(c.get(Calendar.DAY_OF_MONTH));
//            birthdayMonthList.setSelectedIndex(c.get(Calendar.MONTH)+1);
//            for (int i=0; i<StringConsts.S_BIRTHDAY_YEARS.length; i++) {
//                if (StringConsts.S_BIRTHDAY_YEARS[i].equals(Integer.toString(c.get(Calendar.YEAR)))) {
//                    birthdayYearList.setSelectedIndex(i);
//                    break;
//                }
//            }
//        }
//        f.addComponent(new Label(StringConsts.S_BIRTHDAY));
//        ComboBox birthdayDayBox = new ComboBox(birthdayDayList.getModel());
//        f.addComponent(birthdayDayBox);
//        ComboBox birthdayMonthBox = new ComboBox(birthdayMonthList.getModel());
//        f.addComponent(birthdayMonthBox);
//        ComboBox birthdayYearBox = new ComboBox(birthdayYearList.getModel());
//        f.addComponent(birthdayYearBox);

        // Contacts. Editable.
        f.addComponent(new TextComponent(StringConsts.S_CONTACTS));
        final TextField contactPhone = new TextField();
        final TextField contactEmail = new TextField();
        //final TextField contactIcq = new TextField();
        //final TextField contactSkype = new TextField();
        for (int i=0; i<profile.getContact().size(); i++) {
            ContactType c = (ContactType) profile.getContact().elementAt(i);
            if (c.getName().equals(ContactType.CONTACT_NAME_PHONE)) {
                contactPhone.setText(c.getUid());
            } else if (c.getName().equals(ContactType.CONTACT_NAME_EMAIL)) {
                contactEmail.setText(c.getUid());
            }
//                else if (c.getName().equals(ContactType.CONTACT_NAME_ICQ)) {
//                contactIcq.setText(c.getUid());
//            } else if (c.getName().equals(ContactType.CONTACT_NAME_SKYPE)) {
//                contactSkype.setText(c.getUid());
//            }
        }
        f.addComponent(createPair(ContactType.CONTACT_NAME_PHONE + ":", contactPhone, "ProfileLabel", largestW));
        MinorText sample1 = new MinorText("напр. 79031234567"); sample1.setFocusable(false);
        f.addComponent(sample1);

        f.addComponent(createPair(ContactType.CONTACT_NAME_EMAIL + ":", contactEmail, "ProfileLabel", largestW));
       
        //f.addComponent(createPair(ContactType.CONTACT_NAME_ICQ + ":", contactIcq, largestW));
//        f.addComponent(createPair(ContactType.CONTACT_NAME_SKYPE + ":", contactSkype, largestW));

        // Links. Uneditable. TODO:
        /*
        f.addComponent(new Label(StringConsts.S_LINKS));
        for (int i=0; i<profile.getLink().size(); i++) {
            LinkType l = (LinkType) profile.getLink().elementAt(i);
            f.addComponent(createPair(l.getSite(), new Hyperlink(l.getUrl(), l.getUrl()), largestW));
        }
        */

        // About
        Label goals = new MinorLabel(StringConsts.S_PERSONAL_GOALS);
        goals.setUIID("ProfileLabel");
        f.addComponent(goals);
        final TextArea about = new TextArea(profile.getAbout());
        //about.setEditable(true);
        f.addComponent(about);

        about.setNextFocusDown(about);


        f.addCommand( new Command(StringConsts.S_SAVE) {
            public void actionPerformed(ActionEvent evt) {
                final Dialog pleaseWait = MyRangeMIDlet.showWaitingAlert(StringConsts.S_SENDING);
                (new Thread() {
                    public void run(){
                        // Change the profile
                        try {
                            //profile.setTextStatus(textStatus.getText().trim());
                            //profile.setResidentCity(residentCity.getText().trim());

//#if FP2009 == "true"
//#                             // Save region and fp2009-nomination
//#                             Vector customParams = new Vector();
//#                             if (region.getText().trim().length()>0)
//#                                 customParams.addElement(new CustomParam(CustomParam.ATTRIBUTE_REGION, fp2009Nomination.getText().trim()));
//#                             if (fp2009Nomination.getText().trim().length()>0)
//#                                 customParams.addElement(new CustomParam(CustomParam.ATTRIBUTE_FP2009_NOMINATION, fp2009Nomination.getText().trim()));
//#                             profile.setCustomParams(customParams);
//#endif
                            //profile.setEduPlace(eduPlace.getText().trim());
                            //profile.setWorkPlace(workPlace.getText().trim());
                            profile.setWorkPost(workPost.getText().trim());
                            profile.setAbout(about.getText().trim());

                            // Set gender. disabled =)
//                            switch (genderGroup.getSelectedIndex()) {
//                                case 0 :
//                                    profile.setGender(UserInfo.GENDER_MALE);
//                                    break;
//                                case 1 :
//                                    profile.setGender(UserInfo.GENDER_FEMALE);
//                                    break;
//                                default :
//                                    break;
//                            }

                            // Set birthday. disabled
//                            int birthdayDayIndex = birthdayDayList.getSelectedIndex();
//                            int birthdayMonthIndex = birthdayMonthList.getSelectedIndex();
//                            int birthdayYearIndex = birthdayYearList.getSelectedIndex();
//                            if (birthdayDayIndex>0 && birthdayMonthIndex>0 && birthdayYearIndex>0) {
//                                Calendar birthday = Calendar.getInstance();
//                                birthday.set(Calendar.YEAR, Integer.parseInt(StringConsts.S_BIRTHDAY_YEARS[birthdayYearIndex]));
//                                birthday.set(Calendar.MONTH, birthdayMonthIndex-1);
//                                birthday.set(Calendar.DAY_OF_MONTH, Integer.parseInt(StringConsts.S_BIRTHDAY_DAYS[birthdayDayIndex]));
//                                profile.setBirthday(new Long(birthday.getTime().getTime()));
//                            }

                            // Set pic
                            if (newPic!=null) profile.setPic(newPicBase64);

                            // Set contacts
                            //Vector contacts = new Vector();
                            if (contactPhone.getText().trim().length()>0 && PhoneNumber.isPhoneNumber(contactPhone.getText()))
                                profile.setPhoneNumber(contactPhone.getText().trim());
                            else throw new Exception(StringConsts.S_WRONG_PHONE);
                                //contacts.addElement(new ContactType(ContactType.CONTACT_NAME_PHONE, contactPhone.getText().trim()));
                            if (contactEmail.getText().trim().length()>0 && EmailAddress.isEmailAddress(contactEmail.getText()))
                                profile.setEmail(contactEmail.getText().trim());
                            else throw new Exception(StringConsts.S_WRONG_EMAIL);
                                //contacts.addElement(new ContactType(ContactType.CONTACT_NAME_EMAIL, contactEmail.getText().trim()));
//                            if (contactIcq.getText().trim().length()>0)
//                                contacts.addElement(new ContactType(ContactType.CONTACT_NAME_ICQ, contactIcq.getText().trim()));
//                            if (contactSkype.getText().trim().length()>0)
//                                contacts.addElement(new ContactType(ContactType.CONTACT_NAME_SKYPE, contactSkype.getText().trim()));
                            //profile.setContact(contacts);

                            // Save the profile
                            //Records.recSettings.addByteData(profile.toRecord(), Records.REC_PROFILE);
                        } catch (Exception e){
                            MyRangeMIDlet.out.println("[Profile] Error: " + e.toString());
                            MyRangeMIDlet.showFailAlert("Exception", e.toString());
                            return;
                        }

                        // Try to send new profile to the server
                        try {
                            // SOAP setUserInfo
                            Integer setUserInfoResponse = StaticActions.setMyUserInfo();
                            MyRangeMIDlet.out.println("[Profile] " + setUserInfoResponse.toString() + " set user info ...OK");
                            pleaseWait.dispose();
                            MyRangeMIDlet.showInfoAlert(StringConsts.S_PROFILE_CHANGED_OK);   
                        } catch (Exception e){
                            try {
                                // Need to send new profile to the server
                                Records.recSettings.addIntData(Records.I_CHANGED_YES, Records.REC_PROFILE_CHANGED);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            pleaseWait.dispose();
                            MyRangeMIDlet.out.println("[Profile] Error: " + e.getMessage());
                            MyRangeMIDlet.showFailAlert(StringConsts.S_PROFILE_CHANGED_FAIL, "Error: " + e.getMessage());
                        }
                    }
                }).start();
            }
        });

        f.addComponent(new Spacer(Spacer.BOTTOM));
        
        f.revalidate();
    }

    private void pickImage(TreeComponent t) {
        try {
            String f = (String) t.getSelectedItem();
            if (f != null) {
                Image img = Image.createImage(Connector.openInputStream(f));

                // Back to the profile form
                ((MyProfileScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.MY_PROFILE_SCREEN]).runWithNewPic(img);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            MyRangeMIDlet.showFailAlert("Error loading image", ex.toString());
        }
    }

    /**
     * Erase new pic before back
     */
    protected void cleanup() {
        newPic = null;
        newPicBase64 = "";
    }

}
