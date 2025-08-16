package com.vkt.pms.validation.annotation;

import com.vkt.pms.validation.validator.DatePatternValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DatePatternValidator.class)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DatePattern {

    String message() default "Invalid Date format. Expected pattern is yyyy-MM-dd";

    String pattern() default "yyyy-MM-dd";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};
}
