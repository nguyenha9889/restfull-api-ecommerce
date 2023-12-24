package com.projectmd5.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = TypeFileValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TypeFile {
   String message() default "Only PNG, JPG and JPEG images are allowed";
   Class<? extends Payload> [] payload() default{};
   Class<?>[] groups() default {};

}
