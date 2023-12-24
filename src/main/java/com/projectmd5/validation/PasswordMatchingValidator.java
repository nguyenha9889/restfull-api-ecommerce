package com.projectmd5.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, Object> {

   private String passwordField;
   private String confirmPasswordField;
   @Override
   public void initialize(PasswordMatching matching) {
      passwordField = matching.password();
      confirmPasswordField = matching.confirmPassword();
   }

   @Override
   public boolean isValid(Object value, ConstraintValidatorContext context) {
      Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(passwordField);
      Object confirmPasswordValue = new BeanWrapperImpl(value).getPropertyValue(confirmPasswordField);

      if (passwordValue==null || !passwordValue.equals(confirmPasswordValue)){
         context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
               .addPropertyNode(confirmPasswordField)
               .addConstraintViolation();
         return false;
      }
      return true;
   }
}
