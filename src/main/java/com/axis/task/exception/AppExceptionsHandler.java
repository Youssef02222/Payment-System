package com.axis.task.exception;

import com.axis.task.model.response.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;

@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        ValidationError errorResponse = new ValidationError();
        errorResponse.setUri(request.getDescription(false));
        List<FieldError> fieldErrorList = result.getFieldErrors();
        for (FieldError f : fieldErrorList) {
            errorResponse.addError(f.getDefaultMessage());
        }
        GenericResponse<ValidationError> errorResponseGenericResponse = new GenericResponse<>(false, "Validation Error", errorResponse);
        return new ResponseEntity<>(errorResponseGenericResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InsufficientBalanceException.class})
    public ResponseEntity<Object> handleUserServiceException(InsufficientBalanceException ex, WebRequest wr){
        GenericResponse<String> errorResponse=new GenericResponse<>(false,ex.getMessage(),null);
        return new  ResponseEntity<>(errorResponse ,HttpStatus.OK);
    }
    @ExceptionHandler(value = {AccountNotFoundException.class})
    public ResponseEntity<Object> accountNotFoundException(AccountNotFoundException ex, WebRequest wr){
        GenericResponse<String> errorResponse=new GenericResponse<>(false,ex.getMessage(),null);
        return new  ResponseEntity<>(errorResponse ,HttpStatus.OK);
    }
    @ExceptionHandler(value = {GeneralException.class})
    public ResponseEntity<Object> handleGeneralException(GeneralException ex, WebRequest request){
        GenericResponse<String> errorResponse=new GenericResponse<>(false,ex.getMessage(),null);
        return new  ResponseEntity<>(errorResponse ,HttpStatus.OK);
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request){
        GenericResponse<String> errorResponse=new GenericResponse<>(false,ex.getMessage(),null);
        return new  ResponseEntity<>(errorResponse ,HttpStatus.OK);
    }



}
