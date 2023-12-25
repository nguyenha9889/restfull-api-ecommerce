package com.projectmd5.controller;

import com.projectmd5.model.dto.request.LoginDTO;
import com.projectmd5.model.dto.request.RegisterDTO;
import com.projectmd5.model.dto.response.JwtResponse;
import com.projectmd5.model.entity.User;
import com.projectmd5.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.myservice.com/v1/auth")
public class AuthController {

   private final IUserService userService;

   @PostMapping("/sign-up")
   public ResponseEntity<?> signUp(@Valid @RequestBody RegisterDTO register) {

      User user = userService.create(register);
      userService.save(user);
      return new ResponseEntity<>("Register successfully!", HttpStatus.CREATED);
   }

   @PostMapping("/sign-in")
   public ResponseEntity<?> signIn(@Valid @RequestBody LoginDTO login) {
      JwtResponse jwtResponse = userService.login(login);
      return ResponseEntity.ok(jwtResponse);
   }
}
