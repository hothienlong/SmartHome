package com.example.smarthome.Adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Model.Room;
import com.example.smarthome.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    Context context;
    ArrayList<Room> lstRoom;

    public RoomAdapter(ArrayList<Room> lstRoom) {
        this.lstRoom = lstRoom;
    }

    @NonNull
    @NotNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_room_item, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoomViewHolder holder, int position) {
        Room room = lstRoom.get(position);

        holder.tvRoomName.setText(room.getName());

    }

    @Override
    public int getItemCount() {
        return lstRoom == null ? 0 : lstRoom.size();
    }

    class RoomViewHolder extends RecyclerView.ViewHolder{

        TextView tvRoomName;
        ImageView imgRoom;

        // ánh xạ
        public RoomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvRoomName = (TextView) itemView.findViewById(R.id.tvRoomName);
            imgRoom = (ImageView) itemView.findViewById(R.id.imgRoom);
        }
    }
}
