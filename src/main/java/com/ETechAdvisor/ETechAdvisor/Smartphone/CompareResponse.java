package com.ETechAdvisor.ETechAdvisor.Smartphone;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompareResponse {

    private Integer winner;
    private SmartphoneResponse smartphoneOne;
    private SmartphoneResponse smartphoneTwo;
}
