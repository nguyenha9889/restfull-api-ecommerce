package com.projectmd5.service;

import com.projectmd5.model.dto.auth.JwtResponse;
import com.projectmd5.model.dto.auth.LoginRequest;
import com.projectmd5.model.dto.auth.RegisterRequest;

public interface IAuthService {

   JwtResponse login(LoginRequest login);
   String register(RegisterRequest register);
}
