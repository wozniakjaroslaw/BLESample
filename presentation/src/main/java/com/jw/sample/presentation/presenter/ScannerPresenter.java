package com.jw.sample.presentation.presenter;

import com.jw.sample.bluetooth.inteface.Device;
import com.jw.sample.bluetooth.inteface.DeviceScanner;
import com.jw.sample.bluetooth.inteface.DeviceScannerFactory;
import com.jw.sample.presentation.view.ScannerView;

import java.util.List;

import rx.Subscriber;

public class ScannerPresenter {

    private ScannerView scannerView;
    private DeviceScanner deviceScanner;
    private List<Device> devices;
    private Subscriber<List<Device>> subscriber;
    private boolean isLocationPermissionGranted;

    public ScannerPresenter(ScannerView scannerView, DeviceScannerFactory deviceScannerFactory, boolean isLocationPermissionGranted){
        this.scannerView = scannerView;
        deviceScanner = deviceScannerFactory.getDeviceScanner();
        subscriber = new ScannerSubscriber();
        this.isLocationPermissionGranted= isLocationPermissionGranted;
        init();
    }

    private void init() {
        if (isLocationPermissionGranted){
            deviceScanner.startScan().subscribe(subscriber);
        }else{
            scannerView.requestLocationPermission();
        }

    }

    public void onDestroy(){
        subscriber.unsubscribe();
        deviceScanner.stopScan();
    }

    public void onLocationPermissionGranted() {
        deviceScanner.startScan().subscribe(subscriber);
    }

    class ScannerSubscriber extends Subscriber<List<Device>>{

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            scannerView.showErrorMessage(e.getMessage());
        }


        @Override
        public void onNext(List<Device> deviceList) {
            if (devices == null){
                devices = deviceList;
                scannerView.setupList(devices);
            }else{
                scannerView.refreshList();
            }
        }
    }


}
