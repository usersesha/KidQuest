package com.example.kidquest.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.kidquest.R;
import com.example.kidquest.adapters.ViewPagerAdapter;

public class Introduce extends AppCompatActivity {
    private ViewPager mSLideViewPager;
    private LinearLayout mDotind;
    private Button backbtn, nextbtn, skipbtn;
    private Intent i;
    private SharedPreferences preferences;
    private TextView[] dots;
    private ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_lay);
        backbtn = findViewById(R.id.backbtn);
        backbtn.setTranslationY(600);
        nextbtn = findViewById(R.id.nextbtn);
        skipbtn = findViewById(R.id.skipButton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getitem(0) > 0){
                    mSLideViewPager.setCurrentItem(getitem(-1),true);
                }
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getitem(0) < 3) {
                    mSLideViewPager.setCurrentItem(getitem(1), true);
                    backbtn.animate().translationY(0).alpha(1).setDuration(300).setStartDelay(100);
                }
                else {
                    preferences = getSharedPreferences(getString(R.string.preferences),MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(getString(R.string.intro), "false");
                    editor.commit();
                    i = new Intent(Introduce.this, Login.class);
                    startActivity(i);
                }
            }
        });

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = getSharedPreferences(getString(R.string.preferences),MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.intro), "false");
                editor.commit();

                i = new Intent(Introduce.this,Login.class);
                startActivity(i);
            }
        });

        mSLideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotind = (LinearLayout) findViewById(R.id.indicator);
        viewPagerAdapter = new ViewPagerAdapter(this);
        mSLideViewPager.setAdapter(viewPagerAdapter);
        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);
    }
    public void setUpindicator(int position){
        dots = new TextView[4];
        mDotind.removeAllViews();
        for (int i = 0 ; i < dots.length ; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("."));
            dots[i].setTextSize(50);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            mDotind.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.active,getApplicationContext().getTheme()));
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onPageSelected(int position) {
            setUpindicator(position);
            if (position > 0){
                backbtn.animate().translationY(0).alpha(1).setDuration(300).setStartDelay(100);
            }
            else {
                backbtn.animate().translationY(600).alpha(0).setDuration(300).setStartDelay(100);
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    private int getitem(int i){
        return mSLideViewPager.getCurrentItem() + i;
    }
}
