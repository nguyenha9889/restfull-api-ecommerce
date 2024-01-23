package com.projectmd5.repository;

import com.projectmd5.model.entity.Category;
import com.projectmd5.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
   Page<Product> findByProductNameEqualsIgnoreCase(String name, Pageable pageable);
   Page<Product> findAllByCategoryIsTrue(Pageable pageable);
   Page<Product> findAllByCategoryAndCategoryIsTrue(Category category, Pageable pageable);
   Page<Product> findAllByProductNameOrDescriptionAndCategoryIsTrue(String name, String description, Pageable pageable);
}
