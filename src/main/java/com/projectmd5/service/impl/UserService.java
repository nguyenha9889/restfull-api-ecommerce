package com.projectmd5.service.impl;

import com.projectmd5.exception.BadRequestException;
import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.user.UserPageResponse;
import com.projectmd5.model.dto.user.AccountResponse;
import com.projectmd5.model.dto.user.UserResponse;
import com.projectmd5.model.entity.Role;
import com.projectmd5.model.entity.User;
import com.projectmd5.repository.IUserRepository;
import com.projectmd5.service.IAccountService;
import com.projectmd5.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.projectmd5.constants.MessageConstant.*;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
   private final IAccountService accountService;
   private final IUserRepository userRepository;
   private final RoleService roleService;
   private final ModelMapper modelMapper;

   @Override
   public List<User> findAll() {
      return userRepository.findAll();
   }

   private Pageable createPageable(int pageNo, int pageSize, String sortBy, String sortDir){
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
      return PageRequest.of(pageNo, pageSize, sort);
   }

   @Override
   public UserPageResponse getPageUser(int pageNo, int pageSize, String sortBy, String sortDir) {
      Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);
      Page<User> pages = userRepository.findAll(pageable);
      List<User> users = pages.getContent();

      List<AccountResponse> data = users.stream().map(
            accountService::mapUserToAccountResponse).toList();

      return UserPageResponse.builder()
            .data(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   @Override
   public User findById(Long userId) {
      return userRepository.findByUserId(userId).orElseThrow(
            () -> new ResourceNotFoundException(USER_NOT_FOUND)
      );
   }

   // search all user by username or email or phone
   @Override
   public UserPageResponse searchWithPaging(String query, int pageNo, int pageSize, String sortBy, String sortDir){
      Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);
      Page<User> pages;
      if (query.trim().matches("\\d+")){
         pages = userRepository.findAllByPhoneContaining(query, pageable);
      } else {
         pages = userRepository.findAllByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, pageable);
      }
      List<User> users = pages.getContent();

      List<AccountResponse> data = users.stream().map(
            accountService::mapUserToAccountResponse).toList();

      return UserPageResponse.builder()
            .data(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   @Override
   public void save(User user) {
      userRepository.save(user);
   }

   @Override
   public UserResponse lockOrEnableUser(Long userId, Boolean status){
      User user = findById(userId);
      user.setStatus(status);
      user.setUpdatedAt(new Date());
      User newUser = userRepository.save(user);
      return mapUserToUserResponse(newUser);
   }

   @Override
   public UserResponse addRoleUser(Long userId, Long roleId){
      User user = findById(userId);
      if (!user.isStatus()){
         throw new BadRequestException(USER_IS_BLOCKED);
      }
      Role role = roleService.findById(roleId);
      if (user.getRoles().stream().noneMatch(r -> Objects.equals(r.getId(), roleId))) {
         throw new BadRequestException(ROLE_EXISTED);
      }
      user.getRoles().add(role);
      user.setUpdatedAt(new Date());
      User newUser = userRepository.save(user);
      return mapUserToUserResponse(newUser);
   }

   @Override
   public void deleteRoleUser(Long userId, Long roleId){
      User user = findById(userId);
      Role role = roleService.findById(roleId);
      if (user.getRoles().stream().noneMatch(r -> Objects.equals(r.getId(), roleId))) {
         throw new BadRequestException(ROLE_NOT_FOUND);
      }
      user.getRoles().remove(role);
      user.setUpdatedAt(new Date());
      userRepository.save(user);
   }

   private UserResponse mapUserToUserResponse(User user){
      UserResponse response = modelMapper.map(user, UserResponse.class);
      Set<Role> roles = user.getRoles();
      List<String> roleList = roles.stream().map(role -> role.getRoleName().name()).toList();
      response.setRoles(roleList);
      return response;
   }
}
