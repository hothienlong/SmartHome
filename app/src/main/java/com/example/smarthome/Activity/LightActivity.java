package com.example.smarthome.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.LightAdapter;
import com.example.smarthome.Model.Light;
import com.example.smarthome.R;
import com.example.smarthome.Service.MQTTService;
import com.example.smarthome.Topic.RelayTopic;
import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

public class LightActivity extends AppCompatActivity implements LightAdapter.LightClickListener {

    Toolbar toolbar;
    RecyclerView recyclerViewLight;
    TextView tvDevicesOn;
    ArrayList<Light> lstLight = new ArrayList<>();
    ImageView imgAddLight;
    LightAdapter.LightClickListener lightClickListener = this;

    MQTTService mqttService;

    String topic = "bbc-led";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        addControls();
        init();
        // connect & subscribe
        startMqtt();
        addEvents();

    }

    // subcriber topic feeds/relay
    private void startMqtt() {
        mqttService = new MQTTService(this, topic);
        mqttService.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", "connected!!!!!");
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                // get status of light
                Log.d("BBBBBBBBBBBBBBBBb", mqttMessage.toString());
//                txtOut.setText(mqttMessage.toString());
                Gson g = new Gson();
                RelayTopic relayTopic = g.fromJson(mqttMessage.toString(), RelayTopic.class);
                Log.d("relayTopic", relayTopic.getId() + " " + relayTopic.getData());

                // update my database
                lstLight.get(Integer.parseInt(relayTopic.getId()))
                        .setStatus(relayTopic.getData().equals("1"));

//                for(int i = 0; i < lstLight.size(); i++){
//                    Log.d("MMM", lstLight.get(i).toString());
//                }

                // tạo adapter
                LightAdapter lightAdapter = new LightAdapter(lstLight);
                // performance
                recyclerViewLight.setHasFixedSize(true);
                // set adapter cho Recycler View
                recyclerViewLight.setAdapter(lightAdapter);

                onLightClick();
                lightAdapter.setmLightClickListener(lightClickListener);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
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

        imgAddLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LightActivity.this, AddLightActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        // Recycler view light -----------
        lstLight = new ArrayList<>();
        lstLight.add(new Light("0", "Đèn trần 1", false));
        lstLight.add(new Light("1", "Đèn trần 2", true));
        lstLight.add(new Light("2", "Đèn học", true));
        lstLight.add(new Light("3", "Đèn đầu giường", false));

//        lstLight.add(new Light("5", "Đèn trần 1", true));
//        lstLight.add(new Light("6", "Đèn trần 2", true));
//        lstLight.add(new Light("7", "Đèn học", false));
//        lstLight.add(new Light("8", "Đèn đầu giường", true));
//
//        lstLight.add(new Light("9", "Đèn trần 1", false));
//        lstLight.add(new Light("10", "Đèn trần 2", false));
//        lstLight.add(new Light("11", "Đèn học", true));
//        lstLight.add(new Light("12", "Đèn đầu giường", true));

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
        imgAddLight = findViewById(R.id.imgAddLight);
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