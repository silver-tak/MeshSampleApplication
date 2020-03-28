package com.silvertak.meshsampleapplication.device;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

public class Test2Device extends BaseDevice {

    public static Test2Device singleton;

    public static Test2Device getSingleton()
    {
        if(singleton != null) return singleton;
        else return null;
    }

    public Test2Device(Activity activity, BluetoothDevice device)
    {
        super(activity, device);
        Test2Device.singleton = this;
    }
}
