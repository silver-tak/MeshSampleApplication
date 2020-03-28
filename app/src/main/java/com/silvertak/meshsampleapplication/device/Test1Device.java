package com.silvertak.meshsampleapplication.device;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

public class Test1Device extends BaseDevice {

    public static Test1Device singleton;

    public static Test1Device getSingleton()
    {
        if(singleton != null) return singleton;
        else return null;
    }

    public Test1Device(Activity activity, BluetoothDevice device)
    {
        super(activity, device);
        Test1Device.singleton = this;
    }
}
