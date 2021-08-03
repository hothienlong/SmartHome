package com.example.smarthome.Activity;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.Model.User;
import com.example.smarthome.Model.Warning;
import com.example.smarthome.R;
import com.example.smarthome.SessionManagement;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WarningSettingActivity extends AppCompatActivity {

    Context mContext = this;
    DatabaseReference mData, mNotiSettingData;

    Switch mSwitchVolume, mSwitchDoor, mSwitchGuest, mSwitchLamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_setting);

        map();
        initWarningSetting();
        addEvents();


    }

    private void addEvents() {
        findViewById(R.id.warningSettingBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSwitchDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotiSettingData.child("door_noti").setValue(mSwitchDoor.isChecked());
            }
        });

//        mSwitchVolume.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mNotiSettingData.child("volume_noti").setValue(mSwitchVolume.isChecked());
//            }
//        });

        mSwitchLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotiSettingData.child("light_noti").setValue(mSwitchLamp.isChecked());
            }
        });

        mNotiSettingData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                Toast.makeText(WarningSettingActivity.this, "mNotiSettingData onDataChange " ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void initWarningSetting() {
        mData = FirebaseDatabase.getInstance().getReference();

        SessionManagement sessionManagement = SessionManagement.getInstance(this);
        String userJson = sessionManagement.getSession();
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);

        mNotiSettingData =  mData.child("users").child(user.getUsername()).child("house").child("noti_setting");



        mNotiSettingData.child("door_noti").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.getValue().equals(true)){
                    mSwitchDoor.setChecked(true);
                }else if (snapshot.getValue().equals(false)){
                    mSwitchDoor.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        mNotiSettingData.child("light_noti").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.getValue().equals(true)){
                    mSwitchLamp.setChecked(true);
                }else if (snapshot.getValue().equals(false)){
                    mSwitchLamp.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

//        mNotiSettingData.child("volume_noti").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if(snapshot.getValue().equals(true)){
//                    mSwitchVolume.setChecked(true);
//                }else if (snapshot.getValue().equals(false)){
//                    mSwitchVolume.setChecked(false);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

    }

    private void map() {
//        mSwitchVolume = findViewById(R.id.warningSettingSwitchVolume);
        mSwitchDoor = findViewById(R.id.warningSettingSwitchDoor);
//        mSwitchGuest = findViewById(R.id.warningSettingSwitchGuest);
        mSwitchLamp = findViewById(R.id.warningSettingSwitchLamp);
    }
}