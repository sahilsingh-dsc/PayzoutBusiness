package com.payzout.business.portfolio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PortfolioResponse {

    @SerializedName("portfolio_id")
    @Expose
    private String portfolioId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("amount")
    @Expose
    private Integer amount;
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

    /**
     * No args constructor for use in serialization
     */
    public PortfolioResponse() {
    }

    /**
     * @param date
     * @param amount
     * @param classId
     * @param portfolioId
     * @param profitPercent
     * @param lockingPeriod
     * @param time
     * @param userId
     */
    public PortfolioResponse(String portfolioId, String userId, Integer amount, String classId, Integer lockingPeriod, Integer profitPercent, String date, String time) {
        super();
        this.portfolioId = portfolioId;
        this.userId = userId;
        this.amount = amount;
        this.classId = classId;
        this.lockingPeriod = lockingPeriod;
        this.profitPercent = profitPercent;
        this.date = date;
        this.time = time;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

}