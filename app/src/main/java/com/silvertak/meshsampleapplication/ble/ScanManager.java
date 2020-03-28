package com.silvertak.meshsampleapplication.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;

import com.silvertak.meshsampleapplication.define.BroadcastDefine;
import com.silvertak.meshsampleapplication.define.MacAddress;
import com.silvertak.meshsampleapplication.device.Test1Device;
import com.silvertak.meshsampleapplication.device.Test2Device;
import com.silvertak.meshsampleapplication.device.Test3Device;
import com.silvertak.meshsampleapplication.device.ZacharyDevice;

import java.util.ArrayList;
import java.util.Collections;

public class ScanManager {

    private Activity activity;

    private enum ScanState { NONE, LESCAN, DISCOVERY, DISCOVERY_FINISHED }
    private ScanState                       scanState = ScanState.NONE;
    private static final long               LESCAN_PERIOD = 10000; // similar to bluetoothAdapter.startDiscovery
    private Handler leScanStopHandler = new Handler();
    private BluetoothAdapter.LeScanCallback leScanCallback;
    private BroadcastReceiver discoveryBroadcastReceiver;
    private IntentFilter discoveryIntentFilter;

    private BluetoothAdapter bluetoothAdapter;

    public ScanManager(Activity activity)
    {
        this.activity = activity;
        if(activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH))
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void scanLe()
    {
        leScanCallback = (device, rssi, scanRecord) -> {
            if(device != null && activity != null) {
                activity.runOnUiThread(() -> { updateScan(device); });
            }
        };
        discoveryBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if(device.getType() != BluetoothDevice.DEVICE_TYPE_CLASSIC && activity != null) {
                        activity.runOnUiThread(() -> updateScan(device));
                    }
                }
                if(intent.getAction().equals((BluetoothAdapter.ACTION_DISCOVERY_FINISHED))) {
                    scanState = ScanState.DISCOVERY_FINISHED; // don't cancel again
                    stopScan();
                }
            }
        };
        discoveryIntentFilter = new IntentFilter();
        discoveryIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        discoveryIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        leScanStopHandler.postDelayed(this::stopScan, LESCAN_PERIOD);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void[] params) {
                bluetoothAdapter.startLeScan(null, leScanCallback);
                return null;
            }
        }.execute(); // start async to prevent blocking UI, because startLeScan sometimes take some seconds
    }

    private void updateScan(BluetoothDevice device) {
        stopScan();
        SerialListener serialListener = new SerialListener() {
            @Override
            public void onSerialConnect() {
                Log.i("ScanManager", "Connect Success, " + device.getName() + ", " + device.getAddress());
                Bundle args = new Bundle();
                args.putString("deviceName", device.getName());
                args.putString("deviceAddress", device.getAddress());
                Intent intent = new Intent(BroadcastDefine.MESH_CONNECT_SUCCESS);
                intent.putExtras(args);
                activity.sendBroadcast(intent);

                if(Test1Device.getSingleton() == null || Test2Device.getSingleton() == null || Test3Device.getSingleton() == null)
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scanLe();
                        }
                    }, 500);
            }

            @Override
            public void onSerialConnectError(Exception e) {
                Bundle args = new Bundle();
                args.putString("e", e.getMessage());
                Intent intent = new Intent(BroadcastDefine.CONNECT_FAIL);
                intent.putExtras(args);
                activity.sendBroadcast(intent);
                //listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSerialRead(byte[] data) {
            }

            @Override
            public void onSerialIoError(Exception e) {
            }
        };

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*if(device.getAddress().equals(MacAddress.ZACHARY_MAC))
                {
                    ZacharyDevice zacharyDevice = new ZacharyDevice(activity, device);
                    zacharyDevice.connect(serialListener);
                }
                else */if(device.getAddress().equals(MacAddress.TEST_1))
                {
                    Test1Device test1Device = new Test1Device(activity, device);
                    test1Device.connect(serialListener);
                }
                else if(device.getAddress().equals(MacAddress.TEST_2))
                {
                    Test2Device test2Device = new Test2Device(activity, device);
                    test2Device.connect(serialListener);
                }
                else if(device.getAddress().equals(MacAddress.TEST_3))
                {
                    Test3Device test3Device = new Test3Device(activity, device);
                    test3Device.connect(serialListener);
                }
            }
        }, 100);
    }

    private void stopScan() {
        switch(scanState) {
            case LESCAN:
                leScanStopHandler.removeCallbacks(this::stopScan);
                bluetoothAdapter.stopLeScan(leScanCallback);
                break;
            case DISCOVERY:
                bluetoothAdapter.cancelDiscovery();
                break;
            default:
                // already canceled
        }
    }
}
