package com.example.smarthome.Service;
import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MQTTService {

    final String serverUri = "tcp://io.adafruit.com:1883";

    final String clientId = "[YourclientID]";
    final String subscriptionTopicRoot = "hoanglan/feeds/";
    final String username = "hoanglan";
    final String password = "aio_ZKTv82oGuCIJ88KxHyqaFuXnADu2";

    String topic = "";

    String fileName = "Service/MQTTService.java";

    public MqttAndroidClient mqttAndroidClient;

    public MQTTService(Context context, String topic){
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        this.topic = topic;
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
                    subscribeToTopic(topic);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w(fileName, "Mqtt failed to connect to: " + serverUri + exception.toString());
                }
            });

        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }


    public void subscribeToTopic(String topic) {
        try {
            Log.d(fileName, "Subscribe to topic: " + subscriptionTopicRoot + topic);

            mqttAndroidClient.subscribe(subscriptionTopicRoot + topic , 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w(fileName,"Mqtt subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w(fileName, "Mqtt subscribed fail!");
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
            message.setPayload(payload.getBytes());
            message.setQos(0);
            mqttAndroidClient.publish(subscriptionTopicRoot + topic, message,null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i(fileName, "Message published succeed!") ;
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i(fileName, "Message published failed!") ;
                }
            });
        } catch (MqttException e) {
            Log.e(fileName, "Message published exception - " + e.toString());
            e.printStackTrace();
        }
    }
}
