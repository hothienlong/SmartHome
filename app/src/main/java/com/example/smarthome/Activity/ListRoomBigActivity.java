package com.example.smarthome.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.ListRoomBigAdapter;
import com.example.smarthome.Model.Light;
import com.example.smarthome.Model.Room;
import com.example.smarthome.Model.RoomBigItem;
import com.example.smarthome.Model.Scene;
import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.SessionManagement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListRoomBigActivity extends AppCompatActivity {

    DatabaseReference reference;
//    ArrayList<Room> lstRoom = new ArrayList<>();
    ArrayList<RoomBigItem> lstRoom = new ArrayList<>();
//    ArrayList<String> lstRoomId = new ArrayList<>();

    RecyclerView recyclerViewRoom;
    ListRoomBigAdapter listRoomAdapter;
    Toolbar toolbar;

    ImageView imgAddRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room_big);
        addControls();
    }

    // khi activity khong co catch intent moi dung finish -> onStart
    // neu ko thi mat intent
    @Override
    protected void onStart() {
        super.onStart();

        // update list rooms after add new data
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

        imgAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListRoomBigActivity.this, AddRoomActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        // Recycler view room -----------

//        ArrayList<Room> lstRoom = new ArrayList<>();
//        lstRoom.add(new Room("Bedroom", null, true));
//        lstRoom.add(new Room("Living room", null, false));
//        lstRoom.add(new Room("Bath room", null, true));
//        lstRoom.add(new Room("Kitchen", null, false));


        // tạo adapter
        listRoomAdapter = new ListRoomBigAdapter(lstRoom);
        // performance
        recyclerViewRoom.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewRoom.setAdapter(listRoomAdapter);


        SessionManagement sessionManagement = SessionManagement.getInstance(this);
        String userJson = sessionManagement.getSession();

        if(userJson != null) {
            Gson gson = new Gson();
            User user = gson.fromJson(userJson, User.class);
            reference = FirebaseDatabase.getInstance().getReference("users").child(user.getUsername()).child("house");


            // Recycler view room -----------
            reference.child("room").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lstRoom.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String roomName = snapshot.child("name").getValue(String.class);
                        Boolean mode = snapshot.child("mode").getValue(Boolean.class);
//                        String idRoom = snapshot.child("id").getValue(String.class);
                        Long roomImage = snapshot.child("image").getValue(Long.class);

                        //1. SELECT * FROM Lights => devices on
                        reference.child("room").child(snapshot.getKey()).child("light").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int deviceOn = 0;
                                int devices = 0;
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        devices++;

                                        Light light = snapshot.getValue(Light.class);
                                        if(light.getStatus().equals(true)) deviceOn++;
                                    }
                                }

//                                tvRoomDevices.setText("Devices on: " + deviceOn + "/" + devices);

                                // Room room = new Room(snapshot.getKey(), roomName, roomImage, mode);
                                RoomBigItem room = new RoomBigItem(snapshot.getKey(), roomName, roomImage, mode, devices, deviceOn);
                                lstRoom.add(room);


//                                Log.d("BBBROOM", "huhu" + String.valueOf(lstRoom.size()));
                                listRoomAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }


                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

    }

    private void addControls() {
        recyclerViewRoom = findViewById(R.id.recyclerViewRoomBig);
        toolbar = findViewById(R.id.roomBigToolbar);
        imgAddRoom = findViewById(R.id.imgAddRoom);
    }
}