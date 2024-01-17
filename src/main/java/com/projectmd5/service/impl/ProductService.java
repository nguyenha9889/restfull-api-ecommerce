package com.projectmd5.service.impl;

import com.projectmd5.exception.ResourceNotFoundException;
import com.projectmd5.model.dto.product.BaseProductResponse;
import com.projectmd5.model.dto.product.ProPageResponse;
import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.model.entity.Category;
import com.projectmd5.model.entity.EProductSize;
import com.projectmd5.model.entity.Product;
import com.projectmd5.model.entity.ProductDetail;
import com.projectmd5.repository.IProductRepository;
import com.projectmd5.service.FilesStorageService;
import com.projectmd5.service.ICategoryService;
import com.projectmd5.service.IProductDetailService;
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
   private final ModelMapper modelMapper;
   private final IProductRepository productRepository;
   private final ICategoryService categoryService;
   private final FilesStorageService storageService;
   @Override
   public List<com.projectmd5.model.entity.Product> findAll() {
      return productRepository.findAll();
   }

   @Override
   public com.projectmd5.model.entity.Product findById(Long id) {
      return productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(PRODUCT_NOT_FOUND)
      );
   }

   @Override
   public ProductDetail create(ProductRequest proRequest) {

      ProductDetail productDetail = ProductDetail.builder()
            .size(EProductSize.valueOf(proRequest.getSize()))
            .dough(proRequest.getDough())
            .unitPrice(proRequest.getUnitPrice())
            .build();

      String imagePath = storageService.uploadFile((proRequest.getImage()));

      Product product = new Product();
      product.setProductName(proRequest.getProductName());
      product.setCategory(categoryService.findById(proRequest.getCategoryId()));
      product.getProductDetail().add(productDetail);
      product.setImagePath(imagePath);
      product.setDescription(proRequest.getDescription());
      product.setCreatedAt(new Date());
      productRepository.save(product);
      return productDetail;
   }

   @Override
   public BaseProductResponse update(Long productId , ProductRequest proRequest) {
      com.projectmd5.model.entity.Product product = findById(productId);

      com.projectmd5.model.entity.Product proUpdate = modelMapper.map(proRequest, com.projectmd5.model.entity.Product.class);
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
      for (com.projectmd5.model.entity.Product p: findAll()) {
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
      com.projectmd5.model.entity.Product product = findById(id);
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

      Page<com.projectmd5.model.entity.Product> pages = productRepository.findAll(pageable);
      List<com.projectmd5.model.entity.Product> data = pages.getContent();

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
      Page<com.projectmd5.model.entity.Product> pages = productRepository.findAllByCategory_Status(true, pageable);
      List<com.projectmd5.model.entity.Product> data = pages.getContent();

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
