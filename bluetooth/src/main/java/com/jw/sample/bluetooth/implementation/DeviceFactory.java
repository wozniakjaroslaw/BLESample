package com.jw.sample.bluetooth.implementation;

import android.bluetooth.BluetoothDevice;

import com.jw.sample.bluetooth.inteface.Device;
import com.jw.sample.bluetooth.model.BLEDevice;
import com.jw.sample.bluetooth.util.IBeaconUtil;

public class DeviceFactory {
    public Device createDevice(BluetoothDevice bluetoothDevice, int rssi, byte[] scanData) {
        return new BLEDevice(bluetoothDevice.getName(), bluetoothDevice.getAddress(), rssi, IBeaconUtil.isIBeacon(scanData));
    }
}
