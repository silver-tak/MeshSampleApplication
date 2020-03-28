package com.silvertak.meshsampleapplication.ble;

import android.bluetooth.BluetoothDevice;

public interface ScanListener {
    void onScanUpdate(BluetoothDevice device);
    void onScanSuccess();
    void onScanTimeout();
}
