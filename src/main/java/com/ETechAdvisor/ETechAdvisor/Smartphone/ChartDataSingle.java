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
@AllArgsConstructor
@NoArgsConstructor
public class ChartDataSingle {
    private List<String> labels = new ArrayList<>(Arrays.asList("Design","Performance","Display","Battery", "Camera", "Storage"));
    private Dataset dataset = new Dataset();
}
