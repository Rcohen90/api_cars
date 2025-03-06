package com.mx.rcq.api_cars.models;

import jakarta.persistence.*;
@Entity
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int averagePrice;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    public Model() {}

    public Model(String name, int averagePrice, Brand brand) {
        this.name = name;
        this.averagePrice = averagePrice;
        this.brand = brand;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getAveragePrice() { return averagePrice; }
    public void setAveragePrice(int averagePrice) { this.averagePrice = averagePrice; }
}
