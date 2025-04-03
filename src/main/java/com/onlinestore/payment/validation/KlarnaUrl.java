package com.onlinestore.payment.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that a URL is from a whitelisted domain to prevent malicious redirects.
 * This is used to ensure users are only redirected to legitimate Klarna pages.
 */
@Documented
@Constraint(validatedBy = KlarnaUrlValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface KlarnaUrl {
    String message() default "URL must be from a whitelisted domain";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
