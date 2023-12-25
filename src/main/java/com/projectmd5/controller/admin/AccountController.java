package com.projectmd5.controller.admin;

import com.projectmd5.model.dto.response.UserResponse;
import com.projectmd5.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api.myservice.com/v1/admin")
public class AccountController {

   private final ModelMapper modelMapper;
   private final IUserService userService;
 /*

/api.myservice.com/v1/admin/users/{userId}/role
/api.myservice.com/v1/admin/users/{userId}
/api.myservice.com/v1/admin/roles
/api.myservice.com/v1/admin/users/search
*/

   @GetMapping("/users")
   public ResponseEntity<?> getList(
         @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam(value = "sortBy", defaultValue = "productId", required = false) String sortBy,
         @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){

      UserResponse response = userService.getAll(pageNo, pageSize, sortBy, sortDir);
      return ResponseEntity.ok(response);
   }

   @PostMapping("/{userId}/role")
   public ResponseEntity<?> addRole(@PathVariable Long userId){
      return ResponseEntity.ok().build();
   }
}
