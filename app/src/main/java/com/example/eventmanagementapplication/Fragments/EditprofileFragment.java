package com.example.eventmanagementapplication.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
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

import com.example.eventmanagementapplication.Activities.LoginActivity;
import com.example.eventmanagementapplication.Activities.new_password;
import com.example.eventmanagementapplication.Activities.password_updated;
import com.example.eventmanagementapplication.Models.User;
import com.example.eventmanagementapplication.R;
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
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

public class EditprofileFragment extends Fragment {
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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.editprofile_fragment, container, false);

        myprofile_fname1 = root.findViewById(R.id.myprofile_fname1);
        myprofile_lname1 = root.findViewById(R.id.myprofile_lname1);
        myprofile_email1 = root.findViewById(R.id.myprofile_email1);
        myprofile_phone1 = root.findViewById(R.id.myprofile_phone1);
        myprofile_address1 = root.findViewById(R.id.myprofile_address1);
        myprofile_birthday1 = root.findViewById(R.id.myprofile_birthday1);
        Gender_group = root.findViewById(R.id.Gender);
        radioButtonMale = root.findViewById(R.id.radioButtonMale);
        radioButtonFemale = root.findViewById(R.id.radioButtonFemale);
        imageView = root.findViewById(R.id.imageView);
        pic_image_btn = root.findViewById(R.id.pic_image_btn);
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
                String Address = snapshot.child("address").getValue(String.class);
                String Gender = snapshot.child("gender").getValue(String.class);
                String Mobile = snapshot.child("mobile").getValue(String.class);
                String Password = snapshot.child("password").getValue(String.class);
                String Picture = snapshot.child("picture").getValue(String.class);
                String dob = snapshot.child("dob").getValue(String.class);
                Log.e("txt_myprofile_picture_picture.. ","onclick :"+Picture );

                myprofile_fname1.setText(Fname);
                myprofile_lname1.setText(Lname);
                myprofile_email1.setText(Email);
                myprofile_address1.setText(Address);
                myprofile_phone1.setText(Mobile);
                String gender = Gender;
                if(gender.equals("Female")){
                    radioButtonFemale.setChecked(true);
                    radioButtonMale.setChecked(false);
                }else {
                    radioButtonFemale.setChecked(false);
                    radioButtonMale.setChecked(true);
                }
                String mBase64string = Picture;
                byte[] imageAsBytes = Base64.decode(mBase64string.getBytes(), Base64.DEFAULT);
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

                myprofile_birthday1.setText(dob);
                myprofile_birthday1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        calenderPickerMethod();
                    }
                });

                pic_image_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //check permission
                        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                            if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                                //permission not granted
                                String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
                                //show pop up
                                requestPermissions(permissions,PERMISSION_CODE);
                            }
                            else{
                                //permission already granted
                                pickImageFromGallary();
                            }
                        }
                        else{
                            //system os is less then
                            pickImageFromGallary();
                        }
                    }
                });

                editProfile_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String txt_myprofile_fname1 = myprofile_fname1.getText().toString();
                        String txt_myprofile_lname1 = myprofile_lname1.getText().toString();
                        String txt_myprofile_email1 = myprofile_email1.getText().toString();
                        String txt_myprofile_phone1 = myprofile_phone1.getText().toString();
                        String txt_myprofile_address1 = myprofile_address1.getText().toString();
                        RadioButton checkedBtn1 = root.findViewById(Gender_group.getCheckedRadioButtonId());

                        try {
                            String txt_gender = checkedBtn1.getText().toString();
                            String txt_myprofile_birthday1 = myprofile_birthday1.getText().toString();
                            String txt_myprofile_picture = profilePic;
                            Log.e("txt_myprofile_picture ","onclick :"+txt_myprofile_picture );

                            if (validFirstname() == false || validLastname() == false || validateEmail() == false || validAddress() == false || validMobile_no() == false) {

                            } else if (validCalender() == false) {
                                Toast.makeText(getContext(), "Invalid date", Toast.LENGTH_SHORT).show();

                            } else {
                                Log.e(" txt_myprofile_fname1","onclick :"+txt_myprofile_fname1 );
                                Log.e(" txt_myprofile_lname1","onclick :"+txt_myprofile_lname1 );
                                Log.e(" txt_myprofile_email1","onclick :"+txt_myprofile_email1 );
                                Log.e("txt_myprofile_phone1 ","onclick :"+txt_myprofile_phone1 );
                                Log.e(" txt_myprofile_address1","onclick :"+txt_myprofile_address1 );
                                Log.e("txt_myprofile_birthday1","onclick :"+txt_myprofile_birthday1 );
                                Log.e(" txt_gender","onclick :"+txt_gender );
                                Log.e("txt_myprofile_picture ","onclick :"+txt_myprofile_picture );

                                Log.e("encodePic ","onclick :"+encodePic );
                                String choose_Picture;
                                if (encodePic.equals("")){
                                    choose_Picture = Picture;
                                    Log.e("txt_myprofile_picture1 ","onclick :"+choose_Picture );
                                }else {
                                    choose_Picture = encodePic;
                                    Log.e("txt_myprofile_picture2 ","onclick :"+choose_Picture );
                                }


                                String oldEmail = Email;
                                String oldPassword = Password;
                                auth.signInWithEmailAndPassword(oldEmail,oldPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        FirebaseUser user =auth.getCurrentUser();
                                        AuthCredential authCredential = EmailAuthProvider.getCredential(oldEmail,oldPassword);
                                        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                user.updateEmail(txt_myprofile_email1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                                                        firebaseDatabase.getReference().child("UsersRegistration").child(user.getUid()).child("email").setValue(txt_myprofile_email1);
//                                                        Toast.makeText(getContext(), "email updated successfully.....", Toast.LENGTH_SHORT).show();

                                                        String user_id = auth.getCurrentUser().getUid();
                                                        Log.e("user id","onclick :"+user_id);
                                                        User user = new User(user_id,txt_myprofile_fname1,txt_myprofile_lname1,txt_myprofile_email1,txt_myprofile_address1,txt_myprofile_phone1,oldPassword,txt_gender,txt_myprofile_birthday1,choose_Picture);
                                                        firebaseDatabase.getReference().child("UsersRegistration").child(user_id).setValue(user);
//                                                      Toast.makeText(RegistrationActivity.this, "image added succesfully in the storage", Toast.LENGTH_SHORT).show();
                                                        Toast.makeText(getActivity(), "update succesfully", Toast.LENGTH_SHORT).show();
                                                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                                        MyProfileFragment myProfileFragment = new MyProfileFragment();
                                                        transaction.replace(R.id.myprofile_container, myProfileFragment).commit();
                                                        // firebase storage lo image update avvatledhu
//                                                        try{
//                                                            Uri myUri = Uri.parse(choose_Picture);
//                                                            StorageReference filereference = storageReference.child(Fname+"."+getFileExtension(myUri));
//                                                            filereference.putFile(myUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                                                @Override
//                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//
////                                                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
////                                                                    startActivity(intent);
//                                                                }
//                                                            });
//                                                        }catch (IllegalArgumentException e){
//                                                            Log.e("User Exception re3","Onclick"+e.getMessage());
//
//
//                                                        }
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getContext(), "1"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        Log.e("1","onclick :"+e);
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "2"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                Log.e("2","onclick :"+e);
                                            }
                                        });
                                    }
                                });


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

    private boolean validateEmail(){
        String email = myprofile_email1.getText().toString().trim();
        if(email.isEmpty()){
            myprofile_email1.setError("Field can't be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            myprofile_email1.setError("please enter a valid email address");
            return false;
        }
        else{
            myprofile_email1.setError(null);
            return true;
        }
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


    private void pickImageFromGallary() {
        Intent intent  =new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PIC_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    try{
                        pickImageFromGallary();
                    }catch (RuntimeException e){

                    }

                }
                else{
                    Toast.makeText(getActivity(),"permission denied.....!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        return imageEncoded;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PIC_CODE)
            imageView.setImageURI(data.getData());
        profilePic = data.getData().toString();

        Uri targetUri = data.getData();
        InputStream imageStream = null;
        try {
            imageStream = getActivity().getContentResolver().openInputStream(targetUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
        encodePic = encodeTobase64(yourSelectedImage);
        Log.e("abc is :","omclick :"+encodePic);
        try {
            Bitmap immagex = yourSelectedImage;
            Log.e("image x","onclick :"+immagex);
            File file = Environment.getExternalStorageDirectory();
            File dir = new File(file.getAbsolutePath()+ "/userProfile/");
            dir.mkdir();
            File file1 = new File(dir, System.currentTimeMillis() + ".jpeg");
            try {
                outputStream = new FileOutputStream(file1);
                Log.e("outputstream x","onclick :"+outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            immagex.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        }catch (NullPointerException e){
            Log.e("error is","onclick :"+e);
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
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime =MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
