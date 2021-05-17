package com.example.smarthome.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.smarthome.R;
import com.example.smarthome.Service.MQTTService;
import com.google.android.material.textfield.TextInputLayout;

public class AddLightActivity extends AppCompatActivity {

    MQTTService mqttService;
    TextInputLayout textInputLightTopic, textInputLightName;
    CardView cardViewAddLight;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_light);

        addControls();

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

        cardViewAddLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttService.subscribeToTopic(textInputLightTopic.getEditText().getText().toString());
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
        textInputLightTopic = findViewById(R.id.textInputLightTopic);
        textInputLightName = findViewById(R.id.textInputLightName);
        cardViewAddLight = findViewById(R.id.cardviewAddLight);
        toolbar = findViewById(R.id.addLightToolbar);
    }
}