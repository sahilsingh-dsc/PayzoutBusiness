package com.payzout.business.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfile {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private GetProfileResponse getProfileResponse;

    /**
     * No args constructor for use in serialization
     */
    public GetProfile() {
    }

    /**
     * @param getProfileResponse
     * @param status
     */
    public GetProfile(Boolean status, GetProfileResponse getProfileResponse) {
        super();
        this.status = status;
        this.getProfileResponse = getProfileResponse;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public GetProfileResponse getGetProfileResponse() {
        return getProfileResponse;
    }

    public void setGetProfileResponse(GetProfileResponse getProfileResponse) {
        this.getProfileResponse = getProfileResponse;
    }

}