package com.example.smarthome.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthome.Activity.HomeGasSettingActivity;
import com.example.smarthome.Activity.LoginActivity;
import com.example.smarthome.Model.User;
import com.example.smarthome.R;
import com.example.smarthome.SessionManagement;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

//import uart.terminal.androidstudio.com.myapplication.R;

public class SettingFragment extends Fragment {

    View v;
    TextInputLayout edtFullName, address, tel;
    TextView tvFullName;
    MaterialButton btnExit;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Retrieve all TextInputEditText
        v = inflater.inflate(R.layout.fragment_setting, container, false);

        addControls();
        showUserProfile();

//        // Get data passed from LoginActivity
//        String user_fullName = getArguments().getString("full_name");
//        String user_address = getArguments().getString("address");
//        String user_tel = getArguments().getString("tel");
//
//        // Show user profile
//        tvFullName.setText(user_fullName);
//        edtFullName.getEditText().setText(user_fullName);
//        address.getEditText().setText(user_address);
//        tel.getEditText().setText(user_tel);


        addEvents();

        return v;
    }

    private void showUserProfile() {
        SessionManagement sessionManagement = SessionManagement.getInstance(getContext());
        String userJson = sessionManagement.getSession();

        if(userJson != null){
            Gson gson = new Gson();
            User user = gson.fromJson(userJson, User.class);
            tvFullName.setText(user.getFull_name());
            edtFullName.getEditText().setText(user.getFull_name());
            address.getEditText().setText(user.getAddress());
            tel.getEditText().setText(user.getTel());
        }
    }

    private void addControls() {
        tvFullName = v.findViewById(R.id.tvFullName);
        edtFullName = v.findViewById(R.id.edtFullName);
        address = v.findViewById(R.id.address);
        tel = v.findViewById(R.id.tel);
        btnExit = v.findViewById(R.id.setting_exit_button);
    }

    private void addEvents() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove session
                SessionManagement sessionManagement = SessionManagement.getInstance(getContext());
                sessionManagement.removeSession();
                // Move to LoginActivity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}