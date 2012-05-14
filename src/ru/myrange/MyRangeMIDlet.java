/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */

package ru.myrange;

import javax.microedition.io.ConnectionNotFoundException;
import ru.myrange.screens.MainMenuScreen;
import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Displayable;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.ToneControl;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import ru.myrange.bluetooth.Bluetooth;
import ru.myrange.datastore.Records;
import ru.myrange.screens.AboutScreen;
import ru.myrange.screens.BtMsgScreen;
import ru.myrange.screens.BusinessMeetScreen;
import ru.myrange.screens.CaptureScreen;
import ru.myrange.screens.CompanyListScreen;
import ru.myrange.screens.CompanyProfileScreen;
import ru.myrange.screens.PasswordValidationScreen;
import ru.myrange.screens.DevicesScreen;
import ru.myrange.screens.EventInfoScreen;
import ru.myrange.screens.EventsScreen;
import ru.myrange.screens.FirstScreen;
import ru.myrange.stuff.xlwuit.InfiniteProgressIndicator;
import ru.myrange.screens.LoginScreen;
import ru.myrange.screens.MeetUsersScreen;
import ru.myrange.screens.MyProfileScreen;
import ru.myrange.screens.MessagesScreen;
import ru.myrange.screens.NewSearchUsersRequestScreen;
import ru.myrange.screens.PincodeScreen;
import ru.myrange.screens.UserMsgScreen;
import ru.myrange.screens.Screen;
import ru.myrange.screens.SearchUsersScreen;
import ru.myrange.screens.SettingsScreen;
import ru.myrange.screens.SmsMsgScreen;
import ru.myrange.screens.StartScreen;
import ru.myrange.screens.TrafficScreen;
import ru.myrange.screens.UserProfileScreen;
import ru.myrange.soap.GetLatestClientVersionResponseElement;
import ru.myrange.soap.Scrobbler;
import ru.myrange.soap.SmsServiceInfo;
import ru.myrange.stuff.Companies;
import ru.myrange.stuff.Messages;
import ru.myrange.stuff.MyConferences;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.MyRoutines;
import ru.myrange.stuff.Out;
import ru.myrange.stuff.SearchUsers;
import ru.myrange.stuff.Signals;
import ru.myrange.stuff.StaticActions;
import ru.myrange.stuff.Users;
import ru.myrange.stuff.xlwuit.TextComponent;
import ru.myrange.util.Password;

/**
 * The myRange MIDlet
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class MyRangeMIDlet extends MIDlet {   
	/**
     * The user login in myRange.
     * It's e-mail from RMS.
     */
    public static String login = null;

    /**
     * The user password in myRange.
     * It's password md5 hash from RMS
     */
    public static String pass = "";

    /**
     * The user password in myRange.
     * It's password md5 hash from RMS
     */
    public static Integer myUserId = null;

    /**
     * Time interval to merge two bluetooth logs.
     * It can change depends on user settings.
     */
    public static long mergeIntervalVar = 360000;

    public static MyRangeMIDlet staticMidlet;

    public static Resources res;
    public static Image[] icons = new Image[StringConsts.I_ICON_NUM];
    public static Image logo = null;
    public static Image waitCircle = null;
    public static Out out;
    //public static Console console;
    public static Bluetooth bluetooth;

    /** True if it's allow to application to connect to the remote server */
    //public static boolean allowConnection = true;
    
    /** Logout indicate */
    private static boolean logout = false;

    /** Only for canvases! */
    private static javax.microedition.lcdui.Display disp;

    /** Screens */
//#if CONF == "true"
//#     public static final int START_SCREEN = 25;  // Start with Pincode screen
//#else
    public static final int START_SCREEN = 0;
//#endif
    public static final int LOGIN_SCREEN = 1;
    public static final int REGISTER_SCREEN = 2;
    public static final int PASSWORD_RECOVERY_SCREEN = 3;
    public static final int PASSWORD_VALIDATION_SCREEN = 4;
    public static final int MAINMENU_SCREEN = 5;
    public static final int DEVICES_SCREEN = 6;
    public static final int MEET_USERS_SCREEN = 7;
    public static final int FAVOURITE_USERS_SCREEN = 8;
    public static final int USER_PROFILE_SCREEN = 9;
    public static final int BILLBOARD_PROFILE_SCREEN = 10;
    public static final int MY_PROFILE_SCREEN = 11;
    public static final int CAPTURE_SCREEN = 12;
    public static final int BT_MSG_SCREEN = 13;
    
    public static final int SMS_MSG_SCREEN = 15;
    public static final int TRAFFIC_SCREEN = 16;
    //public static final int MESSAGES_CHOICSE_SCREEN = 17;
    //public static final int INBOX_SCREEN = 18;
    public static final int MESSAGES_SCREEN = 19;
    public static final int NEW_SEARCH_USERS_REQUEST_SCREEN = 20;
    public static final int USER_MSG_SCREEN = 21;
    public static final int SETTINGS_SCREEN = 22;
    public static final int ABOUT_SCREEN = 23;
    public static final int FIRST_SCREEN = 24;
    public static final int PINCODE_SCREEN = 25;
    public static final int USERS_CHOICSE_SCREEN = 26;
    public static final int SEARCH_USERS_SCREEN = 27;
    public static final int EVENTS_SCREEN = 28;
    public static final int EVENT_INFO_SCREEN = 29;
    public static final int COMPANY_LIST_SCREEN = 30;
    public static final int COMPANY_PROFILE_SCREEN = 31;
    public static final int BUSINESS_MEET_SCREEN = 32;
    public static final int BUSINESS_MEET_VIEW_SCREEN = 33;
    public static final Screen[] SCREENS = new Screen[]{
        new StartScreen(), new LoginScreen(), null,
        null, new PasswordValidationScreen(), new MainMenuScreen(),
        new DevicesScreen(), new MeetUsersScreen(), null,
        new UserProfileScreen(), null, new MyProfileScreen(),
        new CaptureScreen(), new BtMsgScreen(), null,
        new SmsMsgScreen(), new TrafficScreen(), null,
        null, new MessagesScreen(), new NewSearchUsersRequestScreen(),
        new UserMsgScreen(), new SettingsScreen(), new AboutScreen(),
        new FirstScreen(), new PincodeScreen(), null,
        new SearchUsersScreen(), new EventsScreen(), new EventInfoScreen(),
        new CompanyListScreen(), new CompanyProfileScreen(), null,
        new BusinessMeetScreen()
    };
    public static final String[] SCREEN_NAMES = new String[]{
        "Добро пожаловать в myRange!", "myRange", "Регистрация",
        "Восстановление пароля", "Проверка номера", "Главное меню",
        "Устройства рядом", "История встреч", "Избранные",
        "Профиль пользователя", "Профиль компании", "Мой профиль",
        "Камера", "Сообщение по Bluetooth", "",
        "СМС", "Трафик", "",
        "", "Сообщения", "Люди",
        "Cообщения", "Настройки", "О программе",
        "Гид активации", "Вход по коду участника", "Участники",
        "Люди", "Расписание", "Мероприятие","Компании", "Профиль компании", "Встречи",
        "Встреча"
    };

    public static MyRangeMIDlet midlet = null;

    public static void showFailAlert(String S_WRONG_PASSWORD) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /** Commads */
    public final Command askExitCommand = new Command(StringConsts.S_EXIT) {
        public void actionPerformed(ActionEvent evt) {
            exitAppAlert();
        }
    };
    public final Command exitCommand = new Command(StringConsts.S_EXIT) {
        public void actionPerformed(ActionEvent evt) {
            exitApp();
        }
    };
    public final Command hideCommand = new Command(StringConsts.S_HIDE) {
        public void actionPerformed(ActionEvent evt) {
            hide();
        }
    };

    /**
     * Update the information on the screen
     */
	public void updateShow() {
        // repaint main menu screen but don't show it
        SCREENS[MAINMENU_SCREEN].createAll(SCREENS[MAINMENU_SCREEN].getBackCommand());

        if (getDisplayable().isShown()) {
            for (int i=0; i<SCREENS.length; i++) {
                if (SCREENS[i] == null) continue;
                if (Display.getInstance().getCurrent() == SCREENS[i].getForm()){
                    // repaint current screen
                    SCREENS[i].run(SCREENS[i].getBackCommand());
                }
                break;
            }
        }
    }


	protected void destroyApp( boolean unconditional ) throws MIDletStateChangeException{
		exitApp();
	}

	protected void pauseApp() {
	}
	
	protected void startApp() throws MIDletStateChangeException
	{
            midlet = this;
            disp = javax.microedition.lcdui.Display.getDisplay(this);

		Display.init(this);
        
        //although calling directly to initApp() will work on
        //most devices, a good coding practice will be to allow the midp
        //thread to return and to do all the UI on the EDT.
        /*Display.getInstance().callSerially(new Runnable() {
            public void run() {
                initApp();
            }
        });
        */
        initApp();

	}


	public static String platform;
	public static String locale;
	public static String encoding;
	public static String imei;
	
	private static void getSystemInfo()
	{
		try{
			platform = System.getProperty("microedition.platform");
			locale = System.getProperty("microedition.locale");
			encoding = System.getProperty("microedition.encoding");
			
			// Get IMEI on differen mobile divices
			if (imei == null) imei = System.getProperty("com.siemens.IMEI");
			if (imei == null) imei = System.getProperty("com.samsung.imei");
			if (imei == null) imei = System.getProperty("com.sonyericsson.imei");
			if (imei == null) imei = System.getProperty("IMEI");
			if (imei == null) imei = System.getProperty("com.motorola.IMEI");
			if (imei == null) imei = System.getProperty("phone.imei");
			if (imei == null) imei = System.getProperty("com.nokia.IMEI");
			if (imei == null) imei = System.getProperty("com.nokia.mid.imei");
			
			out.println("[System:] Platform: " + platform);
			out.println("[System:] Encoding: " + encoding);
			out.println("[System:] Locale: " + locale);
			out.println("[System:] IMEI: " + imei);
                        out.println("[System:] Camera support: " + System.getProperty("supports.video.capture"));
                        out.println("[System:] Camera encodings: " + System.getProperty("video.snapshot.encodings"));

            String pimVersion = System.getProperty( "microedition.pim.version" );
            if( pimVersion != null )
            {
                // PIM поддерживается
                MyRangeMIDlet.out.println("[PIM] PIM version=" + pimVersion);
            } else {
                // PIM не поддерживается
                MyRangeMIDlet.out.println("[PIM] PIM UNSUPPORTED!");
            }

            String FCAPIVersion = System.getProperty("microedition.io.file.FileConnection.version");
            if( FCAPIVersion != null )
            {
                // FileConnection API поддерживается
                MyRangeMIDlet.out.println("[FileConnection API] API version=" + FCAPIVersion);
            } else {
                // FileConnection API не поддерживается
                MyRangeMIDlet.out.println("[FileConnection API] API UNSUPPORTED!");
            }

		}
		catch( Exception e ){
			out.println("[System] Error: " + e.toString());
		}
	}


	private void initApp()
	{
        try {
            // Create some important objects
            staticMidlet = this;
            out = new Out();
            //console = new Console();

            // Load resources
            //set the theme
            try {
                res = Resources.open("/myRangeTheme.res");
            } catch (IOException e) {
                throw new IOException("Cannot open resources file!");
            }
            UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
            try{
                // Load the icons
                Image img = Image.createImage("/icons.png");
                // Cut the icons
                for (int i=0; i<StringConsts.I_ICON_NUM; i++) {
                    icons[i] = Image.createImage(StringConsts.I_ICON_SIZE, StringConsts.I_ICON_SIZE);
                    icons[i].getGraphics().drawImage(img, (-1)*StringConsts.I_ICON_SIZE*i, 0);
                }
                /*
                for (int i=0; i<MyConsts.I_ICON_NUM; i++) {
                    icons[i] = Image.createImage("/" + Integer.toString(i) + ".png");
                }
                */

                // Load logo image
//#if CONF == "true"
//# //                 logo = Image.createImage("/myRangeConf_logo.png");
//#                logo = Image.createImage("/myRange_logo.png");
//# 
//#else
                logo = Image.createImage("/myRange_logo.png");
//#endif

                // Load "washing machine" progress image
                waitCircle = Image.createImage("/wait-circle.png");
            }catch(java.io.IOException e) {
                out.println(StringConsts.S_ICONS_ERROR);
                out.println("[MIDlet] Error: " + e.toString());
            }

            // Open RMS
            Records.openAll();
            // Load all from RMS
            Records.loadMyInfos();

            try {
                bluetooth = new Bluetooth();
            } catch(Exception e){
                throw new Exception("[Bluetooth] " + e.toString());
            }
            
            // All settings ok
            Dialog.setDefaultDialogPosition(BorderLayout.CENTER);
            getSystemInfo();    // Get system info

            if (MyRoutines.isLogin(login) &&  Password.isPassword(pass) ) {	// If user is loged in
                Dialog pleaseWait = MyRangeMIDlet.showWaitingAlert(StringConsts.S_INITIALIZATION);
                init(false, pleaseWait);    // Start Main Menu
            } else {
                // Select and show the correct screen
                if (MyRoutines.isLogin(login) && (Records.recSettings.readStringData(Records.REC_VALIDATION_PASSWORD).length() > 1)) {
                    // Show code screen
                    Command backToStartCommand = new Command(StringConsts.S_BACK){
                        public void actionPerformed(ActionEvent ae) {
                            //SCREENS[START_SCREEN].run(askExitCommand);
                            MyRangeMIDlet.SCREENS[MyRangeMIDlet.LOGIN_SCREEN].run(askExitCommand);
                        }
                    };
                    MyRangeMIDlet.SCREENS[PASSWORD_VALIDATION_SCREEN].run(backToStartCommand);
                } else {
                    // Show Start Form
                    MyRangeMIDlet.SCREENS[MyRangeMIDlet.LOGIN_SCREEN].run(askExitCommand);
                }
            }
        } catch (Exception e) {
            showAlert("[MIDlet] Exception", e.toString(), Dialog.TYPE_ERROR, new Command[] {exitCommand}, false);
        }
	}

    // TODO
    public static javax.microedition.lcdui.Displayable getDisplayable() {
        return disp.getCurrent();
    }

    public static void setDisplayable(javax.microedition.lcdui.Displayable nextDisplayable)
    {
        try {
            disp.setCurrent(nextDisplayable);
        } catch (Exception e) {
            out.println("[Display:] Error: " + e.toString());
            System.out.println("[Display:] Error: " + e.toString());
        }
    }

    public static void setDisplayable(Alert alert, Displayable nextDisplayable) {
        try {
            if (alert == null) setDisplayable(nextDisplayable);
            else disp.setCurrent(alert, nextDisplayable);
        } catch (Exception e) {
            out.println("[Display:] Error: " + e.toString());
            System.out.println("[Display:] Error: " + e.toString());
        }
    }

    /**
	 * Common utility method to show text as an alert dialog
	 *
	 * @param text Text to show
     * @param type Dialog type
     * @param cmds commands that are added to the form any click on any command will dispose the form
     * @param isWaiting Should show waiting circle or not?
	 */
    public final static Dialog showAlert(final String title, final String text,
            final int type, final Command[] cmds, final boolean isWaiting) {
        final Dialog alert = new Dialog();
        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                alert.getDialogStyle().setBorder(Border.createRoundBorder(6, 6, 0xe3ef5a));
                alert.getTitleStyle().setBackgroundGradientStartColor(0x666666);
                alert.getTitleStyle().setBackgroundGradientEndColor(0x131313);
                alert.getDialogStyle().setBackgroundGradientStartColor(0x666666);
                alert.getDialogStyle().setBackgroundGradientEndColor(0x131313);
                int transitionDuration = 700;
                if (isWaiting) {
                    alert.getDialogStyle().setBorder(Border.createRoundBorder(6, 6, 0xe3ef5a));
                    transitionDuration = 400;
                } else {
                    transitionDuration = 700;
                    alert.setTitle(title);
                }
                alert.setTransitionInAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_VERTICAL,
                        true, transitionDuration));
                alert.setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_VERTICAL,
                        false, transitionDuration));
                alert.setDialogType(type);
                TextComponent t = new TextComponent(text);
                t.getStyle().setBorder(Border.createEmpty());           // Set empty border
                t.getSelectedStyle().setBorder(Border.createEmpty());   // Set empty border
                /*
                if (t.getPreferredH() > Display.getInstance().getDisplayHeight()) {
                    //t.setFocusable(true);
                    alert.setScrollableY(true);
                }
                */
                alert.addComponent(t);
                if (cmds != null && cmds.length>0) {
                    alert.setDefaultCommand(cmds[0]);
                    for (int i=0; i<cmds.length; i++) {
                        alert.addCommand(cmds[i]);
                    }
                }
                alert.setAutoDispose(true);
                //Dialog.setCommandsAsButtons(true);
                Dialog.setAutoAdjustDialogSize(true);
                if (isWaiting) {
                    InfiniteProgressIndicator ipi = new InfiniteProgressIndicator(waitCircle);
                    alert.addComponent(ipi);
                    alert.registerAnimated(ipi);
                    alert.addCommand(new Command(StringConsts.S_HIDE_DIALOG){
                        public void actionPerformed(ActionEvent evt) {
                            if (Display.getInstance().getCurrent() instanceof Dialog) {
                                ((Dialog) Display.getInstance().getCurrent()).dispose();
                            }
                        }
                    });
                }
                int height = Display.getInstance().getDisplayHeight() - (alert.getContentPane().getPreferredH() + alert.getTitleComponent().getPreferredH());
                height /= 2;
                if (height<0) height=0;
                int width = Display.getInstance().getDisplayWidth() - alert.getContentPane().getPreferredW();
                width /= 2;
                if (width<0) width=0;
                alert.setAutoDispose(true);
                // Dispose current
                if (Display.getInstance().getCurrent() instanceof Dialog) {
                    ((Dialog) Display.getInstance().getCurrent()).dispose();
                }
                // Show
                alert.show(height, height, width, width, true, false);
            }
        });
        return alert;
	}

	public final static Dialog showInfoAlert(String text) {
        Command disposeCommand = new Command(StringConsts.S_OK) {
            public void actionPerformed(ActionEvent evt) {
                if (Display.getInstance().getCurrent() instanceof Dialog) {
                    ((Dialog) Display.getInstance().getCurrent()).dispose();
                }
            }
        };
        return showAlert("", text, Dialog.TYPE_INFO, new Command[] {disposeCommand}, false);
	}

    public final static Dialog showFailAlert(String title, String text) {
        Command disposeCommand = new Command(StringConsts.S_OK) {
            public void actionPerformed(ActionEvent evt) {
                if (Display.getInstance().getCurrent() instanceof Dialog) {
                    ((Dialog) Display.getInstance().getCurrent()).dispose();
                }
            }
        };
        return showAlert(title, text, Dialog.TYPE_ERROR, new Command[] {disposeCommand}, false);
	}

    public final static Dialog showWaitingAlert(final String title)
	{
        return showAlert(title, StringConsts.S_WAIT, Dialog.TYPE_INFO, null, true);
	}

    /**
	 * Method for indication
     * via vibration and sound signal
	 *
	 * @param text Text to show
     * @param melody Melody to play
	 */
    public static void attention(String text, byte[] melody)
	{
        // Vibrate 0.5 second
        Display.getInstance().vibrate(500);

        // Make a sound
        try{
            Player p = Manager.createPlayer(Manager.TONE_DEVICE_LOCATOR);
            p.realize();
            ToneControl c = (ToneControl)p.getControl("ToneControl");
            c.setSequence(melody);
            p.start();
        } catch (Exception e) {
            out.println("[Sound:] Error: " + e.toString());
        }

        // Show alert
       showInfoAlert(text);  
	}

    /**
	 * Make a phone call from the midlet
	 *
	 * @param Number phone number to call
	 */
    public void callNumber(String Number)
    {
        boolean b;
        try{
            //pauseApp();
            //notifyPaused();
            hide();
            b = platformRequest("tel:" + Number);
            //resumeRequest();
        } catch(Exception e) {
            e.printStackTrace();
            showFailAlert("Exception", e.toString());
        }
    }

    public boolean requestURL(String url){
        try {
            return platformRequest(url);
        } catch (ConnectionNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Hide the application
     */
	public static void hide()
	{
        setDisplayable(null);
	}

    public void exitAppAlert()
	{
        Command exitCancelCommand = new Command(StringConsts.S_CANCEL);
        try{
            showAlert("", StringConsts.S_EXIT_ALERT, Dialog.TYPE_WARNING, new Command[] {exitCancelCommand, exitCommand}, false);
        }catch(Exception e){
            //TODO: do nothing
        }
    }

    public static synchronized void exitApp(){
        final Dialog pleaseWait = showWaitingAlert(StringConsts.S_EXITING);
        (new Thread() {
            public void run(){
                try {
                    if (!logout) {
                        Users.saveToRMS();          // Store user list in RMS
                        SearchUsers.saveToRMS();    // Store last search results in RMS
                        Messages.saveToRMS();       // Store private messages and smses in RMS
                        MyConferences.saveToRMS();
                        //Favourites.saveToRMS();
                        Signals.saveToRMS();
                    }
                } catch (Exception e) {
                    //showFailAlert("Exception", e.toString());
                    try {
                        // Exit in 3 seconds
                        Thread.sleep(3000);
                    } catch(Exception ignored){}
                }

                try {
                    Bluetooth.forсeStoreBtMeets();	// Forse store bluetooth logs
                    Scrobbler.dumpTrafficToRMS();   // Dump traffic to RMS
                    // Test sleep 1.5 seconds
                    Thread.sleep(1500);
                    // Close all records before exit
                    Records.closeAll();
                    // Test sleep 0.5 seconds
                    Thread.sleep(500);
                } catch (Exception ignored) {}

                if(pleaseWait != null)pleaseWait.dispose();
                // Exit now!
                try{
                    staticMidlet.notifyDestroyed();
                }catch(Exception e){
                    //TODO: unsupported exception
                }
            }
        }).start();
	}

    public synchronized void logout() throws Exception
	{
	showWaitingAlert(StringConsts.S_LOGGINGOUT);
        // Logout. Erase all personal data.
        Records.erasePersonalData();
        logout = true;  // Do not save all data in record store
        //Exit
        exitApp();
	}
     
    
    /**
     * Start SynchChron and GetMsgChron threads,
     * run main menu
     *
     * @param justRegistered true if new user just registered. Show my profile form befor show the main menu
     * @throws java.lang.Exception
     */
	public synchronized void init(final boolean justRegistered, final Dialog pleaseWait)
	{
        // Start the synch chron
        (new Timer()).schedule(new SynchChron(), 1000, SynchChron.SYNCH_CHRON_MS);
        // Start the get new incoming messages chron
        (new Timer()).schedule(new GetMsgChron(), GetMsgChron.MSG_CHRON_MS, GetMsgChron.MSG_CHRON_MS);

        // Init infos (my profile, my conferences, my favouritesusers, my signals users, users, my messages)
        StaticActions.initMyInfos();

        //TODO: delete this ugly code. bweeee!!!
        while(Users.getMyProfile() == null || !SearchUsers.isReady() || !Companies.isReady() ){
            System.out.print("sdfd");
            try{
                Thread.sleep(1000);
             }catch(InterruptedException i){}
        }
        

        pleaseWait.dispose();
        if (justRegistered) {
            // Show first screen
            SCREENS[FIRST_SCREEN].run(null);
        } else {
            SCREENS[MAINMENU_SCREEN].run(hideCommand);
        }

		try {
            Bluetooth.deviceRegistered = Records.recSettings.readBooleanData(Records.REC_DEVICE_REGISTERED);
			bluetooth.startDiscover();
		} catch (Exception ex) {
            System.out.println("[Bluetooth] " + ex.toString());
            showFailAlert(StringConsts.S_ERROR, ex.getMessage());
		}
	}

    /**
     * SynchChron is TimerTask for start the synch process every
     */
    public class SynchChron extends TimerTask
	{
        /**
         * Period in miliseconds to regulary start the synch process
         */
        public static final long SYNCH_CHRON_MS = 3600000;	// 1 hr

        /**
         * Memory optimization
         */
        private void cleanupMemory() {
            // for series 40 devices
            System.gc();
            System.gc();

            Runtime.getRuntime().gc();
			MyRangeMIDlet.out.println("[Memory] total = " +
                    Long.toString(Runtime.getRuntime().totalMemory()) +
                    ", free = " + Long.toString(Runtime.getRuntime().freeMemory()));
         }

		public void run() {
            try {
                // Memory optimization
                cleanupMemory();

                // Start new Bluetooth scanning if the last one was started more than 20 minutes ago
                // TODO remove this
                if ( (MyRangeMIDlet.bluetooth.startScanTime > 0) && (MyRangeMIDlet.bluetooth.startScanTime < System.currentTimeMillis() - 1200000) ) {
                    MyRangeMIDlet.out.println("[Chron:] Force start new scan!");
                    MyRangeMIDlet.bluetooth.reDiscover();
                }

                // Dump traffic to RMS
                Scrobbler.dumpTrafficToRMS();

                // Synchronize to the server
                synchronize(false, null);
            } catch (Exception e) {
                MyRangeMIDlet.out.println("[Chron] Error: " + e.toString());
                MyRangeMIDlet.showFailAlert("[Sync] Exception", e.toString());
            }
		}

	}

    /**
     * Synchronize to the server
     * (ONLY FOR CALL FROM SEPARATE THREAD)
     */
    public synchronized void synchronize(final boolean isReport, final Dialog progress) {
        boolean isNewCritacalVersionAvailable = false;
        String getNewVersionURL = "";
        try {
            String versionResponse = null;
            // Check new version available
            GetLatestClientVersionResponseElement latestClientVersion = StaticActions.getLatestClientVersion();
            if (!latestClientVersion.getLatestClientVersion().equals(StringConsts.S_RELEASE_VERSION)) {
                // New version available
                getNewVersionURL = latestClientVersion.getLatestClientURL();
                if (latestClientVersion.getCriticalChanges().equals("true")) {
                    // Critical changes! Need Exit!
                    isNewCritacalVersionAvailable = true;
                    throw new Exception(StringConsts.S_NEW_VERSION_AVAILABLE + latestClientVersion.getLatestClientVersion() + " " +
                            StringConsts.S_NEW_VERSION_CRITICAL_CHANGES +
                            StringConsts.S_NEW_VERSION_URL + latestClientVersion.getLatestClientURL());
                }
                else {
                    versionResponse = StringConsts.S_NEW_VERSION_AVAILABLE + latestClientVersion.getLatestClientVersion() + " " +
                            StringConsts.S_NEW_VERSION_CHANGES + latestClientVersion.getChanges() +
                            StringConsts.S_NEW_VERSION_URL + latestClientVersion.getLatestClientURL();
                }
            }

            // sendBtMeets
            Bluetooth.forсeStoreBtMeets();	// Forse store bluetooth logs
            Integer sendBtMeetsResponse = StaticActions.sendBtMeets();   // Send logs
            MyRangeMIDlet.out.println("[Sync] " + sendBtMeetsResponse.toString() + " bluetooth logs sending ...OK");

            // Try to upload changes in my infos to the server
            StaticActions.uploadMyInfos();

            // getSmsServiceInfo. Get and save U2U sms service information from the server
            SmsServiceInfo smsServiceInfo = StaticActions.getSmsServiceInfo(StringConsts.S_SMS_U2U_CODENAME);
            Records.recSettings.addByteData(smsServiceInfo.toRecord(), Records.REC_SMS_U2U_INFO);
            MyRangeMIDlet.out.println("[Sync] codeName=" + StringConsts.S_SMS_U2U_CODENAME + " shortCode=" +
                    smsServiceInfo.getShortCode() + " keyWord=" + smsServiceInfo.getKeyWord() + " cost=" +
                    smsServiceInfo.getCost() + " time=" + (new Long(smsServiceInfo.getUpdateTime().longValue()/1000)).toString() + "s");

            // Update online statuses
            Users.updateOnlineStatuses();
            // Get new messages from the server
            Messages.getNewMessages();

            if (versionResponse == null) {
                // Synchronize finished OK
                versionResponse = StringConsts.S_SYNCHRONIZING_OK;
            }

            if (isReport) {
                // Show synchronize information
                Command[] cmds;
                if (getNewVersionURL != null && getNewVersionURL.length() > 0) {
                    final String url = getNewVersionURL;
                    Command getNewVersinCommand = new Command(StringConsts.S_NEW_VERSION_DOWNLOAD) {
                        public void actionPerformed(ActionEvent evt) {
                            try {
                                if (platformRequest(url)) {  // Returns true if the MIDlet suite MUST first exit before start web browser.
                                    MyRangeMIDlet.exitApp();
                                } else {
                                    MyRangeMIDlet.exitApp();     // Exit po lubomu :)
                                }
                            } catch (Exception e) {
                                MyRangeMIDlet.showFailAlert("Exception", e.toString());
                            }
                        }
                    };
                    cmds = new Command[] {getNewVersinCommand, exitCommand};
                } else {
                   cmds = new Command[] {new Command(StringConsts.S_OK)};
                }
                if (progress != null) progress.dispose();
                MyRangeMIDlet.showAlert(null, versionResponse, Dialog.TYPE_INFO, cmds, false);
            }

        } catch (Exception e) {
            MyRangeMIDlet.out.println("[Sync] Error: " + e.toString());
            Command[] cmds;
            if (isNewCritacalVersionAvailable) {
                final String url = getNewVersionURL;
                Command getNewVersinCommand = new Command(StringConsts.S_NEW_VERSION_DOWNLOAD) {
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (platformRequest(url)) {  // Returns true if the MIDlet suite MUST first exit before start web browser.
                                MyRangeMIDlet.exitApp();
                            } else {
                                MyRangeMIDlet.exitApp();     // Exit po lubomu :)
                            }
                        } catch (Exception e) {
                            MyRangeMIDlet.showFailAlert("Exception", e.toString());
                        }
                    }
                };
                cmds = new Command[] {getNewVersinCommand, exitCommand};
            } else {
               cmds = new Command[] {new Command(StringConsts.S_OK)};
            }
            if (progress != null) progress.dispose();
            MyRangeMIDlet.showAlert("[Sync] Exception", e.toString(), Dialog.TYPE_WARNING, cmds, false);
        }
    }


    /**
     * GetMsgChron is TimerTask for regulary get new incoming messages from the server
     */
    class GetMsgChron extends TimerTask
	{
        /**
         * Perion in ms of regulary getting new incoming messages
         * and update online statuses from the server
         */
        public static final long MSG_CHRON_MS = 300000;     // 5 min

		public void run()
		{
            (new Thread() {
                public void run(){
                    try {
                        // Get new incoming messages
                        Messages.getNewMessages();
                    } catch (Exception e) {
                        MyRangeMIDlet.out.println("[Chron] Exception: " + e.toString());
                        MyRangeMIDlet.showFailAlert("[Sync] Exception", e.toString());
                    }
                    // Update online statuses
                    Users.updateOnlineStatuses();
                }
            }).start();
		}
	}

}
