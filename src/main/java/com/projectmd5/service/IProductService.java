package com.projectmd5.service;

import com.projectmd5.model.dto.product.BaseProductResponse;
import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.ProductDetail;
import org.springframework.data.domain.Pageable;


import java.util.List;


public interface IProductService extends IGenericService<com.projectmd5.model.entity.Product, Long>{
   Pageable getPageable(int pageNo, int pageSize, String sortBy, String sortDir);
   ProPageResponse getAllWithPaging(Pageable pageable);
   ProductDetail create(ProductRequest proRequest);
   BaseProductResponse update(Long productId, ProductRequest proRequest);
   boolean existProductName(Long id, String name);

   ProPageResponse getAllPublishWithPaging(Pageable pageable);
   List<com.projectmd5.model.entity.Product> getAllPublish();
   List<com.projectmd5.model.entity.Product> findByNameOrDescription(String name, String description);
   List<com.projectmd5.model.entity.Product> getCreatedList();
   List<com.projectmd5.model.entity.Product> getAllByCategoryId(Long categoryId);
}
