package com.axis.task.service.impl;

import com.axis.task.entity.AccountEntity;
import com.axis.task.model.request.CreateAccountRequest;
import com.axis.task.model.request.UpdateAccountRequest;
import com.axis.task.model.response.CreateAccountResponse;
import com.axis.task.repository.AccountRepository;
import com.axis.task.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        try{
            AccountEntity accountEntity=modelMapper.map(createAccountRequest,AccountEntity.class);
            accountEntity.setAccountId(UUID.randomUUID().toString());
            accountEntity.setBalance(1000);
            accountEntity=accountRepository.save(accountEntity);
            return modelMapper.map(accountEntity,CreateAccountResponse.class);
        }catch (Exception e){
            throw new RuntimeException("Error while creating account");
        }
    }

    @Override
    public void deleteAccount(String accountId) throws AccountNotFoundException {
        try{
            accountRepository.deleteByAccountId(accountId);
        } catch (RuntimeException e){
            throw new AccountNotFoundException("there qas no account with id "+accountId+" found");
        }catch (Exception e){
            throw new RuntimeException("Error while deleting account");
        }
    }

    @Override
    public AccountEntity updateAccount(UpdateAccountRequest updateAccountRequest) {
       try{
           AccountEntity accountEntity= accountRepository.findByAccountId(updateAccountRequest.getAccountId());
           if(accountEntity!=null){
               accountEntity= modelMapper.map(updateAccountRequest,AccountEntity.class);
               accountEntity=accountRepository.save(accountEntity);
               return accountEntity;
           }else{
                throw new AccountNotFoundException("there qas no account with id "+updateAccountRequest.getAccountId()+" found");
           }

       }catch (Exception e) {
           throw new RuntimeException("Error while updating account");
       }
    }

    @Override
    public AccountEntity getAccount(String accountId) {
        try {
            AccountEntity accountEntity = accountRepository.findByAccountId(accountId);
            if (accountEntity != null) {
                return accountEntity;
            } else {
                throw new AccountNotFoundException("there qas no account with id " + accountId + " found");
            }
        }catch (Exception e){
            throw new RuntimeException("Error while getting account");
        }

    }

    @Override
    public Double getBalance(String accountId) {
        try {
            AccountEntity accountEntity = accountRepository.findByAccountId(accountId);
            if (accountEntity != null) {
                return accountEntity.getBalance();
            } else {
                throw new AccountNotFoundException("there qas no account with id " + accountId + " found");
            }
        }catch (Exception e){
            throw new RuntimeException("Error while getting account");
        }
    }
}
