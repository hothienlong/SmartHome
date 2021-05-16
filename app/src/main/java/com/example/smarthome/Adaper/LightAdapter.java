package com.example.smarthome.Adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Model.Light;
import com.example.smarthome.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.LightViewHolder> {

    Context context;
    List<Light> lstLight;
    private LightClickListener mLightClickListener;

    public LightAdapter(List<Light> lstLight) {
        this.lstLight = lstLight;
    }

    @NonNull
    @NotNull
    @Override
    public LightViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_light_item, parent, false);
        return new LightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LightViewHolder holder, int position) {
        Light light = lstLight.get(position);

        holder.tvLightName.setText(light.getName());

        if(light.getStatus()){
            holder.imgLight.setImageResource(R.drawable.ic_light_bulb_on);
        }
        else {
            holder.imgLight.setImageResource(R.drawable.ic_light_bulb_off);
        }

        holder.imgLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(light.getStatus()){
                    holder.imgLight.setImageResource(R.drawable.ic_light_bulb_off);
                    light.setStatus(false);
                }
                else {
                    holder.imgLight.setImageResource(R.drawable.ic_light_bulb_on);
                    light.setStatus(true);
                }
                mLightClickListener.onLightClick();
            }
        });
    }

    public interface LightClickListener{
        // LightActivity sẽ định nghĩa
        void onLightClick();
    }

    public void setmLightClickListener(LightClickListener lightClickListener){
        mLightClickListener = lightClickListener;
    }

    @Override
    public int getItemCount() {
        return lstLight == null ? 0 : lstLight.size();
    }

    // lưu lại view để khi gọi lại chỉ cần lấy ra
    class LightViewHolder extends RecyclerView.ViewHolder{

        ImageView imgLight;
        TextView tvLightName;

        // ánh xạ
        public LightViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgLight = itemView.findViewById(R.id.imgLight);
            tvLightName = itemView.findViewById(R.id.tvLightName);
        }
    }
}
