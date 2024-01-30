package com.projectmd5.service;

import com.projectmd5.model.dto.cart.CartListResponse;
import com.projectmd5.model.dto.cart.CartRequest;
import com.projectmd5.model.dto.cart.CartResponse;
import com.projectmd5.model.entity.Cart;
import com.projectmd5.model.entity.User;

public interface ICartService extends IGenericService<Cart, Long>{
   Cart add(User user, CartRequest cartRequest);
   Cart update(Long cartItemId, Integer quantity);
   CartResponse mapToCartResponse(Cart cart);
   CartListResponse findAllByUser(User user);
   void deleteAll(User user);
}
