package com.example.eventmanagementapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagementapplication.Activities.SubCatActivity;
import com.example.eventmanagementapplication.Models.serviceCategoryModel;
import com.example.eventmanagementapplication.R;

import java.util.ArrayList;

public class ServiceCatAdapter extends RecyclerView.Adapter<ServiceCatAdapter.ViewHolder> {
    private ArrayList<serviceCategoryModel> listdata;
    Context context;

    public ServiceCatAdapter(ArrayList<serviceCategoryModel> listdata) {
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.service_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        serviceCategoryModel currentItem = listdata.get(position);
        Log.e("currentItem.getCat_name","onclick :"+currentItem.getCat_name());

        holder.cat_name.setText(currentItem.getCat_name());
//        holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg);
        holder.cat_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                SubCatFragment myFragment = new SubCatFragment();
//                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, myFragment).addToBackStack(null).commit();

                Intent intent= new Intent(view.getContext(), SubCatActivity.class);
                String Sposition = currentItem.getCat_name();
                intent.putExtra("position",Sposition);
                view.getContext().startActivity(intent);


            }
        });
//        holder.cat_recycler.setImageResource(listdata[position].getImgId());

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
         TextView cat_name,empty_event;
        RecyclerView cat_recycler;
        RelativeLayout linearLayout;
        public ViewHolder(View listItem) {
            super(listItem);
            this.cat_name = (TextView) listItem.findViewById(R.id.cat_name);
//            this.cat_recycler =(RecyclerView)listItem.findViewById(R.id.cat_recycler);
            this.linearLayout = (RelativeLayout) listItem.findViewById(R.id.linearLayout);
        }
    }
}
