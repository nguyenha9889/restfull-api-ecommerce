package com.projectmd5.controller.admin;

import static com.projectmd5.constants.PathConstant.*;
import static com.projectmd5.constants.MessageConstant.*;

import com.projectmd5.model.dto.user.BaseUserResponse;
import com.projectmd5.model.dto.user.UserPageResponse;
import com.projectmd5.model.entity.Role;
import com.projectmd5.model.entity.User;
import com.projectmd5.service.IRoleService;
import com.projectmd5.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;


@RequiredArgsConstructor
@RestController
@RequestMapping(API_V1_ADMIN)
public class AllUserController {

   private final ModelMapper modelMapper;
   private final IUserService userService;
   private final IRoleService roleService;

   @GetMapping(USER)
   public ResponseEntity<?> getList(
         @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(value = "sortBy", defaultValue = "userId", required = false) String sortBy,
         @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){

      Pageable pageable = userService.getPageable(pageNo, pageSize, sortBy, sortDir);
      UserPageResponse response = userService.getAll(pageable);
      return ResponseEntity.ok(response);
   }

   @PutMapping(USER_BY_ID)
   public ResponseEntity<?> lockOrEnableAccount(@PathVariable Long userId, @RequestBody Boolean status){
      User user = userService.findById(userId);
      user.setStatus(status);
      user.setCreatedAt(new Date());
      userService.save(user);
      if (!status){
         return ResponseEntity.ok(USER_BLOCK);
      } else {
         return ResponseEntity.ok(USER_ACTIVE);
      }
   }

   @PostMapping(ROLES_TO_USER)
   public ResponseEntity<?> addRoleToUser(@PathVariable Long userId, @RequestBody Long roleId){
      User user = userService.findById(userId);
      if (!user.isStatus()){
         return ResponseEntity.badRequest().body(USER_BLOCK);
      }
      Role role = roleService.findById(roleId);
      if (user.getRoles().stream().anyMatch(r -> Objects.equals(r.getId(), roleId))) {
         return ResponseEntity.badRequest()
               .body(ROLE_EXISTED);
      }
      user.getRoles().add(role);
      user.setUpdatedAt(new Date());
      userService.save(user);
      BaseUserResponse response = modelMapper.map(user, BaseUserResponse.class);
      return ResponseEntity.ok().body(response);
   }

   @DeleteMapping(ROLES_TO_USER)
   public ResponseEntity<?> deleteRoleOfUser(@PathVariable Long userId, @RequestBody Long roleId){
      User user = userService.findById(userId);
      Role role = roleService.findById(roleId);
      if (user.getRoles().stream().noneMatch(r -> Objects.equals(r.getId(), roleId))) {
         return ResponseEntity.badRequest()
               .body(ROLE_NOT_FOUND);
      }

      user.getRoles().remove(role);
      user.setUpdatedAt(new Date());
      userService.save(user);
      return ResponseEntity.ok().body(DELETE_SUCCESS);
   }

   @GetMapping(SEARCH_BY_USER)
   public ResponseEntity<?> search(@RequestParam(name = "fullName", required = false) String name){

      Pageable pageable = userService.getPageable(0, 5, "userId", "asc");
      UserPageResponse response = userService.findByNameWithPaging(name, pageable);
      return ResponseEntity.ok(response);
   }
}
