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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.eventmanagementapplication.Adapters.DrawerAdapter;
import com.example.eventmanagementapplication.Adapters.ItemSubCatAdapter;
import com.example.eventmanagementapplication.DrawerItem;
import com.example.eventmanagementapplication.Fragments.ChangePasswordFragment;
import com.example.eventmanagementapplication.Fragments.DashBoardFragment;
import com.example.eventmanagementapplication.Fragments.MyEventsFragment;
import com.example.eventmanagementapplication.Fragments.MyProfileFragment;
import com.example.eventmanagementapplication.Models.itemSubCatModel;
import com.example.eventmanagementapplication.Models.serviceCategoryModel;
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

public class SubCatActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener{
    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_MY_PROFILE = 2;
    private static final int POS_MY_EVENTS = 3;
    private static final int POS_ABOUT_US = 4;
    private static final int POS_LOGOUT = 6;

    private String[] screenTitle;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;

    private RecyclerView recyclerView;
    private ItemSubCatAdapter itemSubCatAdapter;

    GoogleSignInClient mGoogleSignInClient;
    ArrayList<itemSubCatModel> item;
    private String Position;
    ArrayList<serviceCategoryModel> item1;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = findViewById(R.id.toolbar_Subcat);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Position = intent.getStringExtra("position");
        Log.e("position get10", "onclick :" + Position);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("positionSubCat", Position);
        editor.commit();

        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
        DatabaseReference databaseReference1 = firebaseDatabase1.getReference().child("AddEvent");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                item1 =new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String selectedEvent = ds.child("event_name").getValue(String.class);
                    item1.add(new serviceCategoryModel(selectedEvent));
                }
                String getCategoryName = Position;
                Log.e("position sub cat","onclick :"+getCategoryName);
//                serviceCategoryModel serviceCategoryModel =item1.get(getPosition);
                String getCatName = getCategoryName;
                Log.e("position sub cat111","onclick :"+getCatName);

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("AddDecoration");
                databaseReference.orderByChild("select_event").equalTo(getCatName).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        item =new ArrayList<>();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            String decorationImage = ds.child("decoration_pic_image").getValue(String.class);
                            String decorationName = ds.child("decoration_name").getValue(String.class);
                            String decStartPrice = ds.child("event_start_price").getValue(String.class);
                            String decUptoPrice = ds.child("event_upto_price").getValue(String.class);


                            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("decorationImage", decorationImage);
                            editor.commit();

                            item.add(new itemSubCatModel(decorationImage,decorationName,decStartPrice,decUptoPrice));

                            recyclerView= findViewById(R.id.cat_recycler);
                            itemSubCatAdapter = new ItemSubCatAdapter(item,SubCatActivity.this);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(itemSubCatAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(SubCatActivity.this, gso);

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
            transaction.replace(R.id.container_Subcat, dashBoardFragment);
        }else if(position == POS_MY_PROFILE){
            MyProfileFragment myProfileFragment = new MyProfileFragment();
            transaction.replace(R.id.container_Subcat, myProfileFragment);
        }else if(position == POS_MY_EVENTS){
            MyEventsFragment settingsFragment = new MyEventsFragment();
            transaction.replace(R.id.container_Subcat, settingsFragment);
        }else if(position == POS_ABOUT_US){
            ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
            transaction.replace(R.id.container_Subcat, changePasswordFragment);
        }else if(position == POS_LOGOUT){
            try{
               Google_signOut();
            }catch (IllegalArgumentException e){
                Log.e("Exceptionshowoneuser1","Onclick"+e.getMessage());

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