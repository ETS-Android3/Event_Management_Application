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

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eventmanagementapplication.Adapters.DrawerAdapter;
import com.example.eventmanagementapplication.DrawerItem;
import com.example.eventmanagementapplication.Fragments.ChangePasswordFragment;
import com.example.eventmanagementapplication.Fragments.DashBoardFragment;
import com.example.eventmanagementapplication.Fragments.MyEventsFragment;
import com.example.eventmanagementapplication.Fragments.MyProfileFragment;
import com.example.eventmanagementapplication.Models.AddDecorationModel;
import com.example.eventmanagementapplication.Models.serviceCategoryModel;
import com.example.eventmanagementapplication.Models.setEventDetailsModel;
import com.example.eventmanagementapplication.R;
import com.example.eventmanagementapplication.SimpleItem;
import com.example.eventmanagementapplication.SpaceItem;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class SetEventDetailsActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener{
    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_MY_PROFILE = 2;
    private static final int POS_MY_EVENTS = 3;
    private static final int POS_ABOUT_US = 4;
    private static final int POS_LOGOUT = 6;

    private String[] screenTitle;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;

    TextView set_meeting_selected_date,set_metting_selected_time;
    Button set_meeting_calender_picker,set_meeting_time_picker,set_event_btn;
    EditText set_metting_description,provided_price;

    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    private int position;
    private String addDecorationID;
    private String vendorEmail;
    ArrayList<AddDecorationModel> item;
    private String Position;
    String user_id;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    ArrayList<serviceCategoryModel> item1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_event_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = findViewById(R.id.toolbar_setEventDetails);
        setSupportActionBar(toolbar);

        set_meeting_selected_date =  findViewById(R.id.set_meeting_selected_date);
//        set_meeting_calender_picker= findViewById(R.id.set_meeting_calender_picker);
        set_metting_selected_time= findViewById(R.id.set_metting_selected_time);
//        set_meeting_time_picker= findViewById(R.id.set_meeting_time_picker);
        provided_price= findViewById(R.id.provided_price);
        set_event_btn = findViewById(R.id.set_event_btn);
        set_metting_description = findViewById(R.id.set_metting_description);


        Intent intent = getIntent();
        Position = intent.getStringExtra("position");
        Log.e("position get", "onclick :" + Position);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String getPosition_sharedpref = sharedpreferences.getString("positionSubCat", "abc");
        Log.e("s1 is" ,"onclick :"+getPosition_sharedpref);

        mAuth=FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(SetEventDetailsActivity.this, gso);

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
                String getcat = getPosition_sharedpref;
                Log.e("position sub cat","onclick :"+getcat);
//                serviceCategoryModel serviceCategoryModel =item1.get(getPosition);
                String getCatName = getcat;
                Log.e("position sub cat11","onclick :"+getCatName);

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/");
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("AddDecoration");
                databaseReference.orderByChild("select_event").equalTo(getCatName).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        item =new ArrayList<>();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            String addDecorationID1 = ds.child("addDecorationID").getValue(String.class);
                            String vendorEmail1 = ds.child("vendorEmail").getValue(String.class);

                            Log.e("addDecorationID","onclcick :"+addDecorationID1);
                            AddDecorationModel addDecorationModel = ds.getValue(AddDecorationModel.class);
                            item.add(addDecorationModel);
                        }
                        int getPosition = Integer.valueOf(Position);
                        Log.e("position","onclick :"+getPosition);
                        AddDecorationModel addDecorationModel =item.get(getPosition);
                        addDecorationID = addDecorationModel.getAddDecorationID();
                        vendorEmail = addDecorationModel.getVendorEmail();


                        Log.e("addDecorationID","onclick :"+addDecorationID);
                        Log.e("vendorEmail","onclick :"+vendorEmail);
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



        set_event_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_event_btn.setEnabled(false);
                String eventTime = set_metting_selected_time.getText().toString();
                Log.e("event time","onclick :"+eventTime);
                String eventDate = set_meeting_selected_date.getText().toString();
                String providePrice = provided_price.getText().toString();
                String eventDescription = set_metting_description.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                Calendar c = Calendar.getInstance();
                Log.e("calender c", "onClick: " + c );
                String date = sdf.format(c.getTime());
                Log.e("date", "onClick: " + date );
                final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm aaa");
                String time = dateFormat.format(c.getTime());
                Log.e("time", "onClick: " + time );

                try {
                    if(TextUtils.isEmpty(providePrice) || TextUtils.isEmpty(eventDescription) || TextUtils.isEmpty(eventTime) || TextUtils.isEmpty(eventDate)){
                        Toast.makeText(SetEventDetailsActivity.this, "Empty credential!", Toast.LENGTH_SHORT).show();
                        set_event_btn.setEnabled(true);
                    }else {
                        if(validCalender() == true) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://vendoreventapplication-default-rtdb.firebaseio.com/").getReference().child("SetEventDetails");
                            String eventDetailsID = mDatabase.push().getKey();
                            String isOrderConfirm = "2";
                            setEventDetailsModel setMeetingDetailsModel = new setEventDetailsModel(eventDetailsID, addDecorationID, user_id, vendorEmail, eventTime, eventDate, providePrice, eventDescription, isOrderConfirm, date, time);
                            mDatabase.child(eventDetailsID).setValue(setMeetingDetailsModel);
                            Toast.makeText(SetEventDetailsActivity.this, "we have successfully register your order wait until vendor accept", Toast.LENGTH_SHORT).show();
                            set_metting_selected_time.setText("");
                            set_meeting_selected_date.setError(null);
                            set_meeting_selected_date.setText("");
                            provided_price.setText("");
                            set_metting_description.setText("");
                        }else{
                            Toast.makeText(SetEventDetailsActivity.this, "Invalid date", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (NullPointerException e){
                    Toast.makeText(SetEventDetailsActivity.this, "Field can not be empty"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("exception ","onclick :"+e);
                    set_event_btn.setEnabled(true);
                 }catch (IllegalArgumentException e){
                    Log.e("User Exception reg1","Onclick"+e.getMessage());
                }catch (Exception e){
                    Log.e("User Exception reg2","Onclick"+e.getMessage());
                }

            }
        });

//        set_meeting_time_picker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timePickerMethod();
//                Toast.makeText(SetEventDetailsActivity.this, "time picker is calling", Toast.LENGTH_SHORT).show();
//            }
//        });

        set_metting_selected_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerMethod();
            }
        });

        set_meeting_selected_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calenderPickerMethod();
            }
        });

//
//        set_meeting_calender_picker.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        calenderPickerMethod();
//                    }
//                });


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

    private void timePickerMethod() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        Log.e("set time is :","onclick :"+minute);
        Log.e("set time is :","onclick :"+hour);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(SetEventDetailsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                if(hourOfDay>12) {
                    set_metting_selected_time.setText(String.valueOf(hourOfDay-12)+ ":"+(String.valueOf(minute)+"pm"));
                } else if(hourOfDay==12) {
                    set_metting_selected_time.setText("12"+ ":"+(String.valueOf(minute)+"pm"));
                } else if(hourOfDay<12) {
                    if(hourOfDay!=0) {
                        set_metting_selected_time.setText(String.valueOf(hourOfDay) + ":" + (String.valueOf(minute) + "am"));
                    } else {
                        set_metting_selected_time.setText("12" + ":" + (String.valueOf(minute) + "am"));
                    }
                }

            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time :");
        mTimePicker.show();
    }

    private void calenderPickerMethod() {
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        try {
            materialDatePicker.show(SetEventDetailsActivity.this.getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        }catch (IllegalArgumentException e){
            Toast.makeText(SetEventDetailsActivity.this, "error from meterial date picker", Toast.LENGTH_SHORT).show();
        }
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        set_meeting_selected_date.setText(materialDatePicker.getHeaderText());
                    }
                }
        );
    }


//    private boolean validCalender() throws ParseException {
//        set_event_btn.setEnabled(true);
//        int yer=0,yer1=0,month=0,month1=0,day=0,day1=0;
//        String input = set_meeting_selected_date.getText().toString().trim();     //input string
//        Log.e("input date", "onClick: " + input );
//        String lastFourDigits = "";
//        String finalDayInput = "";
//        String finalMonthInput = "";
//        if(TextUtils.isEmpty(input)){
//            Toast.makeText(this, "Field can not be empty", Toast.LENGTH_SHORT).show();
//        }else {
//              //substring containing last 4 characters
//            SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM yyyy");
//            Date dt2=sdf1.parse(input);
//            DateFormat format=new SimpleDateFormat("d");
//            DateFormat format0=new SimpleDateFormat("M");
//            finalDayInput=format.format(dt2);
//            finalMonthInput=format0.format(dt2);
//            Log.e("dayInput", "onClick: " + finalDayInput );
//            Log.e("MonthInput", "onClick: " + finalMonthInput );
//        }
//
//
//        if (input.length() > 4)
//        {
//            lastFourDigits = input.substring(input.length() - 4);
//            yer1=Integer.parseInt(lastFourDigits);
//            month1=Integer.parseInt(finalMonthInput);
//            day1=Integer.parseInt(finalDayInput);
//            Log.e("Roja2", "onClick: " + lastFourDigits );
//        }
//
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
//        Calendar c = Calendar.getInstance();
//        Log.e("calender c", "onClick: " + c );
//
//        String date = sdf.format(c.getTime());
//        Log.e("date", "onClick: " + date );
//        Date dt1=sdf.parse(date);
//        DateFormat format2=new SimpleDateFormat("d");
//        DateFormat format3=new SimpleDateFormat("M");
//        String finalDay=format2.format(dt1);
//        String finalMonth=format3.format(dt1);
//        Log.e("day1", "onClick: " + finalDay );
//        Log.e("Month", "onClick: " + finalMonth );
//
//
//        String lastFourDigits1 = "";
//        if (date.length() > 4)
//        {
//            lastFourDigits1 = date.substring(date.length() - 4);
//            yer=Integer.parseInt(lastFourDigits1);
//            day=Integer.parseInt(finalDay);
//            month=Integer.parseInt(finalMonth);
//            Log.e("lastFourDigits", "onClick: " + lastFourDigits );
//
//        }
//
//
//        if(yer1 > yer || month1 > month || day1 > day){
//
////            MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
////
////            materialDateBuilder.setTitleText("SELECT A DATE");
////
////            final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
////            materialDatePicker.show( SetEventDetailsActivity.this.getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
////            materialDatePicker.addOnPositiveButtonClickListener(
////                    new MaterialPickerOnPositiveButtonClickListener() {
////                        @SuppressLint("SetTextI18n")
////                        @Override
////                        public void onPositiveButtonClick(Object selection) {
////                            set_meeting_selected_date.setText("Selected Date1 is : " + materialDatePicker.getHeaderText());
////                        }
////                    });
//            return true;
//        }else{
//            set_meeting_selected_date.setError("choose correct date");
//            return false;
//        }
//
//    }
    private boolean validCalender() throws ParseException {
        set_event_btn.setEnabled(true);
        int current_yer=0,selected_yer1=0,current_month=0,selected_month1=0,current_day=0,selected_day1=0;
        String input = set_meeting_selected_date.getText().toString().trim();     //input string
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
        if(selected_yer1 == current_yer){
            if(selected_month1 <= current_month){
                if(selected_day1 <= current_day){
                    set_meeting_selected_date.setError("Choose correct date ");
                    Toast.makeText(this,"Choose correct date ",Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }

        if(selected_yer1 > current_yer){
            set_meeting_selected_date.setError(null);
            set_meeting_selected_date.setText(input);
            return true;
        }else if(selected_yer1 == current_yer){
            if(selected_month1 > current_month){
                set_meeting_selected_date.setError(null);
                set_meeting_selected_date.setText(input);
                return true;
            }else if(selected_month1 == current_month){
                if(selected_day1 >= current_day){
                    set_meeting_selected_date.setError(null);
                    set_meeting_selected_date.setText(input);
                    return true;
                }else{
                    set_meeting_selected_date.setError("Choose correct date ");
                    Toast.makeText(this,"Choose correct date ",Toast.LENGTH_LONG).show();
                    return false;
                }
            }else{
                set_meeting_selected_date.setError("Choose correct date ");
                Toast.makeText(this,"Choose correct date ",Toast.LENGTH_LONG).show();
                return false;
            }
        }else{
            set_meeting_selected_date.setError("Choose correct date ");
            Toast.makeText(this,"Choose correct date ",Toast.LENGTH_LONG).show();
            return false;
        }

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
            transaction.replace(R.id.container_setEventDetails, dashBoardFragment);
        }else if(position == POS_MY_PROFILE){
            MyProfileFragment myProfileFragment = new MyProfileFragment();
            transaction.replace(R.id.container_setEventDetails, myProfileFragment);
        }else if(position == POS_MY_EVENTS){
            MyEventsFragment settingsFragment = new MyEventsFragment();
            transaction.replace(R.id.container_setEventDetails, settingsFragment);
        }else if(position == POS_ABOUT_US){
            ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
            transaction.replace(R.id.container_setEventDetails, changePasswordFragment);
        }else if(position == POS_LOGOUT){
            try{
                Google_signOut();
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
//                finish();
            }catch (IllegalArgumentException e){
                Log.e("User Exception","Onclick"+e.getMessage());

            }
        }
        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void Google_signOut() {
        // Firebase sign out
        mAuth.signOut();

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


