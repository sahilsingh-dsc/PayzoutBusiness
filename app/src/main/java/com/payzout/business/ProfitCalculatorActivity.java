package com.payzout.business;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.payzout.business.portfolio.Portfolio;
import com.payzout.business.transaction.Transaction;
import com.payzout.business.utils.Constant;
import com.payzout.business.utils.FirestoreConstant;
import com.payzout.business.wallet.Wallet;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ProfitCalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvInvestmentAmount;
    private TextView tvProfitPerMonth;
    private TextView tvProfitInterest;
    private TextView tvLockingPeriod;

    private TextInputEditText tieInvestmentAmount;

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

        tieInvestmentAmount = findViewById(R.id.tieInvestmentAmount);
        tieInvestmentAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String investmentAmount = tieInvestmentAmount.getText().toString();
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
                        tieInvestmentAmount.setError("Min investment amount is ₹ 5000");
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
        DecimalFormat form = new DecimalFormat("0.00");
        EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                .with(ProfitCalculatorActivity.this)
                .setPayeeVpa("ddplan@upi")
                .setPayeeName(getResources().getString(R.string.app_name))
                .setTransactionId(id)
                .setTransactionRefId(id)
                .setDescription("Payzout Investment")
                .setAmount(form.format(investAmt))
                .build();
        easyUpiPayment.startPayment();
        easyUpiPayment.setPaymentStatusListener(new PaymentStatusListener() {
            @Override
            public void onTransactionCompleted(TransactionDetails transactionDetails) {

            }

            @Override
            public void onTransactionSuccess() {
                createTransaction(id, uid);
            }

            @Override
            public void onTransactionSubmitted() {
                Toast.makeText(ProfitCalculatorActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTransactionFailed() {
                Toast.makeText(ProfitCalculatorActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTransactionCancelled() {
                Toast.makeText(ProfitCalculatorActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAppNotFound() {
                Toast.makeText(ProfitCalculatorActivity.this, "Intent not found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void createTransaction(final String id, final String uid) {
        Transaction transaction = new Transaction();
        transaction.setT_id(id);
        transaction.setU_id(uid);
        transaction.setT_type(Constant.TXN_TYPE_CREDIT);
        transaction.setT_amount(tieInvestmentAmount.getText().toString());
        transaction.setT_date(getCurrentDate());
        transaction.setT_time(getCurrentTime());
        transaction.setT_remark(Constant.TXN_REMARK_INVESTMENT);
        transaction.setT_status(Constant.TXN_SUCCESS);

        db.collection(FirestoreConstant.TRANSACTION_COLLECTION)
                .document(id)
                .set(transaction)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            createPortfolio(id, uid);
                        }
                    }
                });

    }

    private void createPortfolio(String id, String uid) {
        Portfolio portfolio = new Portfolio();
        portfolio.setP_id(id);
        portfolio.setU_id(uid);
        portfolio.setP_date(getCurrentDate());
        portfolio.setP_amount(tieInvestmentAmount.getText().toString());
        portfolio.setP_profit("0");
        portfolio.setP_class(inv_class);
        portfolio.setP_interest(inv_interest);
        portfolio.setP_locking(inv_locking);

        db.collection(FirestoreConstant.PORTFOLIO_COLLECTION)
                .document(id)
                .set(portfolio)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            fetchOldBalance(tieInvestmentAmount.getText().toString());
                        }
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
                                int total = invested+amountToUpdate;
                                updateWalletBalance(total);
                            }
                        }
                    }
                });
    }

    private void updateWalletBalance(int total) {
        HashMap hashMap = new HashMap();
        hashMap.put("invested_balance", total+"");
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

}