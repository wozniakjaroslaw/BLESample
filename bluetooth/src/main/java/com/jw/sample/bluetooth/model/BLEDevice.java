package com.jw.sample.bluetooth.model;

import com.jw.sample.bluetooth.inteface.Device;

public class BLEDevice implements Device {

    private String name;
    private String address;
    private int rssi;
    private boolean isIBeaconCompatible;

    public BLEDevice(String name, String address, int rssi, boolean isIBeaconCompatible) {
        this.name = name;
        this.address = address;
        this.rssi = rssi;
        this.isIBeaconCompatible = isIBeaconCompatible;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public int getRssi() {
        return rssi;
    }

    @Override
    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    @Override
    public boolean isIBeaconCompatible() {
        return isIBeaconCompatible;
    }
}
