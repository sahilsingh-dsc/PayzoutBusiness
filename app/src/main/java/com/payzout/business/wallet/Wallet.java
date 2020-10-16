package com.payzout.business.wallet;

public class Wallet {
    private String uid;
    private String invested_balance;
    private String profit_balance;

    public Wallet() {
    }

    public Wallet(String uid, String invested_balance, String profit_balance) {
        this.uid = uid;
        this.invested_balance = invested_balance;
        this.profit_balance = profit_balance;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getInvested_balance() {
        return invested_balance;
    }

    public void setInvested_balance(String invested_balance) {
        this.invested_balance = invested_balance;
    }

    public String getProfit_balance() {
        return profit_balance;
    }

    public void setProfit_balance(String profit_balance) {
        this.profit_balance = profit_balance;
    }
}
