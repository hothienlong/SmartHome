package com.example.smarthome.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.smarthome.Adaper.WarningAdapter00;
import com.example.smarthome.Model.Waning;
import com.example.smarthome.Model.Warning00;
import com.example.smarthome.R;

import java.util.ArrayList;

public class WarningActivity extends AppCompatActivity {

    ListView   lvWarning;
    ArrayList<Warning00> arrayWarning;
    ArrayList<Warning00> arrayWarningold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        initView();
        initViewOld();

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
        arrayWarning.add(new Warning00("More than 80% of the lamps are on, you should turn off unnecessary lights",R.drawable.baseline_lamb,"1 minute left"));
        arrayWarning.add(new Warning00("The main door of the building is open",R.drawable.baseline_door,"1 minute left"));
        arrayWarning.add(new Warning00("There are guests in front of the main door",R.drawable.baseline_visittor,"1 minute left"));

        WarningAdapter00 warningAdapter00 = new WarningAdapter00(getApplicationContext(),arrayWarning);
        recyclerView.setAdapter(warningAdapter00);

    }

    public void initViewOld(){
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recyclerViewWarning00old);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView2.setLayoutManager(layoutManager2);
        arrayWarningold = new ArrayList<>();
        arrayWarningold.add(new Warning00("More than 80% of the lamps are on, you should turn off unnecessary lights",R.drawable.baseline_lamb,"1 minute left"));
        arrayWarningold.add(new Warning00("The main door of the building is open",R.drawable.baseline_door,"1 minute left"));

        WarningAdapter00 warningAdapter01 = new WarningAdapter00(getApplicationContext(),arrayWarningold);
        recyclerView2.setAdapter(warningAdapter01);

    }

}