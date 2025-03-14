package com.onlinestore.payment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTokenCreationRequest {
    
    private Address billingAddress;
    
    private Customer customer;
    
    @NotBlank
    @Size(min = 1, max = 255)
    private String description;
    
    @NotBlank
    private String intendedUse;
    
    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{2,2}(?:-[A-Za-z]{2,2})*$")
    private String locale;
    
    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{2,2}$")
    private String purchaseCountry;
    
    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{3,3}$")
    private String purchaseCurrency;
}
