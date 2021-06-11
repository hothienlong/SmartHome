package com.example.smarthome.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.ListRoomBigAdapter;
import com.example.smarthome.Model.Room;
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
    ArrayList<Room> lstRoom = new ArrayList<>();
//    ArrayList<String> lstRoomId = new ArrayList<>();

    RecyclerView recyclerViewRoom;
    ListRoomBigAdapter listRoomAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room_big);
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

                        Room room = new Room(snapshot.getKey(), roomName, roomImage, mode);
                        lstRoom.add(room);
                    }

                    listRoomAdapter.notifyDataSetChanged();
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

    }
}