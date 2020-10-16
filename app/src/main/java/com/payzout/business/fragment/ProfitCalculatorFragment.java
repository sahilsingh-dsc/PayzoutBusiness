package com.payzout.business.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.payzout.business.ProfitCalculatorActivity;
import com.payzout.business.R;
import com.payzout.business.common.MainActivity;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;

import java.text.DecimalFormat;

public class ProfitCalculatorFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView tvInvestmentAmount;
    private TextView tvProfitPerMonth;
    private TextView tvProfitInterest;
    private TextView tvLockingPeriod;

    private TextInputEditText tieInvestmentAmount;

    private TextView tvInvestNow;
    private double investAmt;

    public ProfitCalculatorFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profit_calculator, container, false);
        initView();
        return view;
    }

    private void initView() {
        tvInvestmentAmount = view.findViewById(R.id.tvInvestmentAmount);
        tvProfitPerMonth = view.findViewById(R.id.tvProfitPerMonth);
        tvProfitInterest = view.findViewById(R.id.tvProfitInterest);
        tvLockingPeriod = view.findViewById(R.id.tvLockingPeriod);

        tvInvestNow = view.findViewById(R.id.tvInvestNow);
        tvInvestNow.setOnClickListener(this);

        tieInvestmentAmount = view.findViewById(R.id.tieInvestmentAmount);
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
                    if (amt > 0) {
                        investAmt = amt;
                        tvInvestNow.setEnabled(true);
                        tvInvestNow.setBackground(getResources().getDrawable(R.drawable.bg_button));
                        tvInvestNow.setTextColor(getResources().getColor(R.color.colorTextH3));
                        doCalculateProfit(amt);
                    } else {
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
    }

    private void doCalculateProfit(int amt) {
        if (amt <= 200000) {
            tvLockingPeriod.setText("3 months");
            tvProfitInterest.setText("12%");
            int profit = (amt*12)/100;
            tvProfitPerMonth.setText("₹ "+profit);
            tvInvestmentAmount.setText("₹ "+amt);
        } else
        if (amt <= 600000) {
            tvLockingPeriod.setText("6 months");
            tvProfitInterest.setText("15%");
            int profit = (amt*15)/100;
            tvProfitPerMonth.setText("₹ "+profit);
            tvInvestmentAmount.setText("₹ "+amt);
        } else
        if (amt <= 1000000) {
            tvLockingPeriod.setText("6 months");
            tvProfitInterest.setText("20%");
            int profit = (amt*20)/100;
            tvProfitPerMonth.setText("₹ "+profit);
            tvInvestmentAmount.setText("₹ "+amt);
        } else
        if (amt > 1000000) {
            tvLockingPeriod.setText("6 months");
            tvProfitInterest.setText("High Return");
            tvProfitPerMonth.setText("₹ "+0);
            tvInvestmentAmount.setText("₹ "+amt);
        }

    }

    @Override
    public void onClick(View view) {
        if (view == tvInvestNow) {
            startPayment();
        }
    }

    private void startPayment() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("transaction").document();
        String id = documentReference.getId();
        DecimalFormat form = new DecimalFormat("0.00");
        EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                .with((MainActivity) getContext())
                .setPayeeVpa("ddplan@upi")
                .setPayeeName(getResources().getString(R.string.app_name))
                .setTransactionId(id)
                .setTransactionRefId(id)
                .setDescription("Payzout Investment")
                .setAmount(form.format(investAmt))
                .build();
        easyUpiPayment.startPayment();
    }

}