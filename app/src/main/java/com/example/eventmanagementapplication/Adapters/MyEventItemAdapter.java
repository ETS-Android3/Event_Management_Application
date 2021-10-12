package com.example.eventmanagementapplication.Adapters;

import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagementapplication.Models.myEventItemsModel;
import com.example.eventmanagementapplication.R;

import java.util.ArrayList;

public class MyEventItemAdapter extends RecyclerView.Adapter<MyEventItemAdapter.ViewHolder> {
    private ArrayList<myEventItemsModel> listdata;
    private final ClickListener listener;

    public MyEventItemAdapter(ArrayList<myEventItemsModel> listdata, ClickListener listener) {
        this.listdata = listdata;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.my_event_item, parent, false);
       ViewHolder viewHolder = new ViewHolder(listItem,listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        myEventItemsModel currentItem = listdata.get(position);
        holder.my_event_date.setText(currentItem.getMy_event_date());
        holder.my_event_time.setText(currentItem.getMy_event_time());
        holder.my_event_decoration_name.setText(currentItem.getMy_event_decoration_name());

        String mBase64string = currentItem.getDecoration_pic_image();
        byte[] imageAsBytes = Base64.decode(mBase64string.getBytes(), Base64.DEFAULT);
        holder.decora_image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        holder.relativeLayout.setBackgroundResource(R.drawable.static_rv_bg);

        Log.e(" currentItem getIsOrderConfirm adapter", "onclick :" + currentItem.getIsOrderConfirm());
        if (currentItem.getIsOrderConfirm().equals("1")) {
            holder.available_service_name.setText("We have successfully registered your order.");
            holder.accepted_btn.setVisibility(View.VISIBLE);
            holder.ok_btn.setVisibility(View.GONE);
            holder.cancel_btn.setVisibility(View.GONE);
        }else if(currentItem.getIsOrderConfirm().equals("2")){
            holder.available_service_name.setText("Your order is pending wait until vendor is confirm.....");
            holder.cancel_btn.setVisibility(View.VISIBLE);
            holder.accepted_btn.setVisibility(View.GONE);
            holder.ok_btn.setVisibility(View.GONE);
        }else if (currentItem.getIsOrderConfirm().equals("0")){
            holder.available_service_name.setText("Sorry, Your order is rejected.");
            holder.ok_btn.setVisibility(View.VISIBLE);
            holder.cancel_btn.setVisibility(View.GONE);
            holder.accepted_btn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView my_event_date,my_event_time,my_event_decoration_name,available_service_name;
        Button cancel_btn,accepted_btn,ok_btn;
        RelativeLayout relativeLayout;
        ImageView decora_image;
        public MyEventItemAdapter.ClickListener clickListener;

        public ViewHolder(View listItem, ClickListener clickListener) {
            super(listItem);
            this.cancel_btn=(Button)listItem.findViewById(R.id.cancel_btn);
            this.accepted_btn=(Button)listItem.findViewById(R.id.accepted_btn);
            this.ok_btn=(Button)listItem.findViewById(R.id.ok_btn);
            this.my_event_date=(TextView)listItem.findViewById(R.id.my_event_date);
            this.my_event_time=(TextView)listItem.findViewById(R.id.my_event_time);
            this.available_service_name=(TextView)listItem.findViewById(R.id.available_service_name);
            this.my_event_decoration_name =(TextView)listItem.findViewById(R.id.my_event_decoration_name);
            this.decora_image = (ImageView)listItem.findViewById(R.id.decora_image);
            this.relativeLayout=(RelativeLayout)listItem.findViewById(R.id.relativeLayout);

            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            clickListener.onButtonClick(position);
                        }
                    }
                }
            });

            ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        clickListener.onOkButtonClick(position);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            clickListener.onButtonClick(getAdapterPosition());
            clickListener.onOkButtonClick(getAdapterPosition());
        }
    }
    public interface ClickListener {

        void onButtonClick(int position);
        void onOkButtonClick(int position);

    }

}
