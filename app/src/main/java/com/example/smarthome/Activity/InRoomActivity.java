package com.example.smarthome.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.Model.Light;
import com.example.smarthome.Model.Room;
import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.SessionManagement;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class InRoomActivity extends AppCompatActivity {

    DatabaseReference reference;
    Toolbar toolbar;
    RelativeLayout relativeLayoutLight, relativeLayoutDoor;
    CollapsingToolbarLayout collapseToolbarHome;

    ImageView doorImg, lightImg;

    TextView tvRoomName, tvRoomDevices;
    ToggleButton toggleAuto;

    String roomId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_room);

        addControls();
        init();
        addEvents();
    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null) {
            roomId = intent.getStringExtra("roomId");

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
                ;


                // Recycler view room -----------
                reference.child(roomId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        String roomName = snapshot.child("name").getValue(String.class);
                        Boolean mode = snapshot.child("mode").getValue(Boolean.class);
                        Long roomImage = snapshot.child("image").getValue(Long.class);

                        tvRoomName.setText(roomName);
                        if(roomImage != null){
                            collapseToolbarHome.setBackgroundResource(roomImage.intValue());
                        }
                        toggleAuto.setChecked(mode);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

                //1. SELECT * FROM Lights => devices on
                reference.child(roomId).child("light").addListenerForSingleValueEvent(new ValueEventListener() {
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

                        tvRoomDevices.setText("Devices on: " + deviceOn + "/" + devices);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        }
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

        relativeLayoutLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InRoomActivity.this, LightActivity.class);
                intent.putExtra("roomId", roomId);
                startActivity(intent);
            }
        });

        relativeLayoutDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDoorActivity();
            }
        });

        doorImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                toDoorActivity();
            }
        });
    }

    private void addControls() {
        toolbar = findViewById(R.id.inRoomToolbar);
        relativeLayoutLight = findViewById(R.id.relativeLayoutLight);
        relativeLayoutDoor = findViewById(R.id.relativeLayoutDoor);
        collapseToolbarHome = findViewById(R.id.collapseToolbarHome);
        tvRoomName = findViewById(R.id.tvRoomName);
        tvRoomDevices = findViewById(R.id.tvRoomDevices);
        toggleAuto = findViewById(R.id.toggleAuto);

        doorImg = findViewById(R.id.inRoomDoorImg);
    }

    public void toDoorActivity() {
        Intent doorIntent = new Intent(InRoomActivity.this, DoorActivity.class);
        Log.d("Change scence", "------------------------Change from InRoomActivity to DoorActivity --------------------");
        startActivity(doorIntent);
    }
}