package com.mx.rcq.api_cars.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.rcq.api_cars.models.Model;

import java.util.List;
@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findByBrandId(Long brandId);
    List<Model> findByAveragePriceGreaterThan(int price);
    List<Model> findByAveragePriceLessThan(int price);
    boolean existsByNameAndBrandId(String name, Long brandId);
}
