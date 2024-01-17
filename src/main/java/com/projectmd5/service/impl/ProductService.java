package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.Category;
import com.projectmd5.model.entity.Product;
import com.projectmd5.repository.IProductRepository;
import com.projectmd5.service.FilesStorageService;
import com.projectmd5.service.ICategoryService;
import com.projectmd5.service.IProductService;
import lombok.RequiredArgsConstructor;
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
   @Override
   public List<Product> findAll() {
      return productRepository.findAll();
   }

   @Override
   public Product findById(Long id) {
      return productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
      );
   }

   @Override
   public Product create(ProductRequest proRequest) {

      String imagePath = storageService.uploadFile((proRequest.getImage()));
      Product product = new Product();
      product.setProductName(proRequest.getProductName());
      product.setCategory(categoryService.findById(proRequest.getCategoryId()));
      product.setImagePath(imagePath);
      product.setDescription(proRequest.getDescription());
      product.setCreatedAt(new Date());
      return productRepository.save(product);
   }

   @Override
   public Product update(Long productId , ProductRequest proRequest) {
      Product product = findById(productId);
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
   public void delete(Long id) {
      Product product = findById(id);
      productRepository.delete(product);
   }

   @Override
   public Pageable getPageable(int pageNo, int pageSize, String sortBy, String sortDir){
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

      return PageRequest.of(pageNo, pageSize, sort);
   }

   /**
    * Lấy danh sách tất cả sản phẩm có phân trang và sắp xếp
    */
   @Override
   public ProPageResponse getAllWithPaging(Pageable pageable) {

      Page<Product> pages = productRepository.findAll(pageable);
      List<Product> data = pages.getContent();

      return ProPageResponse.builder()
            .products(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   /**
    * Lấy danh sách sản phẩm trong những danh mục có trạng thái active, phân trang và sắp xếp
    */
   @Override
   public ProPageResponse getAllPublishWithPaging(Pageable pageable){
      Page<Product> pages = productRepository.findAllByCategory_Status(true, pageable);
      List<Product> data = pages.getContent();

      return ProPageResponse.builder()
            .products(data)
            .pageNo(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .totalElements(pages.getTotalElements())
            .totalPages(pages.getTotalPages())
            .last(pages.isLast())
            .build();
   }

   /**
    * Lấy danh sách sản phẩm trong những danh mục có trạng thái active
    */
   @Override
   public List<Product> getAllPublish(){
      return findAll().stream().filter(p-> p.getCategory().isStatus()).toList();
   }

   /**
    * Tìm kiếm sản phẩm tương đối theo tên hoặc theo mô tả trong những danh mục active
    */
   @Override
   public List<Product> findByNameOrDescription(String name, String description) {
      List<Product> products = productRepository.findByProductNameEqualsIgnoreCaseOrDescription(name.trim(), description);
      return products.stream().filter(p-> p.getCategory().isStatus()).toList();
   }

   /**
    * Tìm các sản phẩm theo category có trạng thái active
    */
   @Override
   public List<Product> getAllByCategoryId(Long categoryId) {
      Category category = categoryService.findById(categoryId);
      return productRepository.findAllByCategory_CategoryIdAndCategory_Status(categoryId, true);
   }

   /**
    * Lấy ra 5 sản phẩm mới nhất (sắp xếp theo createdAt)
    */
   @Override
   public List<Product> getCreatedList(){
      Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
      Page<Product> pages = productRepository.findAllByCategory_Status(true, pageable);
      return pages.getContent();
   }
}
