package com.example.smarthome.Service;
import android.content.Context;
import android.util.Log;

import com.example.smarthome.Activity.LoginActivity;
import com.example.smarthome.Model.Door;
import com.example.smarthome.Model.Light;
import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.SessionManagement;
import com.google.gson.Gson;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;


public class MQTTService {

    private static MQTTService mInstance = null;

    Context context;

    final String serverUri = "tcp://io.adafruit.com:1883";
    private String clientId = "YOUR_USERNAME";
    final String subscriptionTopicRoot = "oolongoopro/feeds/";
    final String username = "oolongoopro";
    final String password = "aio_SWZP48QYzkuFY3tDYRmZ9K1zZ5mX";

    String topic = "";

    public MqttAndroidClient mqttAndroidClient;

    public static MQTTService getInstance(Context context){
        if(mInstance == null){
            mInstance = new MQTTService(context);
        }
        return mInstance;
    }

    private MQTTService(Context context){

        this.context = context;

        SessionManagement sessionManagement = SessionManagement.getInstance(context);
        String userJson = sessionManagement.getSession();

        if(userJson != null){
            Gson gson = new Gson();
            User user = gson.fromJson(userJson, User.class);
            clientId = user.getUsername();
        }

        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
//        this.topic = topic;
        connect();
    }

    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    private void connect() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic(Light.topic);
                    subscribeToTopic(Door.topic);
                    subscribeToTopic("gas");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                    Log.w(this.getClass().getName(), "Failed to connect to: " + serverUri + exception.toString());

                }
            });
        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }


    public void subscribeToTopic(String topic) {
        try {
            Log.d(this.getClass().getName(), subscriptionTopicRoot+topic);
            mqttAndroidClient.subscribe(subscriptionTopicRoot + topic , 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w(this.getClass().getName(),"Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w(this.getClass().getName(), "Subscribed fail!");
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }


    public void publishMessage(String payload, String topic) {
        try {
            if (mqttAndroidClient.isConnected() == false) {
                mqttAndroidClient.connect();
            }

            MqttMessage message = new MqttMessage();
//            message.setPayload(payload.getBytes());
//            message.setQos(0);

            message.setId(1234);
            message.setQos(0);
            message.setRetained(true);

            byte[] b = payload.getBytes(Charset.forName("UTF-8"));
            message.setPayload(b);
            mqttAndroidClient.publish(subscriptionTopicRoot + topic, message,null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i(this.getClass().getName(), "publish succeed!") ;
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i(this.getClass().getName(), "publish failed!") ;
                }
            });
        } catch (MqttException e) {
            Log.e(this.getClass().getName(), e.toString());
            e.printStackTrace();
        }
    }

    public void disconnect() throws MqttException {
        mqttAndroidClient.unregisterResources();
        mqttAndroidClient.close();

        mqttAndroidClient.disconnect();
        mqttAndroidClient.setCallback(null);
        mqttAndroidClient = null;
        mInstance = null;
        Log.d(this.getClass().getName(), "Unregister!");
    }
}
