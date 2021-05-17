package com.example.smarthome.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthome.R;

//import uart.terminal.androidstudio.com.myapplication.R;

public class SettingFragment extends Fragment {

//    TextView fullName, address, tel;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//        // Retrieve all TextInputEditText
//        fullName = getView().findViewById(R.id.full_name);
//        address = getView().findViewById(R.id.address);
//        tel = getView().findViewById(R.id.tel);
//
//        // Get data passed from LoginActivity
//        String user_fullName = getArguments().getString("full_name");
//        String user_address = getArguments().getString("address");
//        String user_tel = getArguments().getString("tel");
//
//        // Show user profile
//        fullName.setText(user_fullName);
//        address.setText(user_address);
//        tel.setText(user_tel);

        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
}
