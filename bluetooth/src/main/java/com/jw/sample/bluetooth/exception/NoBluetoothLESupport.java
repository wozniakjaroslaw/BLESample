package com.jw.sample.bluetooth.exception;

public class NoBluetoothLESupport extends RuntimeException {
    public NoBluetoothLESupport() {
        super("Bluetooth Low Energy not supported on this device!");
    }
}
