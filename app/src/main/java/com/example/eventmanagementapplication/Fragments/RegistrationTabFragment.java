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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventmanagementapplication.Activities.LoginActivity;
import com.example.eventmanagementapplication.R;
import com.example.eventmanagementapplication.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;
import static java.time.LocalDate.parse;

public class RegistrationTabFragment extends Fragment {
    Button Register;
    EditText Fname,Lname,Email,Address,Mobile,Password,Confirm_password;
    private RadioGroup Gender;
    private ImageView imageViews;
    private TextView mShowSelectedDateText,signin;
    private Button mPickDateButtons, pic_image;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private static final int  IMAGE_PIC_CODE=1000;
    private static final int PERMISSION_CODE=1000;
    private String profilePic = "";
    List<User> users;
    private StorageReference storageReference;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    public static final Pattern PASSWORD_PATTERN=Pattern.compile("^"+
            "(?=.*[@#$%^&*+=])"+
            "(?=\\S+$)"+    //no white space
            ".{6,}"+
            "$");


    public static final Pattern MOBILE_NO=Pattern.compile(
            //   "(?=.*[0-9])"+
            //  ".{10,}"
            "(0/91)?[7-9][0-9]{9}"
    );
    FileOutputStream outputStream;
    private String encodePic = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.registration_tab_fragment,container,false);
        users = new ArrayList<User>();
        Register = root.findViewById(R.id.signupp);

        Fname =root.findViewById(R.id.Fname);
        Lname =root.findViewById(R.id.Lname);
        Email =root.findViewById(R.id.Email);
        Address =root.findViewById(R.id.Address);
        Mobile =root.findViewById(R.id.Mobile);
        Password =root.findViewById(R.id.Password);
        Confirm_password =root.findViewById(R.id.Confirm_password);

        Gender = root.findViewById(R.id.Gender);

        pic_image = root.findViewById(R.id.pic_image);
        imageViews = root.findViewById(R.id.imageView);

        mShowSelectedDateText =root.findViewById(R.id.show_selected_date);
        mPickDateButtons = root.findViewById(R.id.calender);
        progressBar=root.findViewById(R.id.progressbar);
        relativeLayout=root.findViewById(R.id.prog_relay);

        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);


        storageReference = FirebaseStorage.getInstance().getReference("UserProfileImages");
        mDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/").getReference().child("UsersRegistration");
        auth = FirebaseAuth.getInstance();



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                String txt_Fname = Fname.getText().toString();
                String txt_Lname = Lname.getText().toString();
                String txt_Email = Email.getText().toString().trim();
                String txt_Address = Address.getText().toString();
                String txt_Mobile = Mobile.getText().toString();
                String txt_Password = Password.getText().toString();
                String txt_Confirm_password = Confirm_password.getText().toString();
                RadioButton checkedBtn = root.findViewById(Gender.getCheckedRadioButtonId());

                try {
                    final String txt_gendervalue = checkedBtn.getText().toString();
                    String txt_dob = mShowSelectedDateText.getText().toString();
                    String txt_picture = profilePic;
                    if (validFirstname() == false || validLastname() == false || validateEmail() == false || validAddress() == false || validMobile_no() == false ||validatePassword() == false || validConfirm_pass() == false ){

                    }else if( TextUtils.isEmpty(txt_gendervalue) || TextUtils.isEmpty(txt_picture) || TextUtils.isEmpty(txt_Fname) ||TextUtils.isEmpty(txt_Lname)
                            ||TextUtils.isEmpty(txt_Email) ||TextUtils.isEmpty(txt_Address) ||TextUtils.isEmpty(txt_Mobile) ||TextUtils.isEmpty(txt_Password)
                            ||TextUtils.isEmpty(txt_Confirm_password) ||TextUtils.isEmpty(txt_dob) ){
                        Toast.makeText(getContext(), "Empty credential!", Toast.LENGTH_SHORT).show();
                        Register.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);

                    }else if (validCalender() == false){
                        Toast.makeText(getContext(), "Invalid date", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                    }
                    else{
                        registerUser(txt_Fname,txt_Lname,txt_Email,txt_Address,txt_Mobile, txt_Password,txt_gendervalue,txt_dob, txt_picture);
                    }
                }catch (IllegalArgumentException e){
                    Log.e("User Exception reg1","Onclick"+e.getMessage());
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    Register.setEnabled(true);
                }catch (Exception e){
                    Log.e("User Exception reg2","Onclick"+e.getMessage());
                    Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    Register.setEnabled(true);
                }
            }
        });

        mPickDateButtons.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calenderPickerMethod();
                    }
                });

        mShowSelectedDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calenderPickerMethod();
            }
        });


        pic_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        return root;
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
                        mShowSelectedDateText.setText(materialDatePicker.getHeaderText());
                    }
                }
        );
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
            imageViews.setImageURI(data.getData());
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

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime =MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void registerUser(String Fname,String Lname,String Email,String Address,String Mobile, String password,String gendervalue,String dob,String picture){
        auth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener(getActivity()
                , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

//                    String user_id = mDatabase.push().getKey();
//                    DatabaseReference CurentUser_db =  mDatabase.child(user_id);
                            try{
                                Uri myUri = Uri.parse(picture);
                                StorageReference filereference = storageReference.child(Fname+"."+getFileExtension(myUri));
                                filereference.putFile(myUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        String user_id = auth.getCurrentUser().getUid();
                                        Log.e("user id","onclick :"+user_id);
                                        User user = new User(user_id,Fname,Lname,Email,Address,Mobile,password,gendervalue,dob,encodePic);
                                        mDatabase.child(user_id).setValue(user);
//                                Toast.makeText(RegistrationActivity.this, "image added succesfully in the storage", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getActivity(), "Registration succesful", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        relativeLayout.setVisibility(View.GONE);
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                        progressBar.setVisibility(View.GONE);
                                        relativeLayout.setVisibility(View.GONE);
                                    }
                                });
                            }catch (IllegalArgumentException e){
                                Log.e("User Exception re3","Onclick"+e.getMessage());
                                progressBar.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.GONE);

                            }


                        }else{
                            Register.setEnabled(true);
                            Toast.makeText(getActivity(), "Registration failed......email is already exists", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), "Registration failed......", Toast.LENGTH_SHORT).show();
                        }
                    }

                }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("error....","onclick :"+e);
            }
        });
    }

    private boolean validateEmail(){
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        Register.setEnabled(true);
        String email=Email.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Field can't be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("please enter a valid email address");
            return false;
        }
        else{
            Email.setError(null);
            return true;
        }
    }


    private boolean validFirstname(){
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        Register.setEnabled(true);
        String fname = Fname.getText().toString().trim();
        String noWhiteSpace="(?=\\S+$)";
        if(fname.isEmpty()){
            Fname.setError("Field can't be empty");
            return false;
        }else if(fname.length()>=15){
            Fname.setError("name is too long");
            return false;
        }else if(fname.matches(noWhiteSpace)){
            Fname.setError("White space are not allowed.");
            return false;
        }else{
            Fname.setError(null);
            return true;
        }
    }

    private boolean validLastname(){
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        Register.setEnabled(true);
        String lname = Lname.getText().toString().trim();
        String noWhiteSpace="(?=\\S+$)";
        if(lname.isEmpty()){
            Lname.setError("Field can't be empty");
            return false;
        }else if(lname.length()>=15){
            Lname.setError("name is too long");
            return false;
        }else if(lname.matches(noWhiteSpace)){
            Lname.setError("White space are not allowed.");
            return false;
        }else{
            Lname.setError(null);
            return true;
        }
    }

    private boolean validEmail(){
        Register.setEnabled(true);
        String email= Email.getText().toString().trim();
        if(email.isEmpty()){
            Email.setError("Field can't be empty");
            return false;
        }
        return true;
    }

    private boolean validAddress(){
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        Register.setEnabled(true);
        String address= Address.getText().toString().trim();
        if(address.isEmpty()){
            Address.setError("Field can't be empty");
            return false;
        }
        return true;
    }

    private boolean validMobile_no(){
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        Register.setEnabled(true);
        String mobile_no = Mobile.getText().toString().trim();
        String noWhiteSpace="(?=\\S+$)";
        if(mobile_no.isEmpty()){
            Mobile.setError("Field can't be empty");
            return false;
        }else if(!MOBILE_NO.matcher(mobile_no).matches()){
            Mobile.setError("please check you are number.");
            return false;
        }else{
            Mobile.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        Register.setEnabled(true);
        String password=Password.getText().toString().trim();

        if(password.isEmpty()){
            Password.setError("Field can't be empty");
            return false;
        }else if(!PASSWORD_PATTERN.matcher(password).matches()){
            Password.setError("please use special charecters and Do not use white space and it shold be 6 charecters. Ex:abc@123");
            return false;
        }
        else{
            Password.setError(null);
            return true;
        }
    }
    private boolean validConfirm_pass(){
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        Register.setEnabled(true);
        String password = Password.getText().toString().trim();
        String confirm_pass = Confirm_password.getText().toString().trim();

        if(confirm_pass.isEmpty()){
            Confirm_password.setError("Field can't be empty");
            return false;
        }else if(!password.equals(confirm_pass)){
            Confirm_password.setError("Password Not matching");
            return false;
        }else{
            Confirm_password.setError(null);
            return true;
        }
    }

    private boolean validCalender() throws ParseException {
        Register.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        int current_yer=0,selected_yer1=0,current_month=0,selected_month1=0,current_day=0,selected_day1=0;
        String input = mShowSelectedDateText.getText().toString().trim();     //input string
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


        if(selected_yer1 < current_yer){
            mShowSelectedDateText.setError(null);
            mShowSelectedDateText.setText(input);
            return true;
        }else if(selected_yer1 == current_yer){
            if(selected_month1 < current_month){
                mShowSelectedDateText.setError(null);
                mShowSelectedDateText.setText(input);
                return true;
            }else if(selected_month1 == current_month){
                if(selected_day1 < current_day){
                    mShowSelectedDateText.setError(null);
                    mShowSelectedDateText.setText(input);
                    return true;
                }else{
                    mShowSelectedDateText.setError("Choose correct date ");
                    Toast.makeText(getContext(),"Choose correct date ",Toast.LENGTH_LONG).show();
                    return false;
                }
            }else{
                mShowSelectedDateText.setError("Choose correct date ");
                Toast.makeText(getContext(),"Choose correct date ",Toast.LENGTH_LONG).show();
                return false;
            }
        }else{
            mShowSelectedDateText.setError("Choose correct date ");
            Toast.makeText(getContext(),"Choose correct date ",Toast.LENGTH_LONG).show();
            return false;
        }

    }

}
