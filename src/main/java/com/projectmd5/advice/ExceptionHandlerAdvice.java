package com.projectmd5.advice;

import com.projectmd5.exception.JWTException;
import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

   @ExceptionHandler(Exception.class)
   public ResponseEntity<MessageResponse> globalExceptionHandler(Exception ex) {
      MessageResponse message = new MessageResponse(ex.getMessage());

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
   public ResponseEntity<MessageResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
      MessageResponse message = new MessageResponse(ex.getMessage());
      return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(AuthenticationException.class)
   public ResponseEntity<MessageResponse> handleAuthException(AuthenticationException ex) {
      MessageResponse message = new MessageResponse(ex.getMessage());
      return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
   }

   @ExceptionHandler(JWTException.class)
   public ResponseEntity<MessageResponse> handleBadRequestException(JWTException ex) {
      MessageResponse message = new MessageResponse(ex.getMessage());

      return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
   }

   @ExceptionHandler(MaxUploadSizeExceededException.class)
   public ResponseEntity<MessageResponse> handleMaxSizeException(MaxUploadSizeExceededException ex, WebRequest request) {
      MessageResponse message = new MessageResponse("Max file size less than 2MB");
      return new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);
   }

   @ExceptionHandler(MultipartException.class)
   public ResponseEntity<MessageResponse> handleMultipartException(MultipartException ex, WebRequest request){
      MessageResponse message = new MessageResponse(ex.getMessage());

      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
   }
}
