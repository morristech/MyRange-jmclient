/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.MediaComponent;
import com.sun.lwuit.events.ActionEvent;
import ru.myrange.MyRangeMIDlet;
import javax.microedition.media.control.VideoControl;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import ru.myrange.stuff.StringConsts;

/**
 * Capture Form. Making picture for the avatar.
 * TODO: WARNING! Dos't work!
 *
 * @author Oleg Ponfilenok
*/
public class CaptureScreen extends Screen {

    private Player player;
    private MediaComponent camera;
    private VideoControl vc;


    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.CAPTURE_SCREEN];
    }

    /**
     * Overload run method
     *
     * @param backCommand - back to previous screen command
     */
    public void run(final Command backCommand) {
        (new Thread() {
            public void run(){
                createAll(backCommand);
                show();
            }
        }).start();
    }

    protected void execute(final Form f) {
        // Test and Show
        final Dialog pleaseWait = MyRangeMIDlet.showWaitingAlert(StringConsts.S_WAIT);
        try {
            Player testPlayer = null;
            try {
                testPlayer = Manager.createPlayer("capture://video");

                // *** Testing the camera type of player ***

                // Prepare the camera
                //MyMIDlet.out.println("[Camera:] Make a test snapshot: " + encoding);
                testPlayer.realize();
                VideoControl testVc = (VideoControl) testPlayer.getControl("VideoControl");
                testVc.initDisplayMode(VideoControl.USE_GUI_PRIMITIVE, null);
                testPlayer.prefetch();
                testPlayer.start();
                //Item testCamera = (Item) testVc.initDisplayMode(VideoControl.USE_GUI_PRIMITIVE, null);

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

            player = Manager.createPlayer("capture://video");
            player.realize();

            // Получаем управление для видео
            //vc = (VideoControl) player.getControl("VideoControl");
            //vc.initDisplayMode(VideoControl.USE_GUI_PRIMITIVE, null);

            //if(vc != null) {
                //MediaComponent camera = (MediaComponent) vc.initDisplayMode(VideoControl.USE_GUI_PRIMITIVE, null);
                camera = new MediaComponent(player);
                vc = (VideoControl) camera.getVideoControl();
                camera.start();

                //camera.setFullScreen(true);
                f.addComponent(camera);

                f.addCommand(new Command(StringConsts.S_SNAPSHOT) {
                    public void actionPerformed(ActionEvent ae) {
                        doSnapshot();
                    }
                });
            //}
            player.prefetch();
            player.start();
            pleaseWait.dispose();

        } catch(Exception e) {
            pleaseWait.dispose();
            MyRangeMIDlet.out.println("[Camera] " + e.toString());
            // Show the error and go back to the profile form
            MyRangeMIDlet.showInfoAlert("Error: " + e.toString());
            // Test camera locators
            testCameraLocators();
        }
    }

    /**
     * Releases resources (very important!)
     */
    protected synchronized void cleanup() {
        try {
            if(camera!=null) {
                camera.stop();
            }
        } catch(Exception ignored) {}
        //camera = null;
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
        final Dialog pleaseWait = MyRangeMIDlet.showWaitingAlert(StringConsts.S_DOING_SNAPSHOT);
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

                    // Cut and resize image from camera
                    Image snapshot = Image.createImage(snapshotBytes, 0, snapshotBytes.length);
                    //snapshot = MyRoutines.imageToSquare(snapshot);
                    //snapshot = snapshot.scaled(MyProfileScreen.bestUserpicSize, MyProfileScreen.bestUserpicSize);

                    // Back to the profile form
                    cleanup();
                    pleaseWait.dispose();
                    ((MyProfileScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.MY_PROFILE_SCREEN]).runWithNewPic(snapshot);

                } catch(Exception e) {
                    pleaseWait.dispose();
                    MyRangeMIDlet.out.println("[Camera] Error: " + e.toString());
                    MyRangeMIDlet.showFailAlert("Exception", e.getMessage() + ". \n" + StringConsts.S_SNAPSHOT_TRY_AGAIN);
                }
            }
        }).start();
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

}