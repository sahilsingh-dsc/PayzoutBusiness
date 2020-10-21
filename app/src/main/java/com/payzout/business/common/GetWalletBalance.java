package com.payzout.business.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetWalletBalance {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     */
    public GetWalletBalance() {
    }

    /**
     * @param data
     * @param status
     */
    public GetWalletBalance(Boolean status, Data data) {
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data {

        @SerializedName("investor_id")
        @Expose
        private String investorId;
        @SerializedName("invested_balance")
        @Expose
        private String investedBalance;
        @SerializedName("profit_balance")
        @Expose
        private String profitBalance;

        /**
         * No args constructor for use in serialization
         */
        public Data() {
        }

        /**
         * @param investorId
         * @param investedBalance
         * @param profitBalance
         */
        public Data(String investorId, String investedBalance, String profitBalance) {
            super();
            this.investorId = investorId;
            this.investedBalance = investedBalance;
            this.profitBalance = profitBalance;
        }

        public String getInvestorId() {
            return investorId;
        }

        public void setInvestorId(String investorId) {
            this.investorId = investorId;
        }

        public String getInvestedBalance() {
            return investedBalance;
        }

        public void setInvestedBalance(String investedBalance) {
            this.investedBalance = investedBalance;
        }

        public String getProfitBalance() {
            return profitBalance;
        }

        public void setProfitBalance(String profitBalance) {
            this.profitBalance = profitBalance;
        }

    }

}