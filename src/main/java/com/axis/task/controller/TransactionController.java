package com.axis.task.controller;

import com.axis.task.entity.TransactionEntity;
import com.axis.task.model.request.DepositRequest;
import com.axis.task.model.request.WithdrawRequest;
import com.axis.task.model.response.GenericResponse;
import com.axis.task.model.response.TransactionResponse;
import com.axis.task.service.AccountService;
import com.axis.task.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Transaction APIs", description = "Transaction management controller")
public class TransactionController {
    private static final String SUCCESS = "SUCCESS";
    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;
    @Operation(summary = "Inquire about transaction", description = "This endpoint retrieves the transaction details for provided transaction id.")
    @GetMapping("/get-transaction/{transactionId}")
    public GenericResponse<TransactionEntity> getTransaction(@PathVariable String transactionId) {
        return GenericResponse.<TransactionEntity>builder().data(transactionService.getTransaction(transactionId)).success(true).message(SUCCESS).build();
    }
    @Operation(summary = "Get account current balance", description = "This endpoint retrieves the current balance for provided account id.")
    @GetMapping("/get-balance/{accountId}")
    public GenericResponse<Double> getBalance(@PathVariable String accountId) {
        return GenericResponse.<Double>builder().data(accountService.getBalance(accountId)).success(true).message(SUCCESS).build();
    }

    @Operation(summary = "deposit fund to account")
    @PostMapping("/deposit")
    public GenericResponse<TransactionResponse> deposit(@RequestBody @Valid DepositRequest depositRequest) {
        return GenericResponse.<TransactionResponse>builder().data(transactionService.deposit(depositRequest)).success(true).message(SUCCESS).build();
    }

    @Operation(summary = "withdraw fund from account")
    @PostMapping("/withdraw")
    public GenericResponse<TransactionResponse> withdraw(@RequestBody @Valid WithdrawRequest withdrawRequest) {
        return GenericResponse.<TransactionResponse>builder().data(transactionService.withdraw(withdrawRequest)).success(true).message(SUCCESS).build();
    }

    @Operation(summary = "reverse a transaction")
    @GetMapping("/reverse-transaction/{transactionId}")
    public GenericResponse<TransactionResponse> reverseTransaction(@PathVariable String transactionId) {
        return GenericResponse.<TransactionResponse>builder().data(transactionService.reverseTransaction(transactionId)).success(true).message(SUCCESS).build();
    }

}
