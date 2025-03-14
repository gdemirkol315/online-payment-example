package com.onlinestore.payment.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductIdentifiers {
    
    @Size(max = 70)
    private String brand;
    
    @Size(max = 750)
    private String categoryPath;
    
    @Size(max = 50)
    private String globalTradeItemNumber;
    
    @Size(max = 70)
    private String manufacturerPartNumber;
    
    @Size(max = 64)
    private String color;
    
    @Size(max = 64)
    private String size;
}
