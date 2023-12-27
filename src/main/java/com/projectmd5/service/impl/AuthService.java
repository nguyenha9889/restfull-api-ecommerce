package com.projectmd5.service.impl;

import com.projectmd5.exception.AuthException;
import com.projectmd5.model.dto.auth.JwtResponse;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
   private final AuthenticationManager authManager;
   private final JwtTokenBuilder jwtBuilder;
   private final IRoleService roleService;
   private final PasswordEncoder passwordEncoder;
   private final ModelMapper modelMapper;
   private final IUserRepository userRepository;

   @Override
   public JwtResponse login(LoginRequest login) {
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
   public String register(RegisterRequest register) {
      User user = modelMapper.map(register, User.class);
      user.setStatus(true);
      user.setPassword(passwordEncoder.encode(register.getPassword()));
      Role userRole = roleService.findByRoleName(RoleName.ROLE_USER);
      Set<Role> roles = new HashSet<>();
      roles.add(userRole);
      user.setRoles(roles);
      user.setCreatedAt(new Date());

      userRepository.save(user);
      return "Register successfully";
   }
}
