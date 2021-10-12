package com.example.eventmanagementapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chaos.view.PinView;
import com.example.eventmanagementapplication.Activities.forgot_passwordActivity;
import com.example.eventmanagementapplication.Activities.otp_code_verify;
import com.example.eventmanagementapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ChangePasswordFragment extends Fragment {

    private TextInputEditText change_pass_phone;
    private Button next_btn;
    int code = 91;
    private FirebaseAuth auth;
    String mVerificationId;
    private FirebaseDatabase firebaseDatabase;
    private String userID;
    private FirebaseUser users;
    private PinView pin_num_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.change_password_fragment, container, false);

//        change_pass_phone = root.findViewById(R.id.change_pass_phone);
//        next_btn = root.findViewById(R.id.next_btn);
//        pin_num_view = root.findViewById(R.id.pin_num_view);
//
//        auth = FirebaseAuth.getInstance();
//        users =  auth.getCurrentUser();
//        userID =users.getUid();
//
//        next_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String phoneNum = change_pass_phone.getEditableText().toString();
//                Log.e("phoneNum","onclick :"+phoneNum);
//                String _phoneNo = "+"+code+change_pass_phone.getEditableText().toString();
//                Log.e("mobile is :","onclick :"+_phoneNo);
//
//                firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
//                DatabaseReference databaseReference = firebaseDatabase.getReference();
//                databaseReference.child("UsersRegistration").child(userID).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String Mobile = snapshot.child("mobile").getValue(String.class);
//                        if(phoneNum.equals(Mobile)){
//                            sendVerificationCodeToUser(_phoneNo);
//                            Intent intent = new Intent(getContext(),otp_code_verify.class);
//                            intent.putExtra("chang_pass_phone",phoneNum);
//                            startActivity(intent);
//                        }else {
//                            change_pass_phone.setError("no such user exit");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

//                Query checkUser = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/").getReference("UsersRegistration").orderByChild("mobile").equalTo(phoneNum);
//                Log.e("check user is :","onclick :"+checkUser);
//                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()){
//                            sendVerificationCodeToUser(_phoneNo);
//                            Intent intent = new Intent(forgot_passwordActivity.this,otp_code_verify.class);
//                            intent.putExtra("chang_pass_phone",phoneNum);
//                            startActivity(intent);
////                            PhoneAuthCredential credential= PhoneAuthProvider.getCredential(mVerificationId,mVerificationId);
////                            signInWithPhoneAuthCredential(credential);
//                        }else {
//                            change_pass_phone.setError("no such user exit");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });

        return root;
    }

//    private void sendVerificationCodeToUser(String phoneNo) {
//        Log.e("sendVerificationCode","onclick"+phoneNo);
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(auth)
//                        .setPhoneNumber(phoneNo)            // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(getActivity())                 // Activity (for callback binding)
//                        .setCallbacks(mCallbacks)           // OnVerificationStateChangedCallbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        @Override
//        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
////            super.onCodeSent(s, forceResendingToken);
//            Log.e("TAG01", "onCodeSent:" + s);
//
//            // Save verification ID and resending token so we can use them later
//            mVerificationId = s;
//            PhoneAuthProvider.ForceResendingToken mResendToken = forceResendingToken;
//        }
//
//        @Override
//        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//            Log.e("TAG", "onVerificationCompleted:" + phoneAuthCredential);
//            String code = phoneAuthCredential.getSmsCode();
//            Log.e("code is :","onclick :"+code);
//            if(code!=null) {
//                pin_num_view.setText(code);
//                signInWithPhoneAuthCredential(phoneAuthCredential);
//
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(@NonNull FirebaseException e) {
//            Log.e("TAG0", "onVerificationFailed"+ e);
//            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                // Invalid request
//            } else if (e instanceof FirebaseTooManyRequestsException) {
//                // The SMS quota for the project has been exceeded
//            }
//        }
//    };
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        auth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.e("TAG1", "signInWithCredential:success"+credential);
//
//
////                            FirebaseUser user = task.getResult().getUser();
////                            Toast.makeText(MainActivity.this, "signInWithCredential:success"+user, Toast.LENGTH_SHORT).show();
//
//
//                } else {
//                    // Sign in failed, display a message and update the UI
//                    Log.e("TAG2", "signInWithCredential:failure"+ task.getException());
//                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid
//                    }
//                }
//            }
//        });
//    }
}
