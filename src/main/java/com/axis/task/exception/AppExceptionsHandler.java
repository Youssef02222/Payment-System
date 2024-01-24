package com.axis.task.exception;

import com.axis.task.model.response.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;

@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidationError validationError=new ValidationError();
        validationError.setUri(request.getDescription(false));
        List<FieldError> fieldErrorList=ex.getBindingResult().getFieldErrors();
        for(FieldError f:fieldErrorList){
            validationError.addError(f.getDefaultMessage());
        }
        return new ResponseEntity<>(validationError,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InsufficientBalanceException.class})
    public ResponseEntity<Object> handleUserServiceException(InsufficientBalanceException ex, WebRequest wr){
        GenericResponse<String> errorResponse=new GenericResponse<>(false,ex.getMessage(),null);
        return new  ResponseEntity<>(errorResponse ,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request){
        GenericResponse<String> errorResponse=new GenericResponse<>(false,ex.getMessage(),null);
        return new  ResponseEntity<>(errorResponse ,HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
