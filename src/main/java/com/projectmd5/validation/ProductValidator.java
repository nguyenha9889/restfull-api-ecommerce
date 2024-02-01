package com.projectmd5.validation;

import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import static com.projectmd5.constants.MessageConstant.*;

@Component
@RequiredArgsConstructor
public class ProductValidator implements Validator {
   private final IProductService productService;
   @Override
   public boolean supports(Class<?> clazz) {
      return ProductRequest.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      ProductRequest request = (ProductRequest) target;

      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", FIELD_NOT_BLANK);
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", FIELD_NOT_BLANK);

      if (request.getCategoryId() == null){
         errors.rejectValue("categoryId", CATEGORY_NULL);
      }

      if (request.getProductId() == null){
         if (request.getImage() == null){
            errors.rejectValue("image", FILE_NULL);
         } else if (request.getImage().getSize() == 0){
            errors.rejectValue("image", FILE_NULL);
         }
      }

      if (request.getProductDetails() == null || request.getProductDetails().isEmpty()){
         errors.rejectValue("productDetails", PRICE_NULL);
      }

      if (!errors.hasFieldErrors()){
         if (productService.existProductName(request.getProductId(), request.getProductName())) {
            errors.rejectValue("productName", PRODUCT_EXISTED);
         }

         if (request.getImage() != null && !isSupportedImageType(request.getImage())){
            errors.rejectValue("image", FILE_UPLOAD_RULE);
         }

         request.getProductDetails().forEach(productDetail -> {
            if (productDetail.getUnitPrice() == null || productDetail.getUnitPrice() <= 0){
               errors.rejectValue("productDetails", PRICE_RULE);
            }
         });
      }

   }

   private boolean isSupportedImageType(MultipartFile image) {
      String imageType = StringUtils.getFilenameExtension(image.getOriginalFilename());
      assert imageType != null;
      return imageType.equals("png")
            || imageType.equals("jpg")
            || imageType.equals("jpeg");
   }
}
