package com.projectmd5.service;

import com.projectmd5.model.dto.product.ProductDetailResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.Product;
import com.projectmd5.model.entity.ProductDetail;

import java.util.List;

public interface IProductDetailService extends IGenericService<ProductDetail, String>{
   List<ProductDetail> create(Product product, ProductRequest proRequest);
   List<ProductDetail> update(Product product, ProductRequest proRequest);
   List<ProductDetailResponse> mapperToDetailsListResponse(List<ProductDetail> productDetails);
   ProductDetailResponse mapperToDetailResponse(ProductDetail productDetail);
   List<ProductDetail> deleteProductDetailsByProductId(Long productId);
}
