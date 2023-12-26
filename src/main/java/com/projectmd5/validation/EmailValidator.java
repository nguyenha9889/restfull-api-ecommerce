package com.projectmd5.validation;

import com.projectmd5.service.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailUnique, String> {
   private final IUserService userService;
   @Override
   public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
      return !userService.existsByEmail(email);
   }
}