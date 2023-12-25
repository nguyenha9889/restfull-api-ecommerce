package com.projectmd5.service;

import com.projectmd5.model.dto.request.ProductDTO;
import com.projectmd5.model.dto.response.ProductResponse;
import com.projectmd5.model.entity.Product;

public interface IProductService extends IGenericService<Product, Long>{
   ProductResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir);
   Product create(ProductDTO proRequest);
   Product edit(Long productId, ProductDTO proRequest);
   boolean existProductName(Long id, String name);
}
