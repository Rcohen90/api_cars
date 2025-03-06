package com.mx.rcq.api_cars.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.rcq.api_cars.models.Brand;


public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByName(String name);
}
