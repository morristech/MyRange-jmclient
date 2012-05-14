/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import java.io.DataOutputStream;
import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.obex.ClientSession;
import javax.obex.HeaderSet;
import javax.obex.Operation;
import ru.myrange.stuff.StringConsts;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.xlwuit.TextComponent;

/**
 * This class realise sending bluetooth message to remote divice
 *
 * @author Oleg Ponfilenok
 */
public class BtMsgScreen extends Screen {

	// Command cmdTemplatesBack = new Command(MyConsts.S_BACK, Command.BACK, 99);
    
    private String deviceAddress;
    private String deviceName = "";
    private String connectionURL;

    public String getName() {
        //return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.BT_MSG_SCREEN];
        return StringConsts.S_BT_MSG_TO_USERNAME + deviceName;
    }


    /**
     * Overload run method
     *
     * @param backCommand - back to previous screen command
     * @param deviceAddress - bluetooth mac address of remote device
     * @param deviceName - friendly name of remote device
     * @param connectionURL
     */
    public final void run(final Command backCommand, String deviceAddress, String deviceName, String connectionURL) {
        this.deviceAddress = deviceAddress;
        this.deviceName = deviceName;
        this.connectionURL = connectionURL;
        createAll(backCommand);
        show();
    }

    protected void execute(final Form f) {

        f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        if (deviceAddress == null || deviceAddress.length() < 1 ||
                connectionURL == null || connectionURL.length() < 1) {
            // No url to send message
            f.addComponent(new TextComponent(StringConsts.S_NO_DEVICE_FAIL_ALERT));
            return;
        }

        final TextArea msgArea = new TextArea();
        f.addComponent(msgArea);

        /*
        f.addCommand(new Command(MyConsts.S_BTMSG_TEMPLATES) {
            public void actionPerformed(ActionEvent ae) {
                // Choice message from templates
                templatesList = new List(MyConsts.S_BTMSG_TEMPLATES, Choice.IMPLICIT, MyConsts.S_BTMSG_TEMPLATES_TITLE, null);
                templatesList.addCommand(cmdTemplatesBack);
                templatesList.setCommandListener(this);
                MyRangeMIDlet.setDisplayable(templatesList);

            }
        });
        */

        f.addCommand(new Command(StringConsts.S_SEND) {
            public void actionPerformed(ActionEvent ae) {
                (new Thread()
                {
                    public void run() {  // Send message to remote device
                        String msg = msgArea.getText().trim();
                        if (msg.length() < 1) {
                            // No any text was entered
                            MyRangeMIDlet.showInfoAlert(StringConsts.S_SEND_MSG_NO_TEXT);
                            return;
                        }

                        final Dialog progress = MyRangeMIDlet.showWaitingAlert(StringConsts.S_SENDING);
                        Connection conn;
                        ClientSession connServer;
                        HeaderSet headerSet;
                        Operation op;
                        DataOutputStream netStream;
                        try {
                            String scheme = "btgoep://";
                            int colon = connectionURL.indexOf(":", connectionURL.indexOf(":") + 1);	// нас интересует толко номер порта
                            int semicolon = connectionURL.indexOf(";", 0);

                            // Open connection
                            String port = connectionURL.substring(colon, semicolon);
                            String url = scheme + deviceAddress + port + ";master=false;encrypt=false;authenticate=false";
                            conn = Connector.open(url);
                            connServer = (ClientSession) conn;

                            byte[] data = msg.getBytes("UTF-8");
                            //data = msg.getBytes("ISO-8859-5");

                            // Send message
                            headerSet = connServer.createHeaderSet();
                            headerSet.setHeader(HeaderSet.LENGTH, new Long(data.length));
                            headerSet.setHeader(HeaderSet.NAME, "message.txt");
                            headerSet.setHeader(HeaderSet.TYPE, "text");
                            //headerSet.setHeader(HeaderSet.NAME, "message.htm");
                            //headerSet.setHeader(HeaderSet.TYPE, "text/html");
                            //String httpHeader = "Content-Type:text/html;charset=utf-8";
                            //headerSet.setHeader(HeaderSet.HTTP, httpHeader.getBytes());

                            connServer.connect(headerSet);
                            op = connServer.put(headerSet);
                            netStream = op.openDataOutputStream();
                            netStream.write(data);
                            netStream.flush();

                            //Close connection
                            netStream.close();
                            op.close();
                            connServer.disconnect(headerSet);
                            conn.close();
                            
                            // Message send OK
                            progress.dispose();
                            MyRangeMIDlet.showInfoAlert(StringConsts.S_SEND_MSG_OK_ALERT);
                        } catch (Exception e){
                            // Message send FAIL
                            progress.dispose();
                            MyRangeMIDlet.showFailAlert(StringConsts.S_SEND_MSG_FAIL_ALERT, "[OBEX] Exception: " + e.toString());
                        }
                    }
                }).start();
            }
        });

    }

}