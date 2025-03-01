package com.kosmostecnologia.facturador.application.rest.exception;

import com.kosmostecnologia.facturador.application.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionHandler2 extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e){

        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProcessException.class)
    public final ResponseEntity<ExceptionResponse> handleAppException(ProcessException e){
        ExceptionResponse exceptionResponse;
        if(e.getErrors() != null && e.getErrors().size() > 0){
            exceptionResponse =  new ExceptionResponse(e.getMessage(),e.getErrors());
        } else{
            exceptionResponse = new ExceptionResponse(e.getMessage());
        }
        return new ResponseEntity<>(exceptionResponse,HttpStatus.CONFLICT);
    }

}
