package com.ETechAdvisor.ETechAdvisor.Smartphone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChartDataDouble {
    private List<String> labels = new ArrayList<>(Arrays.asList("Design","Performance","Display","Battery", "Camera", "Storage"));
    private Dataset dataset1 = new Dataset();
    private Dataset dataset2 = new Dataset();
}
