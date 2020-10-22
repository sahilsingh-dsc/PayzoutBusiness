package com.payzout.business.portfolio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPortfolio {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<GetProtfolioResponse> data = null;

    /**
     * No args constructor for use in serialization
     */
    public GetPortfolio() {
    }

    /**
     * @param data
     * @param status
     */
    public GetPortfolio(Boolean status, List<GetProtfolioResponse> data) {
        super();
        this.status = status;
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<GetProtfolioResponse> getData() {
        return data;
    }

    public void setData(List<GetProtfolioResponse> data) {
        this.data = data;
    }

}