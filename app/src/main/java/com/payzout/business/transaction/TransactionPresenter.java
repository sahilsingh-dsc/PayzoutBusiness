package com.payzout.business.transaction;

import android.content.Context;
import android.util.Log;

import com.payzout.business.apis.APIClient;
import com.payzout.business.apis.KYCInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionPresenter {
    private Context context;
    private TransactionInterface transactionInterface;

    private static final String TAG = "TransactionPresenter";

    public TransactionPresenter(Context context, TransactionInterface transactionInterface) {
        this.context = context;
        this.transactionInterface = transactionInterface;
    }

    public void fetchTransactions(String u_id) {
        KYCInterface kycInterface = APIClient.getRetrofitInstance().create(KYCInterface.class);
        Call<Transaction> call = kycInterface.getTransaction(u_id);
        call.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                Log.e(TAG, "onResponse: " + response.code() + response.message());
                if (response.code() == 200) {
                    transactionInterface.transactionFetchSuccess(response.body());
                } else if (response.code() == 400) {
                    transactionInterface.transactionNotFound("transaction not found");
                } else {
                    transactionInterface.transactionFetchFailed("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {

            }
        });
    }

}
