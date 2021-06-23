package com.example.smarthome.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarthome.R;

//import uart.terminal.androidstudio.com.myapplication.R;

import com.example.smarthome.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    Animation splashBgAnim, splashAppNameAnim;
    ImageView splashBg;
    TextView splashAppName;

//    PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(INTENT_ACTION_GRANT_USB), 0);
//    manager.requestPermission(driver.getDevice(), usbPermissionIntent);
//    manager.requestPermission(driver.getDevice(), PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0));

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

//    @Override
//    public void onNewData(final byte[] data) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                txtOut.append(Arrays.toString(data));
//            }
//        });
//    }

}