package com.projectmd5.service.impl;

import com.projectmd5.model.dto.user.PasswordRequest;
import com.projectmd5.model.dto.user.UserRequest;
import com.projectmd5.model.dto.user.UserResponse;
import com.projectmd5.model.entity.User;
import com.projectmd5.repository.IUserRepository;
import com.projectmd5.service.IUserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService implements IUserAccountService{
   private final IUserRepository userRepository;
   @Override
   public UserResponse update(Long userId, UserRequest userRequest) {
      return null;
   }

   @Override
   public String changePassword(PasswordRequest request) {
      return null;
   }
}
