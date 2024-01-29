package com.projectmd5.service;

import com.projectmd5.model.dto.user.*;
import com.projectmd5.model.entity.Address;
import com.projectmd5.model.entity.User;

import java.util.List;

public interface IAccountService extends IGenericService<User, Long>{
   boolean existsByEmail(Long userId, String email);
   boolean existsByPhone(Long userId, String phone);
   AccountResponse getAccount(User user);
   AccountResponse updateAccount(User user, AccountRequest accountRequest);
   AccountResponse mapUserToAccountResponse(User user);
   String changePassword(User user, PasswordRequest request);
   AddressResponse addNewAddress(User user, AddressRequest request);
   AddressResponse findAddressById(Long addressId);
   List<Address> findDefaultAddress(User user);
   AddressResponse updateAddress(Long addressId, AddressRequest request);
   List<AddressResponse> getAllAddressResponse(User user);
}
