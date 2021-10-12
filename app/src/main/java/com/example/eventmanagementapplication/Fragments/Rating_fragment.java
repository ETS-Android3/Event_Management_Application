package com.example.eventmanagementapplication.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventmanagementapplication.Models.FeedbackModel;
import com.example.eventmanagementapplication.Models.RatingModel;
import com.example.eventmanagementapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smileyrating.SmileyRating;

public class Rating_fragment extends Fragment {
    TextView title_rating,rating_qtn;
    ImageView arrow_rating;
    SmileyRating ratingBar;
    Button rating_btn;
    float v=0;
    private FirebaseAuth auth;
    private FirebaseUser users;
    private String userID;
    String currentUserEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.rating_fragment, container, false);

        title_rating = root.findViewById(R.id.title_rating);
        rating_btn = root.findViewById(R.id.rating_btn);
        arrow_rating = root.findViewById(R.id.arrow_rating);
        ratingBar = root.findViewById(R.id.ratingBar);
        rating_qtn = root.findViewById(R.id.rating_qtn);

        auth = FirebaseAuth.getInstance();
        users =  auth.getCurrentUser();
        userID =users.getUid();
        Log.e("user id :","onclick :"+userID);

        title_rating.setTranslationZ(800);
        arrow_rating.setTranslationX(800);
        rating_qtn.setTranslationX(800);
        rating_btn.setTranslationY(800);

        title_rating.setAlpha(v);
        arrow_rating.setAlpha(v);
        rating_qtn.setAlpha(v);
        rating_btn.setAlpha(v);

        title_rating.animate().translationZBy(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        arrow_rating.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        rating_qtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        rating_btn.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();

        ratingBar.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {
            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                int rating_i = type.getRating();
                String rating = String.valueOf(rating_i);
//                Toast.makeText(getActivity(), rating_i+"Star", Toast.LENGTH_SHORT).show();
                rating_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            if(TextUtils.isEmpty(rating)){
                                Toast.makeText(getActivity(), "Field can not be empty", Toast.LENGTH_SHORT).show();
                            }else {
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                                DatabaseReference databaseReference = firebaseDatabase.getReference();
                                databaseReference.child("UsersRegistration").child(userID).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        currentUserEmail = snapshot.child("email").getValue(String.class);
                                        Log.e("current user email", "onclick :" + currentUserEmail);
//                                        String rating = String.valueOf(rating_i);
                                        Log.e("current user rating", "onclick :" + rating);
                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/").getReference().child("Rating_tbl");
                                        String ratingID = mDatabase.push().getKey();
                                        RatingModel ratingModel = new RatingModel(ratingID, currentUserEmail, rating);
                                        mDatabase.child(ratingID).setValue(ratingModel);
                                        Toast.makeText(getContext(), "Successfully added your Rating", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }catch (NullPointerException e){
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Empty credential", Toast.LENGTH_SHORT).show();
                    }
                    }
                });


            }
        });




        return root;
    }
}
