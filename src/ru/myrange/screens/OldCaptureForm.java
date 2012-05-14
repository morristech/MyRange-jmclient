package ru.myrange.screens;

import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import ru.myrange.MyRangeMIDlet;
import javax.microedition.media.control.VideoControl;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;

import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.MyRoutines;

/**
 * Capture Form. Making picture for the avatar.
 * TODO: Using lcdui.
 *
 * @author Oleg Ponfilenok
 */
public class OldCaptureForm extends Form implements CommandListener, ItemCommandListener
{

    private OldCaptureForm thisInThread = this;  // for link to this from thread
    private Command cmdSnapshot;
	private Command cmdBack;
    private Player player;
    private VideoControl vc;

    /**
     * Test the camera and show the form
     *
     * @param myProfileForm
     */
	public OldCaptureForm() {
		super(StringConsts.S_CAPTURE);

        //addCommand(cmdSnapshot);
        cmdBack = new Command(StringConsts.S_BACK, Command.BACK, 99);
        cmdSnapshot = new Command(StringConsts.S_SNAPSHOT, Command.SCREEN, 1);
		addCommand(cmdSnapshot);
        addCommand(cmdBack);
		setCommandListener(this);

        // Test and Show
        (new Thread() {
            public void run(){
                showWaitingAlert(StringConsts.S_WAIT, thisInThread);
                try {
                    Player testPlayer = null;
                    try {
                        testPlayer = Manager.createPlayer("capture://video");

                        // *** Testing the camera type of player ***

                        // Get snapshot encoding
                        /*
                        String encodings = System.getProperty("video.snapshot.encodings");
                        encodings = (encodings != null) ? encodings : "encoding=png";
                        int endIndex = encodings.indexOf(" ", 1);
                        endIndex = (endIndex > 0) ? endIndex : encodings.length();  // get the first encoding in the list of supported snapchot encodings
                        String encoding = encodings.substring(0,endIndex);
                        */

                        // Prepare the camera
                        //MyMIDlet.out.println("[Camera:] Make a test snapshot: " + encoding);
                        testPlayer.realize();
                        VideoControl testVc = (VideoControl) testPlayer.getControl("VideoControl");
                        testPlayer.prefetch();
                        testPlayer.start();
                        Item testCamera = (Item) testVc.initDisplayMode(VideoControl.USE_GUI_PRIMITIVE, null);

                        // Get test snapshot
                        //byte[] snapshotBytes = testVc.getSnapshot(encoding);
                        byte[] snapshotBytes = testVc.getSnapshot(null);

                        testPlayer.stop();
                        testPlayer.deallocate();    // Releases resources (very important!)
                        testPlayer.close();
                        testPlayer = null;

                        // *** End testing ***

                        player = Manager.createPlayer("capture://video");
                        MyRangeMIDlet.out.println("[Camera] Set capture://video");

                    } catch(Exception e) {
                        if(testPlayer != null) {
                            testPlayer.stop();
                            testPlayer.deallocate();    // Releases resources (very important!)
                            testPlayer.close();
                            testPlayer = null;
                        }
                        MyRangeMIDlet.out.println("[Camera] " + e.toString());
                        player = Manager.createPlayer("capture://image");   // Try to use "capture://image" if there are some problems with "capture://video"
                        MyRangeMIDlet.out.println("[Camera] Set capture://image");
                    }
                    player.realize();

                    // Получаем управление для видео
                    vc = (VideoControl) player.getControl("VideoControl");
                    if(vc != null) {
                        Item camera = (Item) vc.initDisplayMode(VideoControl.USE_GUI_PRIMITIVE, null);
                        /*
                        camera.addCommand(cmdSnapshot);
                        camera.setDefaultCommand(cmdSnapshot);
                        camera.setItemCommandListener(thisInThread);
                        */

                        // Set preferred camera size
                        int screenWidth = Display.getInstance().getDisplayWidth();
                        int screenHeight = Display.getInstance().getDisplayHeight();
                        if (screenWidth < screenHeight) camera.setPreferredSize(screenWidth, -1);   // lock width and unlock height
                        else camera.setPreferredSize(-1, screenHeight);   // lock height and unlock width

                        append(camera);
                    }
                    player.prefetch();
                    player.start();

                    MyRangeMIDlet.setDisplayable(thisInThread);
                } catch(Exception e) {
                    MyRangeMIDlet.out.println("[Camera] " + e.toString());
                    // Show the error and go back to the profile form      
                    MyRangeMIDlet.showFailAlert("Exception", e.toString());
                    // Test camera locators
                    testCameraLocators();
                }
            }
        }).start();
	}

    /**
     * Releases resources (very important!)
     */
    protected synchronized void cleanup() {
        try {
            if(player!=null) {
                player.stop();
                player.deallocate();
                player.close();
            }
        } catch(Exception ignored) {}
        //player = null;
    }

    public synchronized void doSnapshot() {
        showWaitingAlert(StringConsts.S_WAIT, thisInThread);
        (new Thread() {
            public void run(){
                try {
                    // Get snapshot encoding
                    /*
                    String encodings = System.getProperty("video.snapshot.encodings");
                    encodings = (encodings != null) ? encodings : "encoding=png";
                    int endIndex = encodings.indexOf(" ", 1);
                    endIndex = (endIndex > 0) ? endIndex : encodings.length();  // get the first encoding in the list of supported snapchot encodings
                    String encoding = encodings.substring(0,endIndex);
                    */

                    // Prepare the camere
                    //MyMIDlet.out.println("[Camera:] Make a snapshot: " + encoding);
                    //byte[] snapshotBytes = vc.getSnapshot("encoding=png&width=100&height=100");
                    //byte[] snapshotBytes = vc.getSnapshot("encoding=png");
                    //byte[] snapshotBytes = vc.getSnapshot(encoding);
                    byte[] snapshotBytes = vc.getSnapshot(null);

                    Image snapshot = Image.createImage(snapshotBytes, 0, snapshotBytes.length);
                    //snapshot = MyRoutines.imageToSquare(snapshot);
                    //snapshot = snapshot.scaled(MyProfileScreen.bestUserpicSize, MyProfileScreen.bestUserpicSize);

                    // Back to the profile form
                    cleanup();
                    ((MyProfileScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.MY_PROFILE_SCREEN]).runWithNewPic(snapshot);
                } catch(Exception e) {
                    MyRangeMIDlet.out.println("[Camera] Error: " + e.toString());
                    MyRangeMIDlet.showFailAlert("Exception", e.getMessage() + ". \n" + StringConsts.S_SNAPSHOT_TRY_AGAIN);
                }
            }
        }).start();
    }

	public void commandAction(Command c, Displayable d) {
		if (c == cmdSnapshot){
            doSnapshot();
        } else if (c == cmdBack){
            cleanup();
            (MyRangeMIDlet.SCREENS[MyRangeMIDlet.MY_PROFILE_SCREEN]).show();
        }
	}

    public void commandAction(Command c, Item i){
        if (c == cmdSnapshot){
            doSnapshot();
        }
    }

    /**
     * Test the specified camera locator
     */
    void testCameraLocators() {
        final String LOCATORS[]={
            "capture://video",
            "capture://image",
            "capture://devcam",
            "capture://devcam0",
            "capture://devcam1"
        };
        for (int i=0; i<LOCATORS.length; i++) {
            try {
                Player testPlayer = Manager.createPlayer(LOCATORS[i]);
                MyRangeMIDlet.out.println("[Camera] " + LOCATORS[i] + " is VALID camera locator!");
                testPlayer.stop();
                testPlayer.deallocate();    // Releases resources (very important!)
                testPlayer.close();
                testPlayer = null;
            } catch(Exception e) {
                MyRangeMIDlet.out.println("[Camera] " + LOCATORS[i] + " locator Error: " + e.toString());
            }
        }
    }

    public static void showWaitingAlert(String title, Displayable nextDisplayable) {
        Alert a = new Alert(title);
        a.setTimeout(Alert.FOREVER);
        a.setString(StringConsts.S_WAIT);
        a.setIndicator(new Gauge(null, false, Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING));
        if (nextDisplayable == null) MyRangeMIDlet.setDisplayable(a);
        else MyRangeMIDlet.setDisplayable(a, nextDisplayable);
    }

}