package com.payzout.business.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.payzout.business.R;
import com.payzout.business.common.MainActivity;
import com.payzout.business.common.SplashActivity;
import com.payzout.business.utils.Constant;
import com.payzout.business.utils.FirestoreConstant;
import com.payzout.business.wallet.Wallet;

import java.util.concurrent.TimeUnit;

public class CodeActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String country = "+91";
    private static final String TAG = "CodeActivity";
    private TextView tvCodeNotice;
    private FirebaseAuth firebaseAuth;
    private TextView tvContinue;
    private TextView tvCodeStatus;
    private TextView tvResendCode;
    private EditText etCode;
    private ImageView ivGoBack;
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private LinearLayout lhAutoVerify;
    private FrameLayout frameCode;
    private String phoneNumber;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        initView();
    }

    private void initView() {
        tvCodeNotice = findViewById(R.id.tvCodeNotice);
        firebaseAuth = FirebaseAuth.getInstance();
        lhAutoVerify = findViewById(R.id.lhAutoVerify);
        tvContinue = findViewById(R.id.tvContinue);
        etCode = findViewById(R.id.etCode);
        frameCode = findViewById(R.id.frameCode);
        tvCodeStatus = findViewById(R.id.tvCodeStatus);
        tvResendCode = findViewById(R.id.tvResendCode);
        ivGoBack = findViewById(R.id.ivGoBack);
        tvContinue.setOnClickListener(this);
        tvResendCode.setOnClickListener(this);
        ivGoBack.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();
        phoneNumber = getIntent().getStringExtra("phoneNumber");

        setNotice();
        sendCode();
    }

    private void sendCode() {
        lhAutoVerify.setVisibility(View.VISIBLE);
        setUpVerificationCallbacks();
        tvCodeStatus.setText(Constant.AUTH_SENDING_CODE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(country + phoneNumber, 60, TimeUnit.SECONDS, this, verificationCallbacks);
    }

    private void setUpVerificationCallbacks() {
        Log.e(TAG, "setUpVerificationCallbacks: ");
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
                Log.e(TAG, "onVerificationCompleted: ");
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e(TAG, "onVerificationFailed: " + e);
                Toast.makeText(CodeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Log.e(TAG, "onVerificationFailed: ");
                    Snackbar.make(frameCode, "Invalid Credentials", Snackbar.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.e(TAG, "onVerificationFailed: ");
                    lhAutoVerify.setVisibility(View.GONE);
                    Snackbar.make(frameCode, "Limit Reached Try Again In a few Hours", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                tvResendCode.setVisibility(View.VISIBLE);
                tvCodeStatus.setText(Constant.AUTH_AUTO_VERIFY);
                phoneVerificationId = s;
                resendToken = forceResendingToken;
                Log.e(TAG, "onCodeSent: ");
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                lhAutoVerify.setVisibility(View.GONE);
                Log.e(TAG, "onCodeAutoRetrievalTimeOut: ");
            }
        };
    }

    private void setNotice() {
        String codeNotice = getResources().getString(R.string.code_notice) + " " + phoneNumber + ".";
        tvCodeNotice.setText(codeNotice);
        Log.e(TAG, "setNotice: ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public void onClick(View view) {
        if (view == tvContinue) {
            if (!isConnected()) {
                Snackbar.make(frameCode, "Please make sure that you are connect to internet.", Snackbar.LENGTH_LONG).show();
            } else {
                doVerifyCode();
            }
        }

        if (view == tvResendCode) {
            resendCode();
        }

        if (view == ivGoBack) {
            onBackPressed();
        }
    }

    private void resendCode() {
        tvResendCode.setVisibility(View.GONE);
        lhAutoVerify.setVisibility(View.VISIBLE);
        setUpVerificationCallbacks();
        tvCodeStatus.setText(Constant.AUTH_RESENDING_CODE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(country + phoneNumber, 60, TimeUnit.SECONDS, this, verificationCallbacks, resendToken);
    }

    private void doVerifyCode() {
        String otp = etCode.getText().toString();
        if (TextUtils.isEmpty(otp) || !TextUtils.isDigitsOnly(otp) || otp.length() != 6) {
            Toast.makeText(CodeActivity.this, "Please enter valid OTP.", Toast.LENGTH_LONG).show();
            return;
        }

        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(phoneVerificationId, otp);
        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "onComplete: ");
                            Snackbar.make(frameCode, "Successful", Snackbar.LENGTH_LONG).show();
                            createWallet(firebaseAuth.getCurrentUser().getUid());
                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                Log.e(TAG, "onComplete: ");
                                Snackbar.make(frameCode, "Invalid Credentials", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void gotoMain() {
        Intent intent = new Intent(CodeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void createWallet(final String uid) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(FirestoreConstant.WALLET_COLLECTION)
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                Log.e(TAG, "onComplete: wallet exists");
                                gotoMain();
                            } else {

                                Wallet wallet = new Wallet();
                                wallet.setUid(uid);
                                wallet.setInvested_balance("0");
                                wallet.setProfit_balance("0");

                                db.collection(FirestoreConstant.WALLET_COLLECTION)
                                        .document(uid)
                                        .set(wallet)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.e(TAG, "onComplete: wallet does exists");
                                                    gotoMain();
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
    }

}