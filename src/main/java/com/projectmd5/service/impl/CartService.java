package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.cart.CartItemRequest;
import com.projectmd5.model.dto.cart.CartItemResponse;
import com.projectmd5.model.dto.cart.CartResponse;
import com.projectmd5.model.dto.product.ProductDetailResponse;
import com.projectmd5.model.entity.*;
import com.projectmd5.repository.ICartItemRepository;
import com.projectmd5.repository.ICartRepository;
import com.projectmd5.service.ICartService;
import com.projectmd5.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.projectmd5.constants.MessageConstant.CART_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
   private final ICartRepository cartRepository;
   private final ICartItemRepository cartItemRepo;
   private final ProductDetailService productDetailService;
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
   public Cart add(User user, List<CartItemRequest> cartItemRequests) {
      // create cart
      Cart cart = cartRepository.save(Cart.builder()
            .user(user)
            .build());

      // xử lý list cart item request và lưu vào db
      cartItemRequests.forEach(request -> {
         Product product = productService.findById(request.getProductId());
         ProductDetail productDetail = productDetailService.findById(request.getSku());
         CartItem cartItem = CartItem.builder()
               .cart(cart)
               .product(product)
               .productDetail(productDetail)
               .quantity(request.getQuantity())
               .build();

         cartItemRepo.save(cartItem);
         // add cart item vào cart
         cart.getCartItems().add(cartItem);
      });

      BigDecimal totalPrice = cart.getCartItems().stream()
            .map(cartItem -> cartItem
                  .getProductDetail()
                  .getUnitPrice()
                  .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

      cart.setTotalPrice(totalPrice);
      // lưu cart vào db
      return cartRepository.save(cart);
   }


   @Override
   public CartResponse mapToCartResponse(Cart cart) {
      CartResponse cartResponse = new CartResponse();

      List<CartItemResponse> cartItemResponses = new ArrayList<>();
      cart.getCartItems().forEach(cartItem -> {
         ProductDetailResponse detailResponse = productDetailService.mapperToDetailResponse(cartItem.getProductDetail());
         CartItemResponse itemResponse = CartItemResponse.builder()
               .cartItemId(cartItem.getCartItemId())
               .productDetail(detailResponse)
               .quantity(cartItem.getQuantity())
               .build();
         cartItemResponses.add(itemResponse);
      });
      cartResponse.setCartItems(cartItemResponses);
      return CartResponse.builder()
            .cartId(cart.getCartId())
            .userId(cart.getUser().getUserId())
            .cartItems(cartItemResponses)
            .totalPrice(cart.getTotalPrice().longValue())
            .build();
   }


   @Override
   public Cart update(Long cartItemId, int quantity) {
      CartItem cartItem = cartItemRepo.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException(CART_ITEM_NOT_FOUND));
      cartItem.setQuantity(quantity);
      cartItemRepo.save(cartItem);
      return null;
   }
}
