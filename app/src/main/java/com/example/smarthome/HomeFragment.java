package com.example.smarthome;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    View view;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        addControls();
        init();
        return view;
    }

    private void init() {
        collapsingToolbarLayout.setTitle("Good Morning");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(238,238,238));
        collapsingToolbarLayout.setExpandedTitleColor(Color.rgb(238,238,238));
    }

    private void addControls() {
        collapsingToolbarLayout = view.findViewById(R.id.collapseToolbarHome);
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
////        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.action_bar, menu);
//    }

}
