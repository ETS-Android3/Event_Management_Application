package com.example.eventmanagementapplication.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventmanagementapplication.Models.FeedbackModel;
import com.example.eventmanagementapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedback_fragment extends Fragment {
    float v=0;
    TextView title_feedback,feedback_qtn;
    EditText feedback;
    ImageView arrow;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6,checkBox7,checkBox8;
    Button feedback_btn;
    private FirebaseAuth auth;
    private FirebaseUser users;
    private String userID;
    String currentUserEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.feedback_fragment, container, false);

        title_feedback = root.findViewById(R.id.title_feedback);
        feedback_qtn = root.findViewById(R.id.feedback_qtn);
        feedback = root.findViewById(R.id.feedback);
        arrow = root.findViewById(R.id.arrow);
        checkBox1 = root.findViewById(R.id.checkBox1);
        checkBox2 = root.findViewById(R.id.checkBox2);
        checkBox3 = root.findViewById(R.id.checkBox3);
        checkBox4 = root.findViewById(R.id.checkBox4);
        checkBox5 = root.findViewById(R.id.checkBox5);
        checkBox6 = root.findViewById(R.id.checkBox6);
        checkBox7 = root.findViewById(R.id.checkBox7);
        checkBox8 = root.findViewById(R.id.checkBox8);
        feedback_btn = root.findViewById(R.id.feedback_btn);

        auth = FirebaseAuth.getInstance();
        users =  auth.getCurrentUser();
        userID =users.getUid();
        Log.e("user id :","onclick :"+userID);

        title_feedback.setTranslationZ(800);
        feedback.setTranslationX(800);
        feedback_qtn.setTranslationY(800);
        arrow.setTranslationY(800);
        checkBox1.setTranslationY(800);
        checkBox2.setTranslationY(800);
        checkBox3.setTranslationY(800);
        checkBox4.setTranslationY(800);
        checkBox5.setTranslationY(800);
        checkBox6.setTranslationY(800);
        checkBox7.setTranslationY(800);
        checkBox8.setTranslationY(800);
        feedback_btn.setTranslationY(800);

        title_feedback.setAlpha(v);
        feedback.setAlpha(v);
        feedback_qtn.setAlpha(v);
        arrow.setAlpha(v);
        checkBox1.setAlpha(v);
        checkBox2.setAlpha(v);
        checkBox3.setAlpha(v);
        checkBox4.setAlpha(v);
        checkBox5.setAlpha(v);
        checkBox6.setAlpha(v);
        checkBox7.setAlpha(v);
        checkBox8.setAlpha(v);
        feedback_btn.setAlpha(v);

        title_feedback.animate().translationZBy(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        feedback.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        feedback_qtn.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        arrow.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        checkBox1.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        checkBox2.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        checkBox3.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        checkBox4.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        checkBox5.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        checkBox6.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        checkBox7.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        checkBox8.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        feedback_btn.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();



                feedback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedback_m = feedback.getText().toString();
                Boolean qualityOfWork_b = checkBox1.isChecked();
                Log.e("quality","onclick :"+qualityOfWork_b);
                String qualityOfWork_m = Boolean.toString(qualityOfWork_b);
                Log.e("quality string","onclick :"+qualityOfWork_m);
                Boolean valueOfMoney = checkBox2.isChecked();
                String valueOfMoney_m = Boolean.toString(valueOfMoney);
                Boolean grandDecor = checkBox3.isChecked();
                String grandDecor_m = Boolean.toString(grandDecor);
                Boolean professionalism = checkBox4.isChecked();
                String professionalism_m = Boolean.toString(professionalism);
                Boolean uniqueIdea = checkBox5.isChecked();
                String uniqueIdea_m = Boolean.toString(uniqueIdea);
                Boolean classyAndElegent = checkBox6.isChecked();
                String classyAndElegent_m = Boolean.toString(classyAndElegent);
                Boolean Accomodating = checkBox7.isChecked();
                String Accomodating_m = Boolean.toString(Accomodating);
                Boolean onTimeService = checkBox8.isChecked();
                String onTimeService_m = Boolean.toString(onTimeService);

                try {
                    if(TextUtils.isEmpty(feedback_m)){
//                        Toast.makeText(getActivity(), "Field can not be empty", Toast.LENGTH_SHORT).show();
                        feedback.setError("Field can not be empty");
                    }else {
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("UsersRegistration").child(userID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                currentUserEmail = snapshot.child("email").getValue(String.class);
                                Log.e("current user email", "onclick :" + currentUserEmail);
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/").getReference().child("Feedback_tbl");
                                String feedbackID = mDatabase.push().getKey();
                                FeedbackModel feedbackModel = new FeedbackModel(feedbackID, currentUserEmail, feedback_m, qualityOfWork_m, valueOfMoney_m, grandDecor_m, professionalism_m, uniqueIdea_m, classyAndElegent_m, Accomodating_m, onTimeService_m);
                                mDatabase.child(feedbackID).setValue(feedbackModel);
                                Toast.makeText(getContext(), "Thank You for your valuable feedback", Toast.LENGTH_SHORT).show();
                                feedback.setText("");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    feedback.setError("Field can not be empty");
                }

            }
        });

        return root;
    }
}
