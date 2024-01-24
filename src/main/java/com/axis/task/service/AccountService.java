package com.axis.task.service;

import com.axis.task.entity.AccountEntity;
import com.axis.task.model.request.CreateAccountRequest;
import com.axis.task.model.request.UpdateAccountRequest;

public interface AccountService {
    public AccountEntity createAccount(CreateAccountRequest createAccountRequest);
    public String deleteAccount(String accountId);
    public AccountEntity updateAccount(UpdateAccountRequest updateAccountRequest);
    public AccountEntity getAccount(String accountId);

    public Double getBalance(String accountId);
}
