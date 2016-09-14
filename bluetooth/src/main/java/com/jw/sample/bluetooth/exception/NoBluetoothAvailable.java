package com.jw.sample.bluetooth.exception;

public class NoBluetoothAvailable extends RuntimeException {
    public NoBluetoothAvailable() {
        super("Bluetooth not available on this device!");
    }
}
