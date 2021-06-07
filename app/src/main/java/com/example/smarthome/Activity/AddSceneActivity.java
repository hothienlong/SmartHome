package com.example.smarthome.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.smarthome.Adapter.OnOffAdapter;
import com.example.smarthome.Fragment.HomeFragment;
import com.example.smarthome.Model.Light;
import com.example.smarthome.Model.LightState;
import com.example.smarthome.Model.Scene;
import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.SessionManagement;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddSceneActivity extends AppCompatActivity {
    private Context context;
    private ImageView addOnBtn, addOffBtn;

    Toolbar toolbar;

    RecyclerView recyclerViewOnDevice;
    RecyclerView recyclerViewOffDevice;

    public static OnOffAdapter deviceOnAdapter;
    public static OnOffAdapter deviceOffAdapter;

    public static ArrayList<Light> lstLightOn = new ArrayList<Light>();
    public static ArrayList<Light> lstLightOff = new ArrayList<Light>();

    User user;
    public DatabaseReference reference;

    TextInputLayout sceneName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scene);

        toolbar = findViewById(R.id.addSceneToolbar);

        addOnBtn = (ImageView)findViewById(R.id.add_on_button);
        addOffBtn = (ImageView)findViewById(R.id.add_off_button);

        sceneName = findViewById(R.id.scene_name);

        addOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                lstLightOn.clear();
                Intent intent = new Intent(context, AddOnDeviceActicity.class);
                intent.putExtra("type", "on");
                context.startActivity(intent);
            }
        });

        addOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                lstLightOff.clear();
                Intent intent = new Intent(context, AddOnDeviceActicity.class);
                intent.putExtra("type", "off");
                context.startActivity(intent);
            }
        });

        SessionManagement sessionManagement = SessionManagement.getInstance(this);
        String userJson = sessionManagement.getSession();

        if(userJson != null) {
            Gson gson = new Gson();
            user = gson.fromJson(userJson, User.class);

            reference = FirebaseDatabase.getInstance().getReference("users").child(user.getUsername()).child("house");

            Button save = findViewById(R.id.save_button);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<LightState> action = new ArrayList<LightState>();

                    for (int i = 0; i < lstLightOn.size(); i++) {
                        Light light = lstLightOn.get(i);

                        String id = light.getId();

                        String temp = light.getName();
                        String roomName = temp.split(" - ", 2)[1];

                        action.add(new LightState(id, "on", roomName));
                    }

                    for (int i = 0; i < AddSceneActivity.lstLightOff.size(); i++) {
                        Light light = AddSceneActivity.lstLightOff.get(i);

                        String id = light.getId();

                        String temp = light.getName();
                        String roomName = temp.split(" - ", 2)[1];

                        action.add(new LightState(id, "off", roomName));
                    }

                    DatabaseReference reference = FirebaseDatabase.getInstance()
                            .getReference("users").child(user.getUsername()).child("house").child("scene");

                    String name = sceneName.getEditText().getText().toString();

                    reference.push().setValue(new Scene(name, ""), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                            String uniqueKey = ref.getKey();
                            for (int i = 0; i < action.size(); i++) {
                                reference.child(uniqueKey).push().setValue(action.get(i));
                            }
                        }
                    });

                    lstLightOn.clear();
                    lstLightOff.clear();

                    HomeFragment.lstScene.add(new Scene(name, null));
                    HomeFragment.sceneAdapter.notifyDataSetChanged();

                    finish();
                }
            });
        }

        addEvents();
        init();
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
        recyclerViewOnDevice = findViewById(R.id.scene_add_on_device);
        recyclerViewOffDevice = findViewById(R.id.scene_add_off_device);

        // tạo adapter
        deviceOnAdapter = new OnOffAdapter(lstLightOn);
        // performance
        recyclerViewOnDevice.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewOnDevice.setAdapter(deviceOnAdapter);

        // tạo adapter
        deviceOffAdapter = new OnOffAdapter(lstLightOff);
        // performance
        recyclerViewOffDevice.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewOffDevice.setAdapter(deviceOffAdapter);
    }
}