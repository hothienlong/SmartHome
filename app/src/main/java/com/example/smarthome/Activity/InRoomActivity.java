package com.example.smarthome.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.R;

public class InRoomActivity extends AppCompatActivity {

    Toolbar toolbar;
    RelativeLayout relativeLayoutLight, relativeLayoutDoor;

    ImageView doorImg, lightImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_room);

        addControls();
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

        relativeLayoutLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InRoomActivity.this, LightActivity.class);
                startActivity(intent);
            }
        });

        relativeLayoutDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDoorActivity();
            }
        });

        doorImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                toDoorActivity();
            }
        });
    }

    private void addControls() {
        toolbar = findViewById(R.id.inRoomToolbar);
        relativeLayoutLight = findViewById(R.id.relativeLayoutLight);
        relativeLayoutDoor = findViewById(R.id.relativeLayoutDoor);

        doorImg = findViewById(R.id.inRoomDoorImg);
    }

    public void toDoorActivity() {
        Intent doorIntent = new Intent(InRoomActivity.this, DoorActivity.class);
        Log.d("Change scence", "------------------------Change from InRoomActivity to DoorActivity --------------------");
        startActivity(doorIntent);
    }
}