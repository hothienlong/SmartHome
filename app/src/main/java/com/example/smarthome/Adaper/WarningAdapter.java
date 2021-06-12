package com.example.smarthome.Adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.smarthome.Model.Warning;
import com.example.smarthome.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WarningAdapter extends RecyclerView.Adapter<WarningAdapter.WarningViewHolder>{

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
