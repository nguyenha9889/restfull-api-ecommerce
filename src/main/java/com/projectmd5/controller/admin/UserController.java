package com.projectmd5.controller.admin;

import com.projectmd5.model.dto.user.UserPageResponse;
import com.projectmd5.model.dto.user.UserResponse;
import com.projectmd5.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.projectmd5.constants.MessageConstant.DELETE_SUCCESS;
import static com.projectmd5.constants.PathConstant.*;


@RequiredArgsConstructor
@RestController
@RequestMapping(API_V1_ADMIN)
public class UserController {
   private final IUserService userService;

   @GetMapping(USER)
   public ResponseEntity<?> getList(
         @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(name = "sortBy", defaultValue = "updatedAt", required = false) String sortBy,
         @RequestParam(name = "sortDir", defaultValue = "desc", required = false) String sortDir,
         @RequestParam(name = "search", defaultValue = "", required = false) String query){

      UserPageResponse response = null;
      if (query.isBlank()){
         response = userService.getPageUser(pageNo, pageSize, sortBy, sortDir);
      } else {
         response = userService.searchWithPaging(query, pageNo, pageSize, sortBy, sortDir);
      }
      return ResponseEntity.ok(response);
   }

   @PutMapping(USER_BY_ID)
   public ResponseEntity<?> lockOrEnableAccount(@PathVariable Long userId, @RequestBody Boolean status){
      UserResponse response = userService.lockOrEnableUser(userId, status);
      return ResponseEntity.ok(response);
   }

   @PostMapping(ROLES_TO_USER)
   public ResponseEntity<?> addRoleToUser(@PathVariable Long userId, @RequestBody Long roleId){
      UserResponse response = userService.addRoleUser(userId, roleId);
      return ResponseEntity.ok(response);
   }

   @DeleteMapping(ROLES_TO_USER)
   public ResponseEntity<?> deleteRoleOfUser(@PathVariable Long userId, @RequestBody Long roleId){
      userService.deleteRoleUser(userId, roleId);
      return ResponseEntity.ok().body(DELETE_SUCCESS);
   }
}
