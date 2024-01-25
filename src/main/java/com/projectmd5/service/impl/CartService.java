package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.cart.CartRequest;
import com.projectmd5.model.entity.Cart;
import com.projectmd5.model.entity.Product;
import com.projectmd5.model.entity.User;
import com.projectmd5.repository.ICartRepository;
import com.projectmd5.service.ICartService;
import com.projectmd5.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.projectmd5.constants.MessageConstant.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
   private final ICartRepository cartRepository;
   private final IProductService productService;
   @Override
   public List<Cart> findAll() {
      return cartRepository.findAll();
   }

   @Override
   public Cart findById(Long id) {
      return cartRepository.findById(id).orElse(null);
   }

   @Override
   public void save(Cart cart) {
      cartRepository.save(cart);
   }

   @Override
   public void delete(Cart cart) {
      cartRepository.delete(cart);
   }

   @Override
   public Cart create(User user, CartRequest cartRequest) {
      Product product = productService.findById(cartRequest.getProductId());
      if (product == null){
         throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
      }
      return Cart.builder()
            .user(user)
            .product(product)
            .quantity(cartRequest.getQuantity())
            .build();
   }
}
