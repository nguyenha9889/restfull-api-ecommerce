package com.projectmd5.service;

import com.projectmd5.model.dto.product.BaseProductResponse;
import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.Product;


public interface IProductService extends IGenericService<Product, Long>{
   ProPageResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir);
   BaseProductResponse add(ProductRequest proRequest);
   BaseProductResponse update(Long productId, ProductRequest proRequest);
   boolean existProductName(Long id, String name);
}
