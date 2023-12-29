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

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
   private final IUserRepository userRepository;
   private final IAddressRepository addressRepository;
   private final ModelMapper modelMapper;
   private final FilesStorageService storageService;
   private final PasswordEncoder passwordEncoder;
   @Override
   public User findById(Long id) {
      return userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User not found with id " + id)
      );
   }

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
   public BaseUserResponse update(Long userId, AccountRequest accountRequest) {
      User user = findById(userId);
      User userUpdate = modelMapper.map(accountRequest, User.class);

      userUpdate.setUserId(userId);
      userUpdate.setPassword(user.getPassword());
      userUpdate.setStatus(user.isStatus());

      if (accountRequest.getImage() != null && accountRequest.getImage().getSize() > 0){
         userUpdate.setAvatar(storageService.uploadFile(accountRequest.getImage()));
      } else {
         userUpdate.setAvatar(user.getAvatar());
      }

      userUpdate.setCreatedAt(user.getCreatedAt());
      userUpdate.setUpdatedAt(new Date());

      userRepository.save(userUpdate);
      return modelMapper.map(userUpdate, BaseUserResponse.class);
   }

   @Override
   public String changePassword(PasswordRequest request) {
      User user = findById(request.getUserId());
      boolean isPasswordMatching = passwordEncoder.matches(request.getOldPassword(), user.getPassword());
      if (isPasswordMatching) {
         user.setPassword(passwordEncoder.encode(request.getPassword()));
      } else {
         throw new BadRequestException("Mật khẩu không chính xác");
      }
      user.setUpdatedAt(new Date());
      userRepository.save(user);
      return "Cập nhật mật khẩu thành công";
   }

   @Override
   public Address addNewAddress(AddressRequest request) {
      User user = findById(request.getUserId());
      Address address = modelMapper.map(request, Address.class);
      if (request.getPhone().isBlank()){
         address.setPhone(user.getPhone());
      }
      if (request.getReceiveName().isBlank()){
         address.setReceiveName(user.getFullName());
      }
      addressRepository.save(address);
      return address;
   }

   @Override
   public Address findAddressById(Long addressId) {
      return addressRepository.findById(addressId).orElseThrow(
            () -> new ResourceNotFoundException("Không tìm thấy địa chỉ với id " + addressId)
      );
   }

   @Override
   public Address update(Long addressId, AddressRequest request) {
      Address address = findAddressById(addressId);
      Address updateAddress = modelMapper.map(request, Address.class);
      if (request.getPhone().isBlank()){
         address.setPhone(address.getPhone());
      }
      if (request.getReceiveName().isBlank()){
         address.setReceiveName(address.getReceiveName());
      }
      addressRepository.save(address);
      return updateAddress;
   }

   @Override
   public List<Address> getAllAddresses(Long userId) {
      User user = findById(userId);
      return user.getAddresses();
   }


}
