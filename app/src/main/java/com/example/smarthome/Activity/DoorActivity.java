package com.example.smarthome.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.example.smarthome.Adapter.DoorAdapter;
import com.example.smarthome.Data.DoorData;
import com.example.smarthome.Model.Door;
import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.Service.DBUtils;

import com.example.smarthome.Service.MQTTServiceBBC;
import com.example.smarthome.SessionManagement;

import com.example.smarthome.Topic.DoorTopic;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DoorActivity extends AppCompatActivity {

    private String roomId, roomName ;
    private String username;

    private HashMap<String, Door> hashMap ;
    private RecyclerView myrecyclerView;
    DoorAdapter doorAdapter;
    Toolbar toolbar;
    TextView doorTitle ;
    TextView doorAdd ;

    MQTTServiceBBC doorMqtt;

    DatabaseReference doorRef ;

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        doorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //Log.d("SIZE", Integer.toString(Door.initList.size()));
                Log.d("SIZE", Integer.toString(Door.initHash.size()));
                if(snapshot.exists() && Door.initHash.size() == 0 && Door.initList.size()==0) {
                    Log.d("SIZEEEE","EEEEEEE");
                    for (DataSnapshot data : snapshot.getChildren()) {
                        DoorData d = data.getValue(DoorData.class);
                        String key = data.getKey().toString();
                        Door newDoor = new Door(d.getName(), d.getType(), d.getStatus());
                        Door.initHash.put(key, newDoor);
                        Door.initList.add(newDoor);
                    }
                };
                Log.d("SIZEEEEEE", Integer.toString(Door.initList.size()));
                doorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        doorRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if(snapshot.exists()) {
                    DoorData d = snapshot.getValue(DoorData.class);
                    String key = snapshot.getKey().toString();
                    Door newDoor = new Door(d.getName(), d.getType(), d.getStatus());
                    Door oldDoor = Door.initHash.get(key);
                    int index = Door.initList.indexOf(oldDoor);
                    if(index >= 0) {
                        Door.initList.set(index, newDoor);
                        Door.initHash.replace(key, newDoor);
                    }
                    reRender();
                }
            }
            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Log.d("DELETED CHILD", snapshot.getKey() + " " + snapshot.getValue().toString());
                    String key = snapshot.getKey();
                    Door d = Door.initHash.get(key);
                    if(d != null) {
                        Toast.makeText(getContext(), d.getDoorName() + " has just been removed!", Toast.LENGTH_LONG)
                                .show();
                    }
                    int index = Door.initList.indexOf(d);
                    if(index >= 0) {
                        Door.initList.remove(index);
                        Door.initHash.remove(key);
                    }
                }
//                reRender();
                doorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        Log.d("DOOR ADD EVENTS", "Finish adding event in door activity.");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void init() {
        Log.d("DOOR ACT. INIT", "Start initializing door activity.");

        hashMap = Door.initHash;

        if(Door.initList.size() != 0) {
            Door.initList.clear();
        }
        if(Door.initHash.size() != 0) {
            Door.initHash.clear();
        }

        // get username
        SessionManagement sessionManagement = SessionManagement.getInstance(getContext());
        User user = new Gson().fromJson(sessionManagement.getSession(), User.class);
        username = user.getUsername();
        Log.d("USERNAME", username);

        // get room id and room name
        Intent intent = getIntent();
        roomId = intent.getStringExtra("roomId");
        roomName = intent.getStringExtra("roomName");
        Log.d("roomId", roomId);
        Log.d("roomId", roomName);

        // set database path reference
        DBUtils.setDbPath("users/"+username+"/house/room/"+roomId+"/door/");
        Log.d("USEERRRRR", user.getUsername());
        doorRef = DBUtils.getRef();
        Log.d("DDDDDDDDDDDD", doorRef.getKey().toString());

        //
        myrecyclerView = findViewById(R.id.door_recycler_view);
        doorTitle = findViewById(R.id.doorToolbarTitle);

        // set room name title for door
        doorTitle.setText("Door (" + roomName + ")");

        // set adapter
        doorAdapter = new DoorAdapter(this, Door.initList);
        doorAdd = findViewById(R.id.textAddDoorImg);
        toolbar = findViewById(R.id.doorToolbar);

//        doorMqtt = new MQTTServiceBBC(this, Door.topic);
        doorMqtt = MQTTServiceBBC.getInstance(this);

        Log.d("DOOR ACT. INIT", "Finish initializing door activity.");
    }

    private Context getContext() {
        return this;
    }

    public void startRender() {
        Log.d("DOORS RENDER", "Start rendering doors.");
        myrecyclerView.setAdapter(doorAdapter);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(DoorActivity.this));
        Log.d("DOORS RENDER", "Finish rendering doors.");
    }

    public void reRender() {
        doorAdapter.notifyDataSetChanged();
    }

    public void configMqtt() {
        doorMqtt.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
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
                if(!topic.split("/")[2].equals(getResources().getString(R.string.door_topic))) {
                    return;
                }
                try {
                    Gson g = new Gson();
                    DoorTopic doorTopic = g.fromJson(message.toString(), DoorTopic.class);

                    String key = doorTopic.getId();

                    // initilize door parameters

                    String name = ((Door)hashMap.get(key)).getDoorName();
                    Boolean status = doorTopic.getData().equals("1") ? false : true;
                    String type = ((Door)hashMap.get(key)).getDoorType();

                    // notify changed
                    String changedMess = name + " has just " + (status ? "opened!" : "closed!");
                    Toast.makeText(getContext(), changedMess, Toast.LENGTH_LONG).show();

                    // update door status in local list
                    ((Door)hashMap.get(key)).setDoorStatus(status);

                    // initialize door data to update to database
                    DoorData data = new DoorData(name, status, type);

                    Map<String, Object> updateData = new HashMap<String, Object>();

//                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss");
//                    String dateTime = LocalDateTime.now().format(myFormatObj);

                    updateData.put(doorTopic.getId(), data);
                    //startRender();
                    doorAdapter.notifyDataSetChanged();

                    DBUtils.updateChild(updateData);

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