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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final String WITHDRAW = "withdraw";
    private static final String DEPOSIT = "deposit";

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public TransactionEntity getTransaction(String transactionId) {
        try {
            return transactionRepository.findByTransactionId(transactionId);
        } catch (Exception e) {
            logger.error("Error while getting transaction", e);
            throw new GeneralException("Error while getting transaction");
        }
    }

    @Override
    public TransactionResponse deposit(DepositRequest depositRequest) {
        try {
            validateAmount(depositRequest.getAmount());
            AccountEntity accountEntity = getAccountEntity(depositRequest.getAccountId());

            TransactionEntity transactionEntity = createTransactionEntity(accountEntity, depositRequest.getAmount(), DEPOSIT);
            updateAccountBalance(accountEntity, depositRequest.getAmount());

            return createTransactionResponse(transactionEntity, accountEntity.getBalance());
        } catch (Exception e) {
            handleException("Error while depositing amount", e);
            throw new GeneralException("Error while depositing amount");
        }
    }

    @Override
    public TransactionResponse withdraw(WithdrawRequest withdrawRequest) {
        try {
            validateAmount(withdrawRequest.getAmount());
            AccountEntity accountEntity = getAccountEntity(withdrawRequest.getAccountId());

            if (accountEntity.getBalance() < withdrawRequest.getAmount()) {
                throw new InsufficientBalanceException("Insufficient balance");
            }

            TransactionEntity transactionEntity = createTransactionEntity(accountEntity, withdrawRequest.getAmount(), WITHDRAW);
            updateAccountBalance(accountEntity, -withdrawRequest.getAmount());

            return createTransactionResponse(transactionEntity, accountEntity.getBalance());
        } catch (InsufficientBalanceException | GeneralException e) {
            throw e;
        } catch (Exception e) {
            handleException("Error while withdrawing amount", e);
            throw new GeneralException("Error while withdrawing amount");
        }
    }

    @Override
    public TransactionResponse reverseTransaction(String transactionId) {
        try {
            TransactionEntity transactionEntity = getTransactionEntity(transactionId);

            if (TransactionStatus.REVERSED.name().equals(transactionEntity.getStatus())) {
                throw new GeneralException("Transaction already reversed");
            }

            if (DEPOSIT.equals(transactionEntity.getType()) &&
                    transactionEntity.getAccountDetails().getBalance() < transactionEntity.getAmount()) {
                throw new InsufficientBalanceException("Insufficient balance to reverse transaction");
            }

            AccountEntity accountEntity = transactionEntity.getAccountDetails();
            updateAccountBalanceOnReversal(transactionEntity, accountEntity);

            transactionEntity.setStatus(TransactionStatus.REVERSED.name());
            transactionEntity = transactionRepository.save(transactionEntity);

            return createTransactionResponse(transactionEntity, accountEntity.getBalance());
        } catch (InsufficientBalanceException | GeneralException e) {
            throw e;
        } catch (Exception e) {
            handleException("Error while reversing transaction", e);
            throw new GeneralException("Error while reversing transaction");
        }
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new GeneralException("Amount should be greater than 0");
        }
    }

    private AccountEntity getAccountEntity(String accountId) {
        return accountRepository.findByAccountId(accountId);
    }

    private TransactionEntity createTransactionEntity(AccountEntity accountEntity, double amount, String type) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(UUID.randomUUID().toString());
        transactionEntity.setAccountDetails(accountEntity);
        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionDate(new Date());
        transactionEntity.setType(type);
        transactionEntity.setStatus(TransactionStatus.SUCCESS.name());
        return transactionRepository.save(transactionEntity);
    }

    private void updateAccountBalance(AccountEntity accountEntity, double amount) {
        accountEntity.setBalance(accountEntity.getBalance() + amount);
        accountRepository.save(accountEntity);
    }

    private void updateAccountBalanceOnReversal(TransactionEntity transactionEntity, AccountEntity accountEntity) {
        if (DEPOSIT.equals(transactionEntity.getType())) {
            accountEntity.setBalance(accountEntity.getBalance() - transactionEntity.getAmount());
        } else if (WITHDRAW.equals(transactionEntity.getType())) {
            accountEntity.setBalance(accountEntity.getBalance() + transactionEntity.getAmount());
        }
        accountRepository.save(accountEntity);
    }

    private TransactionEntity getTransactionEntity(String transactionId) {
        TransactionEntity transactionEntity = transactionRepository.findByTransactionId(transactionId);
        if (transactionEntity == null) {
            throw new GeneralException("Transaction not found");
        }
        return transactionEntity;
    }

    private TransactionResponse createTransactionResponse(TransactionEntity transactionEntity, double currentBalance) {
        TransactionResponse transactionResponse = modelMapper.map(transactionEntity, TransactionResponse.class);
        transactionResponse.setCurrentBalance(currentBalance);
        return transactionResponse;
    }

    private void handleException(String message, Exception e) {
        logger.error(message, e);
    }
}
