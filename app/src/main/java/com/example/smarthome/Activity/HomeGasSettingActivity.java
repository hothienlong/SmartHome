package com.example.smarthome.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.example.smarthome.Fragment.GasFragment;
import com.example.smarthome.Fragment.HomeFragment;
import com.example.smarthome.R;
import com.example.smarthome.Fragment.SettingFragment;
import com.example.smarthome.Service.MQTTServiceBBC1;
import com.example.smarthome.Service.NotiService;
import com.example.smarthome.Service.MQTTServiceBBC;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.jetbrains.annotations.NotNull;

public class HomeGasSettingActivity extends AppCompatActivity {

    String fullName, address, tel;

    MQTTServiceBBC mqttServiceBBC;
    MQTTServiceBBC1 mqttServiceBBC1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_gas_setting);
        // Connect Mqtt service --> connect trước khi khởi tạo fragment là ko bị lỗi
        mqttServiceBBC = MQTTServiceBBC.getInstance(this);
        mqttServiceBBC1 = MQTTServiceBBC1.getInstance(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fullName = extras.getString("full_name");
            address = extras.getString("address");
            tel = extras.getString("tel");
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, NotiService.class);
        startService(intent);


    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_gas:
                            selectedFragment = new GasFragment();
                            break;
                        case R.id.nav_setting:
                            Bundle bundle = new Bundle();
                            bundle.putString("full_name", fullName);
                            bundle.putString("address", address);
                            bundle.putString("tel", tel);

                            selectedFragment = new SettingFragment();
                            selectedFragment.setArguments(bundle);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "Main destroy");
        try {
            mqttServiceBBC.disconnect();
            mqttServiceBBC1.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

