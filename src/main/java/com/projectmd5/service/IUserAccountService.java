package com.projectmd5.service;

import com.projectmd5.model.dto.user.PasswordRequest;
import com.projectmd5.model.dto.user.UserRequest;
import com.projectmd5.model.dto.user.UserResponse;
import com.projectmd5.model.entity.User;

public interface IUserAccountService extends IGenericService<User, Long>{
   UserResponse update(Long userId, UserRequest userRequest);
   String changePassword(PasswordRequest request);
}
