package com.example.smarthome.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.smarthome.Adapter.LightAdapter;
import com.example.smarthome.Adapter.RoomImageAdapter;
import com.example.smarthome.Model.Light;
import com.example.smarthome.R;

import java.util.ArrayList;

public class RoomImageActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerViewRoomImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_image);

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
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });


    }

    private void addControls() {
        toolbar = findViewById(R.id.roomImageToolbar);
        recyclerViewRoomImage = findViewById(R.id.recyclerViewRoomImage);
    }

    private void init(){
        ArrayList<Integer> lstRoomImageId = new ArrayList<>();
        lstRoomImageId.add(R.drawable.bedroom);
        lstRoomImageId.add(R.drawable.livingroom);
        lstRoomImageId.add(R.drawable.kitchen);
        lstRoomImageId.add(R.drawable.bathroom);
        lstRoomImageId.add(R.drawable.garden);
        lstRoomImageId.add(R.drawable.manytoilet);
        lstRoomImageId.add(R.drawable.stair);
        lstRoomImageId.add(R.drawable.backdoor);
        lstRoomImageId.add(R.drawable.santhuong);
        lstRoomImageId.add(R.drawable.frontdoor);
        lstRoomImageId.add(R.drawable.room_image_item_10);
        lstRoomImageId.add(R.drawable.room_image_item_9);
        lstRoomImageId.add(R.drawable.room_image_item_8);
        lstRoomImageId.add(R.drawable.room_image_item_7);
        lstRoomImageId.add(R.drawable.room_image_item_6);
        lstRoomImageId.add(R.drawable.room_image_item_5);
        lstRoomImageId.add(R.drawable.room_image_item_4);
        lstRoomImageId.add(R.drawable.room_image_item_3);
        lstRoomImageId.add(R.drawable.room_image_item_2);
        lstRoomImageId.add(R.drawable.room_image_item_1);
        lstRoomImageId.add(R.drawable.person_walking);
        lstRoomImageId.add(R.drawable.sun);
        lstRoomImageId.add(R.drawable.night);
        lstRoomImageId.add(R.drawable.footprint);

        // tạo adapter
        RoomImageAdapter roomImageAdapter = new RoomImageAdapter(lstRoomImageId);
        // performance
        recyclerViewRoomImage.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewRoomImage.setAdapter(roomImageAdapter);
    }
}