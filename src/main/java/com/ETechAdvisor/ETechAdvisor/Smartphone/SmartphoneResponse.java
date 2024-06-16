package com.ETechAdvisor.ETechAdvisor.Smartphone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmartphoneResponse {
    private int id;
    private String name;
    private double avgPrice;
    private int score;
    private String imageUrl;
    private List<Overview> overview;
    private int storage;
    private int batteryPower;
    private double screenSize;
    private double megapix;
    private List<Price> priceTags;
    private List<Spec> display;
    private List<Spec> performance;
    private List<Spec> photoAudio;
}
