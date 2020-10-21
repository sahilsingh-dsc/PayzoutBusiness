package com.payzout.business.apis;

import com.payzout.business.common.CheckInvestor;
import com.payzout.business.common.GetWalletBalance;
import com.payzout.business.common.InvestorRegistration;
import com.payzout.business.profile.KYCResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface KYCInterface {

    @FormUrlEncoded
    @POST("Investor/doKyc")
    Call<KYCResponse> sendKYCDetails(@Field("id") String id,
                                     @Field("email") String email,
                                     @Field("gender") String gender,
                                     @Field("dob") String dob,
                                     @Field("pancard") String pancard,
                                     @Field("aadhaar") String aadhaar,
                                     @Field("full_name") String full_name,
                                     @Field("address") String address
    );

    @FormUrlEncoded
    @POST("Investor/CheckInvestor")
    Call<CheckInvestor> checkInvestor(@Field("Userid") String Userid);

    @FormUrlEncoded
    @POST("Investor/investorRegistration")
    Call<InvestorRegistration> register(@Field("id") String id,
                                        @Field("full_name") String full_name,
                                        @Field("phone") String phone,
                                        @Field("city") String city);

}
