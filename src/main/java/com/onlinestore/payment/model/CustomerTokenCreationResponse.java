package com.onlinestore.payment.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTokenCreationResponse {
    
    private Address billingAddress;
    
    private CustomerReadCreateToken customer;
    
    private String paymentMethodReference;
    
    private String redirectUrl;
    
    @NotBlank
    private String tokenId;
}
