package com.onlinestore.payment.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {
    
    @Size(max = 1024)
    private String imageUrl;
    
    @Size(max = 1024)
    private String merchantData;
    
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;
    
    private ProductIdentifiers productIdentifiers;
    
    @Size(max = 1024)
    private String productUrl;
    
    @NotNull
    @Min(0)
    private Long quantity;
    
    @Size(min = 1, max = 8)
    private String quantityUnit;
    
    @Size(max = 255)
    private String reference;
    
    @Min(0)
    @Max(10000)
    private Integer taxRate;
    
    @NotNull
    @Max(200000000)
    private Long totalAmount;
    
    @Min(0)
    private Long totalDiscountAmount;
    
    private Long totalTaxAmount;
    
    private String type;
    
    @NotNull
    @Max(200000000)
    private Long unitPrice;
    
    private Subscription subscription;
}
