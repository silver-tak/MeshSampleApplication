package com.silvertak.meshsampleapplication.device;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

public class ZacharyDevice extends BaseDevice {

    public static ZacharyDevice singleton;

    public static ZacharyDevice getSingleton()
    {
        if(singleton != null) return singleton;
        else return null;
    }

    public ZacharyDevice(Activity activity, BluetoothDevice device)
    {
        super(activity, device);
        ZacharyDevice.singleton = this;
    }
}
