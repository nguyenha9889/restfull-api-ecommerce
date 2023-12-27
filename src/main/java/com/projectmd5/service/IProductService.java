package com.projectmd5.service;

import com.projectmd5.model.dto.product.BaseProductResponse;
import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IProductService extends IGenericService<Product, Long>{
   Pageable getPageable(int pageNo, int pageSize, String sortBy, String sortDir);
   ProPageResponse getAllWithPaging(Pageable pageable);
   ProPageResponse getAllPublishWithPaging(Pageable pageable);
   BaseProductResponse add(ProductRequest proRequest);
   BaseProductResponse update(Long productId, ProductRequest proRequest);
   boolean existProductName(Long id, String name);
   List<Product> findByNameOrDescription(String name, String description);
   List<Product> getAllNewCreated();
   List<Product> getAllByCategoryId(Long categoryId);
}
