package com.projectmd5.service.impl;

import com.projectmd5.exception.JWTException;
import com.projectmd5.model.dto.auth.AuthResponse;
import com.projectmd5.model.dto.auth.LoginRequest;
import com.projectmd5.model.dto.auth.RegisterRequest;
import com.projectmd5.model.entity.Role;
import com.projectmd5.model.entity.RoleName;
import com.projectmd5.model.entity.User;
import com.projectmd5.repository.IUserRepository;
import com.projectmd5.security.jwt.JwtTokenBuilder;
import com.projectmd5.security.principal.UserDetailCustom;
import com.projectmd5.service.IAuthService;
import com.projectmd5.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.projectmd5.constants.MessageConstant.*;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
   private final AuthenticationManager authManager;
   private final IRoleService roleService;
   private final PasswordEncoder passwordEncoder;
   private final ModelMapper modelMapper;
   private final IUserRepository userRepository;
   private final JwtTokenBuilder jwtBuilder;

   @Override
   public boolean isUsernameExisted(String username) {
      return userRepository.existsByUsernameEqualsIgnoreCase(username.trim());
   }

   @Override
   public boolean isEmailExisted(String email) {
      return userRepository.existsByEmailEqualsIgnoreCase(email.trim());
   }

   @Override
   public boolean isPhoneExisted(String phone) {
      return userRepository.existsByPhone(phone.trim());
   }

   @Override
   public AuthResponse login(LoginRequest login) {
      Authentication auth;
      try {
         auth = authManager.authenticate(
               new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
         );
      }catch (RuntimeException e) {
         throw new AuthenticationException(CREDENTIALS_INVALID){
            @Override
            public String getMessage() {
               return super.getMessage();
            }
         };
      }
      UserDetailCustom userDetail = (UserDetailCustom) auth.getPrincipal();

      // check account locked or enable
      if (!userDetail.isAccountNonLocked()){
         throw new JWTException(USER_IS_BLOCKED);
      }
      List<String> roles = userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

      String accessToken = jwtBuilder.generateAccessToken(userDetail);
      String refreshToken = jwtBuilder.generateRefreshToken(userDetail);
      Long expiredAt = jwtBuilder.getExpiredFromToken(refreshToken);
      return AuthResponse.builder()
            .userId(userDetail.getId())
            .fullName(userDetail.getFullName())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .expiredAt(expiredAt)
            .roles(roles)
            .build();
   }

   @Override
   public String register(RegisterRequest register) {
      User user = modelMapper.map(register, User.class);
      user.setStatus(true);
      user.setPassword(passwordEncoder.encode(register.getPassword()));
      Role userRole = roleService.findByRoleName(RoleName.ROLE_USER);
      Set<Role> roles = new HashSet<>();
      roles.add(userRole);
      user.setRoles(roles);
      user.setCreatedAt(new Date());
      user.setUpdatedAt(new Date());
      userRepository.save(user);
      return REGISTER_SUCCESS;
   }


}
