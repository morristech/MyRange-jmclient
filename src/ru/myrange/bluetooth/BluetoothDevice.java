/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.bluetooth;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.RemoteDevice;
import ru.myrange.MyRangeMIDlet;

/**
 * Remote Bluetooth Device with extended fields
 *
 * @author Oleg Ponfilenok
 */
public class BluetoothDevice {
    public static final String FNAME_ERROR = "ERROR";

    private RemoteDevice btDevice = null;
    private DeviceClass cod = null;
    private int deviceCode = 0;
    private String deviceName = "";
    private String bluetoothMac = "";

    public BluetoothDevice(RemoteDevice btDevice, DeviceClass cod) throws Exception
    {
        setRemoteDevice(btDevice);
        setDeviceClass(cod);

        // Get the device friendly name
        String fName = "";
        try {
            fName = btDevice.getFriendlyName(false);   // Probably, this action can take some time...
        }
        catch (Exception e) {
            MyRangeMIDlet.out.println("[BluetoothDevice] Get friendly name ...FAIL");
            MyRangeMIDlet.out.println("[BluetoothDevice] Error: " + e.toString());
            fName = FNAME_ERROR;
        }
        setDeviceName(fName);
        
        // Get device bluetooth mac address
        String deviceAddress = "";
        try {
            deviceAddress = btDevice.getBluetoothAddress().toLowerCase();
        }
        catch (Exception e) {
            MyRangeMIDlet.out.println("[BluetoothDevice] Get bluetooth address ...FAIL");
            throw e;
        }
        setBluetoothMac(deviceAddress);

        // Get device code
        deviceCode = cod.getMajorDeviceClass() + cod.getMinorDeviceClass();
    }

    public RemoteDevice getRemoteDevice() { return btDevice; }
    private DeviceClass getDeviceClass() { return cod; }
    public int getDeviceCode() { return deviceCode; }
    public String getDeviceName() { return deviceName; }
    public String getBluetoothMac() { return bluetoothMac; }

    private void setRemoteDevice(RemoteDevice btDevice) {
        if (btDevice != null) this.btDevice = btDevice;
    }
    private void setDeviceClass(DeviceClass cod) {
        if (cod != null) this.cod = cod;
    }
    private void setDeviceCode(int deviceCode) {
        this.deviceCode = deviceCode;
    }
    private void setDeviceName(String deviceName) {
        if (deviceName != null) this.deviceName = deviceName;
    }
    private void setBluetoothMac(String bluetoothMac) {
        if (bluetoothMac != null) this.bluetoothMac = bluetoothMac;
    }

}
