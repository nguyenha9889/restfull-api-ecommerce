package com.projectmd5.repository;

import com.projectmd5.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
   Optional<User> findByUsername(String username);
   Optional<User> findByUserId(Long userId);
   Boolean existsByUsernameEqualsIgnoreCase(String username);
   Boolean existsByEmailEqualsIgnoreCase(String email);
   Boolean existsByPhone(String phone);
   List<User> findByFullNameContainingIgnoreCase(String name);
}
