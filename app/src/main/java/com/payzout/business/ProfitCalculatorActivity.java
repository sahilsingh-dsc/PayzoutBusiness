package com.payzout.business;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.payzout.business.apis.APIClient;
import com.payzout.business.apis.KYCInterface;
import com.payzout.business.common.MainActivity;
import com.payzout.business.portfolio.CreatePortfolio;
import com.payzout.business.transaction.TransactionNew;
import com.payzout.business.utils.Constant;
import com.payzout.business.utils.FirestoreConstant;
import com.payzout.business.wallet.Wallet;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfitCalculatorActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {

    private TextView tvInvestmentAmount;
    private TextView tvProfitPerMonth;
    private TextView tvProfitInterest;
    private TextView tvLockingPeriod;

    private EditText etInvestmentAmount;

    private TextView tvInvestNow;
    private ImageView ivGoBack;
    private double investAmt;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String uid;

    int profit = 0;
    String inv_class;
    String inv_interest;
    String inv_locking;
    private static final String TAG = "ProfitCalculatorActivit";
    boolean kyc = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_calculator);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        initView();
    }

    private void initView() {
        db = FirebaseFirestore.getInstance();
        tvInvestmentAmount = findViewById(R.id.tvInvestmentAmount);
        tvProfitPerMonth = findViewById(R.id.tvProfitPerMonth);
        tvProfitInterest = findViewById(R.id.tvProfitInterest);
        tvLockingPeriod = findViewById(R.id.tvLockingPeriod);

        tvInvestNow = findViewById(R.id.tvInvestNow);
        tvInvestNow.setOnClickListener(this);

        etInvestmentAmount = findViewById(R.id.etInvestmentAmount);
        etInvestmentAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String investmentAmount = etInvestmentAmount.getText().toString();
                if (investmentAmount.isEmpty()) {
                    tvLockingPeriod.setText("0 months");
                    tvProfitInterest.setText("0%");
                    tvProfitPerMonth.setText("₹ 0");
                    tvInvestmentAmount.setText("₹ 0");
                    tvInvestNow.setEnabled(false);
                    tvInvestNow.setBackground(getResources().getDrawable(R.drawable.bg_text_box));
                    tvInvestNow.setTextColor(getResources().getColor(R.color.colorTextH2));
                } else {
                    int amt = Integer.parseInt(investmentAmount);
                    if (amt >= 5000) {
                        investAmt = amt;
                        tvInvestNow.setEnabled(true);
                        tvInvestNow.setBackground(getResources().getDrawable(R.drawable.bg_button));
                        tvInvestNow.setTextColor(getResources().getColor(R.color.colorTextH3));
                        doCalculateProfit(amt);
                    } else {
                        etInvestmentAmount.setError("Min investment amount is ₹ 5000");
                        tvInvestNow.setEnabled(false);
                        tvInvestNow.setBackground(getResources().getDrawable(R.drawable.bg_text_box));
                        tvInvestNow.setTextColor(getResources().getColor(R.color.colorTextH2));
                        tvLockingPeriod.setText("0 months");
                        tvProfitInterest.setText("0%");
                        tvProfitPerMonth.setText("₹ 0");
                        tvInvestmentAmount.setText("₹ 0");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ivGoBack = findViewById(R.id.ivGoBack);
        ivGoBack.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();

        checkForKYC();

    }

    private void doCalculateProfit(int amt) {
        if (amt <= 200000) {
            inv_class = "Class A";
            inv_interest = "12";
            inv_locking = "90";
            tvLockingPeriod.setText("90 days");
            tvProfitInterest.setText("12%");
            profit = (amt * 12) / 100;
            tvProfitPerMonth.setText("₹ " + profit);
            tvInvestmentAmount.setText("₹ " + amt);
        } else if (amt <= 600000) {
            inv_class = "Class B";
            inv_interest = "15";
            inv_locking = "180";
            tvLockingPeriod.setText("180 days");
            tvProfitInterest.setText("15%");
            profit = (amt * 15) / 100;
            tvProfitPerMonth.setText("₹ " + profit);
            tvInvestmentAmount.setText("₹ " + amt);
        } else if (amt <= 1000000) {
            inv_class = "Class C";
            inv_interest = "20";
            inv_locking = "180";
            tvLockingPeriod.setText("180 days");
            tvProfitInterest.setText("20%");
            profit = (amt * 20) / 100;
            tvProfitPerMonth.setText("₹ " + profit);
            tvInvestmentAmount.setText("₹ " + amt);
        } else if (amt > 1000000) {
            inv_class = "Class D";
            tvLockingPeriod.setText("6 months");
            tvProfitInterest.setText("High Return");
            tvProfitPerMonth.setText("₹ " + 0);
            tvInvestmentAmount.setText("₹ " + amt);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == tvInvestNow) {
            if (kyc) {
                startPayment();
            } else {
                Toast.makeText(this, "Please complete your KYC first.", Toast.LENGTH_SHORT).show();
            }

        }

        if (view == ivGoBack) {
            onBackPressed();
        }
    }

    private void startPayment() {
        DocumentReference documentReference = db.collection(FirestoreConstant.TRANSACTION_COLLECTION).document();
        final String id = documentReference.getId();
        double amount = investAmt * 100;
        doPayment(amount);
    }

    private void doPayment(double amount) {
        Activity activity = this;
        Checkout checkout = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Payzout Business");
            options.put("description", "Investment");
            options.put("image", Constant.PB_LOGO);
            options.put("currency", "INR");
            options.put("amount", 100);
            JSONObject preFill = new JSONObject();
            SharedPreferences preferences = getSharedPreferences("profile", 0);
            String email = preferences.getString("email", "none");
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
            String phoneTrim = phone.replace("+91", "");
            preFill.put("email", email);
            preFill.put("contact", phoneTrim);
            options.put("prefill", preFill);
            checkout.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void createPortfolio(String pid, String uid, String amount) {
        Log.e(TAG, "createPortfolio: " + pid + " " + uid + " " + amount);
        KYCInterface kycInterface = APIClient.getRetrofitInstance().create(KYCInterface.class);
        Call<CreatePortfolio> call = kycInterface.createPortfolio(pid, uid, amount);
        call.enqueue(new Callback<CreatePortfolio>() {
            @Override
            public void onResponse(Call<CreatePortfolio> call, Response<CreatePortfolio> response) {
                Log.e(TAG, "onResponse: " + response.code() + response.message());
                if (response.code() == 200) {
                    Log.e(TAG, "onResponse: Portfolio created");
                    Toast.makeText(ProfitCalculatorActivity.this, "New Portfolio Created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfitCalculatorActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (response.code() == 400) {
                    Log.e(TAG, "onResponse: Portfolio not created");

                } else {
                    Log.e(TAG, "onResponse: Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<CreatePortfolio> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return timeFormat.format(new Date());
    }

    private void checkForKYC() {
        db.collection(FirestoreConstant.INVESTORS_COLLECTION)
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                kyc = true;
                            } else {
                                kyc = false;
                            }
                        }
                    }
                });
    }

    private void fetchOldBalance(final String amount) {
        db.collection(FirestoreConstant.WALLET_COLLECTION)
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                Wallet wallet = task.getResult().toObject(Wallet.class);
                                int invested = Integer.parseInt(wallet.getInvested_balance());
                                int amountToUpdate = Integer.parseInt(amount);
                                int total = invested + amountToUpdate;
                                updateWalletBalance(total);
                            }
                        }
                    }
                });
    }

    private void updateWalletBalance(int total) {
        HashMap hashMap = new HashMap();
        hashMap.put("invested_balance", total + "");
        db.collection(FirestoreConstant.WALLET_COLLECTION)
                .document(uid)
                .update(hashMap)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfitCalculatorActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
        DocumentReference documentReference = db.collection(FirestoreConstant.TRANSACTION_COLLECTION).document();
        String id = documentReference.getId();
        createPortfolio(id, uid, String.valueOf(investAmt));
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Error: "+s, Toast.LENGTH_SHORT).show();
    }
}