package com.onlinestore.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReadCreateToken {
    
    private String dateOfBirth;
    
    private String gender;
}
