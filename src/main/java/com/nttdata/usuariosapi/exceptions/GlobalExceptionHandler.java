package com.nttdata.usuariosapi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessage> handleCustomException(CustomException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
