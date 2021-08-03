package com.example.smarthome.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.smarthome.Adapter.WarningAdapter;
import com.example.smarthome.Model.DoorNotify;
import com.example.smarthome.Model.Light;
import com.example.smarthome.Model.Noti;
import com.example.smarthome.Model.User;
import com.example.smarthome.Model.Warning;
import com.example.smarthome.R;
import com.example.smarthome.Service.NotiService;
import com.example.smarthome.SessionManagement;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class WarningActivity extends AppCompatActivity {

    ArrayList<Warning> arrayWarning = new ArrayList<>();
    ArrayList<Warning> arrayWarningold = new ArrayList<>();
    Integer mCheckLayout = 0;


    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

        mCheckLayout = 1;
        addEvents();
        initView();
        initViewOld();

    }


    private void addEvents() {

        findViewById(R.id.warningSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarningActivity.this, WarningSettingActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.warningBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckLayout = 0;
                finish();
            }
        });

    }

    public void initView(){

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewWarning00);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        mData = FirebaseDatabase.getInstance().getReference();

        SessionManagement sessionManagement = SessionManagement.getInstance(this);
        String userJson = sessionManagement.getSession();
        DatabaseReference notiData;
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);
        notiData = mData.child("users").child(user.getUsername()).child("house").child("noti");

        WarningAdapter warningAdapter = new WarningAdapter(getApplicationContext(),arrayWarning);
        recyclerView.setAdapter(warningAdapter);

        notiData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                warningAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                warningAdapter.notifyDataSetChanged();
            }
        });

        notiData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Noti noti = snapshot.getValue(Noti.class);
                if (mCheckLayout == 1) {
                    if (snapshot.exists()) {
                        Integer number = noti.mSeen + 1;
                        snapshot.getRef().child("mSeen").setValue(number);
                    }
                }
                noti.mSeen = noti.mSeen + 1;
                if (noti.mSeen < 2){
                    int image = R.drawable.baseline_gas_warning;
                    assert noti != null;
                    if (noti.mType.equals("door_noti")) {
                        image = R.drawable.baseline_door;
                    } else if (noti.mType.equals("light_noti")) {
                        image = R.drawable.baseline_lamb;
                    }
                    arrayWarning.add(new Warning(noti.mContent,
                            image,
                            noti.mMoment));
                }
                warningAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                Noti noti = snapshot.getValue(Noti.class);
//                int image = R.drawable.baseline_volume;
//                assert noti != null;
//                if (noti.mType.equals("door_noti")) {
//                    image = R.drawable.baseline_door;
//                } else if (noti.mType.equals("light_noti")) {
//                    image = R.drawable.baseline_lamb;
//                }
//                if (noti.mSeen < 2){
//                    arrayWarning.add(new Warning(noti.mContent,
//                            image,
//                            noti.mMoment));
//                }
//                warningAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                warningAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public void initViewOld(){
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recyclerViewWarning00old);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView2.setLayoutManager(layoutManager2);

        SessionManagement sessionManagement = SessionManagement.getInstance(this);
        String userJson = sessionManagement.getSession();
        DatabaseReference notiData;
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);
//        user.getUsername()

        notiData = mData.child("users").child(user.getUsername()).child("house").child("noti");

        mData = FirebaseDatabase.getInstance().getReference();

        WarningAdapter warningAdapter01 = new WarningAdapter(getApplicationContext(),arrayWarningold);
        recyclerView2.setAdapter(warningAdapter01);

        notiData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Noti noti = snapshot.getValue(Noti.class);
                int image = R.drawable.baseline_gas_warning;
                if (noti.mType.equals("door_noti")){
                    image = R.drawable.baseline_door;
                }else if (noti.mType.equals("light_noti")){
                    image = R.drawable.baseline_lamb;
                }

                if(noti.mSeen > 1){
                    arrayWarningold.add(new Warning(noti.mContent,
                            image,
                            noti.mMoment));
                }

                warningAdapter01.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Noti noti = snapshot.getValue(Noti.class);
                int image = R.drawable.baseline_gas_warning;
                if (noti.mType.equals("door_noti")){
                    image = R.drawable.baseline_door;
                }else if (noti.mType.equals("light_noti")){
                    image = R.drawable.baseline_lamb;
                }

                if(noti.mSeen == 2){
                    arrayWarningold.add(new Warning(noti.mContent,
                            image,
                            noti.mMoment));
                }

                warningAdapter01.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}