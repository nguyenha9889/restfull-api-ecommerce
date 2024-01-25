package com.projectmd5.controller.user;


import com.projectmd5.model.dto.cart.CartRequest;
import com.projectmd5.model.entity.Cart;
import com.projectmd5.model.entity.User;
import com.projectmd5.security.principal.UserDetailCustom;
import com.projectmd5.service.ICartService;
import com.projectmd5.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.projectmd5.constants.PathConstant.API_V1_USER;
import static com.projectmd5.constants.PathConstant.CART;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_USER)
public class CartController {
   private final ICartService cartService;
   private final IUserService userService;
   @PostMapping(CART)
   public ResponseEntity<?> addCart(@RequestBody CartRequest cartRequest){
      UserDetailCustom userDetails = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetails.getId());
      Cart cart = cartService.create(user, cartRequest);
      return null;
   }
}
