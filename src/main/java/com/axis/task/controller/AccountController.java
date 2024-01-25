package com.axis.task.controller;

import com.axis.task.entity.AccountEntity;
import com.axis.task.model.request.CreateAccountRequest;
import com.axis.task.model.request.UpdateAccountRequest;
import com.axis.task.model.response.CreateAccountResponse;
import com.axis.task.model.response.GenericResponse;
import com.axis.task.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@Tag(name = "Account APIs", description = "Account management controller")
public class AccountController {
    private static final String SUCCESS = "SUCCESS";
    @Autowired
    AccountService accountService;
    @Operation(summary = "get Account details",responses = {
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation")})
    @GetMapping("/get-account/{accountId}")
    public GenericResponse<AccountEntity> getAccount(@PathVariable String accountId) {
        return GenericResponse.<AccountEntity>builder().data(accountService.getAccount(accountId)).success(true).message(SUCCESS).build();
    }
    @Operation(summary = "Open new Account")
    @PostMapping("/create-account")
    public GenericResponse<CreateAccountResponse> createAccount(@RequestBody @Valid CreateAccountRequest createAccountRequest) {
        return GenericResponse.<CreateAccountResponse>builder().data(accountService.createAccount(createAccountRequest)).success(true).message(SUCCESS).build();
    }
    @Operation(summary = "update account info")
    @PutMapping("/update-account")
    public GenericResponse<AccountEntity> updateAccount(@RequestBody @Valid UpdateAccountRequest updateAccountRequest) throws AccountNotFoundException {
        return GenericResponse.<AccountEntity>builder().data(accountService.updateAccount(updateAccountRequest)).success(true).message(SUCCESS).build();
    }
    @Operation(summary = "delete Account permanently")
    @DeleteMapping("/delete-account/{accountId}")
    public GenericResponse<String> deleteAccount(@PathVariable String accountId) throws AccountNotFoundException {
        accountService.deleteAccount(accountId);
        return GenericResponse.<String>builder().data("Account deleted successfully").success(true).message(SUCCESS).build();
    }

}
