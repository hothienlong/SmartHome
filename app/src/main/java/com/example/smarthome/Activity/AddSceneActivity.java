package com.example.smarthome.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.smarthome.Adapter.DeviceAdapter;
import com.example.smarthome.Model.Light;
import com.example.smarthome.R;

import java.util.ArrayList;

public class AddSceneActivity extends AppCompatActivity {
    private Context context;
    private ImageView addOnBtn, addOffBtn;

    Toolbar toolbar;

    RecyclerView recyclerViewDevice;

    public static DeviceAdapter deviceAdapter;

    public static ArrayList<Light> lstLightOn = new ArrayList<>();
    public static ArrayList<Light> lstLightOff = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scene);

        toolbar = findViewById(R.id.addSceneToolbar);

        addOnBtn = (ImageView)findViewById(R.id.add_on_button);
        addOffBtn = (ImageView)findViewById(R.id.add_off_button);

        addOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(context, AddOnDeviceActicity.class);
                context.startActivity(intent);
            }
        });

        addOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(context, AddOnDeviceActicity.class);
                context.startActivity(intent);
            }
        });

        addEvents();
        init();
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
        recyclerViewDevice = findViewById(R.id.scene_add_device);

        // tạo adapter
        deviceAdapter = new DeviceAdapter(lstLightOn);
        // performance
        recyclerViewDevice.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewDevice.setAdapter(deviceAdapter);
    }
}