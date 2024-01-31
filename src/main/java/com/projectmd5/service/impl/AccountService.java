package com.projectmd5.service.impl;

import com.projectmd5.exception.BadRequestException;
import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.user.*;
import com.projectmd5.model.entity.Address;
import com.projectmd5.model.entity.User;
import com.projectmd5.repository.IAddressRepository;
import com.projectmd5.repository.IUserRepository;
import com.projectmd5.service.FilesStorageService;
import com.projectmd5.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.projectmd5.constants.MessageConstant.*;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
   private final IUserRepository userRepository;
   private final IAddressRepository addressRepository;
   private final ModelMapper modelMapper;
   private final FilesStorageService storageService;
   private final PasswordEncoder passwordEncoder;

   @Override
   public boolean existsByEmail(Long userId, String email) {
      for (User u: userRepository.findAll()) {
         if (u.getEmail().equalsIgnoreCase(email.toLowerCase().trim())) {
            return !Objects.equals(u.getUserId(), userId);
         }
      }
      return false;
   }

   @Override
   public boolean existsByPhone(Long userId, String phone) {
      for (User u: userRepository.findAll()) {
         if (u.getEmail().equalsIgnoreCase(phone.trim())) {
            return !Objects.equals(u.getUserId(), userId);
         }
      }
      return false;
   }

   @Override
   public AccountResponse getAccount(User user) {
      return mapUserToAccountResponse(user);
   }
   @Override
   public AccountResponse updateAccount(User user, AccountRequest accountRequest) {
      User userUpdate = modelMapper.map(user, User.class);
      userUpdate.setFullName(accountRequest.getFullName());
      userUpdate.setEmail(accountRequest.getEmail());
      userUpdate.setPhone(accountRequest.getPhone());

      if (accountRequest.getImage() != null && accountRequest.getImage().getSize() > 0){
         userUpdate.setAvatar(storageService.uploadFile(accountRequest.getImage()));
      } else {
         userUpdate.setAvatar(user.getAvatar());
      }
      userUpdate.setUpdatedAt(new Date());
      userRepository.save(userUpdate);
      return mapUserToAccountResponse(userUpdate);
   }

   @Override
   public AccountResponse mapUserToAccountResponse(User user){
      return AccountResponse.builder()
            .userId(user.getUserId())
            .username(user.getUsername())
            .fullName(user.getFullName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .avatar(user.getAvatar())
            .status(user.isStatus())
            .build();
   }

   @Override
   public String changePassword(User user, PasswordRequest request) {
      boolean isPasswordMatching = passwordEncoder.matches(request.getOldPassword(), user.getPassword());
      if (isPasswordMatching) {
         user.setPassword(passwordEncoder.encode(request.getPassword()));
      } else {
         throw new BadRequestException(PASSWORD_INVALID);
      }
      user.setUpdatedAt(new Date());
      userRepository.save(user);
      return "Cập nhật mật khẩu thành công";
   }

   @Override
   public AddressResponse addNewAddress(User user, AddressRequest request) {
      Address address = modelMapper.map(request, Address.class);
      Address defaultAddress = findDefaultAddress(user);
      if (defaultAddress == null) {
         address.setDefaultAddress(true);
      } else if (request.isDefaultAddress()){
         defaultAddress.setDefaultAddress(false);
         addressRepository.save(defaultAddress);
      }
      address.setUser(user);
      Address newAddress = addressRepository.save(address);
      return modelMapper.map(newAddress, AddressResponse.class);
   }

   @Override
   public AddressResponse findAddressByIdAndUser(User user, Long addressId) {
      Address address = addressRepository.findByAddressIdAndUser(addressId , user).orElseThrow(
            () -> new ResourceNotFoundException(ADDRESS_NOT_FOUND)
      );
      return modelMapper.map(address, AddressResponse.class);
   }

   @Override
   public AddressResponse updateAddress(User user, Long addressId, AddressRequest request) {
      Address address = addressRepository.findByAddressIdAndUser(addressId , user).orElseThrow(
            () -> new ResourceNotFoundException(ADDRESS_NOT_FOUND)
      );
      address.setReceiveName(request.getReceiveName());
      address.setPhone(request.getPhone());
      address.setFullAddress(request.getFullAddress());
      if (!address.isDefaultAddress() && request.isDefaultAddress()){
         Address defaultAddress = findDefaultAddress(address.getUser());
         defaultAddress.setDefaultAddress(false);
         addressRepository.save(defaultAddress);
         address.setDefaultAddress(true);
      }

      Address updateAddress = addressRepository.save(address);
      return modelMapper.map(updateAddress, AddressResponse.class);
   }

   @Override
   public List<AddressResponse> getAllAddressResponse(User user) {
      List<Address> addresses = addressRepository.findAllByUser(user);
      if (addresses.isEmpty()){
         throw new ResourceNotFoundException(ADDRESSES_EMPTY);
      }
      return addresses.stream()
            .map(address -> modelMapper.map(address, AddressResponse.class))
            .toList();
   }

   @Override
   public Address findDefaultAddress(User user) {
      List<Address> addresses = addressRepository.findAllByUser(user);
      return addresses.stream()
            .filter(Address::isDefaultAddress)
            .findFirst()
            .orElse(null);
   }
}
