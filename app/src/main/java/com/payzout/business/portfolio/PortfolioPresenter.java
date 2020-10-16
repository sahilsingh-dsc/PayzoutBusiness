package com.payzout.business.portfolio;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.payzout.business.utils.FirestoreConstant;

import java.util.ArrayList;
import java.util.List;

public class PortfolioPresenter {

    private Context context;
    private PortfolioInterface portfolioInterface;

    private static final String TAG = "PortfolioPresenter";

    public PortfolioPresenter(Context context, PortfolioInterface portfolioInterface) {
        this.context = context;
        this.portfolioInterface = portfolioInterface;
    }

    public void fetchPortfolio(String u_id) {
        final List<Portfolio> portfolioList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection(FirestoreConstant.PORTFOLIO_COLLECTION)
                .whereEqualTo("u_id", u_id).orderBy("timestamp", Query.Direction.DESCENDING);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().getDocuments().isEmpty()) {
                                for (DocumentSnapshot snapshot : task.getResult()) {
                                    Portfolio portfolio = snapshot.toObject(Portfolio.class);
                                    portfolioList.add(portfolio);
                                }
                                portfolioInterface.portfolioFetchSuccess(portfolioList);
                            } else {
                                portfolioInterface.portfolioNotFound("No Data");
                            }
                        } else {
                            Log.e(TAG, "onComplete: "+task.getException().getMessage());
                            portfolioInterface.portfolioFetchFailed(task.getException().getMessage());
                        }
                    }
                });
    }

}
