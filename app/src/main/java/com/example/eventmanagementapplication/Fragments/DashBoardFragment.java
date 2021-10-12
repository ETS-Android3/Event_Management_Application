package com.example.eventmanagementapplication.Fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.eventmanagementapplication.R;
import com.example.eventmanagementapplication.Adapters.RecyclerAdapter;
import com.example.eventmanagementapplication.Models.StaticRVModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.slidingrootnav.SlidingRootNav;

import java.util.ArrayList;

public class DashBoardFragment extends Fragment {
    LottieAnimationView lottieAnimationView;

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_MY_PROFILE = 2;
    private static final int POS_SETTINGS = 3;
    private static final int POS_ABOUT_US = 4;
    private static final int POS_LOGOUT = 6;

    private String[] screenTitle;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    ArrayList<StaticRVModel> item ;
    AutoCompleteTextView search_box;
    private DatabaseReference mUserDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.dashboard_fragment, container, false);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Toolbar toolbar = root.findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        search_box = (AutoCompleteTextView) root.findViewById(R.id.search_box);
        recyclerView= root.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUserDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/").getReference("ShopProfile");

        lottieAnimationView = root.findViewById(R.id.lottie_search);
        lottieAnimationView.playAnimation();
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);

        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "hi", Toast.LENGTH_SHORT).show();
                String searchText = search_box.getText().toString();
//                firebasrUserSearch(searchText);

            }
        });

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                populateSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        mUserDatabase.addListenerForSingleValueEvent(event);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("ShopProfile");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                item =new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String id = ds.child("id").getValue(String.class);
                    String image = ds.child("signup_shoplogo").getValue(String.class);
                    String shopName = ds.child("signup_shopname").getValue(String.class);
                    Log.e("shopName","onclcik :"+shopName);

                    item.add(new StaticRVModel(image, shopName));

                    recyclerView= root.findViewById(R.id.recycler);
                    recyclerAdapter = new RecyclerAdapter(getActivity(),item);
                    GridLayoutManager GridLayout = new GridLayoutManager(getActivity(), 2,GridLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(GridLayout);
                    recyclerView.setAdapter(recyclerAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

    private void populateSearch(DataSnapshot snapshot) {
        ArrayList<String> names= new ArrayList<>();
        if(snapshot.exists()){
            for (DataSnapshot ds: snapshot.getChildren()){
                String city_name = ds.child("signup_city").getValue(String.class);
                names.add(city_name);
            }

            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,names);
            search_box.setAdapter(adapter);
            search_box.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name =search_box.getText().toString();
                    searchUser(name);
                }
            });

        }else {
            Log.d("users","no data found");
        }
    }

    private void searchUser(String name) {
        Query query = mUserDatabase.orderByChild("signup_city").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    item =new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren()){
                        String image = ds.child("signup_shoplogo").getValue(String.class);
                        String shopName = ds.child("signup_shopname").getValue(String.class);

                        item.add(new StaticRVModel(image, shopName));

                        recyclerAdapter = new RecyclerAdapter(getActivity(),item);
                        GridLayoutManager GridLayout = new GridLayoutManager(getActivity(), 2,GridLayoutManager.VERTICAL,false);
                        recyclerView.setLayoutManager(GridLayout);
                        recyclerView.setAdapter(recyclerAdapter);
                    }
                }else {
                    Log.d("users","no data found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
