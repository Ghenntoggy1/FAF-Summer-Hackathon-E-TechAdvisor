package com.ETechAdvisor.ETechAdvisor.Smartphone;

import com.ETechAdvisor.ETechAdvisor.ChatGPT.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping("/api/smartphones")
public class SmartphoneController {

    private final SmartphoneService smartphoneService;
    @Autowired
    private ChatGptService chatGptService;
    @Autowired
    private SmartphoneRepository smartphoneRepository;

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
            @RequestParam(required = false) Double processorSpeedMin,
            @RequestParam(required = false) Double processorSpeedMax,
            @RequestParam(required = false) Integer refreshRateMin,
            @RequestParam(required = false) Integer refreshRateMax,
            @RequestParam(required = false) Integer ramMin,
            @RequestParam(required = false) Integer ramMax,
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
        return smartphoneService.getFilteredSmartphones(priceMin, priceMax, processorSpeedMin, processorSpeedMax,
                refreshRateMin, refreshRateMax, batteryPowerMin, batteryPowerMax, ramMin, ramMax, brand, displayType,
                storageMin, storageMax, hasAudioJack, megapixMin, megapixMax, screenSizeMin, screenSizeMax, os);
    }

    @GetMapping("/{id}")
    public SmartphoneResponse getSmartphoneById(@PathVariable Integer id) throws IOException, ExecutionException, InterruptedException {
        return smartphoneService.getSmartphoneById(id);
    }

    @GetMapping("/{id}/vs/{other}")
    public CompareResponse compareSmartphones(@PathVariable Integer id,
                                              @PathVariable Integer other) throws IOException, ExecutionException, InterruptedException {

        return smartphoneService.compareSmartphones(id, other);
    }

    @GetMapping("/prompt/{prompt}")
    public ResponseEntity<List<SmartphoneDTO>> getScoredSmartphones(@PathVariable String prompt) {
        String completePrompt = smartphoneService.buildPrompt(prompt);
        System.out.println(completePrompt);
        Map<String, Double> importanceValues = chatGptService.getImportanceValues(completePrompt);
        System.out.println(importanceValues);
        List<Smartphone> smartphones = smartphoneRepository.findAll();
        List<SmartphoneDTO> scoredSmartphones = smartphoneService.calculateAndSortScores(smartphones, importanceValues);
        return ResponseEntity.ok(scoredSmartphones);
    }


    @GetMapping("/chart/{id}")
    public ChartDataSingle getSingleChartData(@PathVariable Integer id){
        return smartphoneService.getSingleChartData(id);
    }

    @GetMapping("/chart/{id}/vs/{other}")
    public ChartDataDouble getDoubleChartData(@PathVariable Integer id, @PathVariable Integer other) {
        return smartphoneService.getDoubleChartData(id, other);
    }
}
