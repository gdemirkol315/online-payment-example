package com.onlinestore.payment.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MerchantUrlsTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldValidateValidUrls() {
        MerchantUrls merchantUrls = MerchantUrls.builder()
                .confirmation("https://checkout.klarna.com/confirmation")
                .notification("https://api.klarna.com/notification")
                .push("https://klarna.com/push")
                .authorization("http://localhost:8080/authorization")
                .build();

        Set<ConstraintViolation<MerchantUrls>> violations = validator.validate(merchantUrls);
        assertTrue(violations.isEmpty(), "No violations should be found for valid URLs");
    }

    @Test
    void shouldRejectInvalidUrls() {
        MerchantUrls merchantUrls = MerchantUrls.builder()
                .confirmation("https://malicious-site.com/fake-klarna")
                .notification("https://api.klarna.com/notification") // Valid
                .push("not-a-url")
                .authorization("https://klarna-phishing.com/auth")
                .build();

        Set<ConstraintViolation<MerchantUrls>> violations = validator.validate(merchantUrls);
        assertEquals(3, violations.size(), "Should have 3 violations for invalid URLs");
        
        // Verify that the violations are for the expected fields
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("confirmation")),
                "Should have violation for confirmation URL");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("push")),
                "Should have violation for push URL");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("authorization")),
                "Should have violation for authorization URL");
    }

    @Test
    void shouldAllowNullOrEmptyUrls() {
        MerchantUrls merchantUrls = MerchantUrls.builder()
                .confirmation(null)
                .notification("")
                .push("https://klarna.com/push") // Valid
                .authorization("http://localhost:8080/authorization") // Valid
                .build();

        Set<ConstraintViolation<MerchantUrls>> violations = validator.validate(merchantUrls);
        assertTrue(violations.isEmpty(), "No violations should be found for null or empty URLs");
    }
}
