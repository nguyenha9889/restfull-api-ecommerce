package com.projectmd5.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = UsernameUniqueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UsernameUnique {
   String username();
   String message() default "Username is existed!";
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}