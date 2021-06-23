package com.example.smarthome.Service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.smarthome.Activity.ChannelNotify;
import com.example.smarthome.Model.DoorNotify;
import com.example.smarthome.Model.Light;
import com.example.smarthome.Model.Noti;
import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.SessionManagement;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

public class NotiService extends Service {

    NotificationManager mNotificationManager;
    NotificationCompat.Builder mNotification;
    DataSnapshot mDataRoomValue;

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    Context mContext = this;
    int mLightOn = 0;
    int mMaxLight = 0;
    boolean mVolumeSetting = false, mDoorSetting = false, mLightSetting = false;

    public NotiService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setWarningSetting();
        setNoti();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    private void setNoti() {

        SessionManagement sessionManagement = SessionManagement.getInstance(this);
        String userJson = sessionManagement.getSession();
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);
//        user.getUsername()

        DatabaseReference houseData = mData.child("users").child(user.getUsername()).child("house");
        DatabaseReference roomData = mData.child("users").child(user.getUsername()).child("house").child("room");
        DatabaseReference notiData = mData.child("users").child(user.getUsername()).child("house").child("noti");
//        Noti noti = new Noti("The door 1 is open", Calendar.getInstance().getTime().toString(),false,"door_noti");
//

        houseData.child("gas_status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //gas notify go here
                Integer gas = snapshot.getValue(Integer.class);
                if (gas == 1) {

                    String messenger = "Gas " + " is high";
//                    Date currentTime = Calendar.getInstance().getTime();

                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String currentTime = formatter.format(date);

//                                                Log.d("AAA","BBBB");
                    mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    mNotification = sendCustomNotification(
                            R.drawable.baseline_gas_warning,
                            messenger,
                            currentTime.toString());
                    mNotificationManager.notify(getNotifyID(), mNotification.build());

                    // Push noti to firebase
                    Noti noti = new Noti(messenger, currentTime.toString(), 0, "gas_noti");
                    notiData.push().setValue(noti);
                    //
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        houseData.child("gas_status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //gas notify go here

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        roomData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                mDataRoomValue = snapshot;
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        roomData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if (snapshot.getKey().equals("gas_status")) {
                    if (snapshot.getValue().equals(0)) {
                        //
                    }
                } else {
                    snapshot.getRef().addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                            if (snapshot.getKey().equals("door")) {
                                snapshot.getRef().addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                                    //Notify
//                                    if (mDoorSetting) {
//                                        DoorNotify door = snapshot.getValue(DoorNotify.class);
//                                        if (door.getStatus()) {
//                                            Date currentTime = Calendar.getInstance().getTime();
//
//                                            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//                                            mNotification = sendCustomNotification(
//                                                    R.drawable.baseline_door,
//                                                    "The door " + door.getName() + " of " + snapshot.getRef().getParent().getParent().getKey() + " is open",
//                                                    currentTime.toString());
//                                            mNotificationManager.notify(getNotifyID(), mNotification.build());
//                                        }
//                                    }
//                                    //
                                    }

                                    @Override
                                    public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                                        //Notify
                                        if (mDoorSetting) {
                                            DoorNotify door = snapshot.getValue(DoorNotify.class);
                                            if (door.getStatus()) {
//                                                Date currentTime = Calendar.getInstance().getTime();
                                                Date date = new Date();
                                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                                String currentTime = formatter.format(date);


                                                String nameRoom = mDataRoomValue.child(snapshot.getRef().getParent().getParent().getKey()).child("name").getValue(String.class);
                                                String messenger = "The door " + door.getName() + " of " + nameRoom + " is open";

//                                                Log.d("AAA","BBBB");
                                                mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                                mNotification = sendCustomNotification(
                                                        R.drawable.baseline_door,
                                                        messenger,
                                                        currentTime.toString());
                                                mNotificationManager.notify(getNotifyID(), mNotification.build());

                                                // Push noti to firebase
                                                Noti noti = new Noti(messenger, currentTime.toString(), 0, "door_noti");
                                                notiData.push().setValue(noti);
                                                //
                                            }
                                        }
                                        //
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
                            } else if (snapshot.getKey().equals("light")) {
                                snapshot.getRef().addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                                        Light light = snapshot.getValue(Light.class);
                                        if (light.getStatus().booleanValue()) {
                                            mLightOn = mLightOn + 1;
                                        }
                                        mMaxLight = mMaxLight + 1;
                                    }

                                    @Override
                                    public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                                        Light light = snapshot.getValue(Light.class);
                                        if (light.getStatus().booleanValue()) {
                                            mLightOn = mLightOn + 1;
                                        } else {
                                            mLightOn = mLightOn - 1;
                                        }
                                        //Notify
                                        if (mLightSetting) {
                                            if (mLightOn * 10 >= mMaxLight * 8) {
//                                                Date currentTime = Calendar.getInstance().getTime();
                                                Date date = new Date();
                                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                                String currentTime = formatter.format(date);
                                                String messenger = "There are " + String.valueOf(mLightOn) + " of " + String.valueOf(mMaxLight) + " lamps in house are on, you should turn off unnecessary lights";

                                                mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                                mNotification = sendCustomNotification(
                                                        R.drawable.baseline_lamb,
                                                        messenger,
                                                        currentTime.toString());

                                                if (!mVolumeSetting) {
                                                    mNotification = mNotification.setSound(null);
                                                }

                                                mNotificationManager.notify(getNotifyID(), mNotification.build());

                                                // Push noti to firebase
                                                Noti noti = new Noti(messenger, currentTime.toString(), 0, "light_noti");
                                                notiData.push().setValue(noti);
                                                //
                                            }
                                        }
                                        //

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

                        @Override
                        public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

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

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

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

    private NotificationCompat.Builder sendCustomNotification(Integer image, String messenger, String time_messenger){

        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.layout_notification);

        notificationLayout.setImageViewResource(R.id.notiImage,image);
        notificationLayout.setTextViewText(R.id.notiText,messenger);
        notificationLayout.setTextViewText(R.id.notiTime,time_messenger);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(mContext, ChannelNotify.CHANNEL_ID)
                        .setSmallIcon(image)
                        .setSound(uri)
                        .setCustomContentView(notificationLayout)
                        .setShowWhen(true);

        return notification;
    }

    private int getNotifyID(){
        return (int) new Date().getTime();
    }

    private void setWarningSetting() {
        SessionManagement sessionManagement = SessionManagement.getInstance(this);
        String userJson = sessionManagement.getSession();
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);
//        user.getUsername()
        DatabaseReference notiSettingData =  mData.child("users").child(user.getUsername()).child("house").child("noti_setting");
        notiSettingData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                snapshot.getRef().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.getKey().equals("door_noti")){
                            if (snapshot.getValue().equals(true)){
                                mDoorSetting = true;
                            }
                            else if (snapshot.getValue().equals(false)){
                                mDoorSetting = false;
                            }
                        }
                        else if (snapshot.getKey().equals("light_noti")){
                            if (snapshot.getValue().equals(true)){
                                mLightSetting = true;
                            }
                            else if (snapshot.getValue().equals(false)){
                                mLightSetting = false;
                            }
                        }
                        else if (snapshot.getKey().equals("volume_noti")){
                            if (snapshot.getValue().equals(true)){
                                mVolumeSetting = true;
                            }
                            else if (snapshot.getValue().equals(false)){
                                mVolumeSetting = false;
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

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