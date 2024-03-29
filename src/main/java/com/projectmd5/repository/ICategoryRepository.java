package com.projectmd5.repository;

import com.projectmd5.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
   List<Category> findAllByStatusIsTrue();
   Page<Category> findAllByCategoryNameContainingIgnoreCase(String name, Pageable pageable);
}
