package com.example.eventmanagementapplication.Fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.eventmanagementapplication.Models.User;
import com.example.eventmanagementapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class EditGoogleProfileFragment extends Fragment {
    EditText myprofile_fname1,myprofile_lname1,myprofile_email1,myprofile_phone1,myprofile_address1;
    TextView myprofile_birthday1;
    RadioGroup Gender_group;
    RadioButton radioButtonMale,radioButtonFemale;
    ImageView imageView;
    Button pic_image_btn,editProfile_btn;

    private String userID;
    private FirebaseAuth auth;
    private FirebaseUser users;
    private static final int  IMAGE_PIC_CODE=1000;
    private static final int PERMISSION_CODE=1000;
    private String profilePic = "";
    private String encodePic = "";
    FileOutputStream outputStream;
    private StorageReference storageReference;

    public static final Pattern MOBILE_NO=Pattern.compile(
            //   "(?=.*[0-9])"+
            //  ".{10,}"
            "(0/91)?[7-9][0-9]{9}"
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.editgoogleprofile_fragment, container, false);

        myprofile_fname1 = root.findViewById(R.id.myprofile_fname1);
        myprofile_lname1 = root.findViewById(R.id.myprofile_lname1);
//        myprofile_email1 = root.findViewById(R.id.myprofile_email1);
        myprofile_phone1 = root.findViewById(R.id.myprofile_phone1);
        myprofile_address1 = root.findViewById(R.id.myprofile_address1);
        myprofile_birthday1 = root.findViewById(R.id.myprofile_birthday1);
        Gender_group = root.findViewById(R.id.Gender);
        radioButtonMale = root.findViewById(R.id.radioButtonMale);
        radioButtonFemale = root.findViewById(R.id.radioButtonFemale);
        imageView = root.findViewById(R.id.imageView);
//        pic_image_btn = root.findViewById(R.id.pic_image_btn);
        editProfile_btn = root.findViewById(R.id.editProfile_btn);

        auth = FirebaseAuth.getInstance();
        users =  auth.getCurrentUser();
        userID =users.getUid();
        storageReference = FirebaseStorage.getInstance().getReference("UserProfileImages");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("UsersRegistration").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Fname = snapshot.child("fname").getValue(String.class);
                String Lname = snapshot.child("lname").getValue(String.class);
                String Email = snapshot.child("email").getValue(String.class);
                String Picture = snapshot.child("picture").getValue(String.class);
                Log.e("txt_myprofile picture. ","onclick :"+Picture );

                GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());

                myprofile_fname1.setText(Fname);
                myprofile_lname1.setText(Lname);
//                myprofile_email1.setText(Email);
//                Glide.with(getContext()).load(Picture).into(imageView);

                myprofile_birthday1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        calenderPickerMethod();
                    }
                });

                editProfile_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String txt_myprofile_fname1 = myprofile_fname1.getText().toString();
                        String txt_myprofile_lname1 = myprofile_lname1.getText().toString();
//                        String txt_myprofile_email1 = myprofile_email1.getText().toString();
                        String txt_myprofile_phone1 = myprofile_phone1.getText().toString();
                        String txt_myprofile_address1 = myprofile_address1.getText().toString();
                        RadioButton checkedBtn1 = root.findViewById(Gender_group.getCheckedRadioButtonId());

                        try {
                            String txt_gender = checkedBtn1.getText().toString();
                            String txt_myprofile_birthday1 = myprofile_birthday1.getText().toString();
                            String txt_myprofile_picture = profilePic;
                            Log.e("txt_myprofile_picture ","onclick :"+txt_myprofile_picture );

                            if (validFirstname() == false || validLastname() == false  || validAddress() == false || validMobile_no() == false) {

                            } else if (validCalender() == false) {
                                Toast.makeText(getContext(), "Invalid date", Toast.LENGTH_SHORT).show();

                            } else {
                                Log.e(" txt_myprofile_fname1","onclick :"+txt_myprofile_fname1 );
                                Log.e(" txt_myprofile_lname1","onclick :"+txt_myprofile_lname1 );
//                                Log.e(" txt_myprofile_email1","onclick :"+txt_myprofile_email1 );
                                Log.e("txt_myprofile_phone1 ","onclick :"+txt_myprofile_phone1 );
                                Log.e(" txt_myprofile_address1","onclick :"+txt_myprofile_address1 );
                                Log.e("txt_myprofile_birthday1","onclick :"+txt_myprofile_birthday1 );
                                Log.e(" txt_gender","onclick :"+txt_gender );
                                Log.e("txt_myprofile_picture ","onclick :"+txt_myprofile_picture );


                                String user_id = auth.getCurrentUser().getUid();
                                Log.e("user id","onclick :"+user_id);
                                User user = new User(user_id,txt_myprofile_fname1,txt_myprofile_lname1,signInAccount.getEmail(),txt_myprofile_address1,txt_myprofile_phone1,txt_gender,txt_myprofile_birthday1,signInAccount.getPhotoUrl().toString());
                                firebaseDatabase.getReference().child("UsersRegistration").child(user_id).setValue(user);
//                                                      Toast.makeText(RegistrationActivity.this, "image added succesfully in the storage", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(), "update succesfully", Toast.LENGTH_SHORT).show();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                MyProfileFragment myProfileFragment = new MyProfileFragment();
                                transaction.replace(R.id.myprofile_container, myProfileFragment).commit();



                            }
                        }catch (Exception e){
                            Log.e("User Exception reg5","Onclick"+e.getMessage());
                            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

    private boolean validCalender() throws ParseException {

        int current_yer=0,selected_yer1=0,current_month=0,selected_month1=0,current_day=0,selected_day1=0;
        String input = myprofile_birthday1.getText().toString().trim();     //input string
        Log.e("selected full date", "onClick: " + input );
        String lastFourDigits = "";     //substring containing last 4 characters
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM yyyy");
        Date dt2=sdf1.parse(input);
        DateFormat format=new SimpleDateFormat("d");
        DateFormat format0=new SimpleDateFormat("M");
        String finalDayInput=format.format(dt2);
        String finalMonthInput=format0.format(dt2);
        Log.e("dayInput", "onClick: " + finalDayInput );
        Log.e("MonthInput", "onClick: " + finalMonthInput );

        if (input.length() > 4)
        {
            lastFourDigits = input.substring(input.length() - 4);
            selected_yer1=Integer.parseInt(lastFourDigits);
            selected_month1=Integer.parseInt(finalMonthInput);
            selected_day1=Integer.parseInt(finalDayInput);
            Log.e("selected_yer1", "onClick: " + selected_yer1 );
            Log.e("selected_month1", "onClick: " + selected_month1 );
            Log.e("selected_day1", "onClick: " + selected_day1 );
        }


        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Calendar c = Calendar.getInstance();
        Log.e("calender c", "onClick: " + c );

        String date = sdf.format(c.getTime());
        Log.e("current full date", "onClick: " + date );
        Date dt1=sdf.parse(date);
        DateFormat format2=new SimpleDateFormat("d");
        DateFormat format3=new SimpleDateFormat("M");
        String finalDay=format2.format(dt1);
        String finalMonth=format3.format(dt1);
        Log.e("day1", "onClick: " + finalDay );
        Log.e("Month", "onClick: " + finalMonth );


        String lastFourDigits1 = "";
        if (date.length() > 4)
        {
            lastFourDigits1 = date.substring(date.length() - 4);
            current_yer=Integer.parseInt(lastFourDigits1);
            current_day=Integer.parseInt(finalDay);
            current_month=Integer.parseInt(finalMonth);
            Log.e("current_yer", "onClick: " + current_yer );
            Log.e("current_month", "onClick: " + current_month );
            Log.e("current_day", "onClick: " + current_day );

        }

        if(selected_yer1 >= current_yer){
            if(selected_month1 >= current_month){
                if(selected_day1 >= current_day){
                    myprofile_birthday1.setError("Choose correct date of birth.");
                    Toast.makeText(getActivity(),"Choose correct date of birth.",Toast.LENGTH_LONG).show();
                    return false;
                }else{
                    myprofile_birthday1.setError(null);
                    myprofile_birthday1.setText(input);
                    return true;
                }
            }else{
                myprofile_birthday1.setError(null);
                myprofile_birthday1.setText(input);
                return true;
            }
        }else{
            myprofile_birthday1.setError(null);
            myprofile_birthday1.setText(input);
            return true;
        }

    }

    private boolean validMobile_no(){

        String mobile_no = myprofile_phone1.getText().toString().trim();
        String noWhiteSpace="(?=\\S+$)";
        if(mobile_no.isEmpty()){
            myprofile_phone1.setError("Field can't be empty");
            return false;
        }else if(!MOBILE_NO.matcher(mobile_no).matches()){
            myprofile_phone1.setError("please check you are number.");
            return false;
        }else{
            myprofile_phone1.setError(null);
            return true;
        }
    }

    private boolean validAddress(){

        String address= myprofile_address1.getText().toString().trim();
        if(address.isEmpty()){
            myprofile_address1.setError("Field can't be empty");
            return false;
        }
        return true;
    }

    private boolean validLastname(){

        String lname = myprofile_lname1.getText().toString().trim();
        String noWhiteSpace="(?=\\S+$)";
        if(lname.isEmpty()){
            myprofile_lname1.setError("Field can't be empty");
            return false;
        }else if(lname.length()>=15){
            myprofile_lname1.setError("name is too long");
            return false;
        }else if(lname.matches(noWhiteSpace)){
            myprofile_lname1.setError("White space are not allowed.");
            return false;
        }else{
            myprofile_lname1.setError(null);
            return true;
        }
    }

    private boolean validFirstname(){

        String fname = myprofile_fname1.getText().toString().trim();
        String noWhiteSpace="(?=\\S+$)";
        if(fname.isEmpty()){
            myprofile_fname1.setError("Field can't be empty");
            return false;
        }else if(fname.length()>=15){
            myprofile_fname1.setError("name is too long");
            return false;
        }else if(fname.matches(noWhiteSpace)){
            myprofile_fname1.setError("White space are not allowed.");
            return false;
        }else{
            myprofile_fname1.setError(null);
            return true;
        }
    }

    private void calenderPickerMethod() {

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        try {
            materialDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        }catch (IllegalArgumentException e){
            Toast.makeText(getActivity(), "error from meterial date picker", Toast.LENGTH_SHORT).show();
        }

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        myprofile_birthday1.setText(materialDatePicker.getHeaderText());
                    }
                }
        );
    }

}
