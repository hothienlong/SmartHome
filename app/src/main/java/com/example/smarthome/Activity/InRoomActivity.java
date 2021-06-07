package com.example.smarthome.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.Model.Room;
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

public class InRoomActivity extends AppCompatActivity {

    DatabaseReference reference;
    Toolbar toolbar;
    RelativeLayout relativeLayoutLight, relativeLayoutDoor;

    TextView tvRoomName;
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
                reference = FirebaseDatabase.getInstance().getReference("users").child(user.getUsername()).child("house");


                // Recycler view room -----------
                reference.child(roomId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        String roomName = snapshot.child("name").getValue(String.class);
                        Boolean mode = snapshot.child("mode").getValue(Boolean.class);
                        Log.d(getClass().getName(), roomName);

                        tvRoomName.setText(roomName);
                        toggleAuto.setChecked(mode);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

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
    }

    private void addControls() {
        toolbar = findViewById(R.id.inRoomToolbar);
        relativeLayoutLight = findViewById(R.id.relativeLayoutLight);
        relativeLayoutDoor = findViewById(R.id.relativeLayoutDoor);
        tvRoomName = findViewById(R.id.tvRoomName);
        toggleAuto = findViewById(R.id.toggleAuto);
    }
}