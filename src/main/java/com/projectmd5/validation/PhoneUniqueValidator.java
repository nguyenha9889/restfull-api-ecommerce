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
public class PhoneUniqueValidator implements ConstraintValidator<PhoneUnique, UserRequest> {
   private final IAuthService authService;
   private String phoneField;
   @Override
   public void initialize(PhoneUnique constraint) {
      phoneField = constraint.phone();
   }

   @Override
   public boolean isValid(UserRequest userRequest, ConstraintValidatorContext context) {
      Object phoneValue = new BeanWrapperImpl(userRequest).getPropertyValue(phoneField);

      if (authService.existsByPhone(userRequest.getUserId(), Objects.requireNonNull(phoneValue).toString())) {

         context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
               .addPropertyNode(phoneField)
               .addConstraintViolation();
         return false;
      }
      return true;
   }
}
