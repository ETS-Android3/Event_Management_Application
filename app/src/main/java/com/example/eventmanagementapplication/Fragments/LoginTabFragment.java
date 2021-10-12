package com.example.eventmanagementapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventmanagementapplication.Activities.MainActivity;
import com.example.eventmanagementapplication.Activities.forgot_passwordActivity;
import com.example.eventmanagementapplication.Models.googleLoginUserModel;
import com.example.eventmanagementapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginTabFragment extends Fragment {
        private static final int RC_SIGN_IN = 234;
        GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

    Button login_btn;
    EditText Email,Password;
    TextView forgetPass;
    ImageView google;
    float v=0;
    FirebaseAuth firebaseAuth;
    private String userID;
    private FirebaseAuth auth;
    private FirebaseUser users;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    ImageView password_visibility,password_visibility_off;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);

        auth = FirebaseAuth.getInstance();
        firebaseAuth =FirebaseAuth.getInstance();

        google = root.findViewById(R.id.fab_google);
        login_btn = root.findViewById(R.id.login_btn);
        Email =root.findViewById(R.id.login_email);
        Password=root.findViewById(R.id.login_password);
        forgetPass=root.findViewById(R.id.forgetpass);
        progressBar=root.findViewById(R.id.progressbar);
        relativeLayout=root.findViewById(R.id.prog_relay);
        password_visibility = root.findViewById(R.id.password_visibility);
        password_visibility_off = root.findViewById(R.id.password_visibility_off);

        mAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);

        google.setTranslationY(300);
        Email.setTranslationX(800);
        Password.setTranslationX(800);
        password_visibility.setTranslationX(800);
        password_visibility_off.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login_btn.setTranslationX(800);

        google.setAlpha(v);
        Email.setAlpha(v);
        Password.setAlpha(v);
        password_visibility.setAlpha(v);
        password_visibility_off.setAlpha(v);
        forgetPass.setAlpha(v);
        login_btn.setAlpha(v);

        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();;
        Email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        Password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        password_visibility.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        password_visibility_off.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login_btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        password_visibility.setVisibility(View.VISIBLE);
        password_visibility_off.setVisibility(View.GONE);

        password_visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_visibility.setVisibility(View.GONE);
                password_visibility_off.setVisibility(View.VISIBLE);
                Password.setTransformationMethod(null);
            }
        });
        password_visibility_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_visibility.setVisibility(View.VISIBLE);
                password_visibility_off.setVisibility(View.GONE);
                Password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), forgot_passwordActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_btn.setEnabled(false);
                String txt_email = Email.getText().toString();
                String txt_password = Password.getText().toString();
                Log.e("email....","onclick :  "+txt_email);
                Log.e("pass....","onclick :  "+txt_password);
                if( TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(getContext(), "Empty credential!", Toast.LENGTH_SHORT).show();
                    login_btn.setEnabled(true);

                }else{
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                    DatabaseReference databaseReference1 = firebaseDatabase.getReference();
                    databaseReference1.child("UsersRegistration").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                String cat=ds.child("email").getValue(String.class);
//                                Toast.makeText(getContext(), "emaIL :"+cat, Toast.LENGTH_SHORT).show();
                                Log.e("email ","onclick :"+cat);
                                if( txt_email.equals(cat)){
                                    loginUser(txt_email, txt_password);  
                                }else {
                                    login_btn.setEnabled(true);
//                                    Toast.makeText(getContext(), "this email is not exit", Toast.LENGTH_SHORT).show();
                                    Email.setText("");
                                    Password.setText("");
                                }
                           
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressBar.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.GONE);
                            Email.setError("no such user exit");
                        }
                    });

//                    try {
//                        Query checkUser = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/").getReference("UsersRegistration").orderByChild("email").equalTo(txt_email);
//                        Log.e("check user is :","onclick :"+checkUser);
//                        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                if(snapshot.exists()){
//                                    loginUser(txt_email, txt_password);
//                                    Toast.makeText(getContext(), "query", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//                                progressBar.setVisibility(View.GONE);
//                                relativeLayout.setVisibility(View.GONE);
//                                Email.setError("no such user exit");
//                            }
//                        });
//                    }catch (Exception e){
//                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }

                }
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        return root;
    }

    public void loginUser(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    progressBar.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    FirebaseUser user =firebaseAuth.getCurrentUser();
                    Log.e("user Uid....","onclick :  "+user.getUid());
                    Log.e("user Email....","onclick :  "+user.getEmail());

                    Toast.makeText(getContext(), "Login succesful", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(),userPageActivity.class));
//                    finish();
                    Intent intent= new Intent(getContext(), MainActivity.class);
                    Log.e("Uid is ...","onClick :"+userID);
                    intent.putExtra("Uid",userID);
                    startActivity(intent);
                    getActivity().finish();
                }else{
                    login_btn.setEnabled(true);
                    Email.setText("");
                    Password.setText("");
                    Toast.makeText(getContext(), "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // malli malli login cheyalsina avasram ledhu e code direct ga main page ni open chesthadi
        if(mAuth.getCurrentUser() != null){
            getActivity().finish();
            Intent intent= new Intent(getContext(), MainActivity.class);
            intent.putExtra("Uid",userID);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
                // ...
                progressBar.setVisibility(View.INVISIBLE);
                relativeLayout.setVisibility(View.INVISIBLE);
//                Toast.makeText(getActivity(), "error is" +e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
                            users =  auth.getCurrentUser();
                            userID =users.getUid();
                            GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/").getReference().child("UsersRegistration");
                            googleLoginUserModel googleLoginUserModel = new googleLoginUserModel(userID,signInAccount.getGivenName(),signInAccount.getFamilyName(),signInAccount.getEmail(),signInAccount.getPhotoUrl().toString());
                            mDatabase.child(userID).setValue(googleLoginUserModel);
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent= new Intent(getContext(), MainActivity.class);
                            Log.e("Uid is ...","onClick :"+userID);
                            intent.putExtra("Uid",userID);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), "Authentication Failed.", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


    private void signIn() {

        progressBar.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}

