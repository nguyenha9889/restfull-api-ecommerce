package com.projectmd5.controller.admin;

import com.projectmd5.model.dto.user.BaseUserResponse;
import com.projectmd5.model.dto.user.UserPageResponse;
import com.projectmd5.model.entity.Role;
import com.projectmd5.model.entity.User;
import com.projectmd5.service.IRoleService;
import com.projectmd5.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api.myservice.com/v1/admin")
public class AccountController {

   private final ModelMapper modelMapper;
   private final IUserService userService;
   private final IRoleService roleService;
 /*
/api.myservice.com/v1/admin/users/{userId}
/api.myservice.com/v1/admin/roles
/api.myservice.com/v1/admin/users/search
*/

   @GetMapping("/users")
   public ResponseEntity<?> getList(
         @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(value = "sortBy", defaultValue = "userId", required = false) String sortBy,
         @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){

      Pageable pageable = userService.getPageable(pageNo, pageSize, sortBy, sortDir);
      UserPageResponse response = userService.getAll(pageable);
      return ResponseEntity.ok(response);
   }

   @PutMapping("/users/{userId}")
   public ResponseEntity<?> lockOrEnableAccount(@PathVariable Long userId, @RequestBody Boolean status){
      User user = userService.findById(userId);
      user.setStatus(status);
      user.setCreatedAt(new Date());
      userService.save(user);
      if (!status){
         return ResponseEntity.ok("This user has been block");
      } else {
         return ResponseEntity.ok("This user has been active");
      }
   }

   @PostMapping("/{userId}/role")
   public ResponseEntity<?> addRoleToUser(@PathVariable Long userId, @RequestBody Long roleId){
      User user = userService.findById(userId);
      if (!user.isStatus()){
         return new ResponseEntity<>(
               "Can not add role because this user has been block",
               HttpStatus.BAD_REQUEST);
      }
      Role role = roleService.findById(roleId);
      if (user.getRoles().stream().anyMatch(r -> Objects.equals(r.getId(), roleId))) {
         return new ResponseEntity<>(
               "User has been granted this role " + role.getRoleName(),
               HttpStatus.BAD_REQUEST);
      }
      user.getRoles().add(role);
      user.setUpdatedAt(new Date());
      userService.save(user);
      BaseUserResponse response = modelMapper.map(user, BaseUserResponse.class);
      return ResponseEntity.ok().body(response);
   }

   @DeleteMapping("/{userId}/role")
   public ResponseEntity<?> deleteRoleOfUser(@PathVariable Long userId, @RequestBody Long roleId){
      User user = userService.findById(userId);
      Role role = roleService.findById(roleId);
      if (user.getRoles().stream().noneMatch(r -> Objects.equals(r.getId(), roleId))) {
         return new ResponseEntity<>(
               "The user does not have this role " + role.getRoleName(),
               HttpStatus.BAD_REQUEST);
      }

      user.getRoles().remove(role);
      user.setUpdatedAt(new Date());
      userService.save(user);
      return ResponseEntity.ok().body("Delete user's role successfully");
   }

   @GetMapping("/users/search")
   public ResponseEntity<?> search(@RequestParam(name = "fullName", required = false) String name){

      Pageable pageable = userService.getPageable(0, 5, "userId", "asc");
      UserPageResponse response = userService.findByNameWithPaging(name, pageable);
      return ResponseEntity.ok(response);
   }
}
