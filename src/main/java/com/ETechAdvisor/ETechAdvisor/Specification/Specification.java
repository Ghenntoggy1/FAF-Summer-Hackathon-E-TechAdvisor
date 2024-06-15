package com.ETechAdvisor.ETechAdvisor.Specification;

import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Specification")
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "specification_id")
    private int specification_id;

    @Column(name = "specification_name")
    private String specification_name;

    @Column(name = "specification_type")
    private String specification_type;
}
