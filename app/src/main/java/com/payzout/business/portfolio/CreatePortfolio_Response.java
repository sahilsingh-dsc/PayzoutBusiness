package com.payzout.business.portfolio;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class CreatePortfolio_Response {
    @ServerTimestamp
    private Date timestamp;
    private String p_id;
    private String u_id;
    private String p_date;
    private String p_class;
    private String p_amount;
    private String p_profit;
    private String p_interest;
    private String p_locking;

    public CreatePortfolio_Response() {
    }

    public CreatePortfolio_Response(Date timestamp, String p_id, String u_id, String p_date, String p_class, String p_amount, String p_profit, String p_interest, String p_locking) {
        this.timestamp = timestamp;
        this.p_id = p_id;
        this.u_id = u_id;
        this.p_date = p_date;
        this.p_class = p_class;
        this.p_amount = p_amount;
        this.p_profit = p_profit;
        this.p_interest = p_interest;
        this.p_locking = p_locking;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getP_date() {
        return p_date;
    }

    public void setP_date(String p_date) {
        this.p_date = p_date;
    }

    public String getP_class() {
        return p_class;
    }

    public void setP_class(String p_class) {
        this.p_class = p_class;
    }

    public String getP_amount() {
        return p_amount;
    }

    public void setP_amount(String p_amount) {
        this.p_amount = p_amount;
    }

    public String getP_profit() {
        return p_profit;
    }

    public void setP_profit(String p_profit) {
        this.p_profit = p_profit;
    }

    public String getP_interest() {
        return p_interest;
    }

    public void setP_interest(String p_interest) {
        this.p_interest = p_interest;
    }

    public String getP_locking() {
        return p_locking;
    }

    public void setP_locking(String p_locking) {
        this.p_locking = p_locking;
    }
}
