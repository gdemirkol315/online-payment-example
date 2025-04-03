package com.onlinestore.payment.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Validator for the KlarnaUrl annotation.
 * Ensures that URLs are only from whitelisted domains to prevent malicious redirects.
 */
@Component
public class KlarnaUrlValidator implements ConstraintValidator<KlarnaUrl, String> {

    // List of whitelisted domains that are allowed for redirection
    private static final List<String> WHITELISTED_DOMAINS = Arrays.asList(
            "klarna.com",
            "playground.klarna.com",
            "api.klarna.com",
            "api-na.klarna.com",
            "api-oc.klarna.com",
            "credit.klarna.com",
            "cdn.klarna.com",
            "js.klarna.com",
            "x.klarnacdn.net",
            "pay.klarna.com",
            "checkout.klarna.com",
            // Add your application's domains here
            "localhost",
            "127.0.0.1"
            // Add any other legitimate domains that should be allowed
    );

    @Override
    public void initialize(KlarnaUrl constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Null values are considered valid (use @NotNull if the field is required)
        if (value == null || value.isEmpty()) {
            return true;
        }

        try {
            URL url = new URL(value);
            String host = url.getHost();
            
            // Check if the host is in the whitelist or is a subdomain of a whitelisted domain
            return WHITELISTED_DOMAINS.stream()
                    .anyMatch(domain -> host.equals(domain) || host.endsWith("." + domain));
                    
        } catch (MalformedURLException e) {
            // If the URL is malformed, it's not valid
            return false;
        }
    }
}
