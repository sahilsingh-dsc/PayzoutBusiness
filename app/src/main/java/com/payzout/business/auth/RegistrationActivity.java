package com.payzout.business.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.payzout.business.R;
import com.payzout.business.utils.PayzoutLoading;

public class RegistrationActivity extends AppCompatActivity {

    private PayzoutLoading payzoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initView();
    }

    private void initView() {
        payzoutLoading = PayzoutLoading.getInstance();
    }
}