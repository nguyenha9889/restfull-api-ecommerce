package com.projectmd5.repository;

import com.projectmd5.model.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductDetailRepository extends JpaRepository<ProductDetail, String> {
   List<ProductDetail> findProductDetailsByProduct_ProductId(Long productId);
}
