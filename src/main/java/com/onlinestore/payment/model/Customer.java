package com.onlinestore.payment.model;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    private String dateOfBirth;
    
    private String gender;
    
    @Pattern(regexp = "^([0-9]{4}|[0-9]{9})$")
    private String lastFourSsn;
    
    private String nationalIdentificationNumber;
    
    private String organizationEntityType;
    
    private String organizationRegistrationId;
    
    private String title;
    
    @Pattern(regexp = "^(person|organization)$")
    private String type;
    
    private String vatId;
}
