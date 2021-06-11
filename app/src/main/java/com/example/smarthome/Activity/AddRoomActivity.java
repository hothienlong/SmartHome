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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarthome.Model.Room;
import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.Service.MQTTService;
import com.example.smarthome.SessionManagement;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class AddRoomActivity extends AppCompatActivity {

    DatabaseReference reference;
    Toolbar toolbar;

    TextView tvChangeImageRoom;

    ImageView imgRoom;
    EditText edtRoomName;

    MaterialButton btnSaveRoom;

    private static final int REQUEST_CODE_ROOM_IMAGE = 0x9345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        addControls();
        init();
        addEvents();
    }

    private void init(){
        Intent intent = getIntent();
        if (intent != null) {

            // get room info
            SessionManagement sessionManagement = SessionManagement.getInstance(this);
            String userJson = sessionManagement.getSession();

            if (userJson != null) {
                Gson gson = new Gson();
                User user = gson.fromJson(userJson, User.class);

                reference = FirebaseDatabase.getInstance()
                        .getReference("users")
                        .child(user.getUsername())
                        .child("house")
                        .child("room")
                ;

            }
        }
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

        btnSaveRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomId = reference.push().getKey(); //this returns the unique key generated by firebase
                Room room = new Room(
                        roomId,
                        edtRoomName.getText().toString(),
                        Long.parseLong(imgRoom.getTag().toString()),
                       false
                );

                reference.child(roomId).setValue(room);

                // Move to HomeGasActivity
                Intent intent = new Intent(AddRoomActivity.this, HomeGasSettingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        toolbar = findViewById(R.id.addRoomToolbar);
        tvChangeImageRoom = findViewById(R.id.tvChangeImageRoom);
        imgRoom = findViewById(R.id.imgRoom);
        btnSaveRoom = findViewById(R.id.btnSaveRoom);
        edtRoomName = findViewById(R.id.edtRoomName);
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

                // set room image
                imgRoom.setImageResource(result);
                // set tag to getImageResource by tag!!
                imgRoom.setTag(result);

            } else {
                // DetailActivity không thành công, không có data trả về.
            }
        }
    }
}