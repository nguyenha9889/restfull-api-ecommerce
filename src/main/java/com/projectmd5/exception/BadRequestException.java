package com.projectmd5.exception;

import java.io.Serial;

public class BadRequestException extends RuntimeException{
   @Serial
   private static final long serialVersionUID = 1L;
   public BadRequestException(String msg) {
      super(msg);
   }
}
