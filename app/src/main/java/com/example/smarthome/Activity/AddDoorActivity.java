package com.example.smarthome.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.R;

public class AddDoorActivity extends AppCompatActivity {

    CardView cardViewAddDoor ;
    Toolbar addDoorToolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_door);

        init();
        addEvents();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void addEvents() {
        addDoorToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent backToDoorIntent = new Intent(AddDoorActivity.this, DoorActivity.class);
//                Log.d("----------------------", "back to door");
//                startActivity(backToDoorIntent);
                finish();
            }
        });
    }

    public void init() {
        addDoorToolbar = findViewById(R.id.addDoorToolbar);
        cardViewAddDoor = findViewById(R.id.cardviewAddDoor);
    }
}