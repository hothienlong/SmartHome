package com.example.smarthome.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarthome.R;

public class AddRoomActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextView tvChangeImageRoom;

    ImageView imgRoom;

    private static final int REQUEST_CODE_ROOM_IMAGE = 0x9345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        addControls();
        addEvents();
    }

    private void addEvents() {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true); // icon
        actionbar.setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvChangeImageRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRoomActivity.this, RoomImageActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ROOM_IMAGE);
            }
        });
    }

    private void addControls() {
        toolbar = findViewById(R.id.addRoomToolbar);
        tvChangeImageRoom = findViewById(R.id.tvChangeImageRoom);
        imgRoom = findViewById(R.id.imgRoom);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
        if (requestCode == REQUEST_CODE_ROOM_IMAGE) {

            // resultCode được set bởi DetailActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if (resultCode == Activity.RESULT_OK) {

                // Nhận dữ liệu từ Intent trả về
                final Integer result = data.getIntExtra("roomImage", R.drawable.bedroom);

                imgRoom.setImageResource(result);

            } else {
                // DetailActivity không thành công, không có data trả về.
            }
        }
    }
}