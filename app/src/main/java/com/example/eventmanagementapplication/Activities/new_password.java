package com.example.eventmanagementapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eventmanagementapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
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

import static com.example.eventmanagementapplication.Fragments.RegistrationTabFragment.PASSWORD_PATTERN;

public class new_password extends AppCompatActivity {
    private Button newPassword_btn;
    private TextInputEditText newPassword,newConfirmPassword;
    private String userID;
    private FirebaseAuth auth;
    private FirebaseUser users;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        newPassword = findViewById(R.id.newPassword);
        newConfirmPassword = findViewById(R.id.newConfirmPassword);
        newPassword_btn = findViewById(R.id.newPassword_btn);

        auth = FirebaseAuth.getInstance();


        Intent i = getIntent();
        String get_new_pass_phone = i.getStringExtra("new_pass_phone");
        Log.e("get_new_pass_phone","onclick :"+get_new_pass_phone);

        newPassword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validNewPassword() == true) {
                    if (validNewConfirmPassword() == true) {
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("UsersRegistration").orderByChild("mobile").equalTo(get_new_pass_phone).addValueEventListener(new ValueEventListener() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot ds : snapshot.getChildren()) {

                                    String oldPassword = ds.child("password").getValue(String.class);
                                    Log.e("old pass in new_pass page","onclick :"+oldPassword);
                                    String oldEmail = ds.child("email").getValue(String.class);
                                    Log.e("oldEmail in new_pass page","onclick :"+oldEmail);
                                    String new_Password=newPassword.getText().toString();
                                    FirebaseAuth fAuth = FirebaseAuth.getInstance();
                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                    System.out.println("the old password is : "+oldPassword);
                                    System.out.println("the old email is : "+oldEmail);
                                    Log.e("currentUser","onclick :"+currentUser);
                                    Log.e("UID","onclick :"+auth.getUid());
                                    auth.signInWithEmailAndPassword(oldEmail,oldPassword).addOnCompleteListener(new_password.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            FirebaseUser user =auth.getCurrentUser();
                                            AuthCredential authCredential = EmailAuthProvider.getCredential(oldEmail,oldPassword);
                                            user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    user.updatePassword(new_Password).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            mDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/").getReference().child("UsersRegistration");
                                                            mDatabase.child(user.getUid()).child("password").setValue(new_Password);
                                                            Toast.makeText(new_password.this, "password updated successfully.....", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(new_password.this,password_updated.class);
                                                            startActivity(intent);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(new_password.this, "1"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            Log.e("1","onclick :"+e);
                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(new_password.this, "2"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    Log.e("2","onclick :"+e);
                                                }
                                            });
                                        }
                                    });


                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });
    }

    private boolean validNewConfirmPassword() {
        String password = newPassword.getText().toString().trim();
        String confirm_pass = newConfirmPassword.getText().toString().trim();

        if(confirm_pass.isEmpty()){
            newConfirmPassword.setError("Field can't be empty");
            return false;
        }else if(!password.equals(confirm_pass)){
            newConfirmPassword.setError("Password Not matching");
            return false;
        }else {
            newConfirmPassword.setError(null);
            return true;
        }
    }

    private boolean validNewPassword() {
        String password=newPassword.getText().toString().trim();

        if(password.isEmpty()){
            newPassword.setError("Field can't be empty");
            return false;
        }else if(!PASSWORD_PATTERN.matcher(password).matches()){
            newPassword.setError("please use special charecters and Do not use white space and it shold be 6 charecters. Ex:abc@123");
            return false;
        }
        else{
            newPassword.setError(null);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(new_password.this,LoginActivity.class);
        startActivity(intent);
    }
}