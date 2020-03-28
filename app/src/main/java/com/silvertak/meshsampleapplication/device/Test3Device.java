package com.silvertak.meshsampleapplication.device;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

public class Test3Device extends BaseDevice {

    public static Test3Device singleton;

    public static Test3Device getSingleton()
    {
        if(singleton != null) return singleton;
        else return null;
    }

    public Test3Device(Activity activity, BluetoothDevice device)
    {
        super(activity, device);
        Test3Device.singleton = this;
    }
}
