package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.product.ProductDetailRequest;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.EProductSize;
import com.projectmd5.model.entity.ProductDetail;
import com.projectmd5.repository.IProductDetailRepository;
import com.projectmd5.service.IProductDetailService;
import com.projectmd5.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.projectmd5.constants.MessageConstant.PRODUCT_DETAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductDetailService implements IProductDetailService {
   private final IProductDetailRepository productDetailRepo;
   private final IProductService productService;
   @Override
   public ProductDetail findById(String sku) {
      return productDetailRepo.findById(sku).orElseThrow(() -> new ResourceNotFoundException(PRODUCT_DETAIL_NOT_FOUND));
   }

   @Override
   public void save(ProductDetail productDetail) {
      productDetailRepo.save(productDetail);
   }

   @Override
   public List<ProductDetail> create(Long productId, ProductRequest proRequest) {
      List<ProductDetailRequest> listRequest = proRequest.getProductDetails();
      List<ProductDetail> listProductDetail = new ArrayList<>();
      for (ProductDetailRequest request : listRequest) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setSku(UUID.randomUUID().toString());
        productDetail.setProduct(productService.findById(productId));
        productDetail.setSize(EProductSize.valueOf(request.getSize()));
        productDetail.setDough(request.getDough());
        productDetail.setUnitPrice(BigDecimal.valueOf(request.getPrice()));

        save(productDetail);
        listProductDetail.add(productDetail);
      }
      return listProductDetail;
   }

   @Override
   public List<ProductDetail> update(Long productId, ProductRequest proRequest) {
      List<ProductDetailRequest> listRequest = proRequest.getProductDetails();
      List<ProductDetail> listProductDetail = new ArrayList<>();
      for (ProductDetailRequest request : listRequest) {
         ProductDetail productDetail = findById(request.getSku());
         productDetail.setProduct(productService.findById(productId));
         productDetail.setSize(EProductSize.valueOf(request.getSize()));
         productDetail.setDough(request.getDough());
         productDetail.setUnitPrice(BigDecimal.valueOf(request.getPrice()));

         save(productDetail);
         listProductDetail.add(productDetail);
      }
      return listProductDetail;
   }
}
