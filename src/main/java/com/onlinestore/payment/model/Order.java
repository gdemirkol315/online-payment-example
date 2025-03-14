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
public class Order {
    
    private AuthorizedPaymentMethod authorizedPaymentMethod;
    
    private String fraudStatus;
    
    @NotBlank
    private String orderId;
    
    private String redirectUrl;
}
