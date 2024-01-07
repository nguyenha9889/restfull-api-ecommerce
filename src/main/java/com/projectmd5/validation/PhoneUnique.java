package com.projectmd5.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.projectmd5.constants.MessageConstant.PHONE_EXISTED;

@Constraint(validatedBy = PhoneUniqueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PhoneUnique {
   String message() default PHONE_EXISTED;
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}
