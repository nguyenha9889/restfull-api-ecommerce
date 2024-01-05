package com.projectmd5.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
   private int statusCode;
   private Date timestamp;
   private String message;
   private String description;

   public ErrorDetails(String message) {
      this.message = message;
   }
}
