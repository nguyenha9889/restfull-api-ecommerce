package com.projectmd5.service.impl;

import com.projectmd5.exception.AuthException;
import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.request.LoginDTO;
import com.projectmd5.model.dto.request.RegisterDTO;
import com.projectmd5.model.dto.response.JwtResponse;
import com.projectmd5.model.dto.response.UserResponse;
import com.projectmd5.model.entity.Role;
import com.projectmd5.model.entity.RoleName;
import com.projectmd5.model.entity.User;
import com.projectmd5.repository.IUserRepository;
import com.projectmd5.security.jwt.JwtTokenBuilder;
import com.projectmd5.security.principal.UserDetailCustom;
import com.projectmd5.service.IRoleService;
import com.projectmd5.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

   private final IUserRepository userRepository;
   private final IRoleService roleService;
   private final PasswordEncoder passwordEncoder;
   private final ModelMapper modelMapper;
   private final AuthenticationManager authManager;
   private final JwtTokenBuilder jwtBuilder;

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
   public User create(RegisterDTO register) {
      Set<String> strRole = register.getRoles();
      Set<Role> roles = new HashSet<>();
      if (strRole == null || strRole.isEmpty()){
         Role userRole = roleService.findByRoleName(RoleName.ROLE_USER);
         roles.add(userRole);
      } else {
         strRole.forEach(role -> {
            switch (role){
               case "admin":
                  Role adminRole = roleService.findByRoleName(RoleName.ROLE_ADMIN);
                  roles.add(adminRole);
                  break;
               case "pm":
                  Role pmRole = roleService.findByRoleName(RoleName.ROLE_PM);
                  roles.add(pmRole);
                  break;
               case "user":
               default:
                  Role userRole = roleService.findByRoleName(RoleName.ROLE_USER);
                  roles.add(userRole);
            }
         });
      }

      User user = modelMapper.map(register, User.class);
      user.setPassword(passwordEncoder.encode(register.getPassword()));
      user.setRoles(roles);
      return user;
   }

   @Override
   public JwtResponse login(LoginDTO login) {
      Authentication auth = null;
      try {
         auth = authManager.authenticate(
               new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
         );
      }catch (RuntimeException e) {
         throw new AuthException("Username or Password is incorrect") ;
      }
      UserDetailCustom userDetail = (UserDetailCustom) auth.getPrincipal();
      List<String> roles = userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

      String accessToken = jwtBuilder.generateAccessToken(userDetail);
      String refreshToken = jwtBuilder.generateRefreshToken(userDetail);

      return JwtResponse.builder()
            .username(userDetail.getUsername())
            .email(userDetail.getEmail())
            .roles(roles)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
   }

   @Override
   public UserResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      Page<User> pages = userRepository.findAll(pageable);
      List<User> data = pages.getContent();

      List<RegisterDTO> dataDTO = data.stream().map(
            u -> modelMapper.map(u, RegisterDTO.class)).toList();

      return UserResponse.builder()
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
