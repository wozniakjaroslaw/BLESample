package com.jw.sample.bluetooth.exception;

public class BluetoothDisabled extends RuntimeException {
    public BluetoothDisabled() {
        super("Bluetooth disabled on this device!");
    }
}
