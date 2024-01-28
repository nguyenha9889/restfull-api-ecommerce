package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductDetailResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.dto.product.ProductResponse;
import com.projectmd5.model.entity.Category;
import com.projectmd5.model.entity.Product;
import com.projectmd5.repository.IProductRepository;
import com.projectmd5.service.FilesStorageService;
import com.projectmd5.service.ICategoryService;
import com.projectmd5.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.projectmd5.constants.MessageConstant.PRODUCT_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {
   private final IProductRepository productRepository;
   private final ICategoryService categoryService;
   private final FilesStorageService storageService;
   private final ProductDetailService productDetailService;
   private final ModelMapper mapper;
   @Override
   public List<Product> findAll() {
      return productRepository.findAll();
   }

   @Override
   public Product findById(Long id) {
      return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
   }

   @Override
   public Product create(ProductRequest proRequest) {
      Product product = mapper.map(proRequest, Product.class);
      product.setCategory(categoryService.findById(proRequest.getCategoryId()));
      String imagePath = storageService.uploadFile((proRequest.getImage()));
      product.setImagePath(imagePath);
      product.setCreatedAt(new Date());
      product.setUpdatedAt(new Date());
      return productRepository.save(product);
   }

   @Override
   public Product update(Product product , ProductRequest proRequest) {
      product.setCategory(categoryService.findById(proRequest.getCategoryId()));

      if (proRequest.getImage() != null && proRequest.getImage().getSize() > 0){
         product.setImagePath(storageService.uploadFile(proRequest.getImage()));
      } else {
         product.setImagePath(proRequest.getImagePath());
      }
      product.setDescription(proRequest.getDescription());
      product.setCreatedAt(product.getCreatedAt());
      product.setUpdatedAt(new Date());
      return productRepository.save(product);
   }

   @Override
   public boolean existProductName(Long id, String name) {
      for (Product p: findAll()) {
         if (p.getProductName().equalsIgnoreCase(name.trim())) {
            return !Objects.equals(p.getProductId(), id);
         }
      }
      return false;
   }

   @Override
   public void save(Product product) {
      productRepository.save(product);
   }

   @Override
   public void delete(Product product) {
      productRepository.delete(product);
   }

   /**
    * Lấy danh sách tất cả sản phẩm có phân trang và sắp xếp
    */
   @Override
   public ProPageResponse getAllWithPaging(int pageNo, int pageSize, String sortBy, String sortDir) {
      Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);

      Page<Product> pages = productRepository.findAll(pageable);
      List<Product> productList = pages.getContent();
      List<ProductResponse> data = productList.stream()
            .map(this::mapperToProductResponse)
            .toList();
      return ProPageResponse.builder()
            .data(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   @Override
   public ProductResponse mapperToProductResponse(Product product){
      ProductResponse productResponse = mapper.map(product, ProductResponse.class);
      productResponse.setCategory(product.getCategory());
      List<ProductDetailResponse>  detailsResponse = productDetailService.mapperToDetailsListResponse(product.getProductDetails());
      productResponse.setProductDetails(detailsResponse);
      return productResponse;
   }

   private Pageable createPageable(int pageNo, int pageSize, String sortBy, String sortDir){
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
      return PageRequest.of(pageNo, pageSize, sort);
   }

@Override
   public ProPageResponse searchWithPaging(String name, int pageNo, int pageSize, String sortBy, String sortDir){
      Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);

      Page<Product> pages = productRepository.findAllByProductNameContainingIgnoreCase(name, pageable);
      List<Product> productList = pages.getContent();
      List<ProductResponse> data = productList.stream()
         .map(this::mapperToProductResponse)
         .toList();

      return ProPageResponse.builder()
            .data(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   //======================================CLIENT FEATURES======================================
   /**
    * Lấy danh sách sản phẩm trong những danh mục có trạng thái active, phân trang và sắp xếp
    */
   @Override
   public ProPageResponse getAllPublishWithPaging(int pageNo, int pageSize, String sortBy, String sortDir){
      Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);
      Page<Product> pages = productRepository.findAllByCategoryIsTrue(pageable);
      List<Product> productList = pages.getContent();
      List<ProductResponse> data = productList.stream()
            .map(this::mapperToProductResponse)
            .toList();

      return ProPageResponse.builder()
            .data(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   @Override
   public ProPageResponse searchByNameOrDescription(String name, int pageNo, int pageSize, String sortBy, String sortDir) {
      Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);
      Page<Product> pages = productRepository.findProductPublishByNameOrDescription(name, name, pageable);
      List<Product> productList = pages.getContent();

      List<ProductResponse> data = productList.stream()
            .map(this::mapperToProductResponse)
            .toList();

      return ProPageResponse.builder()
            .data(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   @Override
   public ProPageResponse searchAllByCategory(Long categoryId, int pageNo, int pageSize, String sortBy, String sortDir) {
      Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);
      Category category = categoryService.findById(categoryId);
      Page<Product> pages = productRepository.findAllByCategoryAndCategoryIsTrue(category, pageable);
      List<Product> productList = pages.getContent();

      List<ProductResponse> data = productList.stream()
            .map(this::mapperToProductResponse)
            .toList();

      return ProPageResponse.builder()
            .data(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }
}
