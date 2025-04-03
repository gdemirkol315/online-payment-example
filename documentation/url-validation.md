# URL Validation for Payment Provider Redirects

## Overview

This document describes the URL validation mechanism implemented to protect users from malicious redirects during the payment flow. The validation ensures that users are only redirected to legitimate Klarna pages and prevents potential attackers from hijacking response links to redirect users to phishing or malicious sites.

## Implementation Details

The validation is implemented using a custom Jakarta Validation annotation (`@KlarnaUrl`) and validator that checks if URLs in the `MerchantUrls` class are pointing to whitelisted domains.

### Components

1. **KlarnaUrl Annotation**: A custom validation annotation that marks URL fields for validation.
2. **KlarnaUrlValidator**: The validator implementation that checks if URLs are from whitelisted domains.
3. **MerchantUrls Class**: Updated to use the `@KlarnaUrl` annotation on all URL fields.

### Whitelisted Domains

The following domains are whitelisted for redirection:

- klarna.com and all subdomains
- playground.klarna.com
- api.klarna.com
- api-na.klarna.com
- api-oc.klarna.com
- credit.klarna.com
- cdn.klarna.com
- js.klarna.com
- x.klarnacdn.net
- pay.klarna.com
- checkout.klarna.com
- localhost (for development)
- 127.0.0.1 (for development)

### Validation Logic

The validator performs the following checks:

1. Null or empty URLs are considered valid (use `@NotNull` if the field is required).
2. The URL must be well-formed (valid syntax).
3. The URL's host must either be in the whitelist or be a subdomain of a whitelisted domain.

## Security Benefits

This validation provides several security benefits:

1. **Prevents Phishing Attacks**: Users cannot be redirected to fake Klarna sites designed to steal credentials.
2. **Prevents Open Redirect Vulnerabilities**: The application cannot be used as a redirect proxy to arbitrary sites.
3. **Reduces Risk of Session Hijacking**: Limits the potential for attackers to steal session information through malicious redirects.

## Testing

Two test classes have been created to verify the validation:

1. **KlarnaUrlValidatorTest**: Tests the validator directly with various URL scenarios.
2. **MerchantUrlsTest**: Tests the integration of the validator with the `MerchantUrls` model.

## Extending the Whitelist

To add additional domains to the whitelist, modify the `WHITELISTED_DOMAINS` list in the `KlarnaUrlValidator` class. Be cautious when adding new domains and ensure they are legitimate and trusted.
