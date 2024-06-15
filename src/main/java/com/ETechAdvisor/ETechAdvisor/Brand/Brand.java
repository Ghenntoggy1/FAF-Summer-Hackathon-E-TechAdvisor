package com.ETechAdvisor.ETechAdvisor.Brand;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "brand_id")
    private Integer brand_id;

    @Column(name = "brand_name")
    private String brand_name;


}
