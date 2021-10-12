package com.example.eventmanagementapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.eventmanagementapplication.R;

public class otp_code_verify extends AppCompatActivity {

    private Button otp_btn;
    private PinView pin_num_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_code_verify);

        otp_btn = findViewById(R.id.otp_btn);
        pin_num_view = findViewById(R.id.pin_num_view);

        Intent i = getIntent();
        String get_chang_pass_phone = i.getStringExtra("chang_pass_phone");
        Log.e("get_chang_pass_phone","onclick :"+get_chang_pass_phone);

        otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin_num_view.getText().toString().isEmpty()){
                    Toast.makeText(otp_code_verify.this, "Blank field can not be processed", Toast.LENGTH_SHORT).show();
                }else if(pin_num_view.getText().toString().length() != 6){
                    Toast.makeText(otp_code_verify.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(otp_code_verify.this, new_password.class);
                    intent.putExtra("new_pass_phone",get_chang_pass_phone);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(otp_code_verify.this,LoginActivity.class);
        startActivity(intent);
    }
}