package com.projectmd5.service;

import com.projectmd5.model.entity.User;

public interface IUserService extends IGenericService<User, Long> {
   Boolean existsByUsername(String username);
   Boolean existsByEmail(String email);
   Boolean existsByPhone(String phone);
}
