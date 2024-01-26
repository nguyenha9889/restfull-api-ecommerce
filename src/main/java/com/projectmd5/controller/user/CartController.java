package com.projectmd5.controller.user;


import com.projectmd5.model.dto.cart.CartItemRequest;
import com.projectmd5.model.dto.cart.CartResponse;
import com.projectmd5.model.entity.Cart;
import com.projectmd5.model.entity.User;
import com.projectmd5.security.principal.UserDetailCustom;
import com.projectmd5.service.ICartService;
import com.projectmd5.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.projectmd5.constants.PathConstant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_USER)
public class CartController {
   private final ICartService cartService;
   private final IUserService userService;
   @PostMapping(CART)
   public ResponseEntity<?> addCart(@RequestBody List<CartItemRequest> cartItemRequests){
      UserDetailCustom userDetails = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetails.getId());
      Cart cart = cartService.add(user, cartItemRequests);
      CartResponse response = cartService.mapToCartResponse(cart);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
   }

   @PutMapping(CART_ITEM_ID)
   public ResponseEntity<?> updateCart(@PathVariable Long cartItemId ,@RequestBody int quantity){

      return null;
   }
}
