package com.axis.task.service;

import com.axis.task.entity.AccountEntity;
import com.axis.task.model.request.CreateAccountRequest;
import com.axis.task.model.request.UpdateAccountRequest;
import com.axis.task.model.response.CreateAccountResponse;

import javax.security.auth.login.AccountNotFoundException;

public interface AccountService {
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);
    public void deleteAccount(String accountId) throws AccountNotFoundException;
    public AccountEntity updateAccount(UpdateAccountRequest updateAccountRequest);
    public AccountEntity getAccount(String accountId);

    public Double getBalance(String accountId);
}
