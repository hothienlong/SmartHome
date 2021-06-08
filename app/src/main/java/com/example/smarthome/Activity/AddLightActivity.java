package com.example.smarthome.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.smarthome.Model.Light;
import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.Service.MQTTService;
import com.example.smarthome.SessionManagement;
import com.example.smarthome.Topic.LightRelayMessage;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class AddLightActivity extends AppCompatActivity {

    MQTTService mqttService;
    TextInputLayout textInputLightName, textInputLightId;
    CardView cardViewAddLight;
    Toolbar toolbar;

    DatabaseReference reference;

    String mRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_light);

        addControls();

        init();

        // connect & subcribe
//        startMqtt();

        addEvents();
    }

    private void init(){
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

        mqttService = new MQTTService(
                this,
                getResources().getString(R.string.light_topic)
        );
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

        // Publish to adafruit & add Light to firebase
        cardViewAddLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Publish to adafruit new light
                LightRelayMessage lightRelayMessage = new LightRelayMessage(
                        textInputLightId.getEditText().getText().toString(),
                        "0",
                        ""
                );
                mqttService.publishMessage(
                        lightRelayMessage.toString(),
                        getResources().getString(R.string.light_topic)
                );

                // Add light to firebase
                Light light = new Light(
                        lightRelayMessage.getId(),
                        textInputLightName.getEditText().getText().toString(),
                        false
                );
                reference.child(lightRelayMessage.getId()).setValue(light);

                // Move to LightActivity
                Intent intent = new Intent(AddLightActivity.this, LightActivity.class);
                intent.putExtra("roomId", mRoomId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    private void addControls() {
//        textInputLightTopic = findViewById(R.id.textInputLightTopic);
        textInputLightName = findViewById(R.id.textInputLightName);
        textInputLightId = findViewById(R.id.textInputLightId);
        cardViewAddLight = findViewById(R.id.cardviewAddLight);
        toolbar = findViewById(R.id.addSceneToolbar);
    }
}