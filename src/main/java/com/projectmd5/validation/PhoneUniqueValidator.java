package com.projectmd5.validation;

import com.projectmd5.service.IAuthService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class PhoneUniqueValidator implements ConstraintValidator<PhoneUnique, String> {
   private final IAuthService authService;

   @Override
   public boolean isValid(String phone, ConstraintValidatorContext context) {
      return !authService.isPhoneExisted(phone);
   }
}
