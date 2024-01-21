package com.projectmd5.service;

import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.Product;

import java.util.List;


public interface IProductService extends IGenericService<com.projectmd5.model.entity.Product, Long>{
   ProPageResponse getAllWithPaging(int pageNo, int pageSize, String sortBy, String sortDir);
   ProPageResponse searchWithPaging(String name, int pageNo, int pageSize, String sortBy, String sortDir);
   Product create(ProductRequest proRequest);
   Product update(Product product, ProductRequest proRequest);
   boolean existProductName(Long id, String name);

   ProPageResponse getAllPublishWithPaging(int pageNo, int pageSize, String sortBy, String sortDir);
   List<Product> getAllPublish();
   List<Product> getCreatedList();
   List<Product> getAllByCategoryActive(Long categoryId);
}
