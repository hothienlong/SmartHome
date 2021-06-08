package com.example.smarthome.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Activity.LightActivity;
import com.example.smarthome.Model.Light;
import com.example.smarthome.R;
import com.example.smarthome.Service.MQTTService;
import com.example.smarthome.Topic.LightRelayMessage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.LightViewHolder> {

    Context context;
    List<Light> lstLight;
//    public Boolean lightSwitch;
    private LightClickListener mLightClickListener;

    ArrayList<LightViewHolder> mLightViewHolders = new ArrayList<>();

    MQTTService mqttService;

    public LightAdapter(List<Light> lstLight) {
        this.lstLight = lstLight;
    }

//    public LightAdapter(List<Light> lstLight) {
//        this.lstLight = lstLight;
//    }


    @NonNull
    @NotNull
    @Override
    public LightViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_light_item, parent, false);

        mqttService = new MQTTService(context, context.getResources().getString(R.string.light_topic));

        return new LightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LightViewHolder holder, int position) {
        mLightViewHolders.add(holder);

        Light light = lstLight.get(position);
        Log.d(getClass().getName(), light.toString());

        holder.tvLightName.setText(light.getName());

        if(!LightActivity.mLightSwitch && light.getStatus()){
            holder.imgLight.setImageResource(R.drawable.ic_light_bulb_on);
        }
        else {
            holder.imgLight.setImageResource(R.drawable.ic_light_bulb_off);
        }

        holder.imgLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LightActivity.mLightSwitch){
                    Toast.makeText(context, "Please turn off switch before turn on the light", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(light.getStatus()){
                        // set image
                        holder.imgLight.setImageResource(R.drawable.ic_light_bulb_off);
                        light.setStatus(false);

                        // change status adafruit
                        LightRelayMessage lightRelayMessage = new LightRelayMessage(
                                light.getId(),
                                "0",
                                ""
                        );
//                    Log.d(this.getClass().getName(), relayTopic.toString());
                        mqttService.publishMessage(
                                lightRelayMessage.toString(),
                                context.getResources().getString(R.string.light_topic)
                        );
                    }
                    else {
                        holder.imgLight.setImageResource(R.drawable.ic_light_bulb_on);
                        light.setStatus(true);


                        LightRelayMessage lightRelayMessage = new LightRelayMessage(
                                light.getId(),
                                "1",
                                ""
                        );
//                    Log.d(this.getClass().getName(), relayTopic.toString());
                        mqttService.publishMessage(
                                lightRelayMessage.toString(),
                                context.getResources().getString(R.string.light_topic)
                        );
                    }
                    mLightClickListener.onLightClick();
                }
            }

        });
    }

    public void turnOnLightSwitch(){
        for(int i=0; i < lstLight.size(); i++){
            if(lstLight.get(i).getStatus()){
                // set image
                mLightViewHolders.get(i).imgLight.setImageResource(R.drawable.ic_light_bulb_off);
//                lstLight.get(i).setStatus(false);

                // change status adafruit
                LightRelayMessage lightRelayMessage = new LightRelayMessage(
                        lstLight.get(i).getId(),
                        "0",
                        ""
                );
//                    Log.d(this.getClass().getName(), relayTopic.toString());
                mqttService.publishMessage(
                        lightRelayMessage.toString(),
                        context.getResources().getString(R.string.light_topic)
                );
            }
        }
    }

    public void turnOffLightSwitch(){
        for(int i=0; i < lstLight.size(); i++){
            if(lstLight.get(i).getStatus()){
                // set image
                mLightViewHolders.get(i).imgLight.setImageResource(R.drawable.ic_light_bulb_off);
//                lstLight.get(i).setStatus(false);

                // change status adafruit
                LightRelayMessage lightRelayMessage = new LightRelayMessage(
                        lstLight.get(i).getId(),
                        "1",
                        ""
                );
//                    Log.d(this.getClass().getName(), relayTopic.toString());
                mqttService.publishMessage(
                        lightRelayMessage.toString(),
                        context.getResources().getString(R.string.light_topic)
                );
            }
        }
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
