package com.projectmd5.service;

import com.projectmd5.model.dto.cart.CartItemRequest;
import com.projectmd5.model.dto.cart.CartResponse;
import com.projectmd5.model.entity.Cart;
import com.projectmd5.model.entity.User;

import java.util.List;

public interface ICartService extends IGenericService<Cart, Long>{
   Cart add(User user, List<CartItemRequest> cartItemRequests);
   Cart update(Long cartItemId, int quantity);
   CartResponse mapToCartResponse(Cart cart);
}
