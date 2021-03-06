package com.payzout.business.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Transaction {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Transaction_Response> data = null;

    /**
     * No args constructor for use in serialization
     */
    public Transaction() {
    }

    /**
     * @param data
     * @param status
     */
    public Transaction(Boolean status, List<Transaction_Response> data) {
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

    public List<Transaction_Response> getData() {
        return data;
    }

    public void setData(List<Transaction_Response> data) {
        this.data = data;
    }


}