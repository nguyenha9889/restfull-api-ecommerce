package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductDetailResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.dto.product.ProductResponse;
import com.projectmd5.model.entity.Category;
import com.projectmd5.model.entity.Product;
import com.projectmd5.model.entity.ProductDetail;
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
   public ProductResponse getProductById(Long productId){
      Product product = findById(productId);
      return mapperToProductResponse(product);
   }

   @Override
   public ProductResponse addNew(ProductRequest proRequest) {
      Category category = categoryService.findById(proRequest.getCategoryId());
      String imagePath = storageService.uploadFile((proRequest.getImages().get(0)));
      Product product = productRepository.save(Product.builder()
            .productName(proRequest.getProductName())
            .category(category)
            .description(proRequest.getDescription())
            .imagePath(imagePath)
            .createdAt(new Date())
            .updatedAt(new Date())
            .build());
      List<ProductDetail> productDetails = productDetailService.add(product, proRequest);
      product.setProductDetails(productDetails);
      productRepository.save(product);
      return mapperToProductResponse(product);
   }

   @Override
   public ProductResponse update(Long productId , ProductRequest proRequest) {
      Product product = findById(productId);
      if (proRequest.getImages() != null){
         product.setImagePath(storageService.uploadFile(proRequest.getImages().get(0)));
      }
      List<ProductDetail> updateList = productDetailService.update(product, proRequest);
      product.getProductDetails().clear();
      product.setProductDetails(updateList);
      product.setDescription(proRequest.getDescription());
      product.setUpdatedAt(new Date());
      productRepository.save(product);
      return mapperToProductResponse(product);
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
