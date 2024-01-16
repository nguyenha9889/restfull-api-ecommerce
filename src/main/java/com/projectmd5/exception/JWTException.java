package com.projectmd5.exception;

import java.io.Serial;

public class JWTException extends RuntimeException{
   @Serial
   private static final long serialVersionUID = 1L;
   public JWTException(String msg) {
      super(msg);
   }
}
