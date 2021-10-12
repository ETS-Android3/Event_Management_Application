package com.example.eventmanagementapplication.Adapters;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.eventmanagementapplication.Fragments.LoginTabFragment;
import com.example.eventmanagementapplication.Fragments.RegistrationTabFragment;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context ;
    int totalTabs;

    public LoginAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public int getCount(){
        return totalTabs;
    }
    public Fragment getItem(int position){
        Log.e("position","onClick :"+position);

            switch (position) {
                case 0:
                    LoginTabFragment loginTabFreagment = new LoginTabFragment();
                    return loginTabFreagment;
                case 1:
                    RegistrationTabFragment registrationTabFragment = new RegistrationTabFragment();
                    return registrationTabFragment;
                default:
                    return null;
            }

    }
}
