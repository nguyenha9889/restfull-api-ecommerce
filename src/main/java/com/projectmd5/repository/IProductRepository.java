package com.projectmd5.repository;

import com.projectmd5.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
   Page<Product> findByProductNameEqualsIgnoreCase(String name, Pageable pageable);

   Page<Product> findAllByCategory_Status(boolean status, Pageable pageable);
   List<Product> findAllByCategory_CategoryIdAndCategory_Status(Long categoryId, boolean status);
}
