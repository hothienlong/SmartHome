package com.example.smarthome.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.smarthome.R;

public class AddSceneActivity extends AppCompatActivity {
    private Context context;
    private ImageView addOnBtn, addOffBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scene);

        addOnBtn = (ImageView)findViewById(R.id.add_on_button);
        addOffBtn = (ImageView)findViewById(R.id.add_off_button);

//        addOnBtn.setOnClickListenernew(new View.OnClickListener() {
//            @Override
//            public void onClick(android.view.View v) {
//                Intent intent = new Intent(context, AddOnDeviceActicity.class);
//                context.startActivity(intent);
//            }
//        });
    }
}