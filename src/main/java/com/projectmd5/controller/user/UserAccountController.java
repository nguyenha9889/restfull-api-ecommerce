package com.projectmd5.controller.user;

import com.projectmd5.model.dto.user.UserRequest;
import com.projectmd5.model.dto.user.UserResponse;
import com.projectmd5.model.entity.User;
import com.projectmd5.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.myservice.com/v1/account")
public class UserAccountController {
   private final IUserService userService;
   private final ModelMapper modelMapper;
   @GetMapping("/{userId}")
   public ResponseEntity<?> getUserById(@PathVariable Long userId){
      User user = userService.findById(userId);
      UserResponse response = modelMapper.map(user, UserResponse.class);

      return ResponseEntity.ok(response);
   }

   @PutMapping("/{userId}")
   public ResponseEntity<?> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRequest request){
      User user = userService.findById(userId);
      UserResponse response = modelMapper.map(user, UserResponse.class);
      return ResponseEntity.ok(response);
   }
}
