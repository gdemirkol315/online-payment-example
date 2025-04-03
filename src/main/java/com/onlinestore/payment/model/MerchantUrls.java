package com.onlinestore.payment.model;

import com.onlinestore.payment.validation.KlarnaUrl;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantUrls {
    
    @Size(max = 2000)
    @KlarnaUrl
    private String confirmation;
    
    @Size(max = 2000)
    @KlarnaUrl
    private String notification;
    
    @Size(max = 2000)
    @KlarnaUrl
    private String push;
    
    @Size(max = 2000)
    @KlarnaUrl
    private String authorization;
}
