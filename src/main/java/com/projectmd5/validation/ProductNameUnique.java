package com.projectmd5.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ProductNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProductNameUnique {
   String message() default "Product name is existed!";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};
}
