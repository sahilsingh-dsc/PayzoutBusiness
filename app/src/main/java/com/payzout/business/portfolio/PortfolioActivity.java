package com.payzout.business.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.payzout.business.R;

import java.util.List;

public class PortfolioActivity extends AppCompatActivity implements View.OnClickListener, PortfolioInterface {

    private ImageView ivGoBack;
    private RecyclerView recyclerPortfolio;
    private LinearLayout lvNoData;
    private LinearLayout lvLoading;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        initView();
    }

    private void initView() {

        ivGoBack = findViewById(R.id.ivGoBack);
        lvNoData = findViewById(R.id.lvNoData);
        lvLoading = findViewById(R.id.lvLoading);

        ivGoBack.setOnClickListener(this);

        recyclerPortfolio = findViewById(R.id.recyclerPortfolio);
        firebaseAuth = FirebaseAuth.getInstance();

        String u_id = firebaseAuth.getCurrentUser().getUid();
        setupRecyclerAndPresenter(u_id);
    }

    private void setupRecyclerAndPresenter(String u_id) {
        loadingData();
        recyclerPortfolio.setLayoutManager(new LinearLayoutManager(this));
        PortfolioPresenter portfolioPresenter = new PortfolioPresenter(PortfolioActivity.this, PortfolioActivity.this);
        portfolioPresenter.fetchPortfolio(u_id);
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
    public void portfolioFetchSuccess(List<Portfolio> portfolioList) {
        PortfolioAdapter portfolioAdapter = new PortfolioAdapter(PortfolioActivity.this, portfolioList);
        portfolioAdapter.notifyDataSetChanged();
        recyclerPortfolio.setAdapter(portfolioAdapter);
        showData();
    }

    @Override
    public void portfolioNotFound(String message) {
        noData();
    }

    @Override
    public void portfolioFetchFailed(String message) {
        noData();
    }

    private void showData() {
        lvLoading.setVisibility(View.GONE);
        lvNoData.setVisibility(View.GONE);
        recyclerPortfolio.setVisibility(View.VISIBLE);
    }

    private void noData() {
        recyclerPortfolio.setVisibility(View.GONE);
        lvLoading.setVisibility(View.GONE);
        lvNoData.setVisibility(View.VISIBLE);
    }

    private void loadingData() {
        lvNoData.setVisibility(View.GONE);
        recyclerPortfolio.setVisibility(View.GONE);
        lvLoading.setVisibility(View.VISIBLE);
    }

}