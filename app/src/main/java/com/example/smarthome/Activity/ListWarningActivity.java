package com.example.smarthome.Activity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.smarthome.Adaper.ListRoomBigAdapter;
import com.example.smarthome.Model.Room;
import com.example.smarthome.Model.Waning;
import com.example.smarthome.R;

import java.util.ArrayList;

public class ListWarningActivity  extends AppCompatActivity{
    RecyclerView recyclerViewWarning;
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

    }
    private void init() {
        // Recycler view waning -----------
        ArrayList<Waning> lstWaning = new ArrayList<>();
        lstWaning.add(new Waning("The main door of the building is open", "@drawable/baseline_door", "1 minute left"));
        lstWaning.add(new Waning("The main door of the building is open", "@drawable/baseline_door", "1 minute left"));
        lstWaning.add(new Waning("The main door of the building is open", "@drawable/baseline_door", "1 minute left"));
        lstWaning.add(new Waning("The main door of the building is open", "@drawable/baseline_door", "1 minute left"));

        // tạo adapter
        ListWarningActivity waningAdapter = new ListWarningActivity(lstWaning);
        // performance
        recyclerViewWarning.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewWarning.setAdapter(waningAdapter);
    }
    private void addControls() {
        recyclerViewWarning = findViewById(R.id.recyclerViewWarning);

    }
}
