package com.onlinestore.payment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantSession {
    
    @NotBlank
    @Size(min = 0, max = 4096)
    private String clientToken;
    
    private List<PaymentMethodCategory> paymentMethodCategories;
    
    @NotBlank
    @Size(min = 0, max = 255)
    private String sessionId;
}
