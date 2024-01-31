package com.projectmd5.controller.user;


import com.projectmd5.model.dto.cart.CartRequest;
import com.projectmd5.model.dto.cart.CartResponse;
import com.projectmd5.model.dto.cart.CartUpdateRequest;
import com.projectmd5.model.dto.order.OrderDetailRequest;
import com.projectmd5.model.dto.order.OrderResponse;
import com.projectmd5.model.entity.Cart;
import com.projectmd5.model.entity.Orders;
import com.projectmd5.model.entity.User;
import com.projectmd5.security.principal.UserDetailCustom;
import com.projectmd5.service.ICartService;
import com.projectmd5.service.IOrderService;
import com.projectmd5.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.projectmd5.constants.MessageConstant.DELETE_SUCCESS;
import static com.projectmd5.constants.PathConstant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_USER)
public class CartController {
   private final ICartService cartService;
   private final IUserService userService;
   private final IOrderService orderService;
   @GetMapping(CART)
   public ResponseEntity<?> getCarts(){
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      List<CartResponse> responses = cartService.findAllByUser(user);
      return ResponseEntity.ok(responses);
   }

   @GetMapping(CART_ID)
   public ResponseEntity<?> getCartById(@PathVariable Long cartId){
      Cart cart = cartService.findById(cartId);
      CartResponse response = cartService.mapToCartResponse(cart);
     return ResponseEntity.ok(response);
   }

   @PostMapping(CART)
   public ResponseEntity<?> addCart(@Valid @RequestBody CartRequest cartRequest){
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      Cart cart = cartService.add(user, cartRequest);
      CartResponse response = cartService.mapToCartResponse(cart);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
   }

   @PostMapping(CART_BUY_NOW)
   public ResponseEntity<?> cartBuyNow(@Valid @RequestBody CartRequest cartRequest){
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      Cart cart = cartService.add(user, cartRequest);
      OrderDetailRequest request = new OrderDetailRequest(Collections.singletonList(cart.getCartId()));
      Orders orders = orderService.createOrder(user, request);
      OrderResponse response = orderService.mapToOrderResponse(orders);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
   }

   @PutMapping(CART_ID)
   public ResponseEntity<?> updateCart(@PathVariable Long cartId , @Valid @RequestBody CartUpdateRequest request){
      Cart cart = cartService.update(cartId, request.getQuantity());
      if (cart == null){
         return ResponseEntity.ok( "CartId " + cartId +" không còn trong giỏ hàng");
      }
      CartResponse response = cartService.mapToCartResponse(cart);
      return ResponseEntity.ok(response);
   }

   @DeleteMapping(CART_ID)
   public ResponseEntity<?> deleteCartById(@PathVariable Long cartId){
      Cart cart = cartService.findById(cartId);
      cartService.delete(cart);
      return ResponseEntity.ok(DELETE_SUCCESS);
   }

   @DeleteMapping(CART)
   public ResponseEntity<?> deleteAllCarts(){
      UserDetailCustom userDetail = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User user = userService.findById(userDetail.getId());
      cartService.deleteAll(user);
      return ResponseEntity.ok(DELETE_SUCCESS);
   }
}
