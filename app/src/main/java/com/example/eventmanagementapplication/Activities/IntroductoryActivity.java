package com.example.eventmanagementapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.eventmanagementapplication.Fragments.OnBoardingFragment1;
import com.example.eventmanagementapplication.Fragments.OnBoardingFragment2;
import com.example.eventmanagementapplication.Fragments.OnBoardingFragment3;
import com.example.eventmanagementapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class IntroductoryActivity extends AppCompatActivity {
    ImageView appname,logo,splashImg;
    LottieAnimationView lottieAnimationView;

    private static final int NUM_PAGES =3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter screenSlidePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
       // getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        logo=(ImageView)findViewById(R.id.logo);
        appname =(ImageView)findViewById(R.id.appname);
        splashImg = (ImageView)findViewById(R.id.img);
        lottieAnimationView = (LottieAnimationView)findViewById(R.id.lottie);

        viewPager = findViewById(R.id.pager);
        screenSlidePagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(screenSlidePagerAdapter);

        lottieAnimationView.playAnimation();
        splashImg.animate().translationY(-2400).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2400).setDuration(1000).setStartDelay(4000);
        appname.animate().translationY(2400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
        switch (position){
            case 0:
                OnBoardingFragment1 tab1=new OnBoardingFragment1();
                return tab1;
            case 1:
                OnBoardingFragment2 tab2=new OnBoardingFragment2();
                return tab2;
            case 2:
                OnBoardingFragment3 tab3=new OnBoardingFragment3();
                return tab3;
        }
        return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // malli malli login cheyalsina avasram ledhu e code direct ga main page ni open chesthadi
        if( FirebaseAuth.getInstance().getCurrentUser() != null){
            finish();
            Intent intent= new Intent(IntroductoryActivity.this, MainActivity.class);
            intent.putExtra("Uid",FirebaseAuth.getInstance().getCurrentUser().getUid() );
            startActivity(intent);
           finish();
        }
    }
}