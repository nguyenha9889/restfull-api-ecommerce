package com.projectmd5.security.principal;

import com.projectmd5.model.entity.User;
import com.projectmd5.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailCustomService implements UserDetailsService {
   private final IUserRepository userRepository;
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("Username not found"));

      List<GrantedAuthority> authorities = user.getRoles().stream().map(
            role -> new SimpleGrantedAuthority(role.getRoleName().name())
      ).collect(Collectors.toList());

      return UserDetailCustom.builder()
            .id(user.getUserId())
            .username(username)
            .email(user.getEmail())
            .password(user.getPassword())
            .authorities(authorities)
            .build();
   }
}
