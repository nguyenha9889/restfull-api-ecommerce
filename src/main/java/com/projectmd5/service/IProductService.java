package com.projectmd5.service;

import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.dto.product.ProductResponse;
import com.projectmd5.model.entity.Product;


public interface IProductService extends IGenericService<com.projectmd5.model.entity.Product, Long>{
   ProPageResponse getAllWithPaging(int pageNo, int pageSize, String sortBy, String sortDir);
   ProPageResponse searchWithPaging(String name, int pageNo, int pageSize, String sortBy, String sortDir);
   Product create(ProductRequest proRequest);
   Product update(Product product, ProductRequest proRequest);
   boolean existProductName(Long id, String name);
   ProductResponse mapperToProductResponse(Product product);

   ProPageResponse getAllPublishWithPaging(int pageNo, int pageSize, String sortBy, String sortDir);
   ProPageResponse searchByNameOrDescription(String name, int pageNo, int pageSize, String sortBy, String sortDir);
   ProPageResponse searchAllByCategory(Long categoryId, int pageNo, int pageSize, String sortBy, String sortDir);
}
