package com.example.smarthome.Adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Activity.RoomImageActivity;
import com.example.smarthome.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoomImageAdapter extends RecyclerView.Adapter<RoomImageAdapter.RoomImageViewHolder> {

    Context context;
    ArrayList<Integer> lstRoomImageId;

    public RoomImageAdapter(ArrayList<Integer> lstRoomImageId) {
        this.lstRoomImageId = lstRoomImageId;
    }

    @NonNull
    @NotNull
    @Override
    public RoomImageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_room_image_item, parent, false);
        return new RoomImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoomImageViewHolder holder, int position) {
        holder.imgRoomImage.setImageResource(lstRoomImageId.get(position));

        // set tag to getImageResource by tag!!
        holder.imgRoomImage.setTag(lstRoomImageId.get(position));

        holder.imgRoomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent mới để chứa dữ liệu trả về
                Intent data = new Intent();

                // Truyền data vào intent
                data.putExtra("roomImage", lstRoomImageId.get(position));

                // Đặt resultCode là Activity.RESULT_OK to
                // thể hiện đã thành công và có chứa kết quả trả về
                ((RoomImageActivity) context).setResult(Activity.RESULT_OK, data);

                // gọi hàm finish() để đóng Activity hiện tại và trở về MainActivity.
                ((RoomImageActivity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstRoomImageId == null ? 0 : lstRoomImageId.size();
    }

    class RoomImageViewHolder extends RecyclerView.ViewHolder{
        ImageView imgRoomImage;

        public RoomImageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgRoomImage = itemView.findViewById(R.id.imgRoomImage);
        }
    }

}
