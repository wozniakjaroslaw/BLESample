package com.jw.sample.bluetooth.inteface;

import android.bluetooth.BluetoothDevice;

import java.util.List;

public interface DeviceContainer {
    void insetOrUpdateDevice(BluetoothDevice bluetoothDevice, int rssi, byte[] scanData);

    List<Device> getDevices();
}
