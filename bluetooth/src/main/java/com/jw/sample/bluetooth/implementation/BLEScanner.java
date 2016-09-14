package com.jw.sample.bluetooth.implementation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;

import com.jw.sample.bluetooth.exception.BluetoothDisabled;
import com.jw.sample.bluetooth.exception.NoBluetoothAvailable;
import com.jw.sample.bluetooth.exception.NoBluetoothLESupport;
import com.jw.sample.bluetooth.inteface.Device;
import com.jw.sample.bluetooth.inteface.DeviceContainer;
import com.jw.sample.bluetooth.inteface.DeviceScanner;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public abstract class BLEScanner implements DeviceScanner {

    BluetoothAdapter bluetoothAdapter;
    Context context;
    private DeviceContainer deviceContainer;
    private Subscriber<? super List<Device>>  subscriber;

    abstract void runScanner(BluetoothAdapter bluetoothAdapter);
    abstract void stopScanner(BluetoothAdapter bluetoothAdapter);

    public BLEScanner(Context context, DeviceContainer deviceContainer) {
        this.context = context;
        this.deviceContainer = deviceContainer;
        bluetoothAdapter = obtainBluetoothAdapter();
    }

    private BluetoothAdapter obtainBluetoothAdapter() {
        BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        return bluetoothManager.getAdapter();
    }

    @Override
    public Observable<List<Device>> startScan() {
        stopScan();
        return Observable.create(new Observable.OnSubscribe<List<Device>>() {
            @Override
            public void call(final Subscriber<? super List<Device>> subscriber) {
                BLEScanner.this.subscriber = subscriber;

                boolean isBLEAvailable =  checkBluetoothAvailability(subscriber);

                if (isBLEAvailable) {
                    runScanner(bluetoothAdapter);
                }
            }
        });
    }

    protected void onDeviceScanned(BluetoothDevice bluetoothDevice, int i, byte[] bytes){
        deviceContainer.insetOrUpdateDevice(bluetoothDevice, i, bytes);
        subscriber.onNext(deviceContainer.getDevices());
    }

    @Override
    public void stopScan() {
        if (bluetoothAdapter != null){
            stopScanner(bluetoothAdapter);
        }
    }

    private boolean checkBluetoothAvailability(Subscriber<? super List<Device>> subscriber) {
        boolean isAvailable = true;
        try {
            if (bluetoothAdapter == null) {
                throw new NoBluetoothAvailable();
            } else if (!bluetoothAdapter.isEnabled()) {
                throw new BluetoothDisabled();
            } else if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
                throw new NoBluetoothLESupport();
            }
        }catch(Exception e){
            subscriber.onError(e);
            isAvailable = false;
        }

        return isAvailable;
    }

}
