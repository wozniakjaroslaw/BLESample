package com.jw.sample.bluetooth.implementation;

import android.content.Context;
import android.os.Build;

import com.jw.sample.bluetooth.inteface.DeviceContainer;
import com.jw.sample.bluetooth.inteface.DeviceScanner;
import com.jw.sample.bluetooth.inteface.DeviceScannerFactory;

public class AndroidSDKVersionScannerFactory implements DeviceScannerFactory {

    private Context context;

    public AndroidSDKVersionScannerFactory(Context context){
        this.context = context;
    }

    @Override
    public DeviceScanner getDeviceScanner() {
        DeviceContainer deviceContainer = new DistinctAddressDeviceList(new DeviceFactory());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            return new BelowAPI21Scanner(context,deviceContainer);
        }else{
            return new API21AndAboveScanner(context,deviceContainer);
        }
    }
}
