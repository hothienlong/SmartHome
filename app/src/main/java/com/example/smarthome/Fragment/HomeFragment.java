package com.example.smarthome.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.ScriptC;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adaper.SceneAdapter;
import com.example.smarthome.Model.Scene;
import com.example.smarthome.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View view;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView recyclerViewScene;

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

        // fake data
        ArrayList<Scene> lstScene = new ArrayList<>();
        lstScene.add(new Scene(null, "Sleep"));
        lstScene.add(new Scene(null, "At work"));
        lstScene.add(new Scene(null, "Gym"));
        lstScene.add(new Scene(null, "Wake up"));
        lstScene.add(new Scene(null, "Movie"));

        // tạo adapter
        SceneAdapter sceneAdapter = new SceneAdapter(lstScene);
        // performance
        recyclerViewScene.setHasFixedSize(true);
        // set adapter cho Recycler View
        recyclerViewScene.setAdapter(sceneAdapter);

    }

    private void addControls() {
        collapsingToolbarLayout = view.findViewById(R.id.collapseToolbarHome);
        recyclerViewScene = view.findViewById(R.id.recyclerViewScene);
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
////        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.action_bar, menu);
//    }

}
