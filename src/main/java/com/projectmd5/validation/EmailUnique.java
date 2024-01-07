package com.projectmd5.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import static com.projectmd5.constants.MessageConstant.EMAIL_EXISTED;

@Constraint(validatedBy = EmailUniqueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EmailUnique {
   String message() default EMAIL_EXISTED;
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}
