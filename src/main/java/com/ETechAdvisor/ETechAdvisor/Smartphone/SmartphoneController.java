package com.ETechAdvisor.ETechAdvisor.Smartphone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/smartphones")
public class SmartphoneController {

    private final SmartphoneService smartphoneService;

    @Autowired
    public SmartphoneController(SmartphoneService smartphoneService) {

        this.smartphoneService = smartphoneService;
        System.out.println(smartphoneService.toString());
    }

    @GetMapping("")
    public List<SmartphoneDTO> getOffers(
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String displayType,
            @RequestParam(required = false) Integer storageMin,
            @RequestParam(required = false) Integer storageMax,
            @RequestParam(required = false) Boolean hasAudioJack,
            @RequestParam(required = false) Integer megapixMin,
            @RequestParam(required = false) Integer megapixMax,
            @RequestParam(required = false) Integer batteryPowerMin,
            @RequestParam(required = false) Integer batteryPowerMax,
            @RequestParam(required = false) Double screenSizeMin,
            @RequestParam(required = false) Double screenSizeMax,
            @RequestParam(required = false) String os
    ) {
        return smartphoneService.getFilteredSmartphones(priceMin, priceMax, brand, displayType, storageMin,
                storageMax, hasAudioJack, megapixMin, megapixMax, batteryPowerMin, batteryPowerMax,
                screenSizeMin, screenSizeMax, os);
    }

    @GetMapping("/{id}")
    public SmartphoneResponse getSmartphoneById(@PathVariable Integer id) throws IOException {
        return smartphoneService.getSmartphoneById(id);
    }
}
