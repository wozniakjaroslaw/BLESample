package com.jw.sample.presentation;

import android.app.Application;
import android.util.Log;

import com.sensorberg.BackgroundDetector;
import com.sensorberg.SensorbergSdk;
import com.sensorberg.SensorbergSdkEventListener;
import com.sensorberg.sdk.Logger;
import com.sensorberg.sdk.resolver.BeaconEvent;

public class App extends Application {

    private final String API_KEY = "399d908cccbd6249cbdea46c0da758b21d01fa59a513ba52941b90478047d3d5";

    private SensorbergSdk sdk;
    private BackgroundDetector detector;

    static {
        if (BuildConfig.DEBUG){
            Logger.enableVerboseLogging();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sdk = new SensorbergSdk(this, API_KEY);//the context object and your api key.
        sdk.registerEventListener(new SensorbergSdkEventListener() {
            @Override
            public void presentBeaconEvent(BeaconEvent beaconEvent) { //your presentBeaconEvent action.
                Log.i("beaconevent", beaconEvent.getBeaconId().toString());
            }
        });

        detector = new BackgroundDetector(sdk);
        registerActivityLifecycleCallbacks(detector);
    }

    public void setLocationPermissionGranted(int type) {
        sdk.sendLocationFlagToReceiver(type);
    }
}
