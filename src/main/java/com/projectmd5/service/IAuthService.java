package com.projectmd5.service;

import com.projectmd5.model.dto.auth.AuthResponse;
import com.projectmd5.model.dto.auth.LoginRequest;
import com.projectmd5.model.dto.auth.RegisterRequest;

public interface IAuthService {
   boolean isUsernameExisted(String username);
   boolean isEmailExisted(String email);
   boolean isPhoneExisted(String phone);

   AuthResponse login(LoginRequest login);
   String register(RegisterRequest register);
}
