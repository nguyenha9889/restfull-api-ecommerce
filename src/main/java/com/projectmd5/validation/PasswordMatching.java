package com.projectmd5.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.projectmd5.constants.MessageConstant.CONFIRM_PASSWORD_NOT_MATCH;

@Constraint(validatedBy = PasswordMatchingValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatching {
   String password();
   String confirmPassword();
   String message() default CONFIRM_PASSWORD_NOT_MATCH;
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};

   @Target({ ElementType.TYPE })
   @Retention(RetentionPolicy.RUNTIME)
   @interface List {
      PasswordMatching[] value();
   }
}
