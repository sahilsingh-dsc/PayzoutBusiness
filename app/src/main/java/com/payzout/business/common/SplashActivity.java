package com.payzout.business.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.payzout.business.R;
import com.payzout.business.auth.PhoneActivity;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        initView();

//        if (isLogin()){
//            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
    }
//
//    private boolean isLogin() {
//    }

    private void initView() {
        firebaseAuth = FirebaseAuth.getInstance();
        delay3000milli();
    }

    private void delay3000milli() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, 3000);
    }

    private void checkLoginStatus() {
        if (firebaseAuth.getCurrentUser() != null) {
            gotoMain();
        } else {
            gotoOnboard();
        }
    }

    private void gotoOnboard() {
        Intent intent = new Intent(SplashActivity.this, OnboardActivity.class);
        startActivity(intent);
        //gotoLogin();
        finish();
    }

//    private void gotoLogin() {
//        Intent intent = new Intent(SplashActivity.this, PhoneActivity.class);
//        startActivity(intent);
//        finish();
//    }

    private void gotoMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}