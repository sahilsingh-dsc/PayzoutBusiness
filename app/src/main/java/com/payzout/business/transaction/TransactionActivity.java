package com.payzout.business.transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.payzout.business.R;

import java.util.List;

public class TransactionActivity extends AppCompatActivity implements View.OnClickListener, TransactionInterface {

    private ImageView ivGoBack;
    private RecyclerView recyclerTransactions;
    private LinearLayout lvNoData;
    private LinearLayout lvLoading;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        initView();
    }

    private void initView() {
        ivGoBack = findViewById(R.id.ivGoBack);
        lvNoData = findViewById(R.id.lvNoData);
        lvLoading = findViewById(R.id.lvLoading);

        ivGoBack.setOnClickListener(this);

        recyclerTransactions = findViewById(R.id.recyclerTransactions);
        firebaseAuth = FirebaseAuth.getInstance();

        String u_id = firebaseAuth.getCurrentUser().getUid();
        setupRecyclerAndPresenter(u_id);
    }

    private void setupRecyclerAndPresenter(String u_id) {
        loadingData();
        recyclerTransactions.setLayoutManager(new LinearLayoutManager(TransactionActivity.this));
        TransactionPresenter transactionPresenter = new TransactionPresenter(TransactionActivity.this, TransactionActivity.this);
        transactionPresenter.fetchTransactions(u_id);
    }

    @Override
    public void onClick(View view) {
        if (view == ivGoBack) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void transactionFetchSuccess(List<Transaction> transactionList) {
        TransactionAdapter transactionAdapter = new TransactionAdapter(TransactionActivity.this, transactionList);
        transactionAdapter.notifyDataSetChanged();
        recyclerTransactions.setAdapter(transactionAdapter);
        showData();
    }

    @Override
    public void transactionNotFound(String message) {
        noData();
    }

    @Override
    public void transactionFetchFailed(String message) {
        noData();
    }

    private void showData() {
        lvLoading.setVisibility(View.GONE);
        lvNoData.setVisibility(View.GONE);
        recyclerTransactions.setVisibility(View.VISIBLE);
    }

    private void noData() {
        recyclerTransactions.setVisibility(View.GONE);
        lvLoading.setVisibility(View.GONE);
        lvNoData.setVisibility(View.VISIBLE);
    }

    private void loadingData() {
        lvNoData.setVisibility(View.GONE);
        recyclerTransactions.setVisibility(View.GONE);
        lvLoading.setVisibility(View.VISIBLE);
    }

}