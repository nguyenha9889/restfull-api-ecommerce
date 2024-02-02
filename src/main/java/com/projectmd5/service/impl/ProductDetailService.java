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

import static com.projectmd5.constants.MessageConstant.PRODUCT_DETAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductDetailService implements IProductDetailService {
   private final IProductDetailRepository productDetailRepo;

   @Override
   public List<ProductDetail> findAll() {
      return productDetailRepo.findAll();
   }

   @Override
   public ProductDetail findById(Long id) {
      return productDetailRepo.findById(id).orElseThrow(
            () -> new RuntimeException(PRODUCT_DETAIL_NOT_FOUND)
      );
   }

   @Override
   public void delete(ProductDetail productDetail) {
      productDetailRepo.delete(findById(productDetail.getProductDetailId()));
   }

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
      detailResponse.setDetailId(productDetail.getProductDetailId());
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
         if (request.getDetailId() == null){
            productDetail = new ProductDetail();
            productDetail.setProduct(product);
         } else {
            productDetail = findById(request.getDetailId());
         }
         if (request.getSize() != null){
            productDetail.setSize(EProductSize.valueOf(request.getSize()));
         }
         if (request.getDough() != null){
            productDetail.setDough(request.getDough());
         }
         productDetail.setUnitPrice(BigDecimal.valueOf(request.getUnitPrice()));
         ProductDetail update = productDetailRepo.save(productDetail);
         productDetails.add(update);
      }
      return productDetails;
   }
}
