package com.ETechAdvisor.ETechAdvisor.Category;


import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoryDTO {
    private int category_id;
    private String category_name;
}
