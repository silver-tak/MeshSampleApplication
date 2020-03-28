package com.silvertak.meshsampleapplication.device;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;

import com.silvertak.meshsampleapplication.ble.SerialListener;
import com.silvertak.meshsampleapplication.ble.SerialSocket;

import java.io.IOException;

public class BaseDevice {

    protected Activity activity;
    protected BluetoothDevice device;
    protected String TAG = "BleDevice";

    protected SerialSocket socket;

    public BaseDevice(Activity activity, BluetoothDevice device)
    {
        this.activity = activity;
        this.device = device;
    }

    public void lightOn()
    {
        try
        {
            if(socket != null) socket.write("1".getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void lightOff()
    {
        try
        {
            if(socket != null) socket.write("0".getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void connect(SerialListener sl)
    {
        try
        {
            socket = new SerialSocket();
            socket.connect(activity, sl, device);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        socket.disconnect();
    }

    public boolean isConnected()
    {
        if(socket != null) return  socket.isConnected(device);
        else return false;
    }

    protected void dialogShow(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Lite BLE");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
