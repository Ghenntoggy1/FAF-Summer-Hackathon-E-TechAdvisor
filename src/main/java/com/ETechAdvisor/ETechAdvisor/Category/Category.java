package com.ETechAdvisor.ETechAdvisor.Category;

import com.ETechAdvisor.ETechAdvisor.Smartphone.Smartphone;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Integer category_id;

    @Column(name = "category_name")
    private String category_name;


}
