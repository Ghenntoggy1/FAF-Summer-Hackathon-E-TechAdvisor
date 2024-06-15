package com.ETechAdvisor.ETechAdvisor.Smartphone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Spec {
    private String name;
    private String value;
    private String description;
}
