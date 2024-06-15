package com.ETechAdvisor.ETechAdvisor.Smartphone;

import com.ETechAdvisor.ETechAdvisor.Brand.Brand;
import com.ETechAdvisor.ETechAdvisor.Category.Category;
import com.ETechAdvisor.ETechAdvisor.Store.Store;
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
@Table(name = "Smartphone")
public class Smartphone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "smartphone_id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "storage")
    private int storage;

    @Column(name = "battery_power")
    private int batteryPower;

    @Column(name = "screen_size")
    private double screenSize;

    @Column(name = "megapix")
    private double megapix;

    @Column(name = "os")
    private String os;

    @Column(name = "has_audiojack")
    private boolean hasAudiojack;

    @Transient
    private int score;

    @Transient
    private int avgPrice;

    @ManyToMany(mappedBy = "smartphoneList")
    private List<Store> storeList = new ArrayList<>();

//    @ManyToMany(mappedBy = "products")
//    private List<Specification> specificationList = new ArrayList<>();
}
