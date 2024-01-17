package com.projectmd5.service;

import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.ProductDetail;

import java.util.List;

public interface IProductDetailService extends IGenericService<ProductDetail, String>{
   List<ProductDetail> create(Long productId, ProductRequest proRequest);
   List<ProductDetail> update(Long productId, ProductRequest proRequest);
}
