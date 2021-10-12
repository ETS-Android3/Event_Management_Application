package com.example.eventmanagementapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagementapplication.Activities.SetEventDetailsActivity;
import com.example.eventmanagementapplication.Activities.SubCatImageActivity;
import com.example.eventmanagementapplication.Models.AddDecorationModel;
import com.example.eventmanagementapplication.Models.itemSubCatModel;
import com.example.eventmanagementapplication.R;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemSubCatAdapter extends RecyclerView.Adapter<ItemSubCatAdapter.ViewHolder> {
    private ArrayList<itemSubCatModel> listdata;
    Context context;
    public ItemSubCatAdapter(ArrayList<itemSubCatModel> listdata,Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_subcategory, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        itemSubCatModel currentItem = listdata.get(position);

        holder.available_service_name.setText(currentItem.getAvailable_service_name());
        String mBase64string = currentItem.getDecora_image();
        byte[] imageAsBytes = Base64.decode(mBase64string.getBytes(), Base64.DEFAULT);
        Log.e("mBase64string","onclick :"+imageAsBytes);
        holder.decora_image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
//        holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg);
        holder.decora_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), SubCatImageActivity.class);
                String getImage = String.valueOf(position);
                intent.putExtra("getImage",getImage);
                v.getContext().startActivity(intent);
            }
        });
        holder.money_end.setText(currentItem.getMoney_end());
        holder.money_start.setText(currentItem.getMoney_start());
        holder.set_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(), SetEventDetailsActivity.class);
                String Sposition = String.valueOf(position);
                intent.putExtra("position",Sposition);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView available_service_name,money_end,money_start;
        ImageView decora_image;
        RelativeLayout linearLayout;
        Button set_meeting;

        public ViewHolder(@NonNull View listItem) {
            super(listItem);
            this.available_service_name = (TextView) listItem.findViewById(R.id.available_service_name);
            this.decora_image= (ImageView) listItem.findViewById(R.id.decora_image);
            linearLayout=(RelativeLayout) listItem.findViewById(R.id.linearLayout_item);
            this.money_end = (TextView) listItem.findViewById(R.id.money_end);
            this.money_start = (TextView) listItem.findViewById(R.id.money_start);
            this.set_meeting = (Button) listItem.findViewById(R.id.set_meeting);
        }
    }
}
