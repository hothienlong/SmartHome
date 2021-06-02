package com.example.smarthome.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.smarthome.Adapter.DeviceAdapter;
import com.example.smarthome.Model.Light;
import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.SessionManagement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddOnDeviceActicity extends AppCompatActivity {

    public DatabaseReference reference;
    public Query roomQuery;
    public ArrayList<String> roomName = new ArrayList<>();

    Toolbar toolbar;
    RecyclerView recyclerViewDevice;
    DeviceAdapter deviceAdapter;
    ArrayList<Light> lstLight = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_on_device);

        toolbar = findViewById(R.id.addSceneToolbar);

        SessionManagement sessionManagement = SessionManagement.getInstance(this);
        String userJson = sessionManagement.getSession();

        if(userJson != null){
            Gson gson = new Gson();
            User user = gson.fromJson(userJson, User.class);

            reference = FirebaseDatabase.getInstance().getReference("users").child(user.getUsername()).child("house");

            reference.child("room_name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        roomName.add(snapshot.getValue(String.class));
                    }

                    for (String room : roomName) {
                        reference.child(room).child("light").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                lstLight.clear();

                                for (DataSnapshot s : snapshot.getChildren()) {
                                    Light light = s.getValue(Light.class);

                                    light.setName(light.getName() + " - " + room);

                                    lstLight.add(light);
                                }

                                deviceAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                        break;
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

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
        recyclerViewDevice = findViewById(R.id.scene_add_device);

        // tạo adapter
        deviceAdapter = new DeviceAdapter(lstLight);
        // performance
        recyclerViewDevice.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewDevice.setAdapter(deviceAdapter);
    }
}