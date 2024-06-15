package com.ETechAdvisor.ETechAdvisor.Smartphone;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private String name;
    private String url;
    private Double price;
    private String merchant;
}
