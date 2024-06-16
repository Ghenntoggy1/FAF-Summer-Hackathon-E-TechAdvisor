package com.ETechAdvisor.ETechAdvisor.Smartphone;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Overview {
    private double value;
    private double other;
    private String unit;
    private String title;
    private String description;
}
