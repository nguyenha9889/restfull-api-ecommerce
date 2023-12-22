package com.projectmd5.service.impl;

import com.projectmd5.repository.IUserRepository;
import com.projectmd5.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
   private final IUserRepository userRepository;
   @Override
   public Boolean existsByUsername(String username) {
      return userRepository.existsByUsername(username);
   }

   @Override
   public Boolean existsByEmail(String email) {
      return userRepository.existsByEmail(email);
   }

   @Override
   public Boolean existsByPhone(String phone) {
      return userRepository.existsByPhone(phone);
   }
}
