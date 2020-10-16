package com.payzout.business.transaction;

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

public class TransactionPresenter {
    private Context context;
    private TransactionInterface transactionInterface;

    private static final String TAG = "TransactionPresenter";

    public TransactionPresenter(Context context, TransactionInterface transactionInterface) {
        this.context = context;
        this.transactionInterface = transactionInterface;
    }

    public void fetchTransactions(String u_id) {
        final List<Transaction> transactionList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection(FirestoreConstant.TRANSACTION_COLLECTION)
                .whereEqualTo("u_id", u_id).orderBy("timestamp", Query.Direction.DESCENDING);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().getDocuments().isEmpty()) {
                                for (DocumentSnapshot snapshot : task.getResult()) {
                                    Transaction transaction = snapshot.toObject(Transaction.class);
                                    transactionList.add(transaction);
                                }
                                transactionInterface.transactionFetchSuccess(transactionList);
                            } else {
                                transactionInterface.transactionNotFound("No Data");
                            }
                        } else {
                            Log.e(TAG, "onComplete: " + task.getException().getMessage());
                            transactionInterface.transactionFetchFailed(task.getException().getMessage());
                        }
                    }
                });
    }

}
