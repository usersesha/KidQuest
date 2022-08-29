package com.example.kidquest.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.kidquest.sign.Signin;
import com.example.kidquest.sign.Signup;

public class LoginAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public LoginAdapter(FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public Fragment getItem(int position){
        switch (position){
            case 1:
            Signup signup = new Signup();
            return signup;
            case 0:
                Signin signin = new Signin();
                return signin;
                default:
                return null;
        }
    }
}
