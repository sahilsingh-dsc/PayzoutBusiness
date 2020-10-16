package com.payzout.business.common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.payzout.business.ProfitCalculatorActivity;
import com.payzout.business.R;
import com.payzout.business.common.banner.Banner;
import com.payzout.business.common.banner.BannerAdapter;
import com.payzout.business.common.banner.BannerInterface;
import com.payzout.business.common.banner.BannerPresenter;
import com.payzout.business.portfolio.PortfolioActivity;
import com.payzout.business.profile.KycActivity;
import com.payzout.business.profile.Profile;
import com.payzout.business.transaction.TransactionActivity;
import com.payzout.business.utils.FirestoreConstant;
import com.payzout.business.wallet.Wallet;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BannerInterface {

    private LinearLayout lvUser;
    private LinearLayout lvProfitCalculator;
    private LinearLayout lvMyPortfolio;
    private LinearLayout lvMyTransactions;
    private LinearLayout lvGetLoan;

    private TextView tvName;
    private TextView tvMobile;
    private ImageView ivKycStatus;

    private RecyclerView recyclerBanner;
    private ImageView ivUserPhoto;

    private TextView tvWalletBalance;
    private TextView tvProfitBalance;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        initView();
    }

    private void initView() {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        lvUser = findViewById(R.id.lvUser);
        ivUserPhoto = findViewById(R.id.ivUserPhoto);
        ivUserPhoto.setOnClickListener(this);

        lvProfitCalculator = findViewById(R.id.lvProfitCalculator);
        lvMyPortfolio = findViewById(R.id.lvMyPortfolio);
        lvMyTransactions = findViewById(R.id.lvMyTransactions);
        lvGetLoan = findViewById(R.id.lvGetLoan);

        tvName = findViewById(R.id.tvName);
        tvMobile = findViewById(R.id.tvMobile);
        lvMyTransactions = findViewById(R.id.lvMyTransactions);

        tvWalletBalance = findViewById(R.id.tvWalletBalance);
        tvProfitBalance = findViewById(R.id.tvProfitBalance);

        lvUser.setOnClickListener(this);
        lvProfitCalculator.setOnClickListener(this);
        lvMyPortfolio.setOnClickListener(this);
        lvMyTransactions.setOnClickListener(this);
        lvGetLoan.setOnClickListener(this);
        ivKycStatus = findViewById(R.id.ivKycStatus);

        recyclerBanner = findViewById(R.id.recyclerBanner);
        recyclerBanner.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        if (recyclerBanner.getOnFlingListener() == null)
            snapHelper.attachToRecyclerView(recyclerBanner);

        if (firebaseAuth.getCurrentUser() != null) {
            uid = firebaseAuth.getCurrentUser().getUid();
            setupPresenter();
            fetchWalletBalance();
        }

    }

    private void setupPresenter() {
        BannerPresenter bannerPresenter = new BannerPresenter(MainActivity.this, MainActivity.this);
        bannerPresenter.fetchBanners();
    }


    @Override
    public void onClick(View view) {
        if (view == lvUser) {
            gotoKyc();
        }
        if (view == lvProfitCalculator) {
            gotoCalc();
        }
        if (view == lvMyPortfolio) {
            gotoPortfolio();
        }
        if (view == lvMyTransactions) {
            gotoTransactions();
        }
        if (view == ivUserPhoto) {
            gotoKyc();
        }
        if (view == lvGetLoan) {
            gotoPlayStore();
        }
    }

    private void gotoPlayStore() {
        Uri marketUri = Uri.parse("market://details?id=" + "com.payzout.customer");
        startActivity(new Intent(Intent.ACTION_VIEW, marketUri));
    }

    private void gotoTransactions() {
        Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
        startActivity(intent);
    }

    private void gotoPortfolio() {
        Intent intent = new Intent(MainActivity.this, PortfolioActivity.class);
        startActivity(intent);
    }

    private void gotoCalc() {
        Intent intent = new Intent(MainActivity.this, ProfitCalculatorActivity.class);
        startActivity(intent);
    }

    private void gotoKyc() {
        Intent intent = new Intent(MainActivity.this, KycActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForData();
        setProfile();
        if (firebaseAuth.getCurrentUser() != null) {
            uid = firebaseAuth.getCurrentUser().getUid();
            fetchWalletBalance();
        }
    }

    @Override
    public void bannerFetchSuccess(List<Banner> bannerList) {
        BannerAdapter bannerAdapter = new BannerAdapter(bannerList, MainActivity.this);
        bannerAdapter.notifyDataSetChanged();
        recyclerBanner.setAdapter(bannerAdapter);
    }

    @Override
    public void bannerFetchError(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bannerNoFound(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    private void fetchWalletBalance() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(FirestoreConstant.WALLET_COLLECTION)
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Wallet wallet = task.getResult().toObject(Wallet.class);
                            if (wallet != null) {
                                String walletBalance = getResources().getString(R.string.rupee) + " " + wallet.getInvested_balance();
                                String profitBalance = getResources().getString(R.string.rupee) + " " + wallet.getProfit_balance();
                                tvWalletBalance.setText(walletBalance);
                                tvProfitBalance.setText(profitBalance);
                            }
                        }
                    }
                });
    }

    private void checkForData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(FirestoreConstant.INVESTORS_COLLECTION)
                .document(uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error == null) {
                            if (value.exists()) {
                                Profile profile = value.toObject(Profile.class);
                                updateData(profile);
                            }
                        }
                    }
                });
    }

    private void updateData(final Profile profile) {
        db.collection(FirestoreConstant.INVESTORS_COLLECTION)
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        SharedPreferences preferences = getSharedPreferences("profile", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("uid", profile.getUid());
                        editor.putString("name", profile.getName());
                        editor.putString("photo", profile.getPhoto());
                        editor.putString("email", profile.getEmail());
                        editor.putString("phone", profile.getPhone());
                        editor.putString("pancard", profile.getPancard());
                        editor.putString("aadhaar", profile.getAadhaar());
                        editor.putString("dob", profile.getDob());
                        editor.putString("gender", profile.getGender());
                        editor.putString("address", profile.getAddress());
                        editor.apply();
                    }
                });
    }

    private void setProfile() {
        SharedPreferences preferences = getSharedPreferences("profile", 0);
        String name = preferences.getString("name", "Complete KYC");
        if (name.equals("Complete KYC")) {
            ivKycStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_error_24));
        } else {
            ivKycStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_check_circle_24));
        }
        tvName.setText(name);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
        String phoneTrim = phone.replace("+91", "");
        tvMobile.setText(phoneTrim);
        Glide.with(MainActivity.this).load(preferences.getString("photo", "")).placeholder(R.drawable.ic_baseline_account_circle_24).into(ivUserPhoto);
    }

}