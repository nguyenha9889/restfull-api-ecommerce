package com.projectmd5.service;

import com.projectmd5.model.dto.request.LoginDTO;
import com.projectmd5.model.dto.request.RegisterDTO;
import com.projectmd5.model.dto.response.UserResponse;
import com.projectmd5.model.entity.User;

public interface IUserService extends IGenericService<User, Long> {
   Boolean existsByUsername(String username);
   Boolean existsByEmail(String email);
   Boolean existsByPhone(String phone);
   User create(RegisterDTO register);
   UserResponse login(LoginDTO login);
}
