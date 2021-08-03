package com.example.smarthome.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Activity.AddSceneActivity;
import com.example.smarthome.Model.Light;
import com.example.smarthome.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    Context context;
    List<Light> lstLight;
    private LightClickListener mLightClickListener;

    public DeviceAdapter(List<Light> lstLight) {
        this.lstLight = lstLight;
    }

    @NonNull
    @NotNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_scene_add_device, parent, false);

        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DeviceViewHolder holder, int position) {
        Light light = lstLight.get(position);

        holder.deviceName.setText(light.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = holder.itemView.findViewById(R.id.checkBox);

                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                }
                else {
                    checkBox.setChecked(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstLight == null ? 0 : lstLight.size();
    }

    public interface LightClickListener{
        // LightActivity sẽ định nghĩa
        void onLightClick();
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder {

        TextView deviceName;

        public DeviceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            deviceName = itemView.findViewById(R.id.deviceName);
        }
    }
}
