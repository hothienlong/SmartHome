package com.example.smarthome.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.smarthome.Adaper.LightAdapter;
import com.example.smarthome.Adaper.ListRoomBigAdapter;
import com.example.smarthome.Model.Light;
import com.example.smarthome.Model.Room;
import com.example.smarthome.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class LightActivity extends AppCompatActivity implements LightAdapter.LightClickListener {

    Toolbar toolbar;
    RecyclerView recyclerViewLight;
    TextView tvDevicesOn;
    ArrayList<Light> lstLight = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        addControls();
        init();
        addEvents();
    }

    private void addEvents() {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true); // icon
        actionbar.setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        // Recycler view light -----------
        lstLight = new ArrayList<>();
        lstLight.add(new Light("1", "Đèn trần 1", true));
        lstLight.add(new Light("2", "Đèn trần 2", false));
        lstLight.add(new Light("3", "Đèn học", false));
        lstLight.add(new Light("4", "Đèn đầu giường", true));

        lstLight.add(new Light("5", "Đèn trần 1", true));
        lstLight.add(new Light("6", "Đèn trần 2", true));
        lstLight.add(new Light("7", "Đèn học", false));
        lstLight.add(new Light("8", "Đèn đầu giường", true));

        lstLight.add(new Light("9", "Đèn trần 1", false));
        lstLight.add(new Light("10", "Đèn trần 2", false));
        lstLight.add(new Light("11", "Đèn học", true));
        lstLight.add(new Light("12", "Đèn đầu giường", true));

        // tạo adapter
        LightAdapter lightAdapter = new LightAdapter(lstLight);
        // performance
        recyclerViewLight.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewLight.setAdapter(lightAdapter);

        onLightClick();
        lightAdapter.setmLightClickListener(this);
    }

    private void addControls() {
        recyclerViewLight = findViewById(R.id.recyclerViewLight);
        toolbar = findViewById(R.id.lightToolbar);
        tvDevicesOn = findViewById(R.id.tvDevicesOn);
    }

    @Override
    public void onLightClick() {
        int counter = 0;
        for (Light light: lstLight) {
            if (light.getStatus().equals(true))
                counter++;
        }

        tvDevicesOn.setText("Devices on: " + counter + "/" + lstLight.size());
    }

}