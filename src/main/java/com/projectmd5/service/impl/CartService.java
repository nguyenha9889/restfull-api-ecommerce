package com.projectmd5.service.impl;

import com.projectmd5.exception.BadRequestException;
import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.cart.CartRequest;
import com.projectmd5.model.dto.cart.CartResponse;
import com.projectmd5.model.dto.product.ProductDetailResponse;
import com.projectmd5.model.entity.Cart;
import com.projectmd5.model.entity.Product;
import com.projectmd5.model.entity.ProductDetail;
import com.projectmd5.model.entity.User;
import com.projectmd5.repository.ICartRepository;
import com.projectmd5.service.ICartService;
import com.projectmd5.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.projectmd5.constants.MessageConstant.CART_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
   private final ICartRepository cartRepository;
   private final ProductDetailService productDetailService;
   private final IProductService productService;

   @Override
   public Cart findByUserAndCartId(User user, Long cartId) {
      return cartRepository.findCartByUserAndCartId(user, cartId).orElseThrow(
            () -> new ResourceNotFoundException(CART_NOT_FOUND)
      );
   }
   @Override
   public CartResponse getCartByUserAndCartId(User user, Long cartId) {
      Cart cart = cartRepository.findCartByUserAndCartId(user, cartId).orElseThrow(
            () -> new ResourceNotFoundException(CART_NOT_FOUND)
      );
      return mapToCartResponse(cart);
   }
   @Override
   public Cart add(User user, CartRequest cartRequest) {
      Product product = productService.findById(cartRequest.getProductId());
      ProductDetail productDetail = productDetailService.findById(cartRequest.getId());
      Cart cart = cartRepository.findCartByUserAndProductAndProductDetail(user, product, productDetail);
      if (cart != null) {
         int newQuantity = cart.getQuantity() + cartRequest.getQuantity();
         cart.setQuantity(newQuantity);
      } else {
         cart = Cart.builder()
               .user(user)
               .product(product)
               .productDetail(productDetail)
               .quantity(cartRequest.getQuantity())
               .build();
      }
      return cartRepository.save(cart);
   }


   @Override
   public CartResponse mapToCartResponse(Cart cart) {
      Product product = cart.getProduct();
      ProductDetail productDetail = cart.getProductDetail();
      ProductDetailResponse detailResponse = new ProductDetailResponse();
      detailResponse.setId(productDetail.getProductDetailId());
      if (productDetail.getSize() != null) {
         detailResponse.setSize(productDetail.getSize().name());
      }
      if (productDetail.getDough() != null) {
         detailResponse.setDough(productDetail.getDough());
      }
      detailResponse.setUnitPrice(productDetail.getUnitPrice().longValue());
      return CartResponse.builder()
            .cartId(cart.getCartId())
            .productId(cart.getProduct().getProductId())
            .productName(product.getProductName())
            .imagePath(product.getImagePath())
            .productDetail(detailResponse)
            .quantity(cart.getQuantity())
            .build();
   }

   @Override
   public List<CartResponse> findAllByUser(User user) {
      List<Cart> carts = cartRepository.findAllByUser(user);
      if (carts.isEmpty()) {
         throw new BadRequestException("Giỏ hàng trống");
      }
      return carts.stream()
            .map(this::mapToCartResponse)
            .toList();
   }

   @Override
   public void deleteAll(User user) {
      List<Cart> carts = cartRepository.findAllByUser(user);
      if (carts.isEmpty()) {
         throw new BadRequestException("Giỏ hàng trống");
      }
      cartRepository.deleteAll(carts);
   }

   @Override
   public Cart update(User user, Long cartId, Integer newQuantity) {
      Cart cart = cartRepository.findCartByUserAndCartId(user, cartId).orElseThrow(
            () -> new ResourceNotFoundException(CART_NOT_FOUND)
      );
      if (newQuantity == 0) {
         delete(cart);
         return null;
      }
      cart.setQuantity(newQuantity);
      return cartRepository.save(cart);
   }

   @Override
   public void delete(Cart cart) {
      cartRepository.delete(cart);
   }
}
