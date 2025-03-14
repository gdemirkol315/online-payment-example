package com.onlinestore.payment.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class CreateOrderRequest {
    
    private String authorizationToken;
    
    private Boolean autoCapture;
    
    private Address billingAddress;
    
    private List<String> customPaymentMethodIds;
    
    private Customer customer;
    
    @Pattern(regexp = "^[A-Za-z]{2,2}(?:-[A-Za-z]{2,2})*$")
    private String locale;
    
    @Size(max = 6000)
    private String merchantData;
    
    @Size(max = 255)
    private String merchantReference1;
    
    @Size(max = 255)
    private String merchantReference2;
    
    private MerchantUrls merchantUrls;
    
    @NotNull
    @Min(0)
    private Long orderAmount;
    
    @NotNull
    private List<OrderLine> orderLines;
    
    @Min(0)
    private Long orderTaxAmount;
    
    private List<PaymentMethodCategory> paymentMethodCategories;
    
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{2,2}$")
    private String purchaseCountry;
    
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{3,3}$")
    private String purchaseCurrency;
    
    private Address shippingAddress;
    
    private String status;
}
