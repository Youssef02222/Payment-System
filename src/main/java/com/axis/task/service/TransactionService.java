package com.axis.task.service;

import com.axis.task.entity.TransactionEntity;
import com.axis.task.model.request.DepositRequest;
import com.axis.task.model.request.WithdrawRequest;

public interface TransactionService {
    public TransactionEntity getTransaction(String transactionId);
    public TransactionEntity deposit(DepositRequest depositRequest);
    public TransactionEntity withdraw(WithdrawRequest withdrawRequest);

    public TransactionEntity reverseTransaction(String transactionId);

}
