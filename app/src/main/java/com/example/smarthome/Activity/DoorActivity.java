package com.example.smarthome.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.example.smarthome.Adapter.DoorAdapter;
import com.example.smarthome.Model.Door;
import com.example.smarthome.R;
import com.example.smarthome.Service.MQTTDoorService;
import com.example.smarthome.Topic.DoorTopic;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.example.smarthome.Service.MQTTService;
import com.google.gson.Gson;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoorActivity extends AppCompatActivity {

    final String dbRef = "doors";

    private List<Door> listDoor ;
    private RecyclerView myrecyclerView;
    DoorAdapter doorAdapter;
    Toolbar toolbar;
    TextView doorBack ;
    TextView doorAdd ;

    MQTTDoorService doorMqtt;

    DatabaseReference doorRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);


        init();
        addEvents();

        startRender();

        configMqtt();

    }

    public void addEvents() {
        Log.d("DOOR ADD EVENTS", "Start adding event in door activity.");
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
        Log.d("DOOR ADD EVENTS", "Finish adding event in door activity.");
    }

    public void init() {
        Log.d("DOOR ACT. INIT", "Start initializing door activity.");

        listDoor = new ArrayList<Door>();
        listDoor.add(new Door("Main Door", "md", "0","/bbc-door"));
        listDoor.add(new Door("Main Window", "mw", "0", "/bbc-door"));
        listDoor.add(new Door("Side Window", "mw", "0", "/bbc-door"));
        listDoor.add(new Door("Side Door", "md", "0", "/bbc-door"));

        myrecyclerView = findViewById(R.id.door_recycler_view);
        doorAdapter = new DoorAdapter(this, listDoor);
        doorAdd = findViewById(R.id.textAddDoorImg);
        toolbar = findViewById(R.id.doorToolbar);

        doorMqtt = new MQTTDoorService(this, "bbc-door");

        doorRef = FirebaseDatabase.getInstance().getReference(dbRef);
        Log.d("DOOR ACT. INIT", "Finish initializing door activity.");
    }

    public void startRender() {
        Log.d("DOORS RENDER", "Start rendering doors.");
        myrecyclerView.setAdapter(doorAdapter);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(DoorActivity.this));
        Log.d("DOORS RENDER", "Finish rendering doors.");
    }

    public void configMqtt() {
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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("MESSSAGE ARRIVED", "Receive latest message " + message.toString() + " from topic "+ topic);
                if(!topic.split("/")[2].equals("bbc-door")) {
                    return;
                }
                try {
                    Gson g = new Gson();
                    DoorTopic doorTopic = g.fromJson(message.toString(), DoorTopic.class);

                    String status = doorTopic.getValue();
                    int position = Integer.parseInt(doorTopic.getId());

                    listDoor.get(position).setDoorStatus(status);
                    doorAdapter.setListDoor(listDoor);

                    Map<String, Object> updateData = new HashMap<String, Object>();

//                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss");
//                    String dateTime = LocalDateTime.now().format(myFormatObj);

                    updateData.put(doorTopic.getId(), doorTopic);

                    startRender();

                    Log.d("UPDATE DATA", "Updating data to database - database name " + dbRef);
                    doorRef.updateChildren(updateData);
                    Log.d("UPDATE DATA", "Updated data to database - database name " + dbRef);

                } catch (Exception e) {
                    Log.e("PROCESSING MESSAGE", "An error occurs while processing message.");
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                return;
            }
        });
    }
}