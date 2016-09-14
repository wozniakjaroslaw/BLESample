package com.jw.sample.bluetooth.implementation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.jw.sample.bluetooth.inteface.DeviceContainer;

public class BelowAPI21Scanner extends BLEScanner {

    BluetoothAdapter.LeScanCallback currentScan;

    public BelowAPI21Scanner(Context context, DeviceContainer deviceContainer) {
        super(context, deviceContainer);
    }

    @Override
    void runScanner(BluetoothAdapter bluetoothAdapter) {
        currentScan = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
                onDeviceScanned(bluetoothDevice, i, bytes);
            }
        };
        bluetoothAdapter.startLeScan(currentScan);
    }

    @Override
    void stopScanner(BluetoothAdapter bluetoothAdapter) {
        if (currentScan != null) {
            bluetoothAdapter.stopLeScan(currentScan);
        }
    }
}
