package com.payzout.business.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.payzout.business.R;
import com.payzout.business.apis.APIClient;
import com.payzout.business.apis.KYCInterface;
import com.payzout.business.common.InvestorRegistration;
import com.payzout.business.common.MainActivity;
import com.payzout.business.profile.KycActivity;
import com.payzout.business.utils.PayzoutLoading;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private PayzoutLoading payzoutLoading;
    private TextInputEditText etFullName;
    private TextInputEditText etPhoneNumber;
    private TextInputEditText etCity;
    private boolean fullNameStatus = false;
    private boolean phoneNumberStatus = false;
    private boolean cityStatus = false;
    private TextView tvProceed;
    private String number;
    private static final String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initView();
    }

    private void initView() {
        payzoutLoading = PayzoutLoading.getInstance();
        etFullName = findViewById(R.id.etFullName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etCity = findViewById(R.id.etCity);
        tvProceed = findViewById(R.id.tvProceed);

        tvProceed.setOnClickListener(this);

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String number = etPhoneNumber.getText().toString();
                if (number.isEmpty()) {
                    phoneNumberStatus = false;
                } else {
                    phoneNumberStatus = true;
                }
                checkForValidation();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = etFullName.getText().toString();
                if (name.isEmpty()) {
                    fullNameStatus = false;
                } else {
                    fullNameStatus = true;
                }
                checkForValidation();
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String city = etCity.getText().toString();
                if (city.isEmpty()) {
                    cityStatus = false;
                } else {
                    cityStatus = true;
                }
                checkForValidation();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        number = firebaseAuth.getCurrentUser().getPhoneNumber();
        etPhoneNumber.setText(number);
    }

    private void checkForValidation() {
        if (fullNameStatus && cityStatus && phoneNumberStatus) {
            tvProceed.setEnabled(true);
            tvProceed.setBackground(getResources().getDrawable(R.drawable.bg_button));
            tvProceed.setTextColor(getResources().getColor(R.color.colorTextH3));
        } else {
            tvProceed.setEnabled(false);
            tvProceed.setBackground(getResources().getDrawable(R.drawable.bg_text_box));
            tvProceed.setTextColor(getResources().getColor(R.color.colorTextH2));
        }
    }


    @Override
    public void onClick(View v) {
        if (v == tvProceed) {
            doShowConfirmationDialog();
        }
    }

    private void doShowConfirmationDialog() {
        new MaterialAlertDialogBuilder(RegistrationActivity.this)
                .setTitle("Register Confirmation")
                .setMessage("Are you sure, you cannot modify these details once you update.")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        doUpdateProfile();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void doUpdateProfile() {
        payzoutLoading.showProgress(RegistrationActivity.this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        String fullName = etFullName.getText().toString();
        String city = etCity.getText().toString();
        KYCInterface kycInterface = APIClient.getRetrofitInstance().create(KYCInterface.class);
        Call<InvestorRegistration> call = kycInterface.register(id, fullName, number, city);

        call.enqueue(new Callback<InvestorRegistration>() {
            @Override
            public void onResponse(Call<InvestorRegistration> call, Response<InvestorRegistration> response) {
                Log.e(TAG, "onResponse: " + response.code() + response.message());
                if (response.code() == 200) {
                    payzoutLoading.hideProgress();
                    goToMain();
                } else if (response.code() == 400) {
                    payzoutLoading.hideProgress();
                    Log.e(TAG, "onResponse: Bad Request");
                } else {
                    payzoutLoading.hideProgress();
                    Log.e(TAG, "onResponse: something went wrong");
                }
            }

            @Override
            public void onFailure(Call<InvestorRegistration> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void goToMain() {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}