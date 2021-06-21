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
import android.view.View;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.smarthome.Adaper.WarningAdapter;
import com.example.smarthome.Model.DoorNotify;
import com.example.smarthome.Model.Light;
import com.example.smarthome.Model.Noti;
import com.example.smarthome.Model.Warning;
import com.example.smarthome.R;
import com.example.smarthome.Service.NotiService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class WarningActivity extends AppCompatActivity {


//    NotificationManager mNotificationManager;
//    NotificationCompat.Builder mNotification;
//    Context mContext = this;
//    int mLightOn = 0;
//    int mMaxLight = 0;
//    boolean mVolumeSetting = false, mDoorSetting = false, mLightSetting = false;

    ArrayList<Warning> arrayWarning = new ArrayList<>();
    ArrayList<Warning> arrayWarningold = new ArrayList<>();


    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, NotiService.class);
        startService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

//        setWarningSetting();
        addEvents();
//        setNoti();
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
                Intent intent = new Intent(WarningActivity.this, HomeGasSettingActivity.class);
                startActivity(intent);
            }
        });

    }

    public void initView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewWarning00);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        mData = FirebaseDatabase.getInstance().getReference();
        DatabaseReference notiData =  mData.child("users").child("long1").child("house").child("noti");

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
                if (snapshot.exists()){
                    Integer number = noti.mSeen + 1;
                    snapshot.getRef().child("mSeen").setValue(number);
                }

                noti.mSeen = noti.mSeen + 1;
                if (noti.mSeen < 2){
                    int image = R.drawable.baseline_volume;
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

        mData = FirebaseDatabase.getInstance().getReference();
        DatabaseReference notiData =  mData.child("users").child("long1").child("house").child("noti");

        WarningAdapter warningAdapter01 = new WarningAdapter(getApplicationContext(),arrayWarningold);
        recyclerView2.setAdapter(warningAdapter01);

        notiData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Noti noti = snapshot.getValue(Noti.class);
                int image = R.drawable.baseline_door;
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
                int image = R.drawable.baseline_door;
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

//
//    private NotificationCompat.Builder sendCustomNotification(Integer image,String messenger, String time_messenger){
//
//        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.layout_notification);
//
//        notificationLayout.setImageViewResource(R.id.notiImage,image);
//        notificationLayout.setTextViewText(R.id.notiText,messenger);
//        notificationLayout.setTextViewText(R.id.notiTime,time_messenger);
//
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder notification =
//                new NotificationCompat.Builder(mContext, ChannelNotify.CHANNEL_ID)
//                        .setSmallIcon(image)
//                        .setSound(uri)
//                        .setCustomContentView(notificationLayout)
//                        .setShowWhen(true);
//
//        return notification;
//    }
//
//    private int getNotifyID(){
//        return (int) new Date().getTime();
//    }
//
//    private void setWarningSetting() {
//        DatabaseReference notiSettingData =  mData.child("users").child("long1").child("house").child("noti_setting");
//        notiSettingData.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                snapshot.getRef().addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                        if (snapshot.getKey().equals("door_noti")){
//                            if (snapshot.getValue().equals(true)){
//                                mDoorSetting = true;
//                            }
//                            else if (snapshot.getValue().equals(false)){
//                                mDoorSetting = false;
//                            }
//                        }
//                        else if (snapshot.getKey().equals("light_noti")){
//                            if (snapshot.getValue().equals(true)){
//                                mLightSetting = true;
//                            }
//                            else if (snapshot.getValue().equals(false)){
//                                mLightSetting = false;
//                            }
//                        }
//                        else if (snapshot.getKey().equals("volume_noti")){
//                            if (snapshot.getValue().equals(true)){
//                                mVolumeSetting = true;
//                            }
//                            else if (snapshot.getValue().equals(false)){
//                                mVolumeSetting = false;
//                            }
//                        }
//
//                    }
//                    @Override
//                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//    }



//
//    private void setNoti(){
//
//        DatabaseReference roomData =  mData.child("users").child("long1").child("house").child("room");
//        DatabaseReference notiData = mData.child("users").child("long1").child("house").child("noti");
////        Noti noti = new Noti("The door 1 is open", Calendar.getInstance().getTime().toString(),false,"door_noti");
////
//        roomData.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                if (snapshot.getKey().equals("gas_status")){
//                    if(snapshot.getValue().equals(0)){
//                        //notify gas here
//                        //
//                        //
//                    }
//                }
//                else {
//                    snapshot.getRef().addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                            if (snapshot.getKey().equals("door")) {
//                                snapshot.getRef().addChildEventListener(new ChildEventListener() {
//                                    @Override
//                                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
////                                    //Notify
////                                    if (mDoorSetting) {
////                                        DoorNotify door = snapshot.getValue(DoorNotify.class);
////                                        if (door.getStatus()) {
////                                            Date currentTime = Calendar.getInstance().getTime();
////
////                                            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////
////                                            mNotification = sendCustomNotification(
////                                                    R.drawable.baseline_door,
////                                                    "The door " + door.getName() + " of " + snapshot.getRef().getParent().getParent().getKey() + " is open",
////                                                    currentTime.toString());
////                                            mNotificationManager.notify(getNotifyID(), mNotification.build());
////                                        }
////                                    }
////                                    //
//                                    }
//
//                                    @Override
//                                    public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                                        //Notify
//                                        if (mDoorSetting) {
//                                            DoorNotify door = snapshot.getValue(DoorNotify.class);
//                                            if (door.getStatus()) {
//                                                Date currentTime = Calendar.getInstance().getTime();
//                                                String messenger = "The door " + door.getName() + " of " + snapshot.getRef().getParent().getParent().getKey() + " is open";
//
////                                                Log.d("AAA","BBBB");
//                                                mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//                                                mNotification = sendCustomNotification(
//                                                        R.drawable.baseline_door,
//                                                        messenger,
//                                                        currentTime.toString());
//                                                mNotificationManager.notify(getNotifyID(), mNotification.build());
//
//                                                // Push noti to firebase
//                                                Noti noti = new Noti(messenger, currentTime.toString(), false, "door_noti");
//                                                notiData.push().setValue(noti);
//                                                //
//                                            }
//                                        }
//                                        //
//                                    }
//
//                                    @Override
//                                    public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
//
//                                    }
//
//                                    @Override
//                                    public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                                    }
//                                });
//                            } else if (snapshot.getKey().equals("light")) {
//                                snapshot.getRef().addChildEventListener(new ChildEventListener() {
//                                    @Override
//                                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                                        Light light = snapshot.getValue(Light.class);
//                                        if (light.getStatus().booleanValue()) {
//                                            mLightOn = mLightOn + 1;
//                                        }
//                                        mMaxLight = mMaxLight + 1;
//                                    }
//
//                                    @Override
//                                    public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                                        Light light = snapshot.getValue(Light.class);
//                                        if (light.getStatus().booleanValue()) {
//                                            mLightOn = mLightOn + 1;
//                                        } else {
//                                            mLightOn = mLightOn - 1;
//                                        }
//                                        //Notify
//                                        if (mLightSetting) {
//                                            if (mLightOn >= mMaxLight * 8 / 10) {
//                                                Date currentTime = Calendar.getInstance().getTime();
//                                                String messenger = "There are " + String.valueOf(mLightOn) + " of " + String.valueOf(mMaxLight) + " lamps in house are on, you should turn off unnecessary lights";
//
//                                                mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//                                                mNotification = sendCustomNotification(
//                                                        R.drawable.baseline_lamb,
//                                                        messenger,
//                                                        currentTime.toString());
//
//                                                if (!mVolumeSetting) {
//                                                    mNotification = mNotification.setSound(null);
//                                                }
//
//                                                mNotificationManager.notify(getNotifyID(), mNotification.build());
//
//                                                // Push noti to firebase
//                                                Noti noti = new Noti(messenger, currentTime.toString(), false, "light_noti");
//                                                notiData.push().setValue(noti);
//                                                //
//                                            }
//                                        }
//                                        //
//
//                                    }
//
//                                    @Override
//                                    public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
//
//                                    }
//
//                                    @Override
//                                    public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                                    }
//                                });
//                            }
//                        }
//
//                        @Override
//                        public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//
//    }

//    private int getNotificationId(){
//        return (int) new Date().getTime();
//    }

}