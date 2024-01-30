package com.projectmd5.repository;

import com.projectmd5.model.entity.Cart;
import com.projectmd5.model.entity.Product;
import com.projectmd5.model.entity.ProductDetail;
import com.projectmd5.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
   Cart findCartByUserAndProductAndProductDetail(User user, Product product, ProductDetail productDetail);
   List<Cart> findAllByUser(User user);
}
