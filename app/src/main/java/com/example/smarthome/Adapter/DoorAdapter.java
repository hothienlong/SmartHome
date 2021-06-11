package com.example.smarthome.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Model.Door;
import com.example.smarthome.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DoorAdapter extends RecyclerView.Adapter<DoorAdapter.MyViewHolder> {
    private Context myCtx;
    private List<Door> listDoor;

    public DoorAdapter(Context ctx, List<Door> initList) {
        myCtx = ctx;
        listDoor = initList;
    }

    public void setListDoor(List<Door> newList) {
        listDoor = newList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(myCtx);
        View view = inflater.inflate(R.layout.door_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.doorImg.setImageResource(listDoor.get(position).getDoorImgId());
        holder.doorTitle.setText(listDoor.get(position).getDoorName());
    }

    @Override
    public int getItemCount() {
        return listDoor.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView doorTitle;
        public ImageView doorImg;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            doorImg = itemView.findViewById(R.id.doorImg);
            doorTitle = itemView.findViewById(R.id.doorTitle);
        }
    }

}
