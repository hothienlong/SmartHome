package com.example.smarthome.Adapter;

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
import com.example.smarthome.Service.MQTTService;
import com.example.smarthome.Topic.RelayTopic;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.LightViewHolder> {

    Context context;
    List<Light> lstLight;
    private LightClickListener mLightClickListener;

    MQTTService mqttService;

    public LightAdapter(List<Light> lstLight) {
        this.lstLight = lstLight;
    }

    String topic = "bbc-led";

    @NonNull
    @NotNull
    @Override
    public LightViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_light_item, parent, false);

        mqttService = new MQTTService(context, topic);
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
                    // set image
                    holder.imgLight.setImageResource(R.drawable.ic_light_bulb_off);
                    light.setStatus(false);

                    // change status adafruit
                    RelayTopic relayTopic = new RelayTopic(Integer.toString(position), "0", "");
//                    Log.d("JJJ", relayTopic.toString());
                    mqttService.publishMessage(relayTopic.toString(), topic);
                }
                else {
                    holder.imgLight.setImageResource(R.drawable.ic_light_bulb_on);
                    light.setStatus(true);

                    RelayTopic relayTopic = new RelayTopic(Integer.toString(position), "1", "");
//                    Log.d("JJJ", relayTopic.toString());
                    mqttService.publishMessage(relayTopic.toString(), topic);
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