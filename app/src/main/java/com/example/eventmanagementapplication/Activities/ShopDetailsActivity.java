package com.example.eventmanagementapplication.Activities;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.eventmanagementapplication.Adapters.DrawerAdapter;
import com.example.eventmanagementapplication.DrawerItem;
import com.example.eventmanagementapplication.Fragments.ChangePasswordFragment;
import com.example.eventmanagementapplication.Fragments.DashBoardFragment;
import com.example.eventmanagementapplication.Fragments.MyEventsFragment;
import com.example.eventmanagementapplication.Fragments.MyProfileFragment;
import com.example.eventmanagementapplication.Models.ShopDetailsModel;
import com.example.eventmanagementapplication.R;
import com.example.eventmanagementapplication.SimpleItem;
import com.example.eventmanagementapplication.SpaceItem;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class ShopDetailsActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener{
    private TextView title_shop_details,shop_name,shop_name1,shop_address,shop_address1,shop_phone,shop_phone1,shop_email,shop_email1,shop_open_time,shop_open_time1,shop_close_time,shop_close_time1,shop_rating,shop_rating1;
    float v=0;

    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_MY_PROFILE = 2;
    private static final int POS_MY_EVENTS = 3;
    private static final int POS_ABOUT_US = 4;
    private static final int POS_LOGOUT = 6;

    private String[] screenTitle;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;

    GoogleSignInClient mGoogleSignInClient;

    private String Position;
    ArrayList<ShopDetailsModel> item1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = findViewById(R.id.toolbar_shop_details);
        setSupportActionBar(toolbar);

        shop_name =findViewById(R.id.shop_name);
        shop_name1 = findViewById(R.id.shop_name1);
        shop_address = findViewById(R.id.shop_address);
        shop_address1 = findViewById(R.id.shop_address1);
        shop_phone = findViewById(R.id.shop_phone);
        shop_phone1 = findViewById(R.id.shop_phone1);
        shop_open_time = findViewById(R.id.shop_open_time);
        shop_open_time1 =findViewById(R.id.shop_open_time1);
        shop_close_time = findViewById(R.id.shop_close_time);
        shop_close_time1 = findViewById(R.id.shop_close_time1);
        shop_rating = findViewById(R.id.shop_rating);
        shop_rating1 = findViewById(R.id.shop_rating1);
        title_shop_details=findViewById(R.id.title_shop_details);

        Intent intent = getIntent();
        Position = intent.getStringExtra("position");
        Log.e("position get", "onclick :" + Position);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(ShopDetailsActivity.this, gso);

        shop_name.setTranslationX(800);
        shop_name1.setTranslationX(800);
        shop_address.setTranslationX(800);
        shop_address1.setTranslationX(800);
        shop_phone.setTranslationX(800);
        shop_phone1.setTranslationX(800);
        shop_open_time.setTranslationX(800);
        shop_open_time1.setTranslationX(800);
        shop_close_time.setTranslationX(800);
        shop_close_time1.setTranslationX(800);
        shop_rating.setTranslationX(800);
        shop_rating1.setTranslationX(800);
        title_shop_details.setTranslationZ(800);

        shop_name.setAlpha(v);
        shop_name1.setAlpha(v);
        shop_address.setAlpha(v);
        shop_address1.setAlpha(v);
        shop_phone.setAlpha(v);
        shop_phone1.setAlpha(v);
        shop_open_time.setAlpha(v);
        shop_open_time1.setAlpha(v);
        shop_close_time.setAlpha(v);
        shop_close_time1.setAlpha(v);
        shop_rating.setAlpha(v);
        shop_rating1.setAlpha(v);
        title_shop_details.setAlpha(v);

        shop_name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        shop_name1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        shop_address.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        shop_address1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        shop_phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();
        shop_phone1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1300).start();
        shop_open_time.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1500).start();
        shop_open_time1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1700).start();
        shop_close_time.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1900).start();
        shop_close_time1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2100).start();
        shop_rating.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2300).start();
        shop_rating1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2500).start();
        title_shop_details.animate().translationZBy(0).alpha(1).setDuration(1000).setStartDelay(600).start();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Rating_tbl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int ratingCount = 0;
                int ratingUserLength = 0;
                int rating_1 = 0;
                int rating_2 = 0;
                int rating_3 = 0;
                int rating_4 = 0;
                int rating_5 = 0;
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String rating = ds.child("rating").getValue(String.class);
                    Log.e("rating","onclick :"+rating);
                    if(rating != null){
                        ratingUserLength++;
                        int rating_int = Integer.parseInt(rating);
                        Log.e("rating int","onclick :"+rating_int);
                        if(rating_int == 1){
                            rating_1++;
                        }else if (rating_int == 2){
                            rating_2++;
                        }else if (rating_int == 3){
                            rating_3++;
                        }else if (rating_int == 4){
                            rating_4++;
                        }else if (rating_int == 5){
                            rating_5++;
                        }
                    }else{
                        shop_rating1.setText("No Ratings are given by user till now");
                    }
                    Log.e("rating User Length","onclick :"+ratingUserLength);
                    Log.e("rating 1 User Length","onclick :"+rating_1);
                    Log.e("rating 2 User Length","onclick :"+rating_2);
                    Log.e("rating 3 User Length","onclick :"+rating_3);
                    Log.e("rating 4 User Length","onclick :"+rating_4);
                    Log.e("rating 5 User Length","onclick :"+rating_5);
                    if(ratingCount <= ratingUserLength){
                        ratingCount++;
                    }
                    Log.e("total members1","onclick :"+ratingCount);

                }
                Log.e("total members","onclick :"+ratingCount);
                int upValue = 5*rating_5 + 4*rating_4 + 3*rating_3 + 2*rating_2 + 1*rating_1 ;
                Log.e("upValue","onclick :"+upValue);
                int lowValue = rating_5 + rating_4 + rating_3 + rating_2 + rating_1;
                Log.e("lowValue","onclick :"+lowValue);
                float finalCountRating = (float)upValue / lowValue;
                Log.e("finalCountRating new","onclick :"+finalCountRating);
                String finalCountRating_str = String.valueOf(finalCountRating);
                String ratingCount_str = String.valueOf(ratingCount);
                shop_rating1.setText(finalCountRating_str+"/"+"("+ratingCount_str+")");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
        DatabaseReference databaseReference1 = firebaseDatabase1.getReference();
        databaseReference1.child("ShopProfile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                item1 =new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String signup_shopname = ds.child("signup_shopname").getValue(String.class);
                    String signup_shopaddress = ds.child("signup_shopaddress").getValue(String.class);
                    String signup_shopphoneNo = ds.child("signup_shopphoneNo").getValue(String.class);
                    String signup_opentime = ds.child("signup_opentime").getValue(String.class);
                    String signup_closetime = ds.child("signup_closetime").getValue(String.class);
                    item1.add(new ShopDetailsModel(signup_shopname,signup_shopaddress,signup_shopphoneNo,signup_opentime,signup_closetime));
                }
                int getPosition = Integer.valueOf(Position);
                Log.e("position sub cat","onclick :"+getPosition);
                ShopDetailsModel shopDetailsModel =item1.get(getPosition);
                String get_signup_shopname = shopDetailsModel.getSignup_shopname();
                String get_signup_shopaddress = shopDetailsModel.getSignup_shopaddress();
                String get_signup_shopphoneNo = shopDetailsModel.getSignup_shopphoneNo();
                String get_signup_opentime = shopDetailsModel.getSignup_opentime();
                String get_signup_closetime = shopDetailsModel.getSignup_closetime();
                Log.e("position sub cat","onclick :"+shopDetailsModel.getSignup_shopname());

                shop_name1.setText(get_signup_shopname);
                shop_address1.setText(get_signup_shopaddress);
                shop_phone1.setText(get_signup_shopphoneNo);
                shop_open_time1.setText(get_signup_opentime);
                shop_close_time1.setText(get_signup_closetime);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

                slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();
        screenIcons = loadScreenIcons();
        screenTitle = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_MY_PROFILE),
                createItemFor(POS_MY_EVENTS),
                createItemFor(POS_ABOUT_US),
                new SpaceItem(250),
                createItemFor(POS_LOGOUT)
        ));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

//        adapter.setSelected(POS_DASHBOARD);
    }
    private DrawerItem createItemFor(int position){
        return new SimpleItem(screenIcons[position],screenTitle[position]).withIconTint(R.color.pink)
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.pink))
                .withSelectedTextTint(color(R.color.pink));
    }

    @ColorInt
    private int color(@ColorRes int res){
        return ContextCompat.getColor(this,res);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for(int i= 0; i< ta.length(); i++){
            int id = ta.getResourceId(i, 0);
            if(id!=0){
                icons[i] = ContextCompat.getDrawable(this,id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(position == POS_DASHBOARD){
            DashBoardFragment dashBoardFragment = new DashBoardFragment();
            transaction.replace(R.id.container_shop_details, dashBoardFragment);
        }else if(position == POS_MY_PROFILE){
            MyProfileFragment myProfileFragment = new MyProfileFragment();
            transaction.replace(R.id.container_shop_details, myProfileFragment);
        }else if(position == POS_MY_EVENTS){
            MyEventsFragment settingsFragment = new MyEventsFragment();
            transaction.replace(R.id.container_shop_details, settingsFragment);
        }else if(position == POS_ABOUT_US){
            ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
            transaction.replace(R.id.container_shop_details, changePasswordFragment);
        }else if(position == POS_LOGOUT){
            try{
              Google_signOut();
            }catch (IllegalArgumentException e){
                Log.e("User Exception showoneuser1","Onclick"+e.getMessage());

            }
        }
        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void Google_signOut() {
        // Firebase sign out
        FirebaseAuth.getInstance().signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                    }
                });
    }
}