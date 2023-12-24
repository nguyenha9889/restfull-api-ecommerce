package com.projectmd5.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = CategoryNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CategoryNameUnique {
   String message() default "Category name is existed!";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};
}
