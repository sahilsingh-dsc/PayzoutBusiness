package com.payzout.business.portfolio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatePortfolio {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private PortfolioResponse portfolioResponse;

    /**
     * No args constructor for use in serialization
     */
    public CreatePortfolio() {
    }

    /**
     * @param portfolioResponse
     * @param status
     */
    public CreatePortfolio(Boolean status, PortfolioResponse portfolioResponse) {
        super();
        this.status = status;
        this.portfolioResponse = portfolioResponse;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public PortfolioResponse getPortfolioResponse() {
        return portfolioResponse;
    }

    public void setPortfolioResponse(PortfolioResponse portfolioResponse) {
        this.portfolioResponse = portfolioResponse;
    }


}