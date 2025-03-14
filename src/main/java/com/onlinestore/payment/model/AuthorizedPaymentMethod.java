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
public class AuthorizedPaymentMethod {
    
    private Integer numberOfDays;
    
    private Integer numberOfInstallments;
    
    @NotBlank
    private String type;
}
