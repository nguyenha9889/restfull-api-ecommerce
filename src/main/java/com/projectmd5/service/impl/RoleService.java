package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.entity.Role;
import com.projectmd5.model.entity.RoleName;
import com.projectmd5.repository.IRoleRepository;
import com.projectmd5.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.projectmd5.constants.MessageConstant.ROLE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
   private final IRoleRepository roleRepository;

   @Override
   public Role findByRoleName(RoleName roleName) {
      return roleRepository.findByRoleName(roleName).orElseThrow(
            () -> new ResourceNotFoundException(ROLE_NOT_FOUND)
      );
   }

   @Override
   public List<Role> findAll() {
      return roleRepository.findAll();
   }

   @Override
   public Role findById(Long id) {
      return roleRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(ROLE_NOT_FOUND)
      );
   }
}
