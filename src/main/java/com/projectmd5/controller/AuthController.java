package com.projectmd5.controller;

import com.projectmd5.model.dto.auth.JwtResponse;
import com.projectmd5.model.dto.auth.LoginRequest;
import com.projectmd5.model.dto.auth.RegisterRequest;
import com.projectmd5.service.IAuthService;
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
   private final IAuthService authService;

   @PostMapping("/sign-up")
   public ResponseEntity<?> signUp(@Valid @RequestBody RegisterRequest register) {

      return new ResponseEntity<>(authService.register(register), HttpStatus.CREATED);
   }

   @PostMapping("/sign-in")
   public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest login) {
      JwtResponse jwtResponse = authService.login(login);
      return ResponseEntity.ok(jwtResponse);
   }
}
