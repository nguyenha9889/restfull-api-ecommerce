package com.projectmd5.validation;

import com.projectmd5.model.dto.product.ProductRequest;
import com.projectmd5.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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
         if (request.getImages() == null){
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

         request.getProductDetails().forEach(productDetail -> {
            if (productDetail.getUnitPrice() == null || productDetail.getUnitPrice() <= 0){
               errors.rejectValue("productDetails", PRICE_RULE);
            }
         });
      }

   }
}
