package com.payzout.business.transaction;

public interface TransactionInterface {
    void transactionFetchSuccess(Transaction transaction);

    void transactionNotFound(String message);

    void transactionFetchFailed(String message);
}
