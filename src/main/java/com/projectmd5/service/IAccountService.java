package com.projectmd5.service;

import com.projectmd5.model.dto.user.*;
import com.projectmd5.model.entity.Address;
import com.projectmd5.model.entity.User;

import java.util.List;

public interface IAccountService extends IGenericService<User, Long>{
   boolean existsByEmail(Long userId, String email);
   boolean existsByPhone(Long userId, String phone);
   BaseUserResponse update(Long userId, AccountRequest accountRequest);
   String changePassword(PasswordRequest request);
   Address addNewAddress(AddressRequest request);
   Address findAddressById(Long addressId);
   Address update(Long addressId, AddressRequest request);
   List<Address> getAllAddresses(Long userId);
}
