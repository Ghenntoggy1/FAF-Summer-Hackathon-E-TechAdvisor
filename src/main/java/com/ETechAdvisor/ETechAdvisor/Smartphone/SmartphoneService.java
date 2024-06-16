package com.ETechAdvisor.ETechAdvisor.Smartphone;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
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
                                                      Double processorSpeedMin,
                                                      Double processorSpeedMax,
                                                      Integer refreshRateMin,
                                                      Integer refreshRateMax,
                                                      Integer batteryPowerMin,
                                                      Integer batteryPowerMax,
                                                      Integer ramMin,
                                                      Integer ramMax,
                                                      String brand,
                                                      String displayType,
                                                      Integer storageMin,
                                                      Integer storageMax,
                                                      Boolean hasAudioJack,
                                                      Integer megapixMin,
                                                      Integer megapixMax,
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

            if (processorSpeedMin != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("processorSpeed"), processorSpeedMin));
            }

            if (processorSpeedMax != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("processorSpeed"), processorSpeedMax));
            }

            if (refreshRateMin != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("refreshRate"), refreshRateMin));
            }

            if (refreshRateMax != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("refreshRate"), refreshRateMax));
            }

            if (batteryPowerMin != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("batteryPower"), batteryPowerMin));
            }

            if (batteryPowerMax != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("batteryPower"), batteryPowerMax));
            }

            if (ramMin != null) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ram"), ramMin));
            }

            if (ramMax != null) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("ram"), ramMax));
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
            calculateScore(smartphone, priceMin, priceMax, processorSpeedMin, processorSpeedMax, refreshRateMin,
                    refreshRateMax, batteryPowerMin, batteryPowerMax, ramMin, ramMax, brand, displayType, storageMin,
                    storageMax, hasAudioJack, megapixMin, megapixMax, screenSizeMin, screenSizeMax, os);
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
                                Double processorSpeedMin,
                                Double processorSpeedMax,
                                Integer refreshRateMin,
                                Integer refreshRateMax,
                                Integer batteryPowerMin,
                                Integer batteryPowerMax,
                                Integer ramMin,
                                Integer ramMax,
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

        score += Math.max(0, Math.min(getPriceScore(smartphone, priceMin, priceMax), 1));
        total_weight += 1;

        score += Math.max(0, Math.min(getRAMScore(smartphone, ramMin, ramMax), 1));
        total_weight += 1;

        score += Math.max(0, Math.min(getProcessorScore(smartphone, processorSpeedMin, processorSpeedMax), 1));
        total_weight += 1;

        score += Math.max(0, Math.min(getRefreshScore(smartphone, refreshRateMin, refreshRateMax), 1));
        total_weight += 1;

        score += Math.max(0, Math.min(getStorageScore(smartphone, storageMin, storageMax), 1));
        total_weight += 1;

        score += Math.max(0, Math.min(getBatteryScore(smartphone, batteryPowerMin, batteryPowerMax), 1));
        total_weight += 1;

        score += Math.max(0, Math.min(getScreenSizeScore(smartphone, screenSizeMin, screenSizeMax), 1));
        total_weight += 1;

        score += Math.max(0, Math.min(getMegapixScore(smartphone, megapixMin, megapixMax), 1));
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

    private int getRAMScore(Smartphone smartphone, Integer ramMin, Integer ramMax) {
        int ram_score;
        if (ramMin != null && ramMax != null) {
            ram_score = (smartphone.getRam() - ramMin) / (ramMax - ramMin);
        }
        else {
            ram_score = smartphone.getRam() / maxRam.get();
        }
        return ram_score;
    }

    private int getRefreshScore(Smartphone smartphone, Integer refreshRateMin, Integer refreshRateMax) {
        int refresh_rate_score;
        if (refreshRateMin != null && refreshRateMax != null) {
            refresh_rate_score = (smartphone.getRefreshRate() - refreshRateMin) / (refreshRateMax - refreshRateMin);
        }
        else {
            refresh_rate_score = smartphone.getRefreshRate() / maxRefreshRate.get();
        }
        return refresh_rate_score;
    }

    private double getProcessorScore(Smartphone smartphone, Double processorSpeedMin, Double processorSpeedMax) {
        double processor_score;
        if (processorSpeedMin != null && processorSpeedMax != null) {
            processor_score = (smartphone.getProcessorSpeed() - processorSpeedMin) / (processorSpeedMax - processorSpeedMin);
        }
        else {
            processor_score = smartphone.getProcessorSpeed() / maxProcessorSpeed.get();
        }
        return processor_score;
    }

    private double getMegapixScore(Smartphone smartphone, Integer megapixMin, Integer megapixMax) {
        double megapix_score;
        if (megapixMin != null && megapixMax != null) {
            megapix_score = (smartphone.getMegapix() - megapixMin) / (megapixMax - megapixMin);
        }
        else {
            megapix_score = smartphone.getMegapix() / maxScreenSize.get();
        }
        return megapix_score;
    }

    private double getScreenSizeScore(Smartphone smartphone, Double screenSizeMin, Double screenSizeMax) {
        double screen_size_score;
        if (screenSizeMin != null && screenSizeMax != null) {
            screen_size_score = (smartphone.getScreenSize() - screenSizeMin) / (screenSizeMax - screenSizeMin);
        }
        else {
            screen_size_score = smartphone.getScreenSize() / maxScreenSize.get();
        }
        return screen_size_score;
    }

    private double getBatteryScore(Smartphone smartphone, Integer batteryPowerMin, Integer batteryPowerMax) {
        double battery_score;
        if (batteryPowerMin != null && batteryPowerMax != null) {
            battery_score = (double) (smartphone.getBatteryPower() - batteryPowerMin) / (batteryPowerMax - batteryPowerMin);
        }
        else {
            battery_score = (double) smartphone.getBatteryPower() / maxBatteryPower.get();
        }
        return battery_score;
    }

    private double getStorageScore(Smartphone smartphone, Integer storageMin, Integer storageMax) {
        double storage_score;
        if (storageMin != null && storageMax != null) {
            storage_score = (double) (smartphone.getStorage() - storageMin) / (storageMax - storageMin);
        }
        else {
            storage_score = (double) smartphone.getStorage() / maxStorage.get();
        }
        return storage_score;
    }

    private double getPriceScore(Smartphone smartphone, Double priceMin, Double priceMax) {
        double price_score;
        if (priceMin != null && priceMax != null) {
            price_score = 1 - ((smartphone.getAvgPrice() - priceMin) / (priceMax - priceMin));
        }
        else {
            price_score = smartphone.getAvgPrice() / maxPrice.get();
        }
        return price_score;
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

    public SmartphoneResponse getSmartphoneById(Integer id) throws IOException, ExecutionException, InterruptedException {
        Smartphone smartphone = smartphoneRepository.findById(id).orElse(null);

        if(smartphone == null) {
            return null;
        }
        List<Overview> overviews = getOverview(smartphone);
        List<Price> prices = getPrices(smartphone.getName());

        List<Spec> display = new ArrayList<>(Arrays.asList(
            Spec.builder()
                 .name("Screen size")
                 .value(String.valueOf(smartphone.getScreenSize()))
                 .description("The size of the screen (measured diagonally).")
                 .build(),
            Spec.builder()
                    .name("Display Type")
                    .value(smartphone.getDisplayType())
                    .description("The type of technology used in the display.")
                    .build(),
            Spec.builder()
                    .name("Refresh Rate")
                    .value(String.valueOf(smartphone.getRefreshRate()))
                    .description("Refresh rate for a display refers to the number of times per second the screen updates its image, measured in Hertz (Hz). A higher refresh rate results in smoother motion on the screen, which is especially important for fast-paced activities like gaming and watching videos.")
                    .build(),
            Spec.builder()
                    .name("Resolution")
                    .value(smartphone.getResolution())
                    .description("The frequency at which the display is refreshed (1 Hz = once per second). A higher refresh rate results in smoother UI animations and video playback.")
                    .build()
        ));
        List<Spec> performance = new ArrayList<>(Arrays.asList(
                Spec.builder()
                        .name("RAM")
                        .value(String.valueOf(smartphone.getRam()))
                        .description("Random-access memory (RAM) is a form of memory used to store working data and machine code. Having more RAM is particularly useful for multitasking, allowing you to run more programs at once or have more tabs open in your browser.")
                        .build(),
                Spec.builder()
                        .name("Processor Model")
                        .value(smartphone.getProcessorModel())
                        .description("A processor model is like the brain of a computer or phone, deciding how fast it can think and work on tasks. Different models have varying abilities, like speed and efficiency, which affect how well devices can handle tasks and run programs.")
                        .build(),
                Spec.builder()
                        .name("Processor Speed")
                        .value(String.valueOf(smartphone.getProcessorSpeed()))
                        .description("The CPU speed indicates how many processing cycles per second can be executed by a CPU, considering all of its cores (processing units). It is calculated by adding the clock rates of each core or, in the case of multi-core processors employing different microarchitectures, of each group of cores.")
                        .build(),
                Spec.builder()
                        .name("Internal Storage")
                        .value(String.valueOf(smartphone.getStorage()))
                        .description("The CPU speed indicates how many processing cycles per second can be executed by a CPU, considering all of its cores (processing units). It is calculated by adding the clock rates of each core or, in the case of multi-core processors employing different microarchitectures, of each group of cores.")
                        .build(),
                Spec.builder()
                        .name("Geekbench 6 Results")
                        .value(String.valueOf(smartphone.getGeekbenchResult()))
                        .description("A Geekbench result is a score that measures the performance of a smartphone's processor and memory. Higher scores indicate better performance, helping users compare the capabilities of different devices.")
                        .build(),
                Spec.builder()
                        .name("Battery Power")
                        .value(String.valueOf(smartphone.getBatteryPower()))
                        .description("Battery power, or battery capacity, represents the amount of electrical energy that a battery can store. More battery power can be an indication of longer battery life.")
                        .build()
        ));
        List<Spec> photoAudio = new ArrayList<>(Arrays.asList(
                Spec.builder()
                        .name("Camera Megapixels")
                        .value(String.valueOf(smartphone.getMegapix()))
                        .description("The number of megapixels determines the resolution of the images captured with the main camera. A higher megapixel count means that the camera is capable of capturing more details. However, the megapixel count is not the only important element determining the quality of an image.")
                        .build(),
                Spec.builder()
                        .name("Audio Jack")
                        .value(String.valueOf(smartphone.getHasAudiojack()))
                        .description("With a standard mini jack socket, you can use the device with most headphones.")
                        .build(),
                Spec.builder()
                        .name("Stereo Speakers")
                        .value(smartphone.getStereoSpeakers())
                        .description("Devices with stereo speakers deliver sound from independent channels on both left and right sides, creating a richer sound and a better experience.")
                        .build()
        ));
        calculateScore(smartphone, null, null, null, null, null,null,null,null,null,null,null,null,null,null,null,null,null,null,null, null);

        return SmartphoneResponse.builder()
                .id(id)
                .name(smartphone.getName())
                .avgPrice(smartphone.getAvgPrice())
                .score(smartphone.getScore())
                .imageUrl(smartphone.getImageUrl())
                .overview(overviews)
                .storage(smartphone.getStorage())
                .batteryPower(smartphone.getBatteryPower())
                .screenSize(smartphone.getScreenSize())
                .megapix(smartphone.getMegapix())
                .priceTags(prices)
                .display(display)
                .performance(performance)
                .photoAudio(photoAudio)
                .build();
    }

    public List<Price> getPrices(String name) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3); // Create a thread pool with 3 threads
        PriceApi priceApi = new PriceApi();
        List<Callable<Price>> callables = List.of(
                () -> priceApi.getPrice(name, "amazon"),
                () -> priceApi.getPrice(name, "ebay"),
                () -> priceApi.getPrice(name, "google_shopping")
        );

        List<Future<Price>> futures = executorService.invokeAll(callables);

        List<Price> prices = new ArrayList<>();

        for (Future<Price> future : futures) {
            try {
                Price price = future.get(); // This will block until the result is available
                if (price != null) {
                    prices.add(price);
                }
            } catch (InterruptedException | ExecutionException e) {
                // Handle exceptions as needed
                e.printStackTrace();
            }
        }

        executorService.shutdown(); // Shutdown the executor service

        return prices;
    }
    private List<Overview> getOverview(Smartphone smartphone){
        List<Overview> overviews = new ArrayList<>();
        if(smartphone.getScreenSize() > averageScreenSize.get()){
            overviews.add(new Overview(smartphone.getScreenSize(), averageScreenSize.get(), "cm", "Screen Size", "The size of the screen (measured diagonally)."));
        }

        if (smartphone.getProcessorSpeed() > averageProcessorSpeed.get()){
            overviews.add(new Overview(smartphone.getProcessorSpeed(), averageProcessorSpeed.get(), "GHz", "Processor Speed", "The CPU speed indicates how many processing cycles per second can be executed by a CPU, considering all of its cores (processing units). It is calculated by adding the clock rates of each core or, in the case of multi-core processors employing different microarchitectures, of each group of cores."));
        }

        if (smartphone.getRefreshRate() > averageRefreshRate.get()){
            overviews.add(new Overview(smartphone.getRefreshRate(), averageRefreshRate.get(), "Hz", "Refresh Rate", "The frequency at which the display is refreshed (1 Hz = once per second). A higher refresh rate results in smoother UI animations and video playback."));
        }

        if (smartphone.getRam() > averageRam.get()){
            overviews.add(new Overview(smartphone.getRam(), averageRam.get(), "GB", "RAM", "Random-access memory (RAM) is a form of memory used to store working data and machine code. Having more RAM is particularly useful for multitasking, allowing you to run more programs at once or have more tabs open in your browser."));
        }

        if (smartphone.getStorage() > averageStorage.get()){
            overviews.add(new Overview(smartphone.getStorage(), averageStorage.get(), "GB", "Internal Storage Size", "The internal storage refers to the built-in storage space available in a device for system data, apps, and user-generated data. With a large amount of internal storage, you can save more files and apps on your device."));
        }

        if (smartphone.getGeekbenchResult() > averageGeekbenchResult.get()){
            overviews.add(new Overview(smartphone.getGeekbenchResult(), averageGeekbenchResult.get(), "Points", "Geekbench Rating", "A Geekbench result is a score that measures the performance of a smartphone's processor and memory. Higher scores indicate better performance, helping users compare the capabilities of different devices."));
        }

        if (smartphone.getBatteryPower() > averageBatteryPower.get()){
            overviews.add(new Overview(smartphone.getBatteryPower(), averageBatteryPower.get(), "mAh", "Battery Power", "Battery power, or battery capacity, represents the amount of electrical energy that a battery can store. More battery power can be an indication of longer battery life."));
        }

        if (smartphone.getMegapix() > averageMegapix.get()){
            overviews.add(new Overview(smartphone.getMegapix(), averageMegapix.get(), "MP", "Megapixels", "The number of megapixels determines the resolution of the images captured with the main camera. A higher megapixel count means that the camera is capable of capturing more details. However, the megapixel count is not the only important element determining the quality of an image."));
        }

        return overviews;
    }
    private List<Overview> getOverview(Smartphone smartphone1, Smartphone smartphone2){
        List<Overview> overviews = new ArrayList<>();
        if(smartphone1.getScreenSize() > smartphone2.getScreenSize()){
            Double screenSizePercentage = ((smartphone1.getScreenSize() - smartphone2.getScreenSize()) / smartphone2.getScreenSize()) * 100;
            overviews.add(new Overview(smartphone1.getScreenSize(), smartphone2.getScreenSize(), "cm", screenSizePercentage + "% Bigger Screen Size", "The size of the screen (measured diagonally)."));
        }

        if (smartphone1.getProcessorSpeed() > smartphone2.getProcessorSpeed()){
            Double processorPercentage = ((smartphone1.getProcessorSpeed() - smartphone2.getProcessorSpeed()) / (double) smartphone2.getProcessorSpeed()) * 100;
            overviews.add(new Overview(smartphone1.getProcessorSpeed(), smartphone2.getProcessorSpeed(), "GHz", processorPercentage + "% Faster CPU Speed", "The CPU speed indicates how many processing cycles per second can be executed by a CPU, considering all of its cores (processing units). It is calculated by adding the clock rates of each core or, in the case of multi-core processors employing different microarchitectures, of each group of cores."));
        }

        if (smartphone1.getRefreshRate() > smartphone2.getRefreshRate()){
            Double ratePercentage = ((smartphone1.getRefreshRate() - smartphone2.getRefreshRate()) / (double) smartphone2.getRefreshRate()) * 100;
            overviews.add(new Overview(smartphone1.getRefreshRate(), smartphone2.getRefreshRate(), "Hz", ratePercentage + "% Smoother Video Playback ", "The frequency at which the display is refreshed (1 Hz = once per second). A higher refresh rate results in smoother UI animations and video playback."));
        }

        if (smartphone1.getRam() > smartphone2.getRam()){
            Double ramPercentage = ((smartphone1.getRam() - smartphone2.getRam()) / (double) smartphone1.getRam()) * 100;
            overviews.add(new Overview(smartphone1.getRam(), smartphone2.getRam(), "GB", ramPercentage + "% Faster Memory Operations", "Random-access memory (RAM) is a form of memory used to store working data and machine code. Having more RAM is particularly useful for multitasking, allowing you to run more programs at once or have more tabs open in your browser."));
        }

        if (smartphone1.getStorage() > smartphone2.getStorage()){
            Double storagePercentage = ((smartphone1.getStorage() - smartphone2.getStorage()) / (double) smartphone2.getStorage()) * 100;
            overviews.add(new Overview(smartphone1.getStorage(), smartphone2.getStorage(), "GB", storagePercentage + "% Higher Capacity for Internal Storage", "The internal storage refers to the built-in storage space available in a device for system data, apps, and user-generated data. With a large amount of internal storage, you can save more files and apps on your device."));
        }

        if (smartphone1.getGeekbenchResult() > smartphone2.getGeekbenchResult()){
            Double geekbenchPercentage = ((smartphone1.getGeekbenchResult() - smartphone2.getGeekbenchResult()) / (double) smartphone2.getGeekbenchResult()) * 100;
            overviews.add(new Overview(smartphone1.getGeekbenchResult(), smartphone2.getGeekbenchResult(), "Points",  geekbenchPercentage+ "% Higher Geekbench Rating", "A Geekbench result is a score that measures the performance of a smartphone's processor and memory. Higher scores indicate better performance, helping users compare the capabilities of different devices."));
        }

        if (smartphone1.getBatteryPower() > smartphone2.getBatteryPower()){
            Integer diffBatteryPower = smartphone1.getBatteryPower() - smartphone2.getBatteryPower();
            Double batteryPercentage = ((smartphone1.getBatteryPower() - smartphone2.getBatteryPower()) / (double) smartphone2.getBatteryPower()) * 100;
            overviews.add(new Overview(smartphone1.getBatteryPower(), smartphone2.getBatteryPower(), "mAh", batteryPercentage + "% Longer Work Time - approx. " + diffBatteryPower / (0.75 * 1000) + " More Time", "Battery power, or battery capacity, represents the amount of electrical energy that a battery can store. More battery power can be an indication of longer battery life."));
        }

        if (smartphone1.getMegapix() > smartphone2.getMegapix()){
            Double megapixPercentage = ((smartphone1.getMegapix() - smartphone2.getMegapix()) / smartphone2.getMegapix()) * 100;
            overviews.add(new Overview(smartphone1.getMegapix(), smartphone1.getMegapix(), "MP", megapixPercentage + "% More Megapixels", "The number of megapixels determines the resolution of the images captured with the main camera. A higher megapixel count means that the camera is capable of capturing more details. However, the megapixel count is not the only important element determining the quality of an image."));
        }

        return overviews;
    }

    private SmartphoneResponse createSmartphone(Smartphone smartphone, Smartphone smartphone2) throws IOException, ExecutionException, InterruptedException {
        if(smartphone == null) {
            return null;
        }
        List<Overview> overviews = getOverview(smartphone,smartphone2);
        List<Price> prices = getPrices(smartphone.getName());

        List<Spec> display = new ArrayList<>(Arrays.asList(
                Spec.builder()
                        .name("Screen size")
                        .value(String.valueOf(smartphone.getScreenSize()))
                        .description("The size of the screen (measured diagonally).")
                        .build(),
                Spec.builder()
                        .name("Display Type")
                        .value(smartphone.getDisplayType())
                        .description("The type of technology used in the display.")
                        .build(),
                Spec.builder()
                        .name("Refresh Rate")
                        .value(String.valueOf(smartphone.getRefreshRate()))
                        .description("Refresh rate for a display refers to the number of times per second the screen updates its image, measured in Hertz (Hz). A higher refresh rate results in smoother motion on the screen, which is especially important for fast-paced activities like gaming and watching videos.")
                        .build(),
                Spec.builder()
                        .name("Resolution")
                        .value(smartphone.getResolution())
                        .description("The frequency at which the display is refreshed (1 Hz = once per second). A higher refresh rate results in smoother UI animations and video playback.")
                        .build()
        ));
        List<Spec> performance = new ArrayList<>(Arrays.asList(
                Spec.builder()
                        .name("RAM")
                        .value(String.valueOf(smartphone.getRam()))
                        .description("Random-access memory (RAM) is a form of memory used to store working data and machine code. Having more RAM is particularly useful for multitasking, allowing you to run more programs at once or have more tabs open in your browser.")
                        .build(),
                Spec.builder()
                        .name("Processor Model")
                        .value(smartphone.getProcessorModel())
                        .description("A processor model is like the brain of a computer or phone, deciding how fast it can think and work on tasks. Different models have varying abilities, like speed and efficiency, which affect how well devices can handle tasks and run programs.")
                        .build(),
                Spec.builder()
                        .name("Processor Speed")
                        .value(String.valueOf(smartphone.getProcessorSpeed()))
                        .description("The CPU speed indicates how many processing cycles per second can be executed by a CPU, considering all of its cores (processing units). It is calculated by adding the clock rates of each core or, in the case of multi-core processors employing different microarchitectures, of each group of cores.")
                        .build(),
                Spec.builder()
                        .name("Internal Storage")
                        .value(String.valueOf(smartphone.getStorage()))
                        .description("The CPU speed indicates how many processing cycles per second can be executed by a CPU, considering all of its cores (processing units). It is calculated by adding the clock rates of each core or, in the case of multi-core processors employing different microarchitectures, of each group of cores.")
                        .build(),
                Spec.builder()
                        .name("Geekbench 6 Results")
                        .value(String.valueOf(smartphone.getGeekbenchResult()))
                        .description("A Geekbench result is a score that measures the performance of a smartphone's processor and memory. Higher scores indicate better performance, helping users compare the capabilities of different devices.")
                        .build(),
                Spec.builder()
                        .name("Battery Power")
                        .value(String.valueOf(smartphone.getBatteryPower()))
                        .description("Battery power, or battery capacity, represents the amount of electrical energy that a battery can store. More battery power can be an indication of longer battery life.")
                        .build()
        ));
        List<Spec> photoAudio = new ArrayList<>(Arrays.asList(
                Spec.builder()
                        .name("Camera Megapixels")
                        .value(String.valueOf(smartphone.getMegapix()))
                        .description("The number of megapixels determines the resolution of the images captured with the main camera. A higher megapixel count means that the camera is capable of capturing more details. However, the megapixel count is not the only important element determining the quality of an image.")
                        .build(),
                Spec.builder()
                        .name("Audio Jack")
                        .value(String.valueOf(smartphone.getHasAudiojack()))
                        .description("With a standard mini jack socket, you can use the device with most headphones.")
                        .build(),
                Spec.builder()
                        .name("Stereo Speakers")
                        .value(smartphone.getStereoSpeakers())
                        .description("Devices with stereo speakers deliver sound from independent channels on both left and right sides, creating a richer sound and a better experience.")
                        .build()
        ));
        calculateScore(smartphone, null, null, null, null, null,null,null,null,null,null,null,null,null,null,null,null,null,null,null, null);

        return SmartphoneResponse.builder()
                .id(smartphone.getId())
                .name(smartphone.getName())
                .avgPrice(smartphone.getAvgPrice())
                .score(smartphone.getScore())
                .imageUrl(smartphone.getImageUrl())
                .overview(overviews)
                .storage(smartphone.getStorage())
                .batteryPower(smartphone.getBatteryPower())
                .screenSize(smartphone.getScreenSize())
                .megapix(smartphone.getMegapix())
                .priceTags(prices)
                .display(display)
                .performance(performance)
                .photoAudio(photoAudio)
                .build();
    }
    public CompareResponse compareSmartphones(Integer id, Integer other) throws IOException, ExecutionException, InterruptedException {
        Smartphone smartphone1 = smartphoneRepository.findById(id).orElse(null);
        Smartphone smartphone2 = smartphoneRepository.findById(other).orElse(null);
        int winnner;
        SmartphoneResponse smartphoneResponse1 = createSmartphone(smartphone1,smartphone2);
        SmartphoneResponse smartphoneResponse2 = createSmartphone(smartphone2,smartphone1);
        if(smartphoneResponse1.getAvgPrice() > smartphoneResponse2.getAvgPrice()){
            winnner = smartphoneResponse1.getId();
        }
        else{
            winnner = smartphoneResponse2.getId();
        }
        return  CompareResponse.builder()
                .winner(winnner)
                .smartphoneOne(smartphoneResponse1)
                .smartphoneTwo(smartphoneResponse2)
                .build();
    }

    public ChartData getSingleChartData(Integer id) {
        ChartData chartData = new ChartData();
        Smartphone smartphone = smartphoneRepository.findById(id).orElse(null);
        Random random = new Random();
        int designScore = random.nextInt(60,100);
        int performance = (int)(getProcessorScore(smartphone,null,null) + getRAMScore(smartphone,null,null) )/ 2;
        int display = (int)(getRefreshScore(smartphone,null,null) + getScreenSizeScore(smartphone,null,null))/2;
        int battery = (int)(getBatteryScore(smartphone,null,null));
        int camera = (int)(getMegapixScore(smartphone,null,null));
        int storage = (int)(getStorageScore(smartphone,null,null));
        List<Integer> list = new ArrayList<>(Arrays.asList(designScore, performance,display,battery,camera,storage));
        chartData.getDataset().setData(list);
        return chartData;
    }
}
