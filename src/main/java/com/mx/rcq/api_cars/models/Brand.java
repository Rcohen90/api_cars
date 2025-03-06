package com.mx.rcq.api_cars.models;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    
    @Column(nullable = true)
    private double averagePrice;

    public Brand(String name) {
        this.name = name;
        this.averagePrice = 0.0;
    }
}
