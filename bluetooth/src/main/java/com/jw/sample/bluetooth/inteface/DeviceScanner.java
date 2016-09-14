package com.jw.sample.bluetooth.inteface;

import java.util.List;

import rx.Observable;

public interface DeviceScanner {
    Observable<List<Device>> startScan();
    void stopScan();
}
