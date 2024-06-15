package com.ETechAdvisor.ETechAdvisor.Smartphone;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class SmartphoneService {
    private final SmartphoneRepository smartphoneRepository;

    private AtomicReference<Double> averageScreenSize = new AtomicReference<>(0.0);
    private AtomicReference<Double> averageProcessorSpeed = new AtomicReference<>(0.0);
    private AtomicReference<Integer> averageRefreshRate = new AtomicReference<>(0);
    private AtomicReference<Integer> averageRam = new AtomicReference<>(0);
    private AtomicReference<Integer> averageStorage = new AtomicReference<>(0);
    private AtomicReference<Integer> averageGeekbenchResult = new AtomicReference<>(0);
    private AtomicReference<Integer> averageBatteryPower = new AtomicReference<>(0);
    private AtomicReference<Double> averageMegapix = new AtomicReference<>(0.0);

    // Maximum values
    private AtomicReference<Double> maxScreenSize = new AtomicReference<>(0.0);
    private AtomicReference<Double> maxProcessorSpeed = new AtomicReference<>(0.0);
    private AtomicReference<Integer> maxRefreshRate = new AtomicReference<>(0);
    private AtomicReference<Integer> maxRam = new AtomicReference<>(0);
    private AtomicReference<Integer> maxStorage = new AtomicReference<>(0);
    private AtomicReference<Integer> maxGeekbenchResult = new AtomicReference<>(0);
    private AtomicReference<Integer> maxBatteryPower = new AtomicReference<>(0);
    private AtomicReference<Double> maxMegapix = new AtomicReference<>(0.0);
    private AtomicReference<Double> maxPrice = new AtomicReference<>(0.0);
    @Autowired
    public SmartphoneService(SmartphoneRepository smartphoneRepository) {
        this.smartphoneRepository = smartphoneRepository;
    }

    public List<SmartphoneDTO> getFilteredSmartphones(Double priceMin,
                                                      Double priceMax,
                                                      String brand,
                                                      String displayType,
                                                      Integer storageMin,
                                                      Integer storageMax,
                                                      Boolean hasAudioJack,
                                                      Integer megapixMin,
                                                      Integer megapixMax,
                                                      Integer batteryPowerMin,
                                                      Integer batteryPowerMax,
                                                      Double screenSizeMin,
                                                      Double screenSizeMax,
                                                      String os) {
        Specification<Smartphone> smartphoneSpecification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (priceMin != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("avgPrice"), priceMin));
            }

            if (priceMax != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("avgPrice"), priceMax));
            }

            if (brand != null) {
                predicateList.add(criteriaBuilder.equal(root.get("brand").get("brand_name"), brand));
            }

            if (displayType != null) {
                predicateList.add(criteriaBuilder.equal(root.get("displayType"), displayType));
            }

            if (storageMin != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("storage"), storageMin));
            }

            if (storageMax != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("storage"), storageMax));
            }

            if (hasAudioJack != null) {
                predicateList.add(criteriaBuilder.equal(root.get("hasAudioJack"), hasAudioJack));
            }

            if (megapixMin != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("megapix"), megapixMin));
            }

            if (megapixMax != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("megapix"), megapixMax));
            }

            if (batteryPowerMin != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("batteryPower"), batteryPowerMin));
            }

            if (batteryPowerMax != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("batteryPower"), batteryPowerMax));
            }

            if (screenSizeMin != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("screenSize"), screenSizeMin));
            }

            if (screenSizeMax != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("screenSize"), screenSizeMax));
            }

            if (os != null) {
                predicateList.add(criteriaBuilder.equal(root.get("os"), os));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };

        List<Smartphone> filteredSmartphones = smartphoneRepository.findAll(smartphoneSpecification);

        for (Smartphone smartphone : filteredSmartphones) {
            calculateScore(smartphone, priceMin, priceMax, batteryPowerMin,
                    batteryPowerMax, brand, displayType, storageMin, storageMax,
                    hasAudioJack, megapixMin, megapixMax, screenSizeMin, screenSizeMax,
                    os);
        }

        return filteredSmartphones.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private SmartphoneDTO mapToDTO(Smartphone smartphone) {
        return SmartphoneDTO.builder()
                .id(smartphone.getId())
                .name(smartphone.getName())
                .avgPrice(smartphone.getAvgPrice())
                .score(smartphone.getScore())
                .imageUrl(smartphone.getImageUrl())
                .storage(smartphone.getStorage())
                .batteryPower(smartphone.getBatteryPower())
                .screenSize(smartphone.getScreenSize())
                .megapix(smartphone.getMegapix())
                .build();
    }
    private void calculateScore(Smartphone smartphone,
                                Double priceMin,
                                Double priceMax,
                                Integer batteryPowerMin,
                                Integer batteryPowerMax,
                                String brand,
                                String displayType,
                                Integer storageMin,
                                Integer storageMax,
                                Boolean hasAudioJack,
                                Integer megapixMin,
                                Integer megapixMax,
                                Double screenSizeMin,
                                Double screenSizeMax,
                                String os){
        double score = 0;
        int total_weight = 0;

        double price_score;
        if (priceMin != null && priceMax != null) {
            price_score = 1 - ((smartphone.getAvgPrice() - priceMin) / (priceMax - priceMin));
        }
        else {
            price_score = smartphone.getAvgPrice() / maxPrice.get();
        }
        score += Math.max(0, Math.min(price_score, 1));
        total_weight += 1;

        double storage_score;
        if (storageMin != null && storageMax != null) {
            storage_score = (double) (smartphone.getStorage() - storageMin) / (storageMax - storageMin);
        }
        else {
            storage_score = (double) smartphone.getStorage() / maxStorage.get();
        }
        score += Math.max(0, Math.min(storage_score, 1));
        total_weight += 1;

        double battery_score;
        if (batteryPowerMin != null && batteryPowerMax != null) {
            battery_score = (double) (smartphone.getBatteryPower() - batteryPowerMin) / (batteryPowerMax - batteryPowerMin);
        }
        else {
            battery_score = (double) smartphone.getBatteryPower() / maxBatteryPower.get();
        }
        score += Math.max(0, Math.min(battery_score, 1));
        total_weight += 1;

        double screen_size_score;
        if (screenSizeMin != null && screenSizeMax != null) {
            screen_size_score = (smartphone.getScreenSize() - screenSizeMin) / (screenSizeMax - screenSizeMin);
        }
        else {
            screen_size_score = smartphone.getScreenSize() / maxScreenSize.get();
        }
        score += Math.max(0, Math.min(screen_size_score, 1));
        total_weight += 1;

        double megapix_score;
        if (megapixMin != null && megapixMax != null) {
            megapix_score = (smartphone.getMegapix() - megapixMin) / (megapixMax - megapixMin);
        }
        else {
            megapix_score = smartphone.getMegapix() / maxScreenSize.get();
        }
        score += Math.max(0, Math.min(megapix_score, 1));
        total_weight += 1;

        if (os != null) {
            if (smartphone.getOs().equalsIgnoreCase(os)) {
                score += 1;
            }
            total_weight += 1;
        }

        if (displayType != null) {
            if (smartphone.getDisplayType().equalsIgnoreCase(displayType)) {
                score += 1;
            }
            total_weight += 1;
        }

        if (brand != null) {
            if (smartphone.getBrand().getBrand_name().equalsIgnoreCase(brand)) {
                score += 1;
            }
            total_weight += 1;
        }

        if (hasAudioJack != null) {
            if (smartphone.getHasAudiojack() && hasAudioJack) {
                score += 1;
            }
            total_weight += 1;
        }

        int final_score = (int) ((score / total_weight) * 100);
        smartphone.setScore(final_score);
    }

    @PostConstruct
    public void init() {
        calculateAverages();
        calculateMaxValues();
    }

    public void calculateAverages() {
        List<Smartphone> smartphones = smartphoneRepository.findAll();
        int size = smartphones.size();
        double totalScreenSize = smartphones.stream()
                .mapToDouble(Smartphone::getScreenSize)
                .sum();
        averageScreenSize.set(totalScreenSize / size);

        double totalProcessorSpeed = smartphones.stream()
                .mapToDouble(Smartphone::getProcessorSpeed)
                .sum();
        averageProcessorSpeed.set(totalProcessorSpeed / size);

        int totalRefreshRate = smartphones.stream()
                .mapToInt(Smartphone::getRefreshRate)
                .sum();
        averageRefreshRate.set(totalRefreshRate / size);

        int totalRam = smartphones.stream()
                .mapToInt(Smartphone::getRam)
                .sum();
        averageRam.set( totalRam / size);

        int totalStorage = smartphones.stream()
                .mapToInt(Smartphone::getStorage)
                .sum();
        averageStorage.set(totalStorage / size);

        int totalGeekbenchResult = smartphones.stream()
                .mapToInt(Smartphone::getGeekbenchResult)
                .sum();
        averageGeekbenchResult.set(totalGeekbenchResult / size);

        int totalBatteryPower = smartphones.stream()
                .mapToInt(Smartphone::getBatteryPower)
                .sum();
        averageBatteryPower.set(totalBatteryPower / size);

        double totalMegapix = smartphones.stream()
                .mapToDouble(Smartphone::getMegapix)
                .sum();
        averageMegapix.set(totalMegapix / size);
    }
    public void calculateMaxValues() {
        List<Smartphone> smartphones = smartphoneRepository.findAll();

        double maxScreenSizeValue = smartphones.stream()
                .mapToDouble(Smartphone::getScreenSize)
                .max().orElse(0.0);
        maxScreenSize.set(maxScreenSizeValue);

        double maxProcessorSpeedValue = smartphones.stream()
                .mapToDouble(Smartphone::getProcessorSpeed)
                .max().orElse(0.0);
        maxProcessorSpeed.set(maxProcessorSpeedValue);

        int maxRefreshRateValue = smartphones.stream()
                .mapToInt(Smartphone::getRefreshRate)
                .max().orElse(0);
        maxRefreshRate.set(maxRefreshRateValue);

        int maxRamValue = smartphones.stream()
                .mapToInt(Smartphone::getRam)
                .max().orElse(0);
        maxRam.set(maxRamValue);

        int maxStorageValue = smartphones.stream()
                .mapToInt(Smartphone::getStorage)
                .max().orElse(0);
        maxStorage.set(maxStorageValue);

        int maxGeekbenchResultValue = smartphones.stream()
                .mapToInt(Smartphone::getGeekbenchResult)
                .max().orElse(0);
        maxGeekbenchResult.set(maxGeekbenchResultValue);

        int maxBatteryPowerValue = smartphones.stream()
                .mapToInt(Smartphone::getBatteryPower)
                .max().orElse(0);
        maxBatteryPower.set(maxBatteryPowerValue);

        double maxMegapixValue = smartphones.stream()
                .mapToDouble(Smartphone::getMegapix)
                .max().orElse(0.0);
        maxMegapix.set(maxMegapixValue);
        double maxPriceValue = smartphones.stream()
                .mapToDouble(Smartphone::getAvgPrice)
                .max().orElse(0.0);
        maxPrice.set(maxPriceValue);
    }

    @Override
    public String toString() {
        return "SmartphoneService{" +
                "smartphoneRepository=" + smartphoneRepository +
                ", averageScreenSize=" + averageScreenSize +
                ", averageProcessorSpeed=" + averageProcessorSpeed +
                ", averageRefreshRate=" + averageRefreshRate +
                ", averageRam=" + averageRam +
                ", averageStorage=" + averageStorage +
                ", averageGeekbenchResult=" + averageGeekbenchResult +
                ", averageBatteryPower=" + averageBatteryPower +
                ", averageMegapix=" + averageMegapix +
                ", maxScreenSize=" + maxScreenSize +
                ", maxProcessorSpeed=" + maxProcessorSpeed +
                ", maxRefreshRate=" + maxRefreshRate +
                ", maxRam=" + maxRam +
                ", maxStorage=" + maxStorage +
                ", maxGeekbenchResult=" + maxGeekbenchResult +
                ", maxBatteryPower=" + maxBatteryPower +
                ", maxMegapix=" + maxMegapix +
                '}';
    }

    public SmartphoneResponse getSmartphoneById(Integer id){
        Smartphone smartphone = smartphoneRepository.findById(id).orElse(null);

        if(smartphone == null) {
            return null;
        }
        List<Overview> overviews = getOverview(smartphone);
        SmartphoneResponse smartphoneResponse = SmartphoneResponse.builder()
                .id(id)
                .name(smartphone.getName())
                .avgPrice(smartphone.getAvgPrice())
                .score(smartphone.getScore())
                .imageUrl(smartphone.getImageUrl())

                .build();

        return null;
    }
    private List<Overview> getOverview(Smartphone smartphone){
        List<Overview> overviews = new ArrayList<>();
        if(smartphone.getScreenSize() > averageScreenSize.get()){
            overviews.add(new Overview(smartphone.getScreenSize(), averageScreenSize.get(), "cm", "Screen Size", "The size of the screen (measured diagonally)."));
        }
        return null;
    }
}
