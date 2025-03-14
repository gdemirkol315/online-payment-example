package com.onlinestore.payment.model;

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
public class Subscription {
    
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;
    
    @NotBlank
    private String interval;
    
    @NotNull
    @Min(1)
    private Integer intervalCount;
}
