package com.example.smarthome.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.SessionManagement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AddOnDeviceActicity extends AppCompatActivity {

    public DatabaseReference reference;
    public Query roomQuery;
    public ArrayList<String> roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_on_device);

        SessionManagement sessionManagement = SessionManagement.getInstance(this);
        String userJson = sessionManagement.getSession();

        if(userJson != null){
            Gson gson = new Gson();
            User user = gson.fromJson(userJson, User.class);

            reference = FirebaseDatabase.getInstance().getReference("users/" + user.getUsername() + "/house");

            reference.child("room_name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        roomName.add(snapshot.getValue(String.class));
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }



    }
}