package com.example.smarthome.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.smarthome.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class WarningSettingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_setting);

        addEvents();

    }
    private void addEvents() {
        findViewById(R.id.warningSettingBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarningSettingActivity.this, WarningActivity.class);
                startActivity(intent);
            }
        });

        Switch simpleSwitch = (Switch) findViewById(R.id.switchVolume);
        Boolean switchState = simpleSwitch.isChecked();
        simpleSwitch.setChecked(true);
    }

}