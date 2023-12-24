package com.projectmd5.validation;

import com.projectmd5.service.IProductService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductNameValidator implements ConstraintValidator<ProductNameUnique, String> {
   private final IProductService productService;
   @Override
   public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
      return !productService.existByProductName(name.trim());
   }
}
