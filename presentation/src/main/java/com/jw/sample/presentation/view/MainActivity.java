package com.jw.sample.presentation.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jw.sample.bluetooth.implementation.AndroidSDKVersionScannerFactory;
import com.jw.sample.bluetooth.inteface.Device;
import com.jw.sample.presentation.App;
import com.jw.sample.presentation.R;
import com.jw.sample.presentation.presenter.ScannerPresenter;
import com.sensorberg.sdk.SensorbergServiceMessage;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ScannerView {

    private static final int MY_PERMISSION_REQUEST_LOCATION_SERVICES = 1;
    ScannerPresenter scannerPresenter;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        scannerPresenter = new ScannerPresenter(this, new AndroidSDKVersionScannerFactory(this), isLocationPermissionGranted());
    }

    @Override
    public void setupList(List<Device> deviceList) {
        adapter = new ScannerRecyclerAdapter(deviceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void refreshList() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        };
        recyclerView.post(runnable);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Functionality limited");
            builder.setMessage("Since location access has not been granted, " +
                    "this app will not be able to discover beacons when in the background.");
            builder.setPositiveButton(android.R.string.ok, null);

            if (Build.VERSION.SDK_INT >= 17) {
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSION_REQUEST_LOCATION_SERVICES);
                    }

                });
            }

            builder.show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_LOCATION_SERVICES);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerPresenter.onDestroy();
    }

    private boolean isLocationPermissionGranted(){
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_LOCATION_SERVICES: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ((App) getApplication()).setLocationPermissionGranted(SensorbergServiceMessage.MSG_LOCATION_SET);
                    scannerPresenter.onLocationPermissionGranted();
                } else {
                    ((App) getApplication()).setLocationPermissionGranted(SensorbergServiceMessage.MSG_LOCATION_NOT_SET_WHEN_NEEDED);
                }

            }
        }
    }
}
