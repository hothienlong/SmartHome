package com.example.smarthome.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.ScriptC;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Activity.RoomBigActivity;
import com.example.smarthome.Adaper.RoomAdapter;
import com.example.smarthome.Adaper.RoomBigAdapter;
import com.example.smarthome.Adaper.SceneAdapter;
import com.example.smarthome.Model.Room;
import com.example.smarthome.Model.Scene;
import com.example.smarthome.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View view;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView recyclerViewScene, recyclerViewRoom;
    ImageView imgViewAllRoom;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        addControls();
        init();
        addEvents();
        return view;
    }

    private void addEvents() {
        imgViewAllRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RoomBigActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        collapsingToolbarLayout.setTitle("Good Morning");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(238,238,238));
        collapsingToolbarLayout.setExpandedTitleColor(Color.rgb(238,238,238));

        // fake data

        // Recycler view scene
        ArrayList<Scene> lstScene = new ArrayList<>();
        lstScene.add(new Scene("Sleep", null));
        lstScene.add(new Scene("At work", null));
        lstScene.add(new Scene("Gym", null));
        lstScene.add(new Scene("Wake up", null));
        lstScene.add(new Scene("Movie", null));

        // tạo adapter
        SceneAdapter sceneAdapter = new SceneAdapter(lstScene);
        // performance
        recyclerViewScene.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewScene.setAdapter(sceneAdapter);

        // Recycler view room -----------
        ArrayList<Room> lstRoom = new ArrayList<>();
        lstRoom.add(new Room("Bedroom", null));
        lstRoom.add(new Room("Living room", null));
        lstRoom.add(new Room("Bath room", null));
        lstRoom.add(new Room("Kitchen", null));

        // tạo adapter
        RoomAdapter roomAdapter = new RoomAdapter(lstRoom);
        // performance
        recyclerViewRoom.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewRoom.setAdapter(roomAdapter);

    }

    private void addControls() {
        collapsingToolbarLayout = view.findViewById(R.id.collapseToolbarHome);
        recyclerViewScene = view.findViewById(R.id.recyclerViewScene);
        recyclerViewRoom = view.findViewById(R.id.recyclerViewRoom);
        imgViewAllRoom = view.findViewById(R.id.imgViewAllRoom);
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
////        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.action_bar, menu);
//    }

}
