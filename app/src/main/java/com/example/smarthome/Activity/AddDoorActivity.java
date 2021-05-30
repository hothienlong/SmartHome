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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.Data.DoorData;
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

//    String dbRef = "doors/data";
    DatabaseReference doorRef;

    CardView cardViewAddDoor ;
    Toolbar addDoorToolbar;

    TextInputLayout idLayout, nameLayout;
    RadioGroup radioGroup;
    RadioButton radioButton;



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
                String  doorId = idLayout.getEditText().getText().toString();
                String doorName = nameLayout.getEditText().getText().toString();
                String doorType ;

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                doorType = (radioButton.getText().toString().equals("Door")) ? "door" : "window";
                Log.d("ADDDDDD", "Door Type: " + doorType);

                // create a new door
                Door newDoor = new Door(doorName, doorType, false);
                Door.initHash.put(doorId, newDoor);
                Door.initList.add(newDoor);

                DoorData d = new DoorData(doorName, false, doorType);

                HashMap<String, Object> dbData = new HashMap<String, Object>();
                dbData.put(doorId, d);

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

        idLayout = findViewById(R.id.textInputDoorId);
        nameLayout = findViewById(R.id.textInputDoorName);
        radioGroup = findViewById(R.id.doorSelection);

        doorRef = FirebaseDatabase.getInstance().getReference();

    }

    public void checkDoor(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }
}