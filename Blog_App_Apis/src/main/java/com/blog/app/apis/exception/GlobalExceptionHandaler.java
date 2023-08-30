package com.blog.app.apis.exception;

import com.blog.app.apis.dto.responseDto.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandaler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponses> resourceNotFoundExceptionsHendaler( ResourceNotFoundException ex){
        String message= ex.getMessage();
        ApiResponses apiResponses=new ApiResponses(message,false);
        return new ResponseEntity<>(apiResponses, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<Map<String, String>>  handelMethodNotFoundException(MethodArgumentNotValidException ex){
        Map<String, String> resp=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String filedName=((FieldError)error).getField();
            String message=error.getDefaultMessage();
            resp.put(filedName,message);
        });
        return new ResponseEntity< Map<String,String> >(resp,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponses> handelApiException( ApiException ex){
        String message= ex.getMessage();
        ApiResponses apiResponses=new ApiResponses(message,true);
        return new ResponseEntity<>(apiResponses, HttpStatus.BAD_REQUEST);
    }
}