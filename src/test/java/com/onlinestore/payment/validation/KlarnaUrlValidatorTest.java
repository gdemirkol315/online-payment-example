package com.onlinestore.payment.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KlarnaUrlValidatorTest {

    private KlarnaUrlValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new KlarnaUrlValidator();
    }

    @Test
    void shouldAllowNullOrEmptyValues() {
        assertTrue(validator.isValid(null, context));
        assertTrue(validator.isValid("", context));
    }

    @Test
    void shouldAllowWhitelistedDomains() {
        // Direct matches
        assertTrue(validator.isValid("https://klarna.com/path", context));
        assertTrue(validator.isValid("https://api.klarna.com/v1/checkout", context));
        assertTrue(validator.isValid("https://checkout.klarna.com/session", context));
        
        // Subdomains of whitelisted domains
        assertTrue(validator.isValid("https://us.klarna.com/checkout", context));
        assertTrue(validator.isValid("https://pay-eu.klarna.com/payment", context));
        
        // Local development URLs
        assertTrue(validator.isValid("http://localhost:8080/callback", context));
        assertTrue(validator.isValid("http://127.0.0.1:3000/success", context));
    }

    @Test
    void shouldRejectNonWhitelistedDomains() {
        assertFalse(validator.isValid("https://malicious-site.com/fake-klarna", context));
        assertFalse(validator.isValid("https://evil.com/redirect?to=klarna.com", context));
        assertFalse(validator.isValid("https://klarna.com.fake-domain.com/checkout", context));
        assertFalse(validator.isValid("https://klarna-phishing.com/login", context));
    }

    @Test
    void shouldRejectMalformedUrls() {
        assertFalse(validator.isValid("not-a-url", context));
        assertFalse(validator.isValid("http:/malformed", context));
        assertFalse(validator.isValid("ftp:klarna.com", context));
    }
}
