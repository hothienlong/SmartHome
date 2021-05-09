package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    Animation splashBgAnim, splashAppNameAnim;
    ImageView splashBg;
    TextView splashAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Animations
        splashBgAnim = AnimationUtils.loadAnimation(this, R.anim.splash_bg_animation);
        splashAppNameAnim = AnimationUtils.loadAnimation(this, R.anim.slpash_app_name_animation);

        // Hooks
        splashBg = findViewById(R.id.splash_bg);
        splashAppName = findViewById(R.id.splash_app_name);

        splashBg.setAnimation(splashBgAnim);
        splashAppName.setAnimation(splashAppNameAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}