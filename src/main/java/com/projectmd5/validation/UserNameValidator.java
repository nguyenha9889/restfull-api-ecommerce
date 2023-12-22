package com.projectmd5.validation;

import com.projectmd5.service.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserNameValidator implements ConstraintValidator<UserNameUnique, String> {
   private final IUserService userService;
   @Override
   public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
      return !userService.existsByUsername(username);
   }
}
