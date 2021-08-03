package com.example.smarthome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Model.Light;
import com.example.smarthome.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OnOffAdapter extends RecyclerView.Adapter<OnOffAdapter.OnOffViewHolder> {

    Context context;
    List<Light> lstLight;
    private DeviceAdapter.LightClickListener mLightClickListener;

    public OnOffAdapter(List<Light> lstLight) {
        this.lstLight = lstLight;
    }

    @NonNull
    @NotNull
    @Override
    public OnOffAdapter.OnOffViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_scene_item_task, parent, false);

        return new OnOffAdapter.OnOffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OnOffAdapter.OnOffViewHolder holder, int position) {
        Light light = lstLight.get(position);

        holder.itemName.setText(light.getName());
    }

    @Override
    public int getItemCount() {
        return lstLight == null ? 0 : lstLight.size();
    }

    public interface LightClickListener{
        // LightActivity sẽ định nghĩa
        void onLightClick();
    }

    class OnOffViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;

        public OnOffViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name);
        }
    }
}
