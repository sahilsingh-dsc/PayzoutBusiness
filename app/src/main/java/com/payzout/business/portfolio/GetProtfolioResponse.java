package com.payzout.business.portfolio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProtfolioResponse {

    @SerializedName("portfolio_id")
    @Expose
    private String portfolioId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("locking_period")
    @Expose
    private Integer lockingPeriod;
    @SerializedName("profit_percent")
    @Expose
    private Integer profitPercent;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("profit_balance")
    @Expose
    private Double profitBalance;

    /**
     * No args constructor for use in serialization
     */
    public GetProtfolioResponse() {
    }

    /**
     * @param date
     * @param classId
     * @param amount
     * @param portfolioId
     * @param profitPercent
     * @param lockingPeriod
     * @param time
     * @param userId
     * @param profitBalance
     */
    public GetProtfolioResponse(String portfolioId, String userId, String classId, Integer lockingPeriod, Integer profitPercent, String date, String time, Integer amount, Double profitBalance) {
        super();
        this.portfolioId = portfolioId;
        this.userId = userId;
        this.classId = classId;
        this.lockingPeriod = lockingPeriod;
        this.profitPercent = profitPercent;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.profitBalance = profitBalance;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Integer getLockingPeriod() {
        return lockingPeriod;
    }

    public void setLockingPeriod(Integer lockingPeriod) {
        this.lockingPeriod = lockingPeriod;
    }

    public Integer getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(Integer profitPercent) {
        this.profitPercent = profitPercent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getProfitBalance() {
        return profitBalance;
    }

    public void setProfitBalance(Double profitBalance) {
        this.profitBalance = profitBalance;
    }

}