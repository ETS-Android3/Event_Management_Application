package com.example.eventmanagementapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagementapplication.Activities.ShopDetailsActivity;
import com.example.eventmanagementapplication.Activities.ShopImageActivity;
import com.example.eventmanagementapplication.Models.StaticRVModel;
import com.example.eventmanagementapplication.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{
    Context context;
    private ArrayList<StaticRVModel> items;
    int row_index=-1;

    public RecyclerAdapter(Context context,ArrayList<StaticRVModel> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stativ_rv_item,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        StaticRVModel currentItem = items.get(position);
        String mBase64string = currentItem.getImage();
        byte[] imageAsBytes = Base64.decode(mBase64string.getBytes(), Base64.DEFAULT);
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        holder.textView.setText(currentItem.getText());
        holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg);

        holder.Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg);
                Intent intent= new Intent(view.getContext(), ShopDetailsActivity.class);
                String Sposition = String.valueOf(position);
                intent.putExtra("position",Sposition);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                row_index = position;
//                notifyDataSetChanged();
//            }
//        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_selected_bg);
                Intent intent= new Intent(view.getContext(), ShopImageActivity.class);
                String Sposition = String.valueOf(position);
                intent.putExtra("position",Sposition);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView textView,Details;
        ImageView imageView;
        GridLayout linearLayout;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_shop);
            textView = itemView.findViewById(R.id.name_shop);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            Details=itemView.findViewById(R.id.details_shop);
        }
    }
}
