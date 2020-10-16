package com.payzout.business.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.local.Persistence;
import com.google.zxing.Result;
import com.payzout.business.R;
import com.payzout.business.utils.FirestoreConstant;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class QRActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private LinearLayout lvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r);
        initView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initView() {
        scannerView = findViewById(R.id.scanner_view);
        lvResult = findViewById(R.id.lvResult);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processResult(result.toString());
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

    }

    private void processResult(String result) {
        try {
            lvResult.setVisibility(View.VISIBLE);
            AadhaarCard newCard = new AadhaarXMLParser().parse(result);
            saveResultToFirestore(newCard);
        } catch (XmlPullParserException e) {
            lvResult.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Card Not Supported", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            lvResult.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    private void saveResultToFirestore(AadhaarCard newCard) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        db.collection(FirestoreConstant.INVESTORS_COLLECTION)
                .document(firebaseAuth.getCurrentUser().getUid())
                .set(newCard)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            lvResult.setVisibility(View.GONE);
                            onBackPressed();
                        } else {
                            lvResult.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        lvResult.setVisibility(View.GONE);
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        lvResult.setVisibility(View.GONE);
        mCodeScanner.releaseResources();
        super.onPause();
    }

}