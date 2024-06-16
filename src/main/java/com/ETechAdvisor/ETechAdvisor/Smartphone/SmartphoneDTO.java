package com.ETechAdvisor.ETechAdvisor.Smartphone;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SmartphoneDTO {
    private Integer id;
    private String name;
    private Double avgPrice;
    private Integer score;
    private String imageUrl;
    private Integer storage;
    private Integer batteryPower;
    private Double screenSize;
    private Double megapix;
//    Privet Dima, kak dela?
}
