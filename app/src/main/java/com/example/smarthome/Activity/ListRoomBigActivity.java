package com.example.smarthome.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.smarthome.Adaper.ListRoomBigAdapter;
import com.example.smarthome.Model.Room;
import com.example.smarthome.R;

import java.util.ArrayList;

public class ListRoomBigActivity extends AppCompatActivity {

    RecyclerView recyclerViewRoom;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room_big);
        addControls();
        init();
        addEvents();
    }

    private void addEvents() {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true); // icon
        actionbar.setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        // Recycler view room -----------
        ArrayList<Room> lstRoom = new ArrayList<>();
        lstRoom.add(new Room("Bedroom", null, true));
        lstRoom.add(new Room("Living room", null, false));
        lstRoom.add(new Room("Bath room", null, true));
        lstRoom.add(new Room("Kitchen", null, false));

        // tạo adapter
        ListRoomBigAdapter roomAdapter = new ListRoomBigAdapter(lstRoom);
        // performance
        recyclerViewRoom.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewRoom.setAdapter(roomAdapter);
    }

    private void addControls() {
        recyclerViewRoom = findViewById(R.id.recyclerViewRoomBig);
        toolbar = findViewById(R.id.roomBigToolbar);

    }
}