package com.projectmd5.controller.admin;

import com.projectmd5.model.entity.Role;
import com.projectmd5.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import static com.projectmd5.constants.PathConstant.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_V1_ADMIN)
public class RoleController {
   private final IRoleService roleService;
   @GetMapping(ROLES)
   public ResponseEntity<?> getList(){
      List<Role> roles = roleService.findAll();
      return ResponseEntity.ok(roles);
    }
}
