package com.example.smarthome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Activity.AddSceneActivity;
import com.example.smarthome.Activity.HomeGasSettingActivity;
import com.example.smarthome.Activity.LoginActivity;
import com.example.smarthome.Model.Scene;
import com.example.smarthome.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SceneAdapter extends RecyclerView.Adapter<SceneAdapter.SceneViewHolder> {

    Context context;
    ArrayList<Scene> lstScene;

    public SceneAdapter(ArrayList<Scene> lstScene) {
        this.lstScene = lstScene;
    }

    @NonNull
    @NotNull
    @Override
    public SceneViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_scene_item, parent, false);
        return new SceneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SceneViewHolder holder, int position) {
        Scene scene = lstScene.get(position);

        holder.tvSceneName.setText(scene.getName());


//        try {
//            Picasso.get().load(scene.getLogo()).into(holder.imgScene);
//        }
//        catch (IllegalArgumentException e){
//            Log.d("PICASSO", e.getMessage());
//        }
    }

    @Override
    public int getItemCount() {
        return lstScene == null ? 0 : lstScene.size();
    }

    class SceneViewHolder extends RecyclerView.ViewHolder{

        TextView tvSceneName;
        ImageView imgScene;

        public SceneViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvSceneName = (TextView) itemView.findViewById(R.id.tvSceneName);
            imgScene = (ImageView) itemView.findViewById(R.id.imgScene);
        }
    }
}
