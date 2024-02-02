package com.projectmd5.repository;

import com.projectmd5.model.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductDetailRepository extends JpaRepository<ProductDetail, Long> {
}
