/* myRange
 * Copyright © 2009 My Range Group
 */

/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.bluetooth;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.events.ActionEvent;
import java.util.Vector;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import ru.myrange.stuff.StringConsts;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.datastore.Records;
import ru.myrange.screens.BtMsgScreen;
import ru.myrange.soap.BtMeet;
import ru.myrange.stuff.MeetUsers;

/**
 * Class to work with bluetooth discovering and store BtMeets.
 *
 * @author Oleg Ponfilenok
 */
public class Bluetooth implements DiscoveryListener {

    // *** DEVICE CLASSES ***
	public static final int I_CLASS_COMPUTER = 0x100;			// Computer (desktop,notebook, PDA, organizers, .... )
	public static final int I_CLASS_DESKTOP = 0x104;			// Desktop workstation
	public static final int I_CLASS_SERVER = 0x108;				// Server-class computer
	public static final int I_CLASS_LAPTOP = 0x10C;				// Laptop
	public static final int I_CLASS_HANDNELD = 0x110;			// Handheld PC/PDA (clam shell)
	public static final int I_CLASS_PDA = 0x114;				// Palm sized PC/PDA
	public static final int I_CLASS_WEARABLE = 0x118;			// Wearable computer (Watch sized)

	public static final int I_CLASS_PHONE = 0x200;				// Phone (cellular, cordless, payphone, modem, ...)
	public static final int I_CLASS_CELLULAR = 0x204;			// Cellular
	public static final int I_CLASS_CORDLESS = 0x208;			// Cordless
	public static final int I_CLASS_SMARTPHONE = 0x20c;			// Smart phone
	public static final int I_CLASS_MODEM = 0x210;				// Wired modem or voice gateway
	public static final int I_CLASS_ISDN = 0x214;				// Common ISDN Access

	public static final int I_CLASS_LAN = 0x300;				// LAN /Network Access point

	public static final int I_CLASS_AUDIO = 0x400;				// Audio/Video (headset,speaker,stereo, video display, vcr... )
	public static final int I_CLASS_HEDSET = 0x404;				// Device conforms to the Headset profile
	public static final int I_CLASS_HANDSFREE = 0x408;			// Hands-free
	public static final int I_CLASS_MICROPHONE = 0x410;			// Microphone
	public static final int I_CLASS_LOUDSPEAKER = 0x414;		// Loudspeaker
	public static final int I_CLASS_HEADPHONES = 0x418;			// Headphones
	public static final int I_CLASS_PORTABLE = 0x41c;			// Portable Audio
	public static final int I_CLASS_CARAUDIO = 0x420;			// Car audio
	public static final int I_CLASS_SETTOP = 0x424;				// Set-top box
	public static final int I_CLASS_HIFI = 0x428;				// HiFi Audio Device
	public static final int I_CLASS_VCR = 0x42c;				// VCR
	public static final int I_CLASS_VIDEOCAMERA = 0x430;		// Video Camera
	public static final int I_CLASS_CAMCORDER = 0x434;			// Camcorder
	public static final int I_CLASS_VIDEOMONITOR = 0x438;		// Video Monitor
	public static final int I_CLASS_VIDEODISPLAY = 0x43c;		// Video Display and Loudspeaker
	public static final int I_CLASS_CONFERENCING = 0x440;		// Video Conferencing
	public static final int I_CLASS_TOY = 0x438;				// Gaming/Toy

	public static final int I_CLASS_PERIPHERAL = 0x500;			// Peripheral (mouse, joystick, keyboards, ..... )
	public static final int I_CLASS_KEYBOARD = 0x540;			// Keyboard
	public static final int I_CLASS_POINTING = 0x580;			// Pointing device
	public static final int I_CLASS_COMBO = 0x5c0;				// Combo keyboard/pointing device
	public static final int I_CLASS_JOYSTICK = 0x504;			// Joystick
	public static final int I_CLASS_GAMEPAD = 0x508;			// Gamepad
	public static final int I_CLASS_REMOTE = 0x50c;				// Remote control
	public static final int I_CLASS_SENSING = 0x510;			// Sensing device
	public static final int I_CLASS_TABLET = 0x514;				// Digitizer tablet
	public static final int I_CLASS_CARD = 0x518;				// Card Reader (e.g. SIM Card Reader)

	public static final int I_CLASS_IMAGING = 0x600;			// Imaging (printing, scanner, camera, display, ...)
	public static final int I_CLASS_DISPLAY = 0x610;			// Display
	public static final int I_CLASS_CAMERA = 0x620;				// Camera
	public static final int I_CLASS_SCANNER = 0x640;			// Scanner
	public static final int I_CLASS_PRINTER = 0x680;			// Printer

	public static final int I_CLASS_BTGPS = 0x1f00;				// Bluetooth GPS reciever

	public static final String S_CLASS_COMPUTER = "Computer";
	public static final String S_CLASS_DESKTOP = "Desktop";
	public static final String S_CLASS_LAPTOP = "Laptop";
	public static final String S_CLASS_PDA = "PDA";
	public static final String S_CLASS_PHONE = "Phone";
	public static final String S_CLASS_CELLULAR = "Cellular";
	public static final String S_CLASS_CORDLESS = "Cordless";
	public static final String S_CLASS_SMARTPHONE = "Smart phone";
	public static final String S_CLASS_BTGPS = "Bluetooth GPS";

    /** Is Bluetooth-address registered */
    public static boolean deviceRegistered = false;

    /** Does bluetooth work? */
    public static boolean isWork = false;

	/**
     * UUID for a OBEXOBJECTPUSH
     */
    private static final UUID[] obexUuid = new UUID[]{new UUID("1105", true)};

    private String btAddress = null;
    private Integer deviceClass = null;
    private String friendlyName = null;

    public LocalDevice localDevice;
    private DiscoveryAgent discoveryAgent;
    public DeviceClass deviceClassObject;
    
    //private RemoteDevice[] devList; 
    public Vector deviceVec = new Vector(); 		// Vector of the found Bluetooth devices
    public static Vector btMeetsList = new Vector(); // Vectron of bluetooth logs for the active devices (not in record store)
    public boolean isScanning = false;
    public int scanNum = 0;
    public long startScanTime = 0;		// Время начала i-того сканирования
    public long endScanTime = 0;		// Время окончания i-того сканирования

    // Search services
    public static Dialog searchServicesProgress;
	public static RemoteDevice searchServicesDevice = null;
	public static Bluetooth.SearchServicesThread searchServicesThread;

    //private ServiceRecord serviceRecord;

    public Bluetooth() throws Exception
    {
        // Get the local device
        try {
            localDevice = LocalDevice.getLocalDevice();
        }catch(Exception e) {
            // Problem with Bluetooth initialization
            throw new Exception(StringConsts.S_BT_INIT_ERROR);
        }

        btAddress = localDevice.getBluetoothAddress().toLowerCase();  // Get local bluetooth address
        MyRangeMIDlet.out.println("[Bluetooth] btAddress = " + btAddress);
        friendlyName = localDevice.getFriendlyName();   // Get local friendly name
        MyRangeMIDlet.out.println("[Bluetooth] friendlyName = " + friendlyName);

        // Get the device class
        deviceClassObject = localDevice.getDeviceClass();
        deviceClass = new Integer( deviceClassObject.getMajorDeviceClass() + deviceClassObject.getMinorDeviceClass() );
        MyRangeMIDlet.out.println("[Bluetooth] deviceClass = " + deviceClass.toString());

        int mode = localDevice.getDiscoverable();
        switch (mode) {
            case DiscoveryAgent.NOT_DISCOVERABLE :
                MyRangeMIDlet.out.println("[Bluetooth] Discoverable: " + StringConsts.S_NOT_DISCOVERABLE);
                break;
            case DiscoveryAgent.GIAC :
                MyRangeMIDlet.out.println("[Bluetooth] Discoverable: " + StringConsts.S_GIAC);
                break;
            case DiscoveryAgent.LIAC :
                MyRangeMIDlet.out.println("[Bluetooth] Discoverable: " + StringConsts.S_LIAC);
                break;
            default :
                MyRangeMIDlet.out.println("[Bluetooth] Discoverable: " + "0x" + Integer.toString(mode, 16));
                break;
        }

        // Get the discovery agent
        discoveryAgent = localDevice.getDiscoveryAgent();
        isWork = true;
        deviceRegistered = Records.recSettings.readBooleanData(Records.REC_DEVICE_REGISTERED);
    }

    /**
	 * Returns the local bluetooth address
	 *
	 * @return
	 */
	public String getBtAddress() {
        return btAddress;
    }

    /**
	 * Returns the local device class
	 *
	 * @return
	 */
	public Integer getDeviceClass() {
        return deviceClass;
    }

    /**
	 * Returns the local friendly name
	 *
	 * @return
	 */
	public String getFriendlyName() {
        return friendlyName;
    }

    
    public synchronized void setDiscoverable(int mode)
    {
    	MyRangeMIDlet.out.print("[Bluetooth:] Set Discoverable");
    	try {
    		localDevice.setDiscoverable(mode);
            MyRangeMIDlet.out.println(" ...OK");
        }
        catch (Exception e) {
        	MyRangeMIDlet.out.println(" ...FAIL");
        	MyRangeMIDlet.out.println("[Bluetooth:] Error: " + e.toString());
        }
    }
    
    public synchronized int getDiscoverable()
    {
    	MyRangeMIDlet.out.print("[Bluetooth:] Get Discoverable");
        int mode = localDevice.getDiscoverable();
        MyRangeMIDlet.out.println(" ...OK");
        switch (mode) {
        	case DiscoveryAgent.NOT_DISCOVERABLE :
        		MyRangeMIDlet.out.println("[Bluetooth:] Discoverable: " + StringConsts.S_NOT_DISCOVERABLE);
        		break;
        	case DiscoveryAgent.GIAC :
        		MyRangeMIDlet.out.println("[Bluetooth:] Discoverable: " + StringConsts.S_GIAC);
        		break;
        	case DiscoveryAgent.LIAC :
        		MyRangeMIDlet.out.println("[Bluetooth:] Discoverable: " + StringConsts.S_LIAC);
        		break;
        	default :
        		MyRangeMIDlet.out.println("[Bluetooth:] Discoverable: " + "0x" + Integer.toString(mode, 16));
        		break;
        }
        return mode;
    }
    
    // Вспомогательный метод реализующий начало фазы поиска устройств и сервисов
    public synchronized void startDiscover() throws IllegalStateException
    {
        if (!isWork) throw new IllegalStateException(StringConsts.S_BT_INIT_ERROR);
        if (!deviceRegistered) throw new IllegalStateException(StringConsts.S_DEVICE_NOT_REGISTERED);

    	MyRangeMIDlet.out.println("[Discover] New scanning started.");
    	startScanTime = System.currentTimeMillis();		// Замечаем время начала сканирования

        // Increase refreshModeCount in MeetUsers class
        if (MeetUsers.isRefreshMode) {
            if (MeetUsers.refreshModeCount > 0) MeetUsers.isRefreshMode = false;    // two scans in refresh mode
            else MeetUsers.refreshModeCount ++;
        }

        try {
            deviceVec.removeAllElements();			// Перед новым поиском очищаем вектор устройств
            
            discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);

            // Simulate discovery of local device. I use this to save interval of scanning.
            BtMeet newBtMeet = new BtMeet(btAddress, deviceClass.intValue(), friendlyName, startScanTime);	// Create bluetooth log
            int i = 0;
            int errCode = 0;
            while(i < btMeetsList.size()) {	// Search this remote device in btLogsList
                BtMeet curBtMeet = (BtMeet) btMeetsList.elementAt(i);
                if (newBtMeet.getBtAddress().compareTo(curBtMeet.getBtAddress()) == 0) {
                    errCode = curBtMeet.merge(newBtMeet);	// Merge interval
                    break;
                }
                i++;
            }
            if ( (btMeetsList.size() == 0) || (i == btMeetsList.size()) || (errCode != 0) ) { // btLogsList is empty or this remote device not found
                btMeetsList.addElement(newBtMeet);
            }

            // If there is no exception
            isScanning = true;
        }
        catch (Exception e) {
        	isScanning = false;
        	MyRangeMIDlet.out.println("[Discover] Error: " + e.toString());
            MyRangeMIDlet.showFailAlert(StringConsts.S_START_SCAN_FAIL + " \n" + e.toString(), null);
        }
        
        //Устройства, найденные в предыдущем запросе. 
        /*
        devList = discoveryAgent.retrieveDevices(DiscoveryAgent.CACHED); 
        if (devList != null) { 
            //serviceRecord = searchServices(devList); 
            if (serviceRecord == null) { 
                // Получаем предопределенные устройства . 
                devList = discoveryAgent.retrieveDevices(DiscoveryAgent.PREKNOWN); 
                if (devList != null) { 
                    //serviceRecord = searchServices(devList); 
                } 
            } 
        } 
        
        if (serviceRecord == null) { 
        	try {
                deviceList.removeAllElements();				// Перед новым поиском очищаем вектор устройств
                discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);
                isScanning = true;		// if no exception
            }
            catch (BluetoothStateException e) {
            	MyMIDlet.out.println(e.toString());
            }
        }
        */
    }
    
    public synchronized void stopDiscover() throws Exception
    {
        discoveryAgent.cancelInquiry(this);
        isScanning = false;
    }

    public synchronized void reDiscover() throws Exception    // Stop and start new bluetooth scanning
    {
        // Stop discovering
        stopDiscover();
        // Start new discovering in 1 second
        long scanTimeOut = 1000;
        MyRangeMIDlet.out.println("[Discover:] Start new scanning in " + (scanTimeOut)/1000 + " s.");
        Thread.sleep(scanTimeOut);
        startDiscover();
    }
    
    // Вызывается, когда обнаружено новое устройство
    /*
     * (non-Javadoc)
     * @see javax.bluetooth.DiscoveryListener#deviceDiscovered(javax.bluetooth.RemoteDevice, javax.bluetooth.DeviceClass)
     */
    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) { 
        try {
            BluetoothDevice device = new BluetoothDevice(btDevice, cod);
            deviceVec.addElement(device);

            // *** Store the log of the new device in memory ***
            // Create new bluetooth log
            BtMeet newBtMeet = new BtMeet(device.getBluetoothMac(), device.getDeviceCode(), device.getDeviceName(), System.currentTimeMillis());
            int i = 0;
            int errCode = 0;
            while(i < btMeetsList.size()) {	// Search this remote device in btLogsList
                BtMeet curBtMeet = (BtMeet) btMeetsList.elementAt(i);
                if (newBtMeet.getBtAddress().equals(curBtMeet.getBtAddress())) {
                    errCode = curBtMeet.merge(newBtMeet);	// Merge interval
                    break;
                }
                i++;
            }
            if ( (btMeetsList.size() == 0) || (i == btMeetsList.size()) || (errCode != 0) ) { // btLogsList is empty or this remote device not found
                btMeetsList.addElement(newBtMeet);
            }

            MeetUsers.addUser(device.getBluetoothMac(), newBtMeet.getEndTime().longValue());	// Check discovered device to registered user

            // Update the information in the screen
            MyRangeMIDlet.staticMidlet.updateShow();
        } catch (Exception e) {
            MyRangeMIDlet.out.println( "[Device Discovered] Error: " + e.toString());
        }
    }

    /**
     * Recalculation number of devices near user now
     */
    public int numDevicesNow() {
		return deviceVec.size();
	}

    // Вызывается при завершении фазы запроса
    public void inquiryCompleted(int discType) 
    { 
    	try {
            endScanTime = System.currentTimeMillis();   // Set the time of end of scanning
            scanNum += 1;
            // Store non-actual device bluetooth records in record store
            storeBtMeets(endScanTime);

            // Update the information on the screen
            MyRangeMIDlet.staticMidlet.updateShow();

            // Print information in the out
            MyRangeMIDlet.out.println( "[Discover:] Сканиравание # " + scanNum + " завершено, " + Integer.toString(deviceVec.size()) +
                    " устройств найдено. Время сканирования " + Long.toString((endScanTime - startScanTime)/1000) + " s.");

            long scanTimeOut = StringConsts.L_SCAN_TO;
            try {
                scanTimeOut = Records.recSettings.readLongData(Records.REC_SCAN_TO);
            } catch (Exception ignored) {
                // Ignored!
            }
            // Start new scannig after the some time
            MyRangeMIDlet.out.println("[Discover:] Start new scanning in " + (scanTimeOut)/1000 + " s.");
            Thread.sleep(scanTimeOut);
            if (isScanning) {
                startDiscover();
            }
        } catch (Exception e) {
            MyRangeMIDlet.out.println( "[Inquiry Completed:] Error: " + e.toString());
        }
    }

    // This routine stores the non-actual device bluetooth records in record store
    public static synchronized void storeBtMeets(long unixTime) {
		byte[] record;
		int i = 0;
		while(i < btMeetsList.size()) {
			BtMeet curBtMeet = (BtMeet) btMeetsList.elementAt(i);
			if (curBtMeet.getEndTime().longValue() < unixTime - MyRangeMIDlet.mergeIntervalVar/2) {
				try {
                    record = curBtMeet.toRecord();
				    int id = Records.recBtMeets.addByteData(record);
                } catch (Exception e) {
                    MyRangeMIDlet.out.println("[Bluetooth:] RMS Error: " + e.toString());
                }
                btMeetsList.removeElementAt(i);
				i--;
			}
			i++;
		}
	}

    // This routine forсe stores bluetooth records in record store before application exit
	public static synchronized void forсeStoreBtMeets() {
		byte[] record;
		while(btMeetsList.size() > 0) {
			BtMeet curBtMeet = (BtMeet) btMeetsList.elementAt(0);
			try {
                record = curBtMeet.toRecord();
                Records.recBtMeets.addByteData(record);
            } catch (Exception e) {
                MyRangeMIDlet.out.println("[Bluetooth:] RMS Error: " + e.toString());
            }
			btMeetsList.removeElementAt(0);
		}
	}

    /* Поиск интересующих нас сервисов на удаленном устройстве*/
    public synchronized void searchServices(RemoteDevice dev) throws Exception {
        int[] attribSet = {0x0100, 0x0001, 0x0002, 0x0003, 0x0004};
        // Ищем устройство, поддерживающее интересующий нас сервис.
        try {
            discoveryAgent.searchServices(attribSet, obexUuid, dev, this);
            MyRangeMIDlet.out.println("[Service discovered:] Service search started.");
        } catch (BluetoothStateException e) {
        	MyRangeMIDlet.out.println("[Service discovered:] Error: " + e.toString());
        }
    }

    /* Отменяет поиск сервиса с transID */
    public synchronized void cancelServiceSearch(int transID) {

    }

    // *** DiscoveryListener Callbacks ***

    // Вызывается, когда будет найден сервис
    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
    	//if (serviceRecord != null) return;
		// Сервис найден. Используем первый попавшийся.
		//serviceRecord = servRecord[0];

		for(int i = 0; i < servRecord.length; i++) {
        	String connectionURL = "";
        	try {
                connectionURL = servRecord[i].getConnectionURL(1,false);
                searchServicesThread.cancel();
                if (searchServicesProgress!=null) searchServicesProgress.dispose();
                //new BtMsgScreen(searchServicesDevice, connectionURL);
                
                String deviceAddress = searchServicesDevice.getBluetoothAddress();
                String deviceName = "";
                try
                {
                    // Probably, this action can take some time...
                    deviceName = searchServicesDevice.getFriendlyName(false);
                }
                catch  (Exception e) {
                    deviceName = searchServicesDevice.getBluetoothAddress();
                }
                final Command backCommand = new Command(StringConsts.S_BACK){
                    public void actionPerformed(ActionEvent ae) {
                        MyRangeMIDlet.SCREENS[MyRangeMIDlet.MAINMENU_SCREEN].run(MyRangeMIDlet.staticMidlet.hideCommand);
                    }
                };
                ((BtMsgScreen) MyRangeMIDlet.SCREENS[MyRangeMIDlet.BT_MSG_SCREEN])
                        .run(backCommand, deviceAddress, deviceName, connectionURL);

                //connectionURL = servRecord[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
            } catch (Exception e){
            	MyRangeMIDlet.out.println("[Service discovered] Error: " + e.toString());
            }
            MyRangeMIDlet.out.println("[Service discovered] The connection URL is: " + connectionURL);
    	}
    }

    // Вызывается при завершении поиска сервиса
    public void serviceSearchCompleted(int transID, int respCode) {
    	MyRangeMIDlet.out.println("[Service discovered:] Service search completed.");
    	try {
            if (searchServicesThread.isActive) {    // Если сервис так и не найден
                searchServicesThread.cancel();
                if (searchServicesProgress!=null) searchServicesProgress.dispose();
                MyRangeMIDlet.showFailAlert("Fail", StringConsts.S_SEARCH_SERV_FAIL_ALERT);
            }

        } catch (Exception e){
            MyRangeMIDlet.out.println("[Service discovered:] Error: " + e.toString());
        }
    }

    public synchronized void serviceSearchCompleted() throws Exception {

    }

    /* Класс для поиска сервисов. Вызывается из MyDeviceList */
    public static class SearchServicesThread extends Thread  {
    	private Bluetooth bluetooth;
    	private RemoteDevice dev;
    	public boolean isActive = false;

    	public SearchServicesThread(Bluetooth bluetooth, RemoteDevice dev)
    	{
    		this.bluetooth = bluetooth;
    		this.dev = dev;
    		this.isActive = true;
    	}

    	public void run() {
            try {
    		    bluetooth.searchServices(dev);	// Search OBEXOBJECTPUSH service for send message
            } catch (Exception e){
                MyRangeMIDlet.out.println("[Service discovered:] Error: " + e.toString());
            }
    	}

    	public void cancel(){
    		//bluetooth.cancelServiceSearch(0); // (?)
    		isActive = false;
    		//this = null;
    	}
    }
    
}