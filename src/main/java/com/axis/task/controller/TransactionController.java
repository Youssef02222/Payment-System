package com.axis.task.controller;

import com.axis.task.entity.TransactionEntity;
import com.axis.task.model.request.DepositRequest;
import com.axis.task.model.request.WithdrawRequest;
import com.axis.task.model.response.GenericResponse;
import com.axis.task.model.response.TransactionResponse;
import com.axis.task.service.AccountService;
import com.axis.task.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    private static final String SUCCESS = "SUCCESS";
    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;
    @ApiOperation(value = "Inquire for a transaction", response = TransactionEntity.class)
    @GetMapping("/get-transaction/{transactionId}")
    public GenericResponse<TransactionEntity> getTransaction(@PathVariable String transactionId) {
        return GenericResponse.<TransactionEntity>builder().data(transactionService.getTransaction(transactionId)).success(true).message(SUCCESS).build();
    }
    @ApiOperation(value = "get account current balance")
    @GetMapping("/get-balance/{accountId}")
    public GenericResponse<Double> getBalance(@PathVariable String accountId) {
        return GenericResponse.<Double>builder().data(accountService.getBalance(accountId)).success(true).message(SUCCESS).build();
    }
    @ApiOperation(value = "deposit fund to account", response = TransactionResponse.class)
    @PostMapping("/deposit")
    public GenericResponse<TransactionResponse> deposit(@RequestBody DepositRequest depositRequest) {
        return GenericResponse.<TransactionResponse>builder().data(transactionService.deposit(depositRequest)).success(true).message(SUCCESS).build();
    }
    @ApiOperation(value = "withdraw fund from account", response = TransactionResponse.class)
    @PostMapping("/withdraw")
    public GenericResponse<TransactionResponse> withdraw(@RequestBody WithdrawRequest withdrawRequest) {
        return GenericResponse.<TransactionResponse>builder().data(transactionService.withdraw(withdrawRequest)).success(true).message(SUCCESS).build();
    }
    @ApiOperation(value = "reverse a transaction", response = TransactionResponse.class)
    @GetMapping("/reverse-transaction/{transactionId}")
    public GenericResponse<TransactionResponse> reverseTransaction(@PathVariable String transactionId) {
        return GenericResponse.<TransactionResponse>builder().data(transactionService.reverseTransaction(transactionId)).success(true).message(SUCCESS).build();
    }

}
