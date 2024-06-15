package com.ETechAdvisor.ETechAdvisor.Store;

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
@Table(name = "Store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "store_id")
    private Integer store_id;

    @Column(name = "store_name")
    private String store_name;

    @Column(name = "website_url")
    private String website_url;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Store_Smartphone",
            joinColumns = { @JoinColumn(name = "store_id") },
            inverseJoinColumns = { @JoinColumn(name = "smartphone_id") }
    )
    private List<Smartphone> smartphoneList = new ArrayList<>();
}
