package com.axis.task;

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
import com.axis.task.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void deposit_Success() {
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAmount(100.0);
        depositRequest.setAccountId("accountId");

        AccountEntity mockAccountEntity = new AccountEntity();
        mockAccountEntity.setBalance(500.0);
        Mockito.when(accountRepository.findByAccountId(depositRequest.getAccountId())).thenReturn(mockAccountEntity);

        TransactionEntity mockTransactionEntity = new TransactionEntity();
        Mockito.when(transactionRepository.save(Mockito.any(TransactionEntity.class))).thenReturn(mockTransactionEntity);

        TransactionResponse mockTransactionResponse = new TransactionResponse();
        Mockito.when(modelMapper.map(mockTransactionEntity, TransactionResponse.class)).thenReturn(mockTransactionResponse);

        TransactionResponse result = transactionService.deposit(depositRequest);

        assertNotNull(result);
        assertEquals(mockTransactionResponse, result);
        assertEquals(result.getCurrentBalance(), mockAccountEntity.getBalance());
    }

    @Test
    void withdraw_Success() {
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAmount(100.0);
        withdrawRequest.setAccountId("accountId");

        AccountEntity mockAccountEntity = new AccountEntity();
        mockAccountEntity.setBalance(500.0);
        Mockito.when(accountRepository.findByAccountId(withdrawRequest.getAccountId())).thenReturn(mockAccountEntity);

        TransactionEntity mockTransactionEntity = new TransactionEntity();
        Mockito.when(transactionRepository.save(Mockito.any(TransactionEntity.class))).thenReturn(mockTransactionEntity);

        TransactionResponse mockTransactionResponse = new TransactionResponse();
        Mockito.when(modelMapper.map(mockTransactionEntity, TransactionResponse.class)).thenReturn(mockTransactionResponse);

        TransactionResponse result = transactionService.withdraw(withdrawRequest);

        assertNotNull(result);
        assertEquals(mockTransactionResponse, result);
        assertEquals(result.getCurrentBalance(), mockAccountEntity.getBalance());
    }

    @Test
    void withdraw_InsufficientBalanceException() {
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAmount(1000.0);
        withdrawRequest.setAccountId("accountId");

        AccountEntity mockAccountEntity = new AccountEntity();
        mockAccountEntity.setBalance(500.0);
        Mockito.when(accountRepository.findByAccountId(withdrawRequest.getAccountId())).thenReturn(mockAccountEntity);

        // Perform the test
        assertThrows(InsufficientBalanceException.class, () -> transactionService.withdraw(withdrawRequest));
    }

    @Test
    void reverseTransaction_AlreadyReversed() {
        String transactionId = "123";
        TransactionEntity mockTransactionEntity = new TransactionEntity();
        mockTransactionEntity.setStatus(TransactionStatus.REVERSED.name());

        Mockito.when(transactionRepository.findByTransactionId(transactionId)).thenReturn(mockTransactionEntity);

        // Perform the test
        assertThrows(GeneralException.class, () -> transactionService.reverseTransaction(transactionId));
    }

    @Test
    void reverseTransaction_Deposit_InsufficientBalanceException() {
        String transactionId = "123";
        TransactionEntity mockTransactionEntity = new TransactionEntity();
        mockTransactionEntity.setTransactionId(transactionId);
        mockTransactionEntity.setStatus(TransactionStatus.SUCCESS.name());
        mockTransactionEntity.setAmount(100.0);
        mockTransactionEntity.setType("deposit");

        AccountEntity mockAccountEntity = new AccountEntity();
        mockAccountEntity.setBalance(50.0);
        mockTransactionEntity.setAccountDetails(mockAccountEntity);

        Mockito.when(transactionRepository.findByTransactionId(transactionId)).thenReturn(mockTransactionEntity);

        // Perform the test
        assertThrows(InsufficientBalanceException.class, () -> transactionService.reverseTransaction(transactionId));
    }
}
