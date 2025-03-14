package com.onlinestore.payment.model;

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
public class Address {
    
    @Size(max = 99)
    private String attention;
    
    @Size(max = 99)
    private String city;
    
    @Pattern(regexp = "^[A-Za-z]{2,2}$")
    private String country;
    
    @Size(max = 99)
    private String email;
    
    @Size(max = 99)
    private String familyName;
    
    @Size(max = 99)
    private String givenName;
    
    @Size(max = 99)
    private String organizationName;
    
    @Size(max = 99)
    private String phone;
    
    @Size(max = 10)
    private String postalCode;
    
    @Size(max = 99)
    private String region;
    
    @Size(max = 99)
    private String streetAddress;
    
    @Size(max = 99)
    private String streetAddress2;
    
    @Size(max = 20)
    private String title;
}
