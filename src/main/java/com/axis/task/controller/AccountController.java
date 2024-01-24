package com.axis.task.controller;

import com.axis.task.entity.AccountEntity;
import com.axis.task.model.request.CreateAccountRequest;
import com.axis.task.model.request.UpdateAccountRequest;
import com.axis.task.model.response.CreateAccountResponse;
import com.axis.task.model.response.GenericResponse;
import com.axis.task.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
public class AccountController {
    private static final String SUCCESS = "SUCCESS";
    @Autowired
    AccountService accountService;
    @ApiOperation(value = "get Account details", response = AccountEntity.class)
    @GetMapping("/get-account/{accountId}")
    public GenericResponse<AccountEntity> getAccount(@PathVariable String accountId) {
        return GenericResponse.<AccountEntity>builder().data(accountService.getAccount(accountId)).success(true).message(SUCCESS).build();
    }
    @ApiOperation(value = "Open new Account", response = CreateAccountResponse.class)
    @PostMapping("/create-account")
    public GenericResponse<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return GenericResponse.<CreateAccountResponse>builder().data(accountService.createAccount(createAccountRequest)).success(true).message(SUCCESS).build();
    }
    @ApiOperation(value = "update Account data", response = AccountEntity.class)
    @PutMapping("/update-account")
    public GenericResponse<AccountEntity> updateAccount(@RequestBody UpdateAccountRequest updateAccountRequest) {
        return GenericResponse.<AccountEntity>builder().data(accountService.updateAccount(updateAccountRequest)).success(true).message(SUCCESS).build();
    }
    @ApiOperation(value = "delete Account data")
    @DeleteMapping("/delete-account/{accountId}")
    public GenericResponse<String> deleteAccount(@PathVariable String accountId) throws AccountNotFoundException {
        accountService.deleteAccount(accountId);
        return GenericResponse.<String>builder().data("Account deleted successfully").success(true).message(SUCCESS).build();
    }

}
