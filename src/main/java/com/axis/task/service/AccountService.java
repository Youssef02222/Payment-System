package com.axis.task.service;

import com.axis.task.entity.AccountEntity;
import com.axis.task.model.request.CreateAccountRequest;
import com.axis.task.model.request.UpdateAccountRequest;
import com.axis.task.model.response.CreateAccountResponse;

import javax.security.auth.login.AccountNotFoundException;

public interface AccountService {
     CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);
     void deleteAccount(String accountId) throws AccountNotFoundException;
     AccountEntity updateAccount(UpdateAccountRequest updateAccountRequest) throws AccountNotFoundException;
     AccountEntity getAccount(String accountId);

     Double getBalance(String accountId);
}
