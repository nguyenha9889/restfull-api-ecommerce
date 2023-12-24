package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.request.ProductDTO;
import com.projectmd5.model.dto.response.ProductResponse;
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

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {
   private final ModelMapper modelMapper;
   private final FilesStorageService storageService;
   private final IProductRepository productRepository;
   private final ICategoryService categoryService;
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
   public ProductResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      Page<Product> pages = productRepository.findAll(pageable);
      List<Product> data = pages.getContent();

      List<ProductDTO> dataDTO = data.stream().map(
            p -> modelMapper.map(p, ProductDTO.class)).toList();

      return ProductResponse.builder()
            .products(dataDTO)
            .pageNo(pageNo)
            .pageSize(pageSize)
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   @Override
   public Product create(ProductDTO proDTO) {
      proDTO.setCreatedAt(new Date());
      proDTO.setUpdatedAt(null);

      return modelMapper.map(proDTO, Product.class);
   }

   @Override
   public Product edit(ProductDTO proDTO, Long productId) {
      Product product = findById(productId);
      Category category = categoryService.findById(proDTO.getCategoryId());
      product.setCategory(category);

      product.setProductName(proDTO.getProductName());
      product.setDescription(proDTO.getDescription());
      product.setUnitPrice(proDTO.getUnitPrice());
      product.setQuantity(proDTO.getQuantity());
      product.setUpdatedAt(new Date());

      return product;
   }

   @Override
   public boolean existByProductName(String name) {
      return false;
   }
}
