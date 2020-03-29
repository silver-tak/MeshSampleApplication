package com.silvertak.meshsampleapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.silvertak.meshsampleapplication.define.BroadcastDefine;
import com.silvertak.meshsampleapplication.device.Test1Device;
import com.silvertak.meshsampleapplication.device.Test2Device;
import com.silvertak.meshsampleapplication.device.Test3Device;
import com.silvertak.meshsampleapplication.device.ZacharyDevice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_scan)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(BroadcastDefine.CONNECT_SUCCESS))
                {
                    //navView.setSelectedItemId(R.id.navigation_home);
                    String deviceName = intent.getExtras().getString("deviceName");
                    String deviceAddress = intent.getExtras().getString("deviceAddress");
                    String strMsg = deviceName + ", " + getString(R.string.connect_success);
                    Toast.makeText(MainActivity.this, strMsg, Toast.LENGTH_SHORT).show();
                }
                else if(intent.getAction().equals(BroadcastDefine.CONNECT_FAIL))
                {
                    //navView.setSelectedItemId(R.id.navigation_home);
                    String exception = intent.getExtras().getString("e");
                    Toast.makeText(MainActivity.this, "Connect Fail, " + exception, Toast.LENGTH_SHORT).show();
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastDefine.CONNECT_SUCCESS);
        intentFilter.addAction(BroadcastDefine.CONNECT_FAIL);

        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onBackPressed() {
        if(ZacharyDevice.getSingleton() != null)
            ZacharyDevice.getSingleton().disconnect();
        if(Test1Device.getSingleton() != null)
            Test1Device.getSingleton().disconnect();
        if(Test2Device.getSingleton() != null)
            Test2Device.getSingleton().disconnect();
        if(Test3Device.getSingleton() != null)
            Test3Device.getSingleton().disconnect();

        unregisterReceiver(broadcastReceiver);
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


}
