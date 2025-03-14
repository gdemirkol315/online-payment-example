package com.onlinestore.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorV2 {
    
    private String correlationId;
    
    private String errorCode;
    
    private List<String> errorMessages;
}
