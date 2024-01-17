package com.projectmd5.service;

import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IProductService extends IGenericService<com.projectmd5.model.entity.Product, Long>{
   Pageable getPageable(int pageNo, int pageSize, String sortBy, String sortDir);
   ProPageResponse getAllWithPaging(Pageable pageable);
   Product create(ProductRequest proRequest);
   Product update(Long productId, ProductRequest proRequest);
   boolean existProductName(Long id, String name);

   ProPageResponse getAllPublishWithPaging(Pageable pageable);
   List<Product> getAllPublish();
   List<Product> findByNameOrDescription(String name, String description);
   List<Product> getCreatedList();
   List<Product> getAllByCategoryId(Long categoryId);
}
