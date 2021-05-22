package com.example.smarthome.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.example.smarthome.Adapter.DoorAdapter;
import com.example.smarthome.Model.Door;
import com.example.smarthome.R;
import com.example.smarthome.Topic.DoorTopic;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.example.smarthome.Service.MQTTService;
import com.google.gson.Gson;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

public class DoorActivity extends AppCompatActivity {

    private List<Door> listDoor ;
    private RecyclerView myrecyclerView;
    DoorAdapter doorAdapter;
    Toolbar toolbar;
    TextView doorBack ;
    TextView doorAdd ;
    MQTTService doorMqtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);


        init();
        addEvents();

        myrecyclerView.setAdapter(doorAdapter);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        configMqtt();

    }

    public void addEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        doorAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(DoorActivity.this, AddDoorActivity.class);
                Log.d("Add", "----------------------------------To add -------------------------------");
                startActivity(addIntent);
            }
        });
    }

    public void init() {
        listDoor = new ArrayList<Door>();
        listDoor.add(new Door("Main Door", "md", "0","/bbc-door"));
        listDoor.add(new Door("Main Window", "mw", "0", "/bbc-door"));
        listDoor.add(new Door("Side Window", "mw", "0", "/bbc-door"));
        listDoor.add(new Door("Side Door", "md", "0", "/bbc-door"));

        myrecyclerView = findViewById(R.id.door_recycler_view);
        doorAdapter = new DoorAdapter(this, listDoor);
        doorAdd = findViewById(R.id.textAddDoorImg);
        toolbar = findViewById(R.id.doorToolbar);

        doorMqtt = new MQTTService(this, "bbc-door");
    }

    public void configMqtt() {
        doorMqtt.setCLientId("door112");
        doorMqtt.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                //doorMqtt.subscribeToTopic("bbc-door");
                Log.d("CONNECT COMPLETE", "Connect complete!");
                return;
            }

            @Override
            public void connectionLost(Throwable cause) {
                return ;
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("MESSSAGE ARRIVED", "Receive latest message " + message.toString() + " from topic "+ topic);

                try {
                    Gson g = new Gson();
                    DoorTopic doorTopic = g.fromJson(message.toString(), DoorTopic.class);

                    String status = doorTopic.getValue();
                    int position = Integer.parseInt(doorTopic.getId());

                    listDoor.get(position).setDoorStatus(status);
                    doorAdapter.setListDoor(listDoor);
                    myrecyclerView.setAdapter(doorAdapter);
                    myrecyclerView.setLayoutManager(new LinearLayoutManager(DoorActivity.this));

                } catch (Exception e) {
                    Log.e("JSON MAP ERROR", "An error occurs while mapping json");
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                return;
            }
        });
    }
}