package com.payzout.business.transaction;

import java.util.List;

public interface TransactionInterface {
    void transactionFetchSuccess(List<Transaction> transactionList);

    void transactionNotFound(String message);

    void transactionFetchFailed(String message);
}
