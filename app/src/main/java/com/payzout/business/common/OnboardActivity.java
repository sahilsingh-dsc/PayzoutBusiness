package com.payzout.business.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.payzout.business.R;

public class OnboardActivity extends AppCompatActivity {

    public static ViewPager viewPager;
    OnboardPagerAdapter onboardPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        viewPager = findViewById(R.id.viewPager);
        onboardPagerAdapter = new OnboardPagerAdapter(this);
        viewPager.setAdapter(onboardPagerAdapter);


    }
}