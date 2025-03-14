package com.onlinestore.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodCategory {
    
    private AssetUrls assetUrls;
    
    private String identifier;
    
    private String name;
}
