package com.example.smarthome.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smarthome.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

//import uart.terminal.androidstudio.com.myapplication.R;

public class LoginActivity extends AppCompatActivity {

    Button buttonGo;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        // Click Go button to go to home screen
        buttonGo = findViewById(R.id.button_go);
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeGasSettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();

        if (val.isEmpty()) {
            username.setError("Username cannot be empty.");
            return false;
        }  else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Password cannot be empty.");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        if (!validateUsername() || !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        final String userEnteredUsername = username.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String fullNameFromDB = snapshot.child(userEnteredUsername).child("full_name").getValue(String.class);
                        String addressFromDB = snapshot.child(userEnteredUsername).child("address").getValue(String.class);
                        String telFromDB = snapshot.child(userEnteredUsername).child("tel").getValue(String.class);

                        Intent intent = new Intent(LoginActivity.this, HomeGasSettingActivity.class);

//                        // Bundle data for passing to SettingFragment
//                        Bundle bundle = new Bundle();
//                        bundle.putString("full_name", fullNameFromDB);
//                        bundle.putString("address", addressFromDB);
//                        bundle.putString("tel", telFromDB);
//
//                        // Pass data to SettingFragment
//                        SettingFragment setting = new SettingFragment();
//                        setting.setArguments(bundle);

                        startActivity(intent);
                    } else {
                        password.setError("Wrong password.");
                        password.requestFocus();
                    }
                } else {
                    username.setError("No such user exist.");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}