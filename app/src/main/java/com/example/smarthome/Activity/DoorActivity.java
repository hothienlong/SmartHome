package com.example.smarthome.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.example.smarthome.Adapter.DoorAdapter;
import com.example.smarthome.Model.Door;
import com.example.smarthome.R;
import com.google.firebase.database.ThrowOnExtraProperties;


import java.util.ArrayList;
import java.util.List;

public class DoorActivity extends AppCompatActivity {

    private List<Door> listDoor ;
    private RecyclerView myrecyclerView;
    DoorAdapter doorAdapter;
    Toolbar toolbar;
    TextView doorBack ;
    TextView doorAdd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);


        init();
        addEvents();

        myrecyclerView.setAdapter(doorAdapter);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        doorAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(DoorActivity.this, AddDoorActivity.class);
                Log.d("Add", "----------------------------------To add -------------------------------");
                startActivity(addIntent);
            }
        });
    }

    public void init() {
        listDoor = new ArrayList<Door>();
        listDoor.add(new Door("Main Door", "md", 0,"abcd"));
        listDoor.add(new Door("Main Window", "mw", 1, "abcd"));
        listDoor.add(new Door("Side Window", "mw", 0, "abcd"));
        listDoor.add(new Door("Side Door", "md", 1, "abcd"));

        myrecyclerView = findViewById(R.id.door_recycler_view);
        doorAdapter = new DoorAdapter(this, listDoor);
        doorAdd = findViewById(R.id.textAddDoorImg);
        toolbar = findViewById(R.id.doorToolbar);
    }
}