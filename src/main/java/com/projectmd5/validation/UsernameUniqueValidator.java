package com.projectmd5.validation;

import com.projectmd5.service.IAuthService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class UsernameUniqueValidator implements ConstraintValidator<UsernameUnique, String> {
   private final IAuthService authService;
   @Override
   public boolean isValid(String username, ConstraintValidatorContext context) {
      return !authService.isUsernameExisted(username);
   }
}
