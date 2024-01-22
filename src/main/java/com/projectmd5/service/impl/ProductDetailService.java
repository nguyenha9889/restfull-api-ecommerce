package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.product.ProductDetailRequest;
import com.projectmd5.model.dto.product.ProductDetailResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.EProductSize;
import com.projectmd5.model.entity.Product;
import com.projectmd5.model.entity.ProductDetail;
import com.projectmd5.repository.IProductDetailRepository;
import com.projectmd5.service.IProductDetailService;
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
   @Override
   public ProductDetail findById(String sku) {
      return productDetailRepo.findById(sku).orElseThrow(() -> new ResourceNotFoundException(PRODUCT_DETAIL_NOT_FOUND));
   }

   @Override
   public void save(ProductDetail productDetail) {
      productDetailRepo.save(productDetail);
   }

   @Override
   public List<ProductDetail> create(Product product, ProductRequest proRequest) {
      List<ProductDetailRequest> detailsRequest = proRequest.getProductDetails();
      List<ProductDetail> productDetails = new ArrayList<>();

      for (ProductDetailRequest request : detailsRequest) {
         ProductDetail productDetail = new ProductDetail();
         productDetail.setSku(UUID.randomUUID().toString());
         productDetail.setProduct(product);
         if (request.getSize() != null){
            productDetail.setSize(EProductSize.valueOf(request.getSize()));
         }
         if (request.getDough() != null){
            productDetail.setDough(request.getDough());
         }
         productDetail.setUnitPrice(BigDecimal.valueOf(request.getUnitPrice()));

         save(productDetail);
         productDetails.add(productDetail);
      }
      return productDetails;
   }

   @Override
   public List<ProductDetailResponse> mapperToDetailsResponse(List<ProductDetail> productDetails) {
      List<ProductDetailResponse> responses = new ArrayList<>();
      for (ProductDetail productDetail : productDetails) {
         ProductDetailResponse detailResponse = new ProductDetailResponse();
         detailResponse.setSku(productDetail.getSku());
         if (productDetail.getSize() != null){
            detailResponse.setSize(productDetail.getSize().name());
         }
         if (productDetail.getDough() != null){
            detailResponse.setDough(productDetail.getDough());
         }
         detailResponse.setUnitPrice(productDetail.getUnitPrice().longValue());

         responses.add(detailResponse);
      }
      return responses;
   }

   @Override
   public List<ProductDetail> deleteProductDetailsByProductId(Long productId) {
      List<ProductDetail> productDetails = productDetailRepo.findProductDetailsByProduct_ProductId(productId);
      if (productDetails != null){
         productDetailRepo.deleteAll(productDetails);
         return productDetails;
      }
      return null;
   }

   @Override
   public List<ProductDetail> update(Product product, ProductRequest proRequest) {
      List<ProductDetailRequest> detailsRequest = proRequest.getProductDetails();
      List<ProductDetail> productDetails = new ArrayList<>();

      for (ProductDetailRequest request : detailsRequest) {
         ProductDetail productDetail = findById(request.getSku());
         productDetail.setProduct(product);
         if (request.getSize() != null){
            productDetail.setSize(EProductSize.valueOf(request.getSize()));
         }
         if (request.getDough() != null){
            productDetail.setDough(request.getDough());
         }
         productDetail.setUnitPrice(BigDecimal.valueOf(request.getUnitPrice()));

         save(productDetail);
         productDetails.add(productDetail);
      }
      return productDetails;
   }
}
