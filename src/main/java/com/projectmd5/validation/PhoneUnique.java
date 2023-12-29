package com.projectmd5.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PhoneUniqueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PhoneUnique {
   String message() default "Số điện thoại đã tồn tại";
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}
