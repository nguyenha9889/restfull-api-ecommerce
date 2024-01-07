package com.projectmd5.validation;

import com.projectmd5.model.dto.category.CategoryRequest;
import com.projectmd5.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.projectmd5.constants.MessageConstant.*;

@Component
@RequiredArgsConstructor
public class CategoryValidator implements Validator {
   private final ICategoryService categoryService;
   @Override
   public boolean supports(Class<?> clazz) {
      return CategoryRequest.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      CategoryRequest request = (CategoryRequest) target;
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName", FIELD_NOT_BLANK);
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", FIELD_NOT_BLANK);

      if (!errors.hasFieldErrors()){
         if (categoryService.existCategoryName(request.getCategoryId(), request.getCategoryName())){
            errors.rejectValue("categoryName", CATEGORY_EXISTED);
         }
      }
   }
}
