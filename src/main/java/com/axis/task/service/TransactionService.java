package com.axis.task.service;

import com.axis.task.entity.TransactionEntity;
import com.axis.task.model.request.DepositRequest;
import com.axis.task.model.request.WithdrawRequest;
import com.axis.task.model.response.TransactionResponse;

public interface TransactionService {
    public TransactionEntity getTransaction(String transactionId);
    public TransactionResponse deposit(DepositRequest depositRequest);
    public TransactionResponse withdraw(WithdrawRequest withdrawRequest);

    public TransactionResponse reverseTransaction(String transactionId);

}
