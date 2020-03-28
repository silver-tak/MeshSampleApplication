package com.silvertak.meshsampleapplication.ui.ble;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.silvertak.meshsampleapplication.R;
import com.silvertak.meshsampleapplication.databinding.FragmentHomeBinding;
import com.silvertak.meshsampleapplication.define.MacAddress;
import com.silvertak.meshsampleapplication.device.Test1Device;
import com.silvertak.meshsampleapplication.device.Test2Device;
import com.silvertak.meshsampleapplication.device.Test3Device;
import com.silvertak.meshsampleapplication.device.ZacharyDevice;

import java.util.List;

public class BleFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding mBinding;
    private ZacharyDevice zacharyDevice;
    private Test1Device test1Device;
    private Test2Device test2Device;
    private Test3Device test3Device;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final TedPermission.Builder tedPermission = TedPermission.with(getContext())
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                    }
                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                    }
                })
                .setDeniedTitle("권한 거부")
                .setDeniedMessage("권한을 허용하지 않으시면 이용하실 수 없습니다")
                .setDeniedCloseButtonText("닫기")
                .setPermissions(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        tedPermission.check();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mBinding.setBleFragment(this);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        zacharyDevice = ZacharyDevice.getSingleton();
        test1Device = Test1Device.getSingleton();
        test2Device = Test2Device.getSingleton();
        test3Device = Test3Device.getSingleton();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.zacharyOnButton :
                if(zacharyDevice!= null) zacharyDevice.lightOn(); break;
            case R.id.test1OnButton :
                if(test1Device!= null) test1Device.lightOn(); break;
            case R.id.test2OnButton :
                if(test2Device!= null) test2Device.lightOn(); break;
            case R.id.test3OnButton :
                if(test3Device!= null) test3Device.lightOn(); break;
            case R.id.zacharyOffButton :
                if(zacharyDevice!= null) zacharyDevice.lightOff(); break;
            case R.id.test1OffButton :
                if(test1Device!= null) test1Device.lightOff(); break;
            case R.id.test2OffButton :
                if(test2Device!= null) test2Device.lightOff(); break;
            case R.id.test3OffButton :
                if(test3Device!= null) test3Device.lightOff(); break;
        }
    }
}