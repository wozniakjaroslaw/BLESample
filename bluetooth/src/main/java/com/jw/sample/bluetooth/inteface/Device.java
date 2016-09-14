package com.jw.sample.bluetooth.inteface;

public interface Device {
    String getName();
    String getAddress();
    int getRssi();
    void setRssi(int rssi);
    boolean isIBeaconCompatible();

}
