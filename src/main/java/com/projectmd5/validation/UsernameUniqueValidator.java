package com.projectmd5.validation;

import com.projectmd5.model.dto.user.UserRequest;
import com.projectmd5.service.IAuthService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.Objects;


@RequiredArgsConstructor
@Component
public class UsernameUniqueValidator implements ConstraintValidator<UsernameUnique, UserRequest> {
   private final IAuthService authService;
   private String usernameField;
   @Override
   public void initialize(UsernameUnique constraint) {
      usernameField = constraint.username();
   }

   @Override
   public boolean isValid(UserRequest userRequest, ConstraintValidatorContext context) {
      Object usernameValue = new BeanWrapperImpl(userRequest).getPropertyValue(usernameField);

      if (authService.existsByUsername(userRequest.getUserId(), Objects.requireNonNull(usernameValue).toString())) {

         context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
               .addPropertyNode(usernameField)
               .addConstraintViolation();
         return false;
      }
      return true;
   }
}
