package com.axis.task.service.impl;

import com.axis.task.entity.AccountEntity;
import com.axis.task.exception.GeneralException;
import com.axis.task.model.request.CreateAccountRequest;
import com.axis.task.model.request.UpdateAccountRequest;
import com.axis.task.model.response.CreateAccountResponse;
import com.axis.task.repository.AccountRepository;
import com.axis.task.service.AccountService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private static final String ACCOUNT_NOT_FOUND="there qas no account with found with id : ";
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
            throw new GeneralException("Error while creating account");
        }
    }

    @Override
    @Transactional
    public void deleteAccount(String accountId) throws AccountNotFoundException {
        try{
            accountRepository.deleteByAccountId(accountId);
        } catch (GeneralException e){
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND+accountId);
        }catch (Exception e){
            throw new GeneralException("Error while deleting account");
        }
    }

    @Override
    public AccountEntity updateAccount(UpdateAccountRequest updateAccountRequest) throws AccountNotFoundException {
       try{
           AccountEntity accountEntity= accountRepository.findByAccountId(updateAccountRequest.getAccountId());
           if(accountEntity!=null){
                accountEntity.setName(updateAccountRequest.getName());
                accountEntity.setEmail(updateAccountRequest.getEmail());
                accountEntity.setPhone(updateAccountRequest.getPhone());
               accountEntity=accountRepository.save(accountEntity);
               return accountEntity;
           }else{
                throw new AccountNotFoundException(ACCOUNT_NOT_FOUND+updateAccountRequest.getAccountId());
           }

       }catch (AccountNotFoundException e) {
           throw new AccountNotFoundException(ACCOUNT_NOT_FOUND + updateAccountRequest.getAccountId());
       }
       catch (Exception e) {
           throw new GeneralException("Error while updating account");
       }
    }

    @Override
    public AccountEntity getAccount(String accountId) {
        try {
            AccountEntity accountEntity = accountRepository.findByAccountId(accountId);
            if (accountEntity != null) {
                return accountEntity;
            } else {
                throw new AccountNotFoundException(ACCOUNT_NOT_FOUND+accountId);
            }
        }catch (Exception e){
            throw new GeneralException("Error while getting account");
        }

    }

    @Override
    public Double getBalance(String accountId) {
        try {
            AccountEntity accountEntity = accountRepository.findByAccountId(accountId);
            if (accountEntity != null) {
                return accountEntity.getBalance();
            } else {
                throw new AccountNotFoundException(ACCOUNT_NOT_FOUND+accountId);
            }
        }catch (Exception e){
            throw new GeneralException("Error while getting account");
        }
    }
}
