package com.projectmd5.repository;

import com.projectmd5.model.entity.Role;
import com.projectmd5.model.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
   Optional<Role> findByRoleName(RoleName roleName);
}
