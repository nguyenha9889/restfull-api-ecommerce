package com.projectmd5.service.impl;

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
   public Cart findById(Long id) {
      return cartRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(CART_NOT_FOUND));
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
   public Cart add(User user, CartRequest cartRequest) {
      Product product = productService.findById(cartRequest.getProductId());
      ProductDetail productDetail = productDetailService.findById(cartRequest.getSku());
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
      detailResponse.setSku(productDetail.getSku());
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
   public List<Cart> findAllByUser(User user) {
      return cartRepository.findAllByUser(user);
   }

   @Override
   public void deleteAll(List<Cart> carts) {
      cartRepository.deleteAll(carts);
   }

   @Override
   public Cart update(Long cartId, Integer newQuantity) {
      Cart cart = findById(cartId);
      if (newQuantity == 0) {
         delete(cart);
         return null;
      }
      cart.setQuantity(newQuantity);
      return cartRepository.save(cart);
   }
}
