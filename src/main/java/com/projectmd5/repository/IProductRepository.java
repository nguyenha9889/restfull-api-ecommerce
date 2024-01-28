package com.projectmd5.repository;

import com.projectmd5.model.entity.Category;
import com.projectmd5.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
   Page<Product> findAllByProductNameContainingIgnoreCase(String name, Pageable pageable);
   Page<Product> findAllByCategoryIsTrue(Pageable pageable);
   Page<Product> findAllByCategoryAndCategoryIsTrue(Category category, Pageable pageable);
   @Query("select p from Product p where p.category.status = true and lower(p.productName) like lower(concat('%',?1,'%')) or lower(p.description) like lower(concat('%',?2,'%'))")
   Page<Product> findProductPublishByNameOrDescription(String name, String description, Pageable pageable);
}
