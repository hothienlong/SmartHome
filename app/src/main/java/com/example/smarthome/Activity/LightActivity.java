package com.example.smarthome.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.LightAdapter;
import com.example.smarthome.Model.Light;
import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.Service.MQTTService;
import com.example.smarthome.SessionManagement;
import com.example.smarthome.Topic.LightRelayMessage;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

public class LightActivity extends AppCompatActivity implements LightAdapter.LightClickListener {

    Toolbar toolbar;
    RecyclerView recyclerViewLight;
    LightAdapter lightAdapter;
    TextView tvDevicesOn;
    ArrayList<Light> lstLight = new ArrayList<>();
    ImageView imgAddLight;
    LightAdapter.LightClickListener lightClickListener = this;

    MQTTService mqttService;
    DatabaseReference reference;

    FloatingActionButton fabTurnOnAllLights, fabTurnOffAllLights;

    String mRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        addControls();

//        reference = FirebaseDatabase.getInstance().getReference("lights");

        init();
        // connect & subscribe
        startMqtt();
        addEvents();

    }

    // subcriber topic feeds/relay
    private void startMqtt() {
        mqttService = new MQTTService(this, getResources().getString(R.string.light_topic));

        mqttService.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w(this.getClass().getName(), "connected");
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                // get status of light
                Log.d(this.getClass().getName(), mqttMessage.toString());
                Gson g = new Gson();
                LightRelayMessage lightRelayMessage = g.fromJson(mqttMessage.toString(), LightRelayMessage.class);
//                Log.d(this.getClass().getName(), lightRelayMessage.getId() + " " + lightRelayMessage.getData());

                // Update view status light on/off
                Boolean lightStatusNew = lightRelayMessage.getData().equals("1");

                for(int i=0; i < lstLight.size(); i++){
                    if(lstLight.get(i).getId().equals(lightRelayMessage.getId())){
                        lstLight.get(i).setStatus(lightStatusNew);
                        break;
                    }
                }


                lightAdapter.notifyDataSetChanged();

                // SetText Devices on
                onLightClick();

                // Update my database status light on/off
                reference.child(lightRelayMessage.getId()).child("status").setValue(lightStatusNew);
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
                intent.putExtra("roomId", mRoomId);
                startActivity(intent);
            }
        });

        // On/Off all lights
        fabTurnOffAllLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightAdapter.turnOffAllLight();
            }
        });

        fabTurnOnAllLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightAdapter.turnOnAllLight();
            }
        });
    }

    private void init() {

        // Recycler view light -----------
//        lstLight = new ArrayList<>();
//        lstLight.add(new Light("0", "Đèn trần 1", false));
//        lstLight.add(new Light("1", "Đèn trần 2", true));
//        lstLight.add(new Light("2", "Đèn học", true));
//        lstLight.add(new Light("3", "Đèn đầu giường", false));

        Intent intent = getIntent();
        if (intent != null) {
            mRoomId = intent.getStringExtra("roomId");

            // get room info
            SessionManagement sessionManagement = SessionManagement.getInstance(this);
            String userJson = sessionManagement.getSession();

            if (userJson != null) {
                Gson gson = new Gson();
                User user = gson.fromJson(userJson, User.class);

                reference = FirebaseDatabase.getInstance()
                        .getReference("users")
                        .child(user.getUsername())
                        .child("house")
                        .child("room")
                        .child(mRoomId)
                        .child("light")
                ;

            }
        }


        // tạo adapter
        lightAdapter = new LightAdapter(lstLight);
        lightAdapter.setmLightClickListener(lightClickListener);
        // performance
        recyclerViewLight.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewLight.setAdapter(lightAdapter);

        //1. SELECT * FROM Lights
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstLight.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Light light = snapshot.getValue(Light.class);
                        Log.d(getClass().getName(), light.toString());
                        lstLight.add(light);
                    }
                    lightAdapter.notifyDataSetChanged();
                }

                // SetText Devices on
                onLightClick();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addControls() {
        recyclerViewLight = findViewById(R.id.recyclerViewLight);
        toolbar = findViewById(R.id.lightToolbar);
        tvDevicesOn = findViewById(R.id.tvDevicesOn);
        imgAddLight = findViewById(R.id.imgAddLight);
        fabTurnOnAllLights = findViewById(R.id.floating_action_turn_on_all_lights);
        fabTurnOffAllLights = findViewById(R.id.floating_action_turn_off_all_lights);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mqttService.disconnect();
    }

}