package com.projectmd5.service;

import com.projectmd5.model.dto.cart.CartRequest;
import com.projectmd5.model.entity.Cart;
import com.projectmd5.model.entity.User;

public interface ICartService extends IGenericService<Cart, Long>{
   Cart create(User user, CartRequest cartRequest);
}
