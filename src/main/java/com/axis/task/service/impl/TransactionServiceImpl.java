package com.axis.task.service.impl;

import com.axis.task.entity.AccountEntity;
import com.axis.task.entity.TransactionEntity;
import com.axis.task.enums.TransactionStatus;
import com.axis.task.exception.GeneralException;
import com.axis.task.exception.InsufficientBalanceException;
import com.axis.task.model.request.DepositRequest;
import com.axis.task.model.request.WithdrawRequest;
import com.axis.task.model.response.TransactionResponse;
import com.axis.task.repository.AccountRepository;
import com.axis.task.repository.TransactionRepository;
import com.axis.task.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final String WITHDRAW="withdraw";
    private static final String DEPOSIT="deposit";
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public TransactionEntity getTransaction(String transactionId) {
        try{
            return transactionRepository.findByTransactionId(transactionId);
        }catch (Exception e) {
            throw new GeneralException("Error while getting transaction");
        }
    }

    @Override
    public TransactionResponse deposit(DepositRequest depositRequest) {
        try{
            if(depositRequest.getAmount()<=0){
                throw new GeneralException("Amount should be greater than 0");
            }
            AccountEntity accountEntity=accountRepository.findByAccountId(depositRequest.getAccountId());
            TransactionEntity transactionEntity=new TransactionEntity();
            transactionEntity.setAccountDetails(accountEntity);
            transactionEntity.setAmount(depositRequest.getAmount());
            transactionEntity.setTransactionDate(new Date());
            transactionEntity.setType(DEPOSIT);
            transactionEntity.setStatus(TransactionStatus.SUCCESS.name());
            accountEntity.setBalance(accountEntity.getBalance()+depositRequest.getAmount());
            accountRepository.save(accountEntity);
            transactionEntity=transactionRepository.save(transactionEntity);
            return modelMapper.map(transactionEntity,TransactionResponse.class);
        }catch (Exception e) {
            throw new GeneralException("Error while depositing amount");
        }

    }

    @Override
    public TransactionResponse withdraw(WithdrawRequest withdrawRequest) {
        try{
            if(withdrawRequest.getAmount()<=0){
                throw new GeneralException("Amount should be greater than 0");
            }
            AccountEntity accountEntity=accountRepository.findByAccountId(withdrawRequest.getAccountId());
            if(accountEntity.getBalance()<withdrawRequest.getAmount()){
                throw new InsufficientBalanceException("Insufficient balance");
            }
            TransactionEntity transactionEntity=new TransactionEntity();
            transactionEntity.setAccountDetails(accountEntity);
            transactionEntity.setAmount(withdrawRequest.getAmount());
            transactionEntity.setTransactionDate(new Date());
            transactionEntity.setType(WITHDRAW);
            transactionEntity.setStatus(TransactionStatus.SUCCESS.name());
            accountEntity.setBalance(accountEntity.getBalance()-withdrawRequest.getAmount());
            accountRepository.save(accountEntity);
            transactionEntity=transactionRepository.save(transactionEntity);
            return modelMapper.map(transactionEntity,TransactionResponse.class);
        }catch (Exception e) {
            throw new GeneralException("Error while withdrawing amount");
        }
    }

    @Override
    public TransactionResponse reverseTransaction(String transactionId) {
        try{
            TransactionEntity transactionEntity=transactionRepository.findByTransactionId(transactionId);
            if(transactionEntity==null){
                throw new GeneralException("Transaction not found");
            }
            if(transactionEntity.getStatus().equals(TransactionStatus.REVERSED.name())){
                throw new GeneralException("Transaction already reversed");
            }
            if(transactionEntity.getType().equals(DEPOSIT) && (transactionEntity.getAccountDetails().getBalance()<transactionEntity.getAmount())){
                    throw new InsufficientBalanceException("Insufficient balance to reverse transaction");
            }
            AccountEntity accountEntity=transactionEntity.getAccountDetails();
            if(transactionEntity.getType().equals(DEPOSIT)){
                accountEntity.setBalance(accountEntity.getBalance()-transactionEntity.getAmount());
            }
            else if(transactionEntity.getType().equals(WITHDRAW)){
                accountEntity.setBalance(accountEntity.getBalance()+transactionEntity.getAmount());
            }
            accountRepository.save(accountEntity);
            transactionEntity.setStatus(TransactionStatus.REVERSED.name());
            transactionEntity=transactionRepository.save(transactionEntity);
            return modelMapper.map(transactionEntity,TransactionResponse.class);
        }catch (Exception e) {
            throw new GeneralException("Error while reversing transaction");
        }
    }
}
