package com.example.smarthome.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthome.Data.BuzzerData;
import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.Service.DBUtils;
import com.example.smarthome.Service.MQTTService;
import com.example.smarthome.SessionManagement;
import com.example.smarthome.Topic.GasTopic;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jetbrains.annotations.NotNull;

public class GasFragment extends Fragment {
    final String topic = "gas";
    final String fileName = "GasFragment.java";
    String username = "";
    static int gasStatus ;

    TextView gasConcentration, gasSummary, gasMessage, volumeValText;

    LinearLayout concentration;
    SeekBar seekBar;
    Button saveVolumeButton;
    int finalVolumeVal = 0 ;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gas, container, false);

        gasConcentration = v.findViewById(R.id.gas_concentration);
        gasSummary = v.findViewById(R.id.gas_summary);
        gasMessage = v.findViewById(R.id.gas_message);
        volumeValText = v.findViewById(R.id.volumeValText);
        saveVolumeButton = v.findViewById(R.id.saveVolumeBtn);
        concentration = v.findViewById(R.id.concentration);
        seekBar = v.findViewById(R.id.seekBar);

        initGasStatus();
        getUserInfo();
        MQTTServiceHandler();
        handleSeekBar();

        return v;
    }

    public void MQTTServiceHandler() {
//        MQTTService mqttService = new MQTTService(this.getActivity(), topic);
        MQTTService mqttService = MQTTService.getInstance(getContext());

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
                if(topic.split("/")[2].equals("buzzer")) {
                    Log.d("BUZZER_TOPIC_HANDLE", "Buzzer message received!");
                    handleBuzzerMQTT(message);
                    return;
                }
                else if(topic.split("/")[2].equals("gas")) {
                    Log.d("GAS_TOPIC_HANDLE", "Gas message received!");
                    Log.i(fileName, "Message arrived - " + message.toString());

                    Gson g = new Gson();
                    GasTopic gasTopic = g.fromJson(message.toString(), GasTopic.class);

                    if (gasTopic.getData().equals("0")) {
                        gasStatus = 0;
                        saveGasToDB();
                        gasConcentration.setText("OK!");
                        gasSummary.setText("ALL GOOD!");
                        gasMessage.setText("No thread detected.");

                        concentration.setBackground(getResources().getDrawable(R.drawable.gas_concentration_bg_color_selector));
                    }
                    else if (gasTopic.getData().equals("1")){
                        gasStatus = 1;
                        saveGasToDB();
                        gasConcentration.setText("DANGER!");
                        gasSummary.setText("KITCHEN!");

                        gasMessage.setText("Gas is leaking.");

                        concentration.setBackground(getResources().getDrawable(R.drawable.gas_danger_bg_color_selector));
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    public void initGasStatus() {
        if (gasStatus == 0) {
            gasConcentration.setText("OK!");
            gasSummary.setText("ALL GOOD!");
            gasMessage.setText("No thread detected.");

            concentration.setBackground(getResources().getDrawable(R.drawable.gas_concentration_bg_color_selector));
        }
        else if (gasStatus == 1){
            gasConcentration.setText("DANGER!");
            gasSummary.setText("KITCHEN!");

            gasMessage.setText("Gas is leaking.");

            concentration.setBackground(getResources().getDrawable(R.drawable.gas_danger_bg_color_selector));
        }
    }


    public void handleSeekBar() {
        if(seekBar != null) {
            addSeekBarEvents();
            initBuzzerVolume();
        } else
        {
            Log.e("SEEK BAR NOT FOUNR", "Cannot find seek bar");
        }
    }

    public void getUserInfo() {
        // get username
        SessionManagement sessionManagement = SessionManagement.getInstance(getContext());
        String session = sessionManagement.getSession();
        Log.d("USERNAME", session);
        User user = new Gson().fromJson(sessionManagement.getSession(), User.class);
        username = user.getUsername();
        Log.d("USERNAME", username);
    }

    public void initBuzzerVolume() {
        String dbPath = "users/" + username + "/house/settings/buzzer";
        DBUtils.setDbPath(dbPath);
        DatabaseReference dbRef = DBUtils.getRef();
        if(dbRef != null) {
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        Log.d("BUZZER", snapshot.getValue().toString());
                        finalVolumeVal = Integer.parseInt(snapshot.getValue().toString());
                        seekBar.setProgress(finalVolumeVal);
                        volumeValText.setText("Buzzer: " + Integer.toString(finalVolumeVal));
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } else {
            Log.e("DB REF ERROR", "No ref db for path " + dbPath);
        }
    }

    public void addSeekBarEvents() {
        MQTTService mqttService = MQTTService.getInstance(getContext());

        // add events on seekbar progress changes
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("SEEKBAR", Integer.toString(progress));
                finalVolumeVal = progress;
                volumeValText.setText("Buzzer: " + Integer.toString(finalVolumeVal));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("STOPSEEKBAR", Integer.toString(finalVolumeVal));
            }
        });
        // add events for the button to save value to buzzer on database and adafruit
        saveVolumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbPath = "users/" + username + "/house/settings/buzzer";
                DBUtils.setDbPath(dbPath);
                DatabaseReference dbRef = DBUtils.getRef();
                // check if db reference is nullable
                if(dbRef != null) {
                    dbRef.setValue(finalVolumeVal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("UPDATE_BUZZER", "Updated to buzzer!");
                            Toast toast = Toast.makeText(getContext(), "Updated buzzer to " + finalVolumeVal, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Log.d("UPDATE_BUZZER", "Failed to update to buzzer!");
                            Toast toast = Toast.makeText(getContext(), "Failed to update buzzer to " + finalVolumeVal, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                } else {
                    Log.e("DB REF ERROR", "No ref db for path " + dbPath);
                }
                // check if mqtt service reference is nullable
                if(mqttService != null) {
                    BuzzerData buzzerData = new BuzzerData("2", "SPEAKER", Integer.toString(finalVolumeVal), "");
                    mqttService.publishMessage(buzzerData.toString(), "buzzer");
                } else {
                    Log.e("MQTT_SERVICE_NULL", "Mqtt service is null!");
                }
            }
        });
    }

    public void saveGasToDB() {
        String dbPath = "users/" + username + "/house/gas_status";
        DBUtils.setDbPath(dbPath);
        DatabaseReference dbRef = DBUtils.getRef();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int oldGas = Integer.parseInt(snapshot.getValue().toString());
                    if(oldGas != gasStatus) {
                        dbRef.setValue(gasStatus).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Log.e("UPDATE_GAS_FAILED", "Update gas failed!");
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("UPDATE_GAS_SUCC", "Update gas success!");
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public void handleBuzzerMQTT(MqttMessage message) {
        BuzzerData buzzerData = new Gson().fromJson(message.toString(), BuzzerData.class);

        if(finalVolumeVal != Integer.parseInt(buzzerData.getData())) {
            finalVolumeVal = Integer.parseInt(buzzerData.getData());
            volumeValText.setText("Volume: " + finalVolumeVal);
            seekBar.setProgress(finalVolumeVal);

            Toast.makeText(getContext(), "Buzzer volume has just set to " + finalVolumeVal, Toast.LENGTH_LONG)
                    .show();
            String dbPath = "users/" + username + "/house/settings/buzzer";
            DBUtils.setDbPath(dbPath);
            DatabaseReference dbRef = DBUtils.getRef();
            if (dbRef != null) {
                dbRef.setValue(finalVolumeVal).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("UPDATE_BUZZER", "Updated to buzzer!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.e("UPDATE_BUZZER", "Failed to update to buzzer!");
                    }
                });
            } else {
                Log.e("DB REF ERROR", "No ref db for path " + dbPath);
            }
        }
    }
}
