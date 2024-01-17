package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.entity.ProductDetail;
import com.projectmd5.repository.IProductDetailRepository;
import com.projectmd5.service.IProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.projectmd5.constants.MessageConstant.PRODUCT_DETAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductDetailService implements IProductDetailService {
   private final IProductDetailRepository productTypeRepo;
   @Override
   public ProductDetail findById(String sku) {
      return productTypeRepo.findById(sku).orElseThrow(() -> new ResourceNotFoundException(PRODUCT_DETAIL_NOT_FOUND));
   }

   @Override
   public void save(ProductDetail productDetail) {
      productTypeRepo.save(productDetail);
   }
}
