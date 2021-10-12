package com.example.eventmanagementapplication.Activities;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.eventmanagementapplication.Adapters.DrawerAdapter;
import com.example.eventmanagementapplication.DrawerItem;
import com.example.eventmanagementapplication.Fragments.ChangePasswordFragment;
import com.example.eventmanagementapplication.Fragments.DashBoardFragment;
import com.example.eventmanagementapplication.Fragments.Feedback_fragment;
import com.example.eventmanagementapplication.Fragments.MyEventsFragment;
import com.example.eventmanagementapplication.Fragments.MyProfileFragment;
import com.example.eventmanagementapplication.Fragments.Rating_fragment;
import com.example.eventmanagementapplication.Models.StaticRVModel;
import com.example.eventmanagementapplication.R;
import com.example.eventmanagementapplication.Adapters.RecyclerAdapter;
import com.example.eventmanagementapplication.SimpleItem;
import com.example.eventmanagementapplication.SpaceItem;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    LottieAnimationView lottieAnimationView,lottieAnimationView1,lottieAnimationView2;

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_MY_PROFILE = 2;
    private static final int POS_MY_EVENTS = 3;
    private static final int POS_ABOUT_US = 4;
    private static final int POS_LOGOUT = 6;

    private String[] screenTitle;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    BottomNavigationView bottom_nav;

    GoogleSignInClient mGoogleSignInClient;
    android.app.FragmentTransaction transaction;
    ArrayList<StaticRVModel> item ;
    AutoCompleteTextView search_box;
    private DatabaseReference mUserDatabase;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();

        lottieAnimationView = (LottieAnimationView)findViewById(R.id.lottie);
        lottieAnimationView1 = (LottieAnimationView)findViewById(R.id.lottie1);
        lottieAnimationView2 = (LottieAnimationView)findViewById(R.id.lottie2);
        bottom_nav = (BottomNavigationView)findViewById(R.id.bottom_nav);
        bottom_nav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        lottieAnimationView.playAnimation();
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView1.playAnimation();
        lottieAnimationView1.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView2.playAnimation();
        lottieAnimationView2.setRepeatCount(LottieDrawable.INFINITE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);

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

        adapter.setSelected(POS_DASHBOARD);


        search_box = findViewById(R.id.search_box);
        recyclerView= findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mUserDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/").getReference("ShopProfile");

        lottieAnimationView = findViewById(R.id.lottie_search);
        lottieAnimationView.playAnimation();
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);

        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                String searchText = search_box.getText().toString();
//                firebasrUserSearch(searchText);

            }
        });

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                populateSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        mUserDatabase.addListenerForSingleValueEvent(event);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("ShopProfile");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                item =new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String image = ds.child("signup_shoplogo").getValue(String.class);
                    String shopName = ds.child("signup_shopname").getValue(String.class);
                    Log.e("shopName","onclcik :"+shopName);

                    item.add(new StaticRVModel(image, shopName));

                    recyclerView= findViewById(R.id.recycler);
                    recyclerAdapter = new RecyclerAdapter(getApplicationContext(),item);
                    GridLayoutManager GridLayout = new GridLayoutManager(getApplicationContext(), 2,GridLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(GridLayout);
                    recyclerView.setAdapter(recyclerAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(position == POS_DASHBOARD){
            DashBoardFragment dashBoardFragment = new DashBoardFragment();
            transaction.replace(R.id.container, dashBoardFragment);
        }else if(position == POS_MY_PROFILE){
            MyProfileFragment myProfileFragment = new MyProfileFragment();
            transaction.replace(R.id.container, myProfileFragment);
        }else if(position == POS_MY_EVENTS){
            MyEventsFragment settingsFragment = new MyEventsFragment();
            transaction.replace(R.id.container, settingsFragment);
        }else if(position == POS_ABOUT_US){
            ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
            transaction.replace(R.id.container, changePasswordFragment);
        }else if(position == POS_LOGOUT){
            try{
                Google_signOut();
                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
//                finish();
            }catch (IllegalArgumentException e){
                Log.e("User Exception:","Onclick"+e.getMessage());

            }
        }
        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();

        if(id == R.id.home){
            DashBoardFragment dashBoardFragment = new DashBoardFragment();
            transaction.replace(R.id.container, dashBoardFragment);
        }else if (id == R.id.feedback){
            Feedback_fragment feedback_fragment = new Feedback_fragment();
            transaction.replace(R.id.container, feedback_fragment);
        }else if (id == R.id.rating){
            Rating_fragment rating_fragment = new Rating_fragment();
            transaction.replace(R.id.container, rating_fragment);
        }
        slidingRootNav.closeMenu();
//        transaction.addToBackStack(null);
        transaction.commit();
        return true;
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

    private void populateSearch(DataSnapshot snapshot) {
        ArrayList<String> names= new ArrayList<>();
        if(snapshot.exists()){
            for (DataSnapshot ds: snapshot.getChildren()){
                String city_name = ds.child("signup_city").getValue(String.class);
                names.add(city_name);
            }

            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,names);
            search_box.setAdapter(adapter);
            search_box.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name =search_box.getText().toString();
                    searchUser(name);
                }
            });

        }else {
            Log.d("users","no data found");
        }
    }

    private void searchUser(String name) {
        Query query = mUserDatabase.orderByChild("signup_city").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    item =new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren()){
                        String image = ds.child("signup_shoplogo").getValue(String.class);
                        String shopName = ds.child("signup_shopname").getValue(String.class);

                        item.add(new StaticRVModel(image, shopName));

                        recyclerAdapter = new RecyclerAdapter(getApplicationContext(),item);
                        GridLayoutManager GridLayout = new GridLayoutManager(getApplicationContext(), 2,GridLayoutManager.VERTICAL,false);
                        recyclerView.setLayoutManager(GridLayout);
                        recyclerView.setAdapter(recyclerAdapter);
                    }
                }else {
                    Log.d("users","no data found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);
        Log.e("fragment.. ","onlcick :"+fragment);
        if(fragment != null){
            transaction= fragmentManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }else {
//            finish(); DashBoardFragment{73746f5 (0d4bc41b-634a-48f9-a6a6-6230dd23f3be) id=0x7f0a0095}
            super.onBackPressed();
        }
    }
}