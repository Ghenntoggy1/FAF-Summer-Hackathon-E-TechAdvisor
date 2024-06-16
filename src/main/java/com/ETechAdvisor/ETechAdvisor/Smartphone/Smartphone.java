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
    private Integer id;

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

    @Column(name = "screen_size")
    private Double screenSize;

    @Column(name = "displayType")
    private String displayType;

    @Column(name = "refreshRate")
    private Integer refreshRate;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "RAM")
    private Integer ram;

    @Column(name = "processorModel")
    private String processorModel;

    @Column(name = "processorSpeed")
    private Double processorSpeed;

    @Column(name = "storage")
    private Integer storage;

    @Column(name = "geekbenchResult")
    private Integer geekbenchResult;

    @Column(name = "battery_power")
    private Integer batteryPower;

    @Column(name = "megapix")
    private Double megapix;

    @Column(name = "has_audiojack")
    private Boolean hasAudiojack;

    @Column(name = "stereo_speakers")
    private String stereoSpeakers;

    @Column(name = "os")
    private String os;

    @Transient
    private Integer score;

    @Column(name = "avg_price")
    private Double avgPrice;

    @ManyToMany(mappedBy = "smartphoneList")
    private List<Store> storeList = new ArrayList<>();

//    @ManyToMany(mappedBy = "products")
//    private List<Specification> specificationList = new ArrayList<>();
}
