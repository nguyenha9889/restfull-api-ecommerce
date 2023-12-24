package com.projectmd5.validation;

import com.projectmd5.service.ICategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryNameValidator implements ConstraintValidator<CategoryNameUnique, String> {
   private final ICategoryService categoryService;
   @Override
   public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
      return !categoryService.existByCategoryName(name);
   }
}
