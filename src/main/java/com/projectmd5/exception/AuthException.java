package com.projectmd5.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {
   public AuthException(String msg) {
      super(msg);
   }
}
