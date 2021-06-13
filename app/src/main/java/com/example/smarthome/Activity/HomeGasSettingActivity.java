package com.example.smarthome.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.smarthome.Fragment.GasFragment;
import com.example.smarthome.Fragment.HomeFragment;
import com.example.smarthome.Fragment.SettingFragment;
import com.example.smarthome.R;
import com.example.smarthome.Service.MQTTService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.jetbrains.annotations.NotNull;

public class HomeGasSettingActivity extends AppCompatActivity {

    String fullName, address, tel;

    MQTTService mqttService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_gas_setting);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fullName = extras.getString("full_name");
            address = extras.getString("address");
            tel = extras.getString("tel");
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        // Connect Mqtt service
        mqttService = MQTTService.getInstance(this);
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
            mqttService.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

