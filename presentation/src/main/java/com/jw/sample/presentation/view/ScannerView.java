package com.jw.sample.presentation.view;

import com.jw.sample.bluetooth.inteface.Device;

import java.util.List;

public interface ScannerView {
    void setupList(List<Device> deviceList);
    void refreshList();
    void showErrorMessage(String message);

    void requestLocationPermission();
}
