package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.user.BaseUserResponse;
import com.projectmd5.model.dto.user.UserPageResponse;
import com.projectmd5.model.entity.User;
import com.projectmd5.repository.IUserRepository;
import com.projectmd5.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

   private final IUserRepository userRepository;
   private final ModelMapper modelMapper;


   @Override
   public List<User> findAll() {
      return userRepository.findAll();
   }

   @Override
   public Boolean existsByUsername(String username) {
      return userRepository.existsByUsernameEqualsIgnoreCase(username.trim());
   }

   @Override
   public Boolean existsByEmail(String email) {
      return userRepository.existsByEmailEqualsIgnoreCase(email.trim());
   }

   @Override
   public Boolean existsByPhone(String phone) {
      return userRepository.existsByPhone(phone.trim());
   }

   @Override
   public UserPageResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      Page<User> pages = userRepository.findAll(pageable);
      List<User> data = pages.getContent();

      List<BaseUserResponse> dataDTO = data.stream().map(
            u -> modelMapper.map(u, BaseUserResponse.class)).toList();

      return UserPageResponse.builder()
            .users(dataDTO)
            .pageNo(pageNo)
            .pageSize(pageSize)
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   @Override
   public User findById(Long userId) {
      return userRepository.findByUserId(userId).orElseThrow(
            () -> new ResourceNotFoundException("User not found with id " + userId)
      );
   }

   @Override
   public void save(User user) {
      userRepository.save(user);
   }
}
