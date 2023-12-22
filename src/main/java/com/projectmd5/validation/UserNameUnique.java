package com.projectmd5.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = UserNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserNameUnique {
   String message() default "Username is existed!";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};
}
