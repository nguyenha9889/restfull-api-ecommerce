package com.projectmd5.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PhoneValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PhoneUnique {
   String message() default "Phone number is existed!";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};
}