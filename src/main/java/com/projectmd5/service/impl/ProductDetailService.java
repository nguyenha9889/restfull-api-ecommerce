package com.projectmd5.service.impl;

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

@Service
@RequiredArgsConstructor
public class ProductDetailService implements IProductDetailService {
   private final IProductDetailRepository productDetailRepo;

   @Override
   public void save(ProductDetail productDetail) {
      productDetailRepo.save(productDetail);
   }

   @Override
   public List<ProductDetail> add(Product product, ProductRequest proRequest) {
      List<ProductDetailRequest> detailsRequest = proRequest.getProductDetails();
      List<ProductDetail> productDetails = new ArrayList<>();

      for (ProductDetailRequest request : detailsRequest) {
         ProductDetail productDetail = new ProductDetail();
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
   public List<ProductDetailResponse> mapperToDetailsListResponse(List<ProductDetail> productDetails) {
      List<ProductDetailResponse> responses = new ArrayList<>();
      for (ProductDetail productDetail : productDetails) {
         ProductDetailResponse detailResponse = mapperToDetailResponse(productDetail);
         responses.add(detailResponse);
      }
      return responses;
   }

   @Override
   public ProductDetailResponse mapperToDetailResponse(ProductDetail productDetail) {
      ProductDetailResponse detailResponse = new ProductDetailResponse();
      detailResponse.setId(productDetail.getProductDetailId());
      if (productDetail.getSize() != null){
         detailResponse.setSize(productDetail.getSize().name());
      }
      if (productDetail.getDough() != null){
         detailResponse.setDough(productDetail.getDough());
      }
      detailResponse.setUnitPrice(productDetail.getUnitPrice().longValue());
      return detailResponse;
   }

   @Override
   public List<ProductDetail> update(Product product, ProductRequest proRequest){
      List<ProductDetailRequest> detailsRequest = proRequest.getProductDetails();
      List<ProductDetail> productDetails = new ArrayList<>();
      for (ProductDetailRequest request : detailsRequest) {
         ProductDetail productDetail;
         if (request.getId() == null){
            productDetail = new ProductDetail();
            productDetail.setProduct(product);
            if (request.getSize() != null){
               productDetail.setSize(EProductSize.valueOf(request.getSize()));
            }
            if (request.getDough() != null){
               productDetail.setDough(request.getDough());
            }
            productDetail.setUnitPrice(BigDecimal.valueOf(request.getUnitPrice()));
         } else {
            productDetail = findById(request.getId());
            if (request.getSize() != null){
               productDetail.setSize(EProductSize.valueOf(request.getSize()));
            }
            if (request.getDough() != null){
               productDetail.setDough(request.getDough());
            }
            productDetail.setUnitPrice(BigDecimal.valueOf(request.getUnitPrice()));
         }
         ProductDetail update = productDetailRepo.save(productDetail);
         productDetails.add(update);
      }
      return productDetails;
   }
}
