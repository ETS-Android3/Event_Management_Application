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
import com.example.eventmanagementapplication.Adapters.ServiceCatAdapter;
import com.example.eventmanagementapplication.DrawerItem;
import com.example.eventmanagementapplication.Fragments.ChangePasswordFragment;
import com.example.eventmanagementapplication.Fragments.DashBoardFragment;
import com.example.eventmanagementapplication.Fragments.MyEventsFragment;
import com.example.eventmanagementapplication.Fragments.MyProfileFragment;
import com.example.eventmanagementapplication.Models.ShopDetailsModel;
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

public class ShopImageActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener{
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
    private ServiceCatAdapter serviceCatAdapter;

    GoogleSignInClient mGoogleSignInClient;
    ArrayList<serviceCategoryModel> item;
    ArrayList<ShopDetailsModel> item1;
    private String Position;
    int count = 0;
    TextView empty_event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_image);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = findViewById(R.id.toolbar_shop_image);
        setSupportActionBar(toolbar);

//        empty_event = findViewById(R.id.empty_event);
//        empty_event.setVisibility(View.GONE);

        Intent intent = getIntent();
        Position = intent.getStringExtra("position");
        Log.e("position get", "onclick :" + Position);

        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
        DatabaseReference databaseReference1 = firebaseDatabase1.getReference();
        databaseReference1.child("VendorRegistration").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                item1 =new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String email = ds.child("email").getValue(String.class);
                    item1.add(new ShopDetailsModel(email));
                }
                int getPosition = Integer.valueOf(Position);
                Log.e("position sub cat","onclick :"+getPosition);
                ShopDetailsModel shopDetailsModel =item1.get(getPosition);
                String get_vendor_email = shopDetailsModel.getEmail();
                Log.e("get_vendor_email","onclick :"+get_vendor_email);

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("AddEvent");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        item =new ArrayList<>();

                        for(DataSnapshot ds : snapshot.getChildren()) {
                            String selectedEvent = ds.child("event_name").getValue(String.class);
                            Log.e("selectedEvent","onclick :"+selectedEvent);



                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                                DatabaseReference databaseReference = firebaseDatabase.getReference().child("AddDecoration");

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            String select_event = ds.child("select_event").getValue(String.class);
                                            Log.e("select_event", "onclick :" + select_event);
                                            String vendorEmail = ds.child("vendorEmail").getValue(String.class);
                                            Log.e("vendorEmail", "onclick :" + vendorEmail);

                                            if (vendorEmail.equals(get_vendor_email)) {
                                                if (select_event.equals(selectedEvent)) {
                                                    item.add(new serviceCategoryModel(selectedEvent));
                                                    recyclerView = findViewById(R.id.service_recycler);
                                                    serviceCatAdapter = new ServiceCatAdapter(item);
                                                    recyclerView.setHasFixedSize(true);
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                                    recyclerView.setAdapter(serviceCatAdapter);

                                                }

                                            } else {
//                                                count++;
//                                                Log.e("count", "onclick :" + count);
//                                                empty_event.setVisibility(View.VISIBLE);
                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e("exception3", "onclick :");
                                    }
                                });
                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("exception1", "onclick :");
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("exception2", "onclick :");
            }
        });



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(ShopImageActivity.this, gso);

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
                createItemFor(POS_DASHBOARD),
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
            transaction.replace(R.id.container_shop_image, dashBoardFragment);
        }else if(position == POS_MY_PROFILE){
            MyProfileFragment myProfileFragment = new MyProfileFragment();
            transaction.replace(R.id.container_shop_image, myProfileFragment);
        }else if(position == POS_MY_EVENTS){
            MyEventsFragment settingsFragment = new MyEventsFragment();
            transaction.replace(R.id.container_shop_image, settingsFragment);
        }else if(position == POS_ABOUT_US){
            ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
            transaction.replace(R.id.container_shop_image, changePasswordFragment);
        }else if(position == POS_LOGOUT){
            try{
              Google_signOut();
            }catch (IllegalArgumentException e){
                Log.e(" Exception showoneuser1","Onclick"+e.getMessage());

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