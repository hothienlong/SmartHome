package com.example.smarthome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Activity.InRoomActivity;
import com.example.smarthome.Model.Room;
import com.example.smarthome.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListRoomBigAdapter extends RecyclerView.Adapter<ListRoomBigAdapter.RoomViewHolder> {

    Context context;
    ArrayList<Room> lstRoom;

    public ListRoomBigAdapter(ArrayList<Room> lstRoom) {
        this.lstRoom = lstRoom;
    }

    @NonNull
    @NotNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_list_room_item_big, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoomViewHolder holder, int position) {
        Room room = lstRoom.get(position);

        holder.tvRoomName.setText(room.getName());

        holder.toggleAuto.setChecked(room.getMode());

        holder.roomId = room.getId();


    }

    @Override
    public int getItemCount() {
        return lstRoom == null ? 0 : lstRoom.size();
    }

    class RoomViewHolder extends RecyclerView.ViewHolder{

        TextView tvRoomName, tvDevicesOn;
        ImageView imgRoom;
        ToggleButton toggleAuto;
        String roomId;

        // ánh xạ
        public RoomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvRoomName = (TextView) itemView.findViewById(R.id.tvRoomName);
            imgRoom = (ImageView) itemView.findViewById(R.id.imgRoom);
            tvDevicesOn = (TextView) itemView.findViewById(R.id.tvRoomDevices);
            toggleAuto = (ToggleButton) itemView.findViewById(R.id.toggleAuto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InRoomActivity.class);
                    intent.putExtra("ROOMID", roomId);
                    context.startActivity(intent);
                }
            });
        }
    }
}