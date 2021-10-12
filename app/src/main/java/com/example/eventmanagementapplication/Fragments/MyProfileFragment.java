package com.example.eventmanagementapplication.Fragments;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.eventmanagementapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileFragment extends Fragment {
    TextView myprofile_fname,myprofile_fname1,myprofile_lname,myprofile_lname1,myprofile_address,myprofile_address1,
            myprofile_phone,myprofile_phone1,myprofile_email,myprofile_email1,myprofile_gender,myprofile_gender1,
            myprofile_password,myprofile_password1,myprofile_birthday,myprofile_birthday1;
    Button editProfile_btn1,editProfile_btn;
    CircleImageView profile_image;
    float v=0;
    private FirebaseAuth auth;
    private FirebaseUser users;
    private String userID;
    String currentUserEmail;
    RelativeLayout myprofile_container;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.myprofile_fragment, container, false);

        myprofile_fname = root.findViewById(R.id.myprofile_fname);
        myprofile_fname1 = root.findViewById(R.id.myprofile_fname1);
        myprofile_lname = root.findViewById(R.id.myprofile_lname);
        myprofile_lname1 = root.findViewById(R.id.myprofile_lname1);
        myprofile_address = root.findViewById(R.id.myprofile_address);
        myprofile_address1 = root.findViewById(R.id.myprofile_address1);
        myprofile_phone = root.findViewById(R.id.myprofile_phone);
        myprofile_phone1 = root.findViewById(R.id.myprofile_phone1);
        myprofile_email = root.findViewById(R.id.myprofile_email);
        myprofile_email1 = root.findViewById(R.id.myprofile_email1);
        myprofile_gender = root.findViewById(R.id.myprofile_gender);
        myprofile_gender1 = root.findViewById(R.id.myprofile_gender1);
        myprofile_password = root.findViewById(R.id.myprofile_password);
        myprofile_password1 = root.findViewById(R.id.myprofile_password1);
        myprofile_birthday = root.findViewById(R.id.myprofile_birthday);
        myprofile_birthday1 = root.findViewById(R.id.myprofile_birthday1);
        profile_image = root.findViewById(R.id.profile_image);
        editProfile_btn1 = root.findViewById(R.id.editProfile_btn1);
        editProfile_btn = root.findViewById(R.id.editProfile_btn);

        myprofile_container = root.findViewById(R.id.myprofile_container);

        auth = FirebaseAuth.getInstance();
        users =  auth.getCurrentUser();


        myprofile_fname.setTranslationX(800);
        myprofile_fname1.setTranslationX(800);
        myprofile_lname.setTranslationX(800);
        myprofile_lname1.setTranslationX(800);
        myprofile_address.setTranslationX(800);
        myprofile_address1.setTranslationX(800);
        myprofile_phone.setTranslationX(800);
        myprofile_phone1.setTranslationX(800);
        myprofile_email.setTranslationX(800);
        myprofile_email1.setTranslationX(800);
        myprofile_gender.setTranslationX(800);
        myprofile_gender1.setTranslationX(800);
        myprofile_password.setTranslationX(800);
        myprofile_password1.setTranslationX(800);
        myprofile_birthday.setTranslationX(800);
        myprofile_birthday1.setTranslationX(800);
        profile_image.setTranslationZ(800);

        myprofile_fname.setAlpha(v);
        myprofile_fname1.setAlpha(v);
        myprofile_lname.setAlpha(v);
        myprofile_lname1.setAlpha(v);
        myprofile_address.setAlpha(v);
        myprofile_address1.setAlpha(v);
        myprofile_phone.setAlpha(v);
        myprofile_phone1.setAlpha(v);
        myprofile_email.setAlpha(v);
        myprofile_email1.setAlpha(v);
        myprofile_gender.setAlpha(v);
        myprofile_gender1.setAlpha(v);
        myprofile_password.setAlpha(v);
        myprofile_password1.setAlpha(v);
        myprofile_birthday.setAlpha(v);
        myprofile_birthday1.setAlpha(v);
        profile_image.setAlpha(v);

        myprofile_fname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        myprofile_fname1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        myprofile_lname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        myprofile_lname1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        myprofile_email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();
        myprofile_email1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1300).start();
        myprofile_phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1500).start();
        myprofile_phone1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1700).start();
        myprofile_birthday.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1900).start();
        myprofile_birthday1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2100).start();
        myprofile_gender.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2300).start();
        myprofile_gender1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2500).start();
        myprofile_address.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2700).start();
        myprofile_address1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2900).start();
        myprofile_password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(3100).start();
        myprofile_password1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(3300).start();
        profile_image.animate().translationZBy(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        try {
            userID = users.getUid();
            Log.e("Myprofile user id :", "onclick :" + userID);

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
            DatabaseReference databaseReference = firebaseDatabase.getReference();
            databaseReference.child("UsersRegistration").child(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String Fname = snapshot.child("fname").getValue(String.class);
                    String Lname = snapshot.child("lname").getValue(String.class);
                    String Email = snapshot.child("email").getValue(String.class);
                    String Address = snapshot.child("address").getValue(String.class);
                    String Gender = snapshot.child("gender").getValue(String.class);
                    String Mobile = snapshot.child("mobile").getValue(String.class);
                    String Password = snapshot.child("password").getValue(String.class);
                    String Picture = snapshot.child("picture").getValue(String.class);
                    String dob = snapshot.child("dob").getValue(String.class);

                    GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
                    if (signInAccount != null) {
                        if (Address != null || Gender != null || Mobile != null) {
                            myprofile_fname1.setText(Fname);
                            myprofile_lname1.setText(Lname);
                            myprofile_email1.setText(Email);
                            myprofile_address1.setText(Address);
                            myprofile_gender1.setText(Gender);
                            myprofile_phone1.setText(Mobile);
                            myprofile_password1.setText(Password);
                            myprofile_birthday1.setText(dob);
                            myprofile_password.setVisibility(View.GONE);
                            myprofile_password1.setVisibility(View.GONE);
                            editProfile_btn1.setVisibility(View.GONE);
                            editProfile_btn.setVisibility(View.GONE);
                        } else {
                            myprofile_fname1.setText(signInAccount.getGivenName());
                            myprofile_lname1.setText(signInAccount.getFamilyName());
                            myprofile_email1.setText(signInAccount.getEmail());
                            myprofile_address1.setVisibility(View.GONE);
                            myprofile_gender1.setVisibility(View.GONE);
                            myprofile_phone1.setVisibility(View.GONE);
                            myprofile_password1.setVisibility(View.GONE);
                            myprofile_address.setVisibility(View.GONE);
                            myprofile_gender.setVisibility(View.GONE);
                            myprofile_phone.setVisibility(View.GONE);
                            myprofile_password.setVisibility(View.GONE);
                            myprofile_birthday.setVisibility(View.GONE);
                            myprofile_birthday1.setVisibility(View.GONE);
                            editProfile_btn.setVisibility(View.GONE);
                        }


                        Uri image = signInAccount.getPhotoUrl();

                        Glide.with(getContext()).load(image).into(profile_image);

                        editProfile_btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                EditGoogleProfileFragment editGoogleProfileFragment = new EditGoogleProfileFragment();
                                transaction.replace(R.id.myprofile_container, editGoogleProfileFragment);
                                transaction.commit();
                                myprofile_fname1.setVisibility(View.GONE);
                                myprofile_lname1.setVisibility(View.GONE);
                                myprofile_email1.setVisibility(View.GONE);
                                myprofile_fname.setVisibility(View.GONE);
                                myprofile_lname.setVisibility(View.GONE);
                                myprofile_email.setVisibility(View.GONE);
                                myprofile_address1.setVisibility(View.GONE);
                                myprofile_gender1.setVisibility(View.GONE);
                                myprofile_phone1.setVisibility(View.GONE);
                                myprofile_password1.setVisibility(View.GONE);
                                myprofile_address.setVisibility(View.GONE);
                                myprofile_gender.setVisibility(View.GONE);
                                myprofile_phone.setVisibility(View.GONE);
                                myprofile_password.setVisibility(View.GONE);
                                myprofile_birthday.setVisibility(View.GONE);
                                myprofile_birthday1.setVisibility(View.GONE);
                                editProfile_btn.setVisibility(View.GONE);
                                editProfile_btn1.setVisibility(View.GONE);
                                profile_image.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        myprofile_fname1.setText(Fname);
                        myprofile_lname1.setText(Lname);
                        myprofile_email1.setText(Email);
                        myprofile_address1.setText(Address);
                        myprofile_gender1.setText(Gender);
                        myprofile_phone1.setText(Mobile);
                        myprofile_password1.setText(Password);
                        myprofile_birthday1.setText(dob);
                        editProfile_btn1.setVisibility(View.GONE);
                        try {
                            String mBase64string = Picture;
                            byte[] imageAsBytes = Base64.decode(mBase64string.getBytes(), Base64.DEFAULT);
                            profile_image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                        } catch (NullPointerException e) {
                            Toast.makeText(getContext(), "Please Register in this app", Toast.LENGTH_SHORT).show();
                        }
                        editProfile_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                EditprofileFragment editprofileFragment = new EditprofileFragment();
                                transaction.replace(R.id.myprofile_container, editprofileFragment).commit();

        //                           getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
        //                               @Override
        //                               public void onBackStackChanged() {
        //                                   List<Fragment> f = getFragmentManager().getFragments();
        //                                   Fragment frag = f.get(0);
        //                                   String currentFragment = frag.getClass().getSimpleName();
        //                                   Log.e("currentFragment","onclick :"+currentFragment);
        //                                   if(currentFragment != null){
        //                                       MyProfileFragment myProfileFragment = new MyProfileFragment();
        //                                       transaction.replace(R.id.myprofile_container, myProfileFragment);
        //                                       Log.e("currentFragment...","onclick :");
        //                                   }else{
        //                                       getFragmentManager().popBackStack();
        //                                   }
        //                               }
        //                           });

                                myprofile_fname1.setVisibility(View.GONE);
                                myprofile_lname1.setVisibility(View.GONE);
                                myprofile_email1.setVisibility(View.GONE);
                                myprofile_fname.setVisibility(View.GONE);
                                myprofile_lname.setVisibility(View.GONE);
                                myprofile_email.setVisibility(View.GONE);
                                myprofile_address1.setVisibility(View.GONE);
                                myprofile_gender1.setVisibility(View.GONE);
                                myprofile_phone1.setVisibility(View.GONE);
                                myprofile_password1.setVisibility(View.GONE);
                                myprofile_address.setVisibility(View.GONE);
                                myprofile_gender.setVisibility(View.GONE);
                                myprofile_phone.setVisibility(View.GONE);
                                myprofile_password.setVisibility(View.GONE);
                                myprofile_birthday.setVisibility(View.GONE);
                                myprofile_birthday1.setVisibility(View.GONE);
                                editProfile_btn.setVisibility(View.GONE);
                                editProfile_btn1.setVisibility(View.GONE);
                                profile_image.setVisibility(View.GONE);

                            }
                        });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (NullPointerException e){
            Log.e("exception in my profileFragment","onclick :"+e.getMessage());
        }

        return root;

    }

}
