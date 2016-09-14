package com.jw.sample.presentation.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jw.sample.bluetooth.inteface.Device;
import com.jw.sample.presentation.R;

import java.util.List;

public class ScannerRecyclerAdapter extends RecyclerView.Adapter<ScannerRecyclerAdapter.DeviceViewHolder> {

    List<Device> deviceList;

    public ScannerRecyclerAdapter(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        holder.address.setText(deviceList.get(position).getAddress());
        holder.idevice.setText(deviceList.get(position).isIBeaconCompatible() ? "IBeacon" : "Unknown Beacon");
        String name = deviceList.get(position).getName();
        holder.name.setText(name != null ? name : "Name N/A");
        holder.rssi.setText(String.valueOf(deviceList.get(position).getRssi()));
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView address;
        TextView rssi;
        TextView idevice;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.deviceName);
            address = (TextView) itemView.findViewById(R.id.deviceAddress);
            rssi = (TextView) itemView.findViewById(R.id.deviceRssi);
            idevice = (TextView) itemView.findViewById(R.id.ibeaconDevice);
        }
    }

}
