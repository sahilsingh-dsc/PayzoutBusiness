package com.payzout.business.portfolio;

import android.content.Context;
import android.util.Log;

import com.payzout.business.apis.APIClient;
import com.payzout.business.apis.KYCInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortfolioPresenter {

    private Context context;
    private PortfolioInterface portfolioInterface;

    private static final String TAG = "PortfolioPresenter";

    public PortfolioPresenter(Context context, PortfolioInterface portfolioInterface) {
        this.context = context;
        this.portfolioInterface = portfolioInterface;
    }

    public void fetchPortfolio(String u_id) {
        KYCInterface kycInterface = APIClient.getRetrofitInstance().create(KYCInterface.class);
        Call<GetPortfolio> call = kycInterface.getPortfolio(u_id);
        call.enqueue(new Callback<GetPortfolio>() {
            @Override
            public void onResponse(Call<GetPortfolio> call, Response<GetPortfolio> response) {
                Log.e(TAG, "onResponse: " + response.code() + " " + response.message());
                if (response.code() == 200) {
                    portfolioInterface.portfolioFetchSuccess(response.body());
                } else if (response.code() == 200) {
                    portfolioInterface.portfolioNotFound("Invalid User ID");
                } else {
                    portfolioInterface.portfolioFetchFailed("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<GetPortfolio> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
