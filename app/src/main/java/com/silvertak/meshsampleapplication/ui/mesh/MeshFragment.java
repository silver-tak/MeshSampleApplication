package com.silvertak.meshsampleapplication.ui.mesh;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.silvertak.meshsampleapplication.R;
import com.silvertak.meshsampleapplication.ble.ScanListener;
import com.silvertak.meshsampleapplication.ble.ScanManager;
import com.silvertak.meshsampleapplication.databinding.FragmentMeshBinding;
import com.silvertak.meshsampleapplication.define.EventDefine;
import com.silvertak.meshsampleapplication.device.Test1Device;
import com.silvertak.meshsampleapplication.device.Test2Device;
import com.silvertak.meshsampleapplication.device.Test3Device;
import com.silvertak.meshsampleapplication.device.ZacharyDevice;

public class MeshFragment extends Fragment implements View.OnClickListener{

    private FragmentMeshBinding mBinding;
    private Handler handler;
    private boolean isLoop = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mesh, container, false);
        mBinding.setMeshFragment(this);
        setHasOptionsMenu(true);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch (message.what)
                {
                    case EventDefine.ZACHARY_LIGHT_ON :
                        if(ZacharyDevice.getSingleton() != null) ZacharyDevice.getSingleton().lightOn();
                        handler.sendEmptyMessageDelayed(EventDefine.TEST1_LIGHT_ON, 500);
                        break;
                    case EventDefine.TEST1_LIGHT_ON:
                        if(Test1Device.getSingleton() != null) Test1Device.getSingleton().lightOn();
                        handler.sendEmptyMessageDelayed(EventDefine.TEST2_LIGHT_ON, 500);
                        break;
                    case EventDefine.TEST2_LIGHT_ON:
                        if(Test2Device.getSingleton() != null) Test2Device.getSingleton().lightOn();
                        handler.sendEmptyMessageDelayed(EventDefine.TEST3_LIGHT_ON, 500);
                        break;
                    case EventDefine.TEST3_LIGHT_ON:
                        if(Test3Device.getSingleton() != null) Test3Device.getSingleton().lightOn();
                        if(isLoop) { isLoop = false; handler.sendEmptyMessageDelayed(EventDefine.ZACHARY_LIGHT_OFF, 500); }
                        break;

                    case EventDefine.ZACHARY_LIGHT_OFF :
                        if(ZacharyDevice.getSingleton() != null) ZacharyDevice.getSingleton().lightOff();
                        handler.sendEmptyMessageDelayed(EventDefine.TEST1_LIGHT_OFF, 500);
                        break;
                    case EventDefine.TEST1_LIGHT_OFF:
                        if(Test1Device.getSingleton() != null) Test1Device.getSingleton().lightOff();
                        handler.sendEmptyMessageDelayed(EventDefine.TEST2_LIGHT_OFF, 500);
                        break;
                    case EventDefine.TEST2_LIGHT_OFF:
                        if(Test2Device.getSingleton() != null) Test2Device.getSingleton().lightOff();
                        handler.sendEmptyMessageDelayed(EventDefine.TEST3_LIGHT_OFF, 500);
                        break;
                    case EventDefine.TEST3_LIGHT_OFF:
                        if(Test3Device.getSingleton() != null) Test3Device.getSingleton().lightOff();
                        break;

                    case EventDefine.ZACHARY_DISCONNECT :
                        if(ZacharyDevice.getSingleton() != null) ZacharyDevice.getSingleton().disconnect();
                        handler.sendEmptyMessageDelayed(EventDefine.TEST1_DISCONNECT, 500); break;
                    case EventDefine.TEST1_DISCONNECT :
                        if(Test1Device.getSingleton() != null) Test1Device.getSingleton().disconnect();
                        handler.sendEmptyMessageDelayed(EventDefine.TEST2_DISCONNECT, 500); break;
                    case EventDefine.TEST2_DISCONNECT :
                        if(Test2Device.getSingleton() != null) Test2Device.getSingleton().disconnect();
                        handler.sendEmptyMessageDelayed(EventDefine.TEST3_DISCONNECT, 500); break;
                    case EventDefine.TEST3_DISCONNECT :
                        if(Test3Device.getSingleton() != null) Test3Device.getSingleton().disconnect(); break;
                }
                return true;
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_devices, menu);
        menu.findItem(R.id.ble_disconnect).setVisible(true);
        menu.findItem(R.id.bt_settings).setVisible(false);
        menu.findItem(R.id.ble_scan).setVisible(false);
        menu.findItem(R.id.ble_scan).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ble_disconnect) {
            handler.sendEmptyMessageDelayed(EventDefine.ZACHARY_DISCONNECT, 100);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.prepareMeshButton :
                ScanManager scanManager = new ScanManager(getActivity());
                scanManager.scanLe();
                break;
            case R.id.meshOnButton : relayLightOn();
                break;
            case R.id.meshOffButton : relayLightOff();
                break;
            case R.id.relayButton : relayLoop(); break;
        }
    }

    private void relayLightOn() {
        handler.sendEmptyMessage(EventDefine.ZACHARY_LIGHT_ON);
    }

    private void relayLightOff() {
        handler.sendEmptyMessage(EventDefine.ZACHARY_LIGHT_OFF);
    }

    private void relayLoop()
    {
        isLoop = true;
        relayLightOn();
    }
}