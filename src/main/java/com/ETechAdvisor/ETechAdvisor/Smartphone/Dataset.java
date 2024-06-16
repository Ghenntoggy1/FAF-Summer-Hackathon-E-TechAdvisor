package com.ETechAdvisor.ETechAdvisor.Smartphone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Dataset {
    List<Integer> data = new ArrayList<>();
}
