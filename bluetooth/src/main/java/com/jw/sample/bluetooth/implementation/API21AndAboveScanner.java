package com.jw.sample.bluetooth.implementation;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;

import com.jw.sample.bluetooth.inteface.DeviceContainer;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class API21AndAboveScanner extends BLEScanner {
    ScanCallback scanCallback;

    public API21AndAboveScanner(Context context, DeviceContainer deviceContainer) {
        super(context, deviceContainer);
    }

    @Override
    void runScanner(BluetoothAdapter bluetoothAdapter) {
        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
               ScanRecord scanRecord =  result.getScanRecord();
                onDeviceScanned(result.getDevice(), result.getRssi(), scanRecord != null ? scanRecord.getBytes() : null);
            }
        };

        BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        bluetoothLeScanner.startScan(scanCallback);
    }

    @Override
    void stopScanner(BluetoothAdapter bluetoothAdapter) {
        if (scanCallback !=null){
            bluetoothAdapter.getBluetoothLeScanner().stopScan(scanCallback);
        }
    }
}
