package com.projectmd5.controller;

import com.projectmd5.model.dto.MessageResponse;
import com.projectmd5.model.dto.auth.AuthResponse;
import com.projectmd5.model.dto.auth.LoginRequest;
import com.projectmd5.model.dto.auth.RegisterRequest;
import com.projectmd5.security.jwt.JwtTokenBuilder;
import com.projectmd5.security.principal.UserDetailCustom;
import com.projectmd5.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.projectmd5.constants.PathConstant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_AUTH)
public class AuthController {
   private final IAuthService authService;
   private final JwtTokenBuilder jwtBuilder;

   @PostMapping(REGISTER)
   public ResponseEntity<?> signUp(@Valid @RequestBody RegisterRequest register) {
      MessageResponse response = new MessageResponse(authService.register(register));
      return new ResponseEntity<>(response, HttpStatus.CREATED);
   }

   @PostMapping(LOGIN)
   public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest login) {
      AuthResponse authResponse = authService.login(login);
      return ResponseEntity.ok().body(authResponse);
   }

   @GetMapping(LOGOUT)
   public ResponseEntity<?> signOut() {
      SecurityContextHolder.getContext().setAuthentication(null);
      return ResponseEntity.ok().body(new MessageResponse("Logout successfully"));
   }

   @PostMapping(REFRESH_TOKEN)
   public ResponseEntity<?> getNewToken() {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      String newAccessToken = jwtBuilder.generateAccessToken((UserDetailCustom) principal);
      AuthResponse authResponse = new AuthResponse(newAccessToken);
      return ResponseEntity.ok().body(authResponse);
   }
}
