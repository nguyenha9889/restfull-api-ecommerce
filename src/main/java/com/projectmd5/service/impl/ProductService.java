package com.projectmd5.service.impl;

import com.projectmd5.exception.BadRequestException;
import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.product.BaseProductResponse;
import com.projectmd5.model.dto.product.ProPageResponse;
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
import org.springframework.web.multipart.MultipartException;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {
   private final ModelMapper modelMapper;
   private final IProductRepository productRepository;
   private final ICategoryService categoryService;
   private final FilesStorageService storageService;
   @Override
   public List<Product> findAll() {
      return productRepository.findAll();
   }

   @Override
   public Product findById(Long id) {
      return productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Product not found with id " + id)
      );
   }

   @Override
   public void save(Product product) {
      productRepository.save(product);
   }

   @Override
   public void delete(Long id) {
      Product product = findById(id);
      productRepository.delete(product);
   }

   @Override
   public Pageable getPageable(int pageNo, int pageSize, String sortBy, String sortDir){
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

      return PageRequest.of(pageNo, pageSize, sort);
   }

   @Override
   public ProPageResponse getAll(Pageable pageable) {

      Page<Product> pages = productRepository.findAll(pageable);
      List<Product> data = pages.getContent();

      List<ProductResponse> dataResponse = data.stream().map(
            p -> modelMapper.map(p, ProductResponse.class)).toList();

      return ProPageResponse.builder()
            .products(dataResponse)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   @Override
   public BaseProductResponse add(ProductRequest proRequest) {
      if (proRequest.getImage() == null || proRequest.getImage().isEmpty()) {
         throw new MultipartException("Upload one image with size less than 1MB");
      }
      String imagePath = storageService.uploadFile((proRequest.getImage()));

      Product product = modelMapper.map(proRequest, Product.class);
      //product.setImagePath(product.getImagePath());
      product.setImagePath(imagePath);
      product.setSku(UUID.randomUUID().toString());
      product.setCreatedAt(new Date());

      productRepository.save(product);
      return modelMapper.map(product, BaseProductResponse.class);
   }

   @Override
   public BaseProductResponse update(Long productId , ProductRequest proRequest) {
      Product product = findById(productId);

      if (existProductName(productId, proRequest.getProductName())){
         throw new BadRequestException("Product name is existed!");
      }

      Product proUpdate = modelMapper.map(proRequest, Product.class);
      proUpdate.setProductId(productId);

      Category category = categoryService.findById(proRequest.getCategoryId());
      proUpdate.setCategory(category);

      if (proRequest.getImage() != null && proRequest.getImage().getSize() > 0){
         proUpdate.setImagePath(storageService.uploadFile(proRequest.getImage()));
      } else {
         proUpdate.setImagePath(proRequest.getImagePath());
      }
      proUpdate.setCreatedAt(product.getCreatedAt());
      proUpdate.setUpdatedAt(new Date());

      productRepository.save(proUpdate);
      return modelMapper.map(proUpdate, BaseProductResponse.class);
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
}
