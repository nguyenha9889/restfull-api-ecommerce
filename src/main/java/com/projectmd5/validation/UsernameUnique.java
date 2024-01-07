package com.projectmd5.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.projectmd5.constants.MessageConstant.USERNAME_EXISTED;

@Constraint(validatedBy = UsernameUniqueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UsernameUnique {
   String message() default USERNAME_EXISTED;
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}
