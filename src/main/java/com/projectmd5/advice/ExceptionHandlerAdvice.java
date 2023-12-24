package com.projectmd5.advice;

import com.projectmd5.exception.AuthException;
import com.projectmd5.exception.BadRequestException;
import com.projectmd5.exception.ErrorDetails;
import com.projectmd5.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

   @ExceptionHandler(Exception.class)
   public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception ex, WebRequest request) {
      ErrorDetails message = new ErrorDetails(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

      return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
      Map<String, String> result = new HashMap<>();
      ex.getBindingResult().getFieldErrors().forEach(err ->
            result.put(err.getField(), err.getDefaultMessage()));
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
      ErrorDetails message = new ErrorDetails(
            HttpStatus.NOT_FOUND.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

      return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(AuthenticationException.class)
   public ResponseEntity<ErrorDetails> authException(AuthException ex, WebRequest request) {
      ErrorDetails message = new ErrorDetails(
            HttpStatus.UNAUTHORIZED.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

      return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
   }

   @ExceptionHandler(AccessDeniedException.class)
   public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
      ErrorDetails message = new ErrorDetails(
            HttpStatus.UNAUTHORIZED.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

      return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
   }

   @ExceptionHandler(BadRequestException.class)
   public ResponseEntity<ErrorDetails> handleBadRequestException(BadRequestException ex, WebRequest request) {
      ErrorDetails message = new ErrorDetails(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(MaxUploadSizeExceededException.class)
   public ResponseEntity<ErrorDetails> handleMaxSizeException(MaxUploadSizeExceededException ex, WebRequest request) {
      ErrorDetails message = new ErrorDetails(
            HttpStatus.EXPECTATION_FAILED.value(),
            new Date(),
            "Max file size less than 2MB",
            request.getDescription(false));
      return new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);
   }

   @ExceptionHandler(MultipartException.class)
   public ResponseEntity<ErrorDetails> handleMultipartException(MultipartException ex, WebRequest request){
      ErrorDetails message = new ErrorDetails(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));

      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
   }
}
