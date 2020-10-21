package com.payzout.business.common.banner;

import com.payzout.business.common.GetWalletBalance;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WalletInterface {
    @FormUrlEncoded
    @POST("Investor/getWalletBalance")
    Call<GetWalletBalance> getWallet(@Field("investor_id") String investor_id);
}
