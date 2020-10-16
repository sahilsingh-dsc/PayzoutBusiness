package com.payzout.business.transaction;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Transaction {
    @ServerTimestamp
    private Date timestamp;
    private String t_id;
    private String u_id;
    private String t_type;
    private String t_amount;
    private String t_date;
    private String t_time;
    private String t_remark;
    private String t_status;

    public Transaction() {
    }

    public Transaction(Date timestamp, String t_id, String u_id, String t_type, String t_amount, String t_date, String t_time, String t_remark, String t_status) {
        this.timestamp = timestamp;
        this.t_id = t_id;
        this.u_id = u_id;
        this.t_type = t_type;
        this.t_amount = t_amount;
        this.t_date = t_date;
        this.t_time = t_time;
        this.t_remark = t_remark;
        this.t_status = t_status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getT_type() {
        return t_type;
    }

    public void setT_type(String t_type) {
        this.t_type = t_type;
    }

    public String getT_amount() {
        return t_amount;
    }

    public void setT_amount(String t_amount) {
        this.t_amount = t_amount;
    }

    public String getT_date() {
        return t_date;
    }

    public void setT_date(String t_date) {
        this.t_date = t_date;
    }

    public String getT_time() {
        return t_time;
    }

    public void setT_time(String t_time) {
        this.t_time = t_time;
    }

    public String getT_remark() {
        return t_remark;
    }

    public void setT_remark(String t_remark) {
        this.t_remark = t_remark;
    }

    public String getT_status() {
        return t_status;
    }

    public void setT_status(String t_status) {
        this.t_status = t_status;
    }

}
