package com.example.smarthome.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Activity.AddLightActivity;
import com.example.smarthome.Adapter.LightAdapter;
import com.example.smarthome.Model.Light;
import com.example.smarthome.R;
import com.example.smarthome.Service.MQTTService;
import com.example.smarthome.Topic.GasTopic;
import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

public class GasFragment extends Fragment {

    final String topic = "gas";
    final String fileName = "GasFragment.java";

    TextView gasConcentration;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gas, container, false);

        gasConcentration = v.findViewById(R.id.gas_concentration);

        MQTTServiceHandler();

        return v;
    }

    public void MQTTServiceHandler() {
        MQTTService mqttService = new MQTTService(this.getActivity(), topic);

        mqttService.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.i(fileName, "MQTT Server connected!");
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.i(fileName, message.toString());

                Gson g = new Gson();
                GasTopic gasTopic = g.fromJson(message.toString(), GasTopic.class);

                if (gasTopic.getData().equals("0"))
                    gasConcentration.setText("OK!");
                else if (gasTopic.getData().equals("1"))
                    gasConcentration.setText("DANGER!");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
}
