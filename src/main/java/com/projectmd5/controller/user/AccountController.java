package com.projectmd5.controller.user;

import com.projectmd5.model.dto.user.*;
import com.projectmd5.model.entity.Address;
import com.projectmd5.model.entity.User;
import com.projectmd5.service.IAccountService;
import com.projectmd5.validation.AccountValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.myservice.com/v1")
public class AccountController {
   private final IAccountService accountService;
   private final AccountValidator validator;
   private final ModelMapper modelMapper;
   @GetMapping("/user/{userId}")
   public ResponseEntity<?> getUserById(@PathVariable Long userId){
      User user = accountService.findById(userId);
      UserResponse response = modelMapper.map(user, UserResponse.class);

      return ResponseEntity.ok(response);
   }

   @PutMapping("/user/{userId}")
   public ResponseEntity<?> updateUser(@PathVariable Long userId,
                                       @RequestBody AccountRequest request,
                                       BindingResult bindingResult){
      accountService.findById(userId);
      validator.validate(request, bindingResult);

      if (bindingResult.hasErrors()){
         Map<String, String> errors = new HashMap<>();
         bindingResult.getFieldErrors().forEach(err ->
               errors.put(err.getField(), err.getCode()));
         return ResponseEntity.badRequest().body(errors);
      }

      BaseUserResponse response = accountService.update(userId, request);
      return ResponseEntity.ok(response);
   }

   @PutMapping("/user/change-password")
   public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordRequest request){

      accountService.findById(request.getUserId());
      String response = accountService.changePassword(request);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/user/address/{userId}")
   public ResponseEntity<?> getAllAddresses(@PathVariable Long userId){
      return ResponseEntity.ok(accountService.getAllAddresses(userId));
   }

   @PostMapping("/user/address")
   public ResponseEntity<?> addAddress(@Valid @RequestBody AddressRequest request){

      accountService.findById(request.getUserId());
      Address response = accountService.addNewAddress(request);
      return ResponseEntity.ok(response);
   }

   @GetMapping("/user/address/{addressId}")
   public ResponseEntity<?> getAddressById(@PathVariable Long addressId){

      return ResponseEntity.ok(accountService.findAddressById(addressId));
   }

   @PutMapping("/user/address/{addressId}")
   public ResponseEntity<?> updateAddress(@PathVariable Long addressId,
         @Valid @RequestBody AddressRequest request){

      accountService.findAddressById(addressId);
      Address response = accountService.update(addressId, request);
      return ResponseEntity.ok(response);
   }
}
