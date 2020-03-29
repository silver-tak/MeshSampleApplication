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
import com.silvertak.meshsampleapplication.ui.base.BaseFragment;

public class MeshFragment extends BaseFragment implements View.OnClickListener{

    private FragmentMeshBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mesh, container, false);
        mBinding.setMeshFragment(this);
        setHasOptionsMenu(true);
        init();

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
}