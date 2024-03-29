package com.projectmd5.repository;

import com.projectmd5.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
   Optional<User> findByUsername(String username);
   Optional<User> findByUserId(Long userId);
   Boolean existsByUsernameEqualsIgnoreCase(String username);
   Boolean existsByEmailEqualsIgnoreCase(String email);
   Boolean existsByPhone(String phone);
   Page<User> findAllByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email, Pageable pageable);
   Page<User> findAllByPhoneContaining(String phone, Pageable pageable);
}
