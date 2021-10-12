package com.example.eventmanagementapplication.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagementapplication.Adapters.MyEventItemAdapter;
import com.example.eventmanagementapplication.Models.myEventItemsModel;
import com.example.eventmanagementapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyEventsFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyEventItemAdapter myEventItemAdapter;
    ArrayList<myEventItemsModel> list;
    private String userID;
    private FirebaseAuth auth;
    private FirebaseUser users;
    private TextView available_service_name;
    private Button cancel_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.my_events_fragment, container, false);

        available_service_name = root.findViewById(R.id.available_service_name);
        cancel_btn = root.findViewById(R.id.cancel_btn);

        auth = FirebaseAuth.getInstance();
        users =  auth.getCurrentUser();
        userID =users.getUid();
        Log.e("user id :","onclick :"+userID);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("SetEventDetails");
        databaseReference.orderByChild("customerID").equalTo(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String isOrderConfirm = ds.child("isOrderConfirm").getValue(String.class);
                    String eventDetailsID = ds.child("eventDetailsID").getValue(String.class);
                    Log.e(" isOrderConfirm","onclick :"+isOrderConfirm);

                        String meetingDate = ds.child("meetingDate").getValue(String.class);
                        String meetingTime = ds.child("meetingTime").getValue(String.class);
                        String addDecorationID = ds.child("addDecorationID").getValue(String.class);

                        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                        DatabaseReference databaseReference2 = firebaseDatabase2.getReference();
                        databaseReference2.child("AddDecoration").orderByChild("addDecorationID").equalTo(addDecorationID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    String decoration_name = ds.child("decoration_name").getValue(String.class);
                                    String decoration_pic_image = ds.child("decoration_pic_image").getValue(String.class);
                                    Log.e("decoration_name", "onclick :" + decoration_name);


                                    list.add(new myEventItemsModel(meetingDate, meetingTime, decoration_name,userID,isOrderConfirm,eventDetailsID,decoration_pic_image));
                                    recyclerView = root.findViewById(R.id.my_event_recycler);
                                    myEventItemAdapter = new MyEventItemAdapter(list, new MyEventItemAdapter.ClickListener() {
                                        @Override
                                        public void onButtonClick(int position) {
                                            myEventItemsModel myEventItemsModel = list.get(position);
                                            FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                                            DatabaseReference databaseReference1 = firebaseDatabase1.getReference();
                                            databaseReference1.child("SetEventDetails").child(myEventItemsModel.getEventDetailsID()).removeValue();
                                        }

                                        @Override
                                        public void onOkButtonClick(int position) {
                                            myEventItemsModel myEventItemsModel = list.get(position);
                                            FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                                            DatabaseReference databaseReference1 = firebaseDatabase1.getReference();
                                            databaseReference1.child("SetEventDetails").child(myEventItemsModel.getEventDetailsID()).removeValue();
                                        }
                                    });
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    recyclerView.setAdapter(myEventItemAdapter);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }
}
