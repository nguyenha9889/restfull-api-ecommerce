package com.projectmd5.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = EmailUniqueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EmailUnique {
   String message() default "Địa chỉ email đã tồn tại";
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}
