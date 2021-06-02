package com.example.smarthome.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.smarthome.Adaper.WarningAdapter;
import com.example.smarthome.Model.Warning;
import com.example.smarthome.R;

import java.util.ArrayList;

public class WarningActivity extends AppCompatActivity {

    ListView   lvWarning;
    ArrayList<Warning> arrayWarning;
    ArrayList<Warning> arrayWarningold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        initView();


        findViewById(R.id.warningSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarningActivity.this, WarningSettingActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.warningBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarningActivity.this, HomeGasSettingActivity.class);
                startActivity(intent);
            }
        });

    }

    public void initView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewWarning00);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        arrayWarning = new ArrayList<>();
        arrayWarning.add(new Warning("New", R.drawable.baseline_empty_image, R.drawable.baseline_bg_warning35dp, ""));
        arrayWarning.add(new Warning("More than 80% of the lamps are on, you should turn off unnecessary lights",R.drawable.baseline_lamb, R.drawable.baseline_bg_warning70dp,"1 minute left"));
        arrayWarning.add(new Warning("The main door of the building is open",R.drawable.baseline_door, R.drawable.baseline_bg_warning70dp,"1 minute left"));
        arrayWarning.add(new Warning("There are guests in front of the main door",R.drawable.baseline_visittor, R.drawable.baseline_bg_warning70dp,"1 minute left"));
        arrayWarning.add(new Warning("Before", R.drawable.baseline_empty_image, R.drawable.baseline_bg_warning35dp, ""));
        arrayWarning.add(new Warning("More than 80% of the lamps are on, you should turn off unnecessary lights",R.drawable.baseline_lamb, R.drawable.baseline_bg_warning70dp,"1 minute left"));
        arrayWarning.add(new Warning("The main door of the building is open",R.drawable.baseline_door, R.drawable.baseline_bg_warning70dp,"1 minute left"));


        WarningAdapter warningAdapter = new WarningAdapter(getApplicationContext(),arrayWarning);
        recyclerView.setAdapter(warningAdapter);

    }

}