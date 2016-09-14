package com.jw.sample.bluetooth.implementation;

import android.bluetooth.BluetoothDevice;

import com.jw.sample.bluetooth.inteface.Device;
import com.jw.sample.bluetooth.inteface.DeviceContainer;

import java.util.ArrayList;
import java.util.List;

public class DistinctAddressDeviceList implements DeviceContainer {

    List<Device> devicesList;
    DeviceFactory deviceFactory;

    public DistinctAddressDeviceList(DeviceFactory deviceFactory){
        this.deviceFactory = deviceFactory;
    }


    @Override
    public void insetOrUpdateDevice(BluetoothDevice bluetoothDevice, int rssi, byte[] scanData) {
        Device device = findDevice(bluetoothDevice);
        if (device == null){
            getDeviceList().add(deviceFactory.createDevice(bluetoothDevice, rssi, scanData));
        }else{
            device.setRssi(rssi);
        }
    }


    @Override
    public List<Device> getDevices() {
        return getDeviceList();
    }

    private List<Device> getDeviceList() {
        if (devicesList == null) {
            devicesList = new ArrayList<>();
        }
        return devicesList;
    }

    private Device findDevice(BluetoothDevice device) {
        for (Device item : getDeviceList()) {
            if (device.getAddress() != null
                    && item.getAddress() != null
                    && device.getAddress().equals(item.getAddress())) {
                return item;
            }
        }
        return null;
    }

}
