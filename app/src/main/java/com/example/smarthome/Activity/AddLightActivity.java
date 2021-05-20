package com.example.smarthome.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.smarthome.Model.Light;
import com.example.smarthome.R;
import com.example.smarthome.Service.MQTTService;
import com.example.smarthome.Topic.RelayTopic;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddLightActivity extends AppCompatActivity {

    MQTTService mqttService;
    TextInputLayout textInputLightName, textInputLightId;
    CardView cardViewAddLight;
    Toolbar toolbar;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_light);

        addControls();

        reference = FirebaseDatabase.getInstance().getReference("lights");
        mqttService = new MQTTService(this, "relay");

        // connect & subcribe
//        startMqtt();

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

        // Publish to adafruit & add Light to firebase
        cardViewAddLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Publish to adafruit new light
                RelayTopic relayTopic = new RelayTopic(
                        textInputLightId.getEditText().getText().toString(),
                        "0",
                        ""
                );
                mqttService.publishMessage(relayTopic.toString(),"relay");

                // Add light to firebase
                Light light = new Light(
                        relayTopic.getId(),
                        textInputLightName.getEditText().getText().toString(),
                        false
                );
                reference.child(relayTopic.getId()).setValue(light);

                // Move to LightActivity
                Intent intent = new Intent(AddLightActivity.this, LightActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

//    private void startMqtt() {
//        mqttService = new MQTTService(this);
//        mqttService.setCallback(new MqttCallbackExtended() {
//            @Override
//            public void connectComplete(boolean b, String s) {
//                Log.w("mqtt", "connected");
//            }
//
//            @Override
//            public void connectionLost(Throwable throwable) {
//
//            }
//
//            @Override
//            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
//                Log.d("BBB", mqttMessage.toString());
////                txtOut.setText(mqttMessage.toString());
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//
//            }
//        });
//    }

    private void addControls() {
//        textInputLightTopic = findViewById(R.id.textInputLightTopic);
        textInputLightName = findViewById(R.id.textInputLightName);
        textInputLightId = findViewById(R.id.textInputLightId);
        cardViewAddLight = findViewById(R.id.cardviewAddLight);
        toolbar = findViewById(R.id.addLightToolbar);
    }
}