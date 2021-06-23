package com.example.smarthome.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.example.smarthome.Model.Noti;
import com.example.smarthome.Model.User;
import com.example.smarthome.Model.Warning;
import com.example.smarthome.R;
import com.example.smarthome.SessionManagement;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WarningAdapter extends RecyclerView.Adapter<WarningAdapter.WarningViewHolder>{

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    Context context;
    ArrayList<Warning> mArrayWarning;

    public WarningAdapter(Context context, ArrayList<Warning> mArrayWarning) {
        this.context = context;
        this.mArrayWarning = mArrayWarning;
    }

    @NonNull
    @NotNull
    @Override
    public WarningViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.layout_list_warning,parent,false);
        return new WarningViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WarningViewHolder holder, int position) {
        holder.imgWarning.setImageResource(mArrayWarning.get(position).getImage());
        holder.name.setText(mArrayWarning.get(position).getName());
        holder.text.setText(mArrayWarning.get(position).getText());

        holder.imgWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayWarning.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyDataSetChanged();

                SessionManagement sessionManagement = SessionManagement.getInstance(context);
                String userJson = sessionManagement.getSession();
                Gson gson = new Gson();
                User user = gson.fromJson(userJson, User.class);
//        user.getUsername()

                DatabaseReference notiData = mData.child("users").child(user.getUsername()).child("house").child("noti");
                notiData.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        Noti noti = snapshot.getValue(Noti.class);
                        if (noti.mContent.equals(holder.name.getText()) && noti.mMoment.equals(holder.text.getText())){
                            snapshot.getRef().setValue(null);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayWarning.size();
    }


    class WarningViewHolder extends RecyclerView.ViewHolder{
        ImageView imgWarning;
        TextView name,text;
        public WarningViewHolder(@NonNull View itemView){
            super(itemView);
            imgWarning = (ImageView) itemView.findViewById(R.id.warningImage);
            name = (TextView) itemView.findViewById(R.id.warningText);
            text = (TextView) itemView.findViewById(R.id.warningTime);
        }

    }
}
