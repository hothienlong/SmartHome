package com.example.smarthome.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.Model.Door;
import com.example.smarthome.R;
import com.example.smarthome.Service.DBUtils;
import com.example.smarthome.Topic.DoorTopic;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddDoorActivity extends AppCompatActivity {

    String dbRef = "doors/data";
    DatabaseReference doorRef;

    CardView cardViewAddDoor ;
    Toolbar addDoorToolbar;

    TextInputLayout doorTopic, nameLayout, typeLayout;


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
                finish();
            }
        });
        cardViewAddDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doorName = nameLayout.getEditText().getText().toString();
                String doorType = typeLayout.getEditText().getText().toString();

                // create a new door
                new Door(doorName, doorType, "0", "bbc-door");

                int index = Door.index - 1;

                DoorTopic d = new DoorTopic(Integer.toString(index), doorName, "0", "");
                d.setType(doorType);

                HashMap<String, Object> dbData = new HashMap<String, Object>();
                dbData.put(Integer.toString(index), d);

                DBUtils.updateChild(dbData);

                //Intent backToDoorIntent = new Intent(AddDoorActivity.this, DoorActivity.class);
                //Log.d("----------------------", "back to door");
                //startActivity(backToDoorIntent);
                finish();
            }
        });
    }

    public void init() {
        addDoorToolbar = findViewById(R.id.addDoorToolbar);
        cardViewAddDoor = findViewById(R.id.cardviewAddDoor);

        nameLayout = findViewById(R.id.textInputDoorName);
        typeLayout = findViewById(R.id.textInputDoorType);

        doorRef = FirebaseDatabase.getInstance().getReference();

    }
}