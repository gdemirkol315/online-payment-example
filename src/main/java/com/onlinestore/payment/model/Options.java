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
public class Options {
    
    @Pattern(regexp = "^#[A-Fa-f0-9]{6}$")
    private String colorBorder;
    
    @Pattern(regexp = "^#[A-Fa-f0-9]{6}$")
    private String colorBorderSelected;
    
    @Pattern(regexp = "^#[A-Fa-f0-9]{6}$")
    private String colorDetails;
    
    @Pattern(regexp = "^#[A-Fa-f0-9]{6}$")
    private String colorText;
    
    private String radiusBorder;
}
