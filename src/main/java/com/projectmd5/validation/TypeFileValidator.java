package com.projectmd5.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class TypeFileValidator implements ConstraintValidator<TypeFile, MultipartFile> {

   @Override
   public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

      String contentType = multipartFile.getContentType();
      assert contentType != null;
      return isSupportedContentType(contentType);
   }

   private boolean isSupportedContentType(String contentType) {
      return contentType.equals("image/png")
            || contentType.equals("image/jpg")
            || contentType.equals("image/jpeg");
   }
}
