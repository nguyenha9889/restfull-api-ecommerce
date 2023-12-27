package com.projectmd5.repository;

import com.projectmd5.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
   @Query("select u from User u where lower(u.fullName) like lower(concat('%',?1,'%'))")
   Page<User> findByNameWithPagination(String name, Pageable pageable);
}
