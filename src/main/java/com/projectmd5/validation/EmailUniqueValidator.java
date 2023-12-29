package com.projectmd5.validation;

import com.projectmd5.model.dto.auth.RegisterRequest;
import com.projectmd5.service.IAuthService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {
   private final IAuthService authService;
   @Override
   public boolean isValid(String email, ConstraintValidatorContext context) {
      return !authService.isEmailExisted(email);
   }
}
