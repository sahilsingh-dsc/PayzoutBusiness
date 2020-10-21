package com.payzout.business.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckInvestor {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public CheckInvestor() {
    }

    /**
     *
     * @param message
     * @param status
     */
    public CheckInvestor(Boolean status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}