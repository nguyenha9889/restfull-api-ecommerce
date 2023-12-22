package com.projectmd5.advice;

import com.projectmd5.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<?> handleValidateObjectRequest(MethodArgumentNotValidException ex){
      Map<String, String> result = new HashMap<>();
      ex.getBindingResult().getFieldErrors().forEach(err ->
            result.put(err.getField(), err.getDefaultMessage()));
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(UsernameNotFoundException.class)
   @ResponseStatus(value = HttpStatus.NOT_FOUND)
   public ErrorMessage resourceNotFoundException(UsernameNotFoundException ex, WebRequest request) {

      return new ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));
   }
}
