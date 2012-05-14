/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.screens;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import java.util.Vector;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.MyRoutines;
import ru.myrange.bluetooth.Bluetooth;
import ru.myrange.bluetooth.BluetoothDevice;
import ru.myrange.stuff.xlwuit.IconText;
import ru.myrange.stuff.xlwuit.MenuCellRenderer;
import ru.myrange.stuff.xlwuit.XList;

/**
 * Show and work with bluetooth devices
 *
 * @author Oleg Ponfilenok
 */
public class DevicesScreen extends Screen {

    public String getName() {
        return MyRangeMIDlet.SCREEN_NAMES[MyRangeMIDlet.DEVICES_SCREEN];
    }

    protected void execute(final Form f) {
        Vector devices = new Vector();
        int i = 0;
		while(i < MyRangeMIDlet.bluetooth.deviceVec.size()) {
			Image icon;
			try {
                BluetoothDevice device = (BluetoothDevice) MyRangeMIDlet.bluetooth.deviceVec.elementAt(i);
				/*
                if (deviceName.length() < 1) {  // if friendly name is unreachable, show bluetooth address
                    deviceName = deviceName + "@" + deviceAddress;
                }
                */

				// Set the icon of the device
				switch (device.getDeviceCode()) {
					case Bluetooth.I_CLASS_COMPUTER :
						icon = MyRangeMIDlet.icons[StringConsts.I_ICON_COMPUTER];
						break;
					case Bluetooth.I_CLASS_DESKTOP :
						icon = MyRangeMIDlet.icons[StringConsts.I_ICON_DESKTOP];
						break;
					case Bluetooth.I_CLASS_LAPTOP :
						icon = MyRangeMIDlet.icons[StringConsts.I_ICON_LAPTOP];
						break;
					case Bluetooth.I_CLASS_PDA :
						icon = MyRangeMIDlet.icons[StringConsts.I_ICON_PDA];
						break;
					case Bluetooth.I_CLASS_CELLULAR :
						icon = MyRangeMIDlet.icons[StringConsts.I_ICON_CELLULAR];
						break;
					case Bluetooth.I_CLASS_CORDLESS :
						icon = MyRangeMIDlet.icons[StringConsts.I_ICON_CORDLESS];
						break;
					case Bluetooth.I_CLASS_SMARTPHONE :
						icon = MyRangeMIDlet.icons[StringConsts.I_ICON_SMARTPHONE];
						break;
                    case Bluetooth.I_CLASS_AUDIO :
                    case Bluetooth.I_CLASS_HEDSET :
                    case Bluetooth.I_CLASS_HANDSFREE :
                    case Bluetooth.I_CLASS_MICROPHONE :
                    case Bluetooth.I_CLASS_LOUDSPEAKER :
                    case Bluetooth.I_CLASS_HEADPHONES :
                    case Bluetooth.I_CLASS_PORTABLE :
                    case Bluetooth.I_CLASS_CARAUDIO :
						icon = MyRangeMIDlet.icons[StringConsts.I_ICON_HANDSFREE];
						break;
					case Bluetooth.I_CLASS_BTGPS :
						icon = MyRangeMIDlet.icons[StringConsts.I_ICON_BTGPS];
						break;
					default :
						icon = MyRangeMIDlet.icons[StringConsts.I_ICON_UNKNOWN_DEVICE];
						break;
				}
				devices.addElement(new IconText(icon, device.getDeviceName()));
			}
			catch (Exception e) {
				MyRangeMIDlet.out.println("[DeviceList] Error: " + e.toString());
			}
			i += 1;
		}

        // Create the devices list
        final XList devicesList = new XList(devices);
        devicesList.setListCellRenderer(new MenuCellRenderer());
        devicesList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Show information about the selected device
                BluetoothDevice device = (BluetoothDevice)
                        MyRangeMIDlet.bluetooth.deviceVec.elementAt(devicesList.getSelectedIndex());
                String info = StringConsts.S_DEVICE_FNAME + " " + device.getDeviceName() + " \n" +
                        StringConsts.S_DEVICE_BTID + " " + device.getBluetoothMac() + " \n" +
                        StringConsts.S_DEVICE_CLASS + " " + MyRoutines.devClassToString(device.getDeviceCode());
                MyRangeMIDlet.showInfoAlert(info);
            }
        });
        f.addComponent(devicesList);

        f.setTitle(StringConsts.S_DEVICES + " (" + Integer.toString(MyRangeMIDlet.bluetooth.numDevicesNow()) + ")");

        f.addCommand(new Command(StringConsts.S_REFRESH) {
            public void actionPerformed(ActionEvent evt) {
                try {
                    MyRangeMIDlet.bluetooth.reDiscover();
                    //MeetUsers.addUser("00174b19dab4", System.currentTimeMillis());
                } catch (Exception ex){
                    MyRangeMIDlet.showFailAlert(StringConsts.S_ERROR, ex.getMessage());
                }
            }
        });

        if (devices.size() > 0) {   // if there are any devices in the list
            // Add send bluetooth message command
            f.addCommand(new Command(StringConsts.S_SEND_BT_MSG) {
                public void actionPerformed(ActionEvent evt) {
                    int index = devicesList.getSelectedIndex();
                    if (index>-1 && index<MyRangeMIDlet.bluetooth.deviceVec.size()) {
                        try {
                            BluetoothDevice selected = (BluetoothDevice) MyRangeMIDlet.bluetooth.deviceVec.elementAt(index);
                            Bluetooth.searchServicesThread = new Bluetooth.SearchServicesThread(MyRangeMIDlet.bluetooth, selected.getRemoteDevice());
                            Bluetooth.searchServicesThread.start();
                            // show waiting dialog
                            final Command searchCancelCommand = new Command(StringConsts.S_CANCEL){
                                public void actionPerformed(ActionEvent ae) {
                                    if (Bluetooth.searchServicesThread!=null) {
                                        Bluetooth.searchServicesThread.cancel();
                                    }
                                    Bluetooth.searchServicesProgress.dispose();
                                }
                            };
                            Bluetooth.searchServicesProgress = MyRangeMIDlet.showAlert(
                                    StringConsts.S_SEARCH_SERV, StringConsts.S_WAIT, Dialog.TYPE_INFO,
                                    new Command[] {searchCancelCommand}, true);
                        } catch (Exception e){
                            Bluetooth.searchServicesProgress.dispose();
                            MyRangeMIDlet.showFailAlert("Exception", e.toString());
                        }
                    }
                }
            });
        }

    }
	
}
