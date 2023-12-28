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
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, UserRequest> {
   private final IAuthService authService;
   private String emailField;
   @Override
   public void initialize(EmailUnique constraint) {
      emailField = constraint.email();
   }

   @Override
   public boolean isValid(UserRequest userRequest, ConstraintValidatorContext context) {
      Object emailValue = new BeanWrapperImpl(userRequest).getPropertyValue(emailField);

      if (authService.existsByEmail(userRequest.getUserId(), Objects.requireNonNull(emailValue).toString())) {

         context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
               .addPropertyNode(emailField)
               .addConstraintViolation();
         return false;
      }
      return true;
   }
}
