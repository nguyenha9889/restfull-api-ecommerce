package com.projectmd5.controller.user;

import com.projectmd5.model.dto.user.*;
import com.projectmd5.model.entity.User;
import com.projectmd5.security.principal.UserDetailCustom;
import com.projectmd5.service.IAccountService;
import com.projectmd5.service.IUserService;
import com.projectmd5.validation.AccountValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.projectmd5.constants.PathConstant.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1)
public class AccountController {
   private final IAccountService accountService;
   private final IUserService userService;
   private final AccountValidator validator;

   @GetMapping(USER)
   public ResponseEntity<?> getAccount() {
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      AccountResponse response = accountService.getAccount(user);
      return ResponseEntity.ok(response);
   }

   @PutMapping(USER)
   public ResponseEntity<?> updateAccount(@ModelAttribute AccountRequest request,
                                          BindingResult bindingResult) {

      validator.validate(request, bindingResult);

      if (bindingResult.hasErrors()) {
         Map<String, String> errors = new HashMap<>();
         bindingResult.getFieldErrors().forEach(err ->
               errors.put(err.getField(), err.getCode()));
         return ResponseEntity.badRequest().body(errors);
      }
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      AccountResponse response = accountService.updateAccount(user, request);
      return ResponseEntity.ok(response);
   }

   @PutMapping(CHANGE_PASSWORD)
   public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordRequest request) {
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      String response = accountService.changePassword(user, request);
      return ResponseEntity.ok(response);
   }

   @GetMapping(USER_ADDRESS)
   public ResponseEntity<?> getAllAddresses() {
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      return ResponseEntity.ok(accountService.getAllAddressResponse(user));
   }

   @PostMapping(USER_ADDRESS)
   public ResponseEntity<?> addAddress(@Valid @RequestBody AddressRequest request) {
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      AddressResponse response = accountService.addNewAddress(user, request);
      return ResponseEntity.ok(response);
   }

   @GetMapping(USER_ADDRESS_ID)
   public ResponseEntity<?> getAddressById(@PathVariable Long addressId) {
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      AddressResponse response = accountService.findAddressByIdAndUser(user, addressId);
      return ResponseEntity.ok(response);
   }

   @PutMapping(USER_ADDRESS_ID)
   public ResponseEntity<?> updateAddress(@PathVariable Long addressId,
                                          @Valid @RequestBody AddressRequest request) {
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      AddressResponse response = accountService.updateAddress(user, addressId, request);
      return ResponseEntity.ok(response);
   }
}
