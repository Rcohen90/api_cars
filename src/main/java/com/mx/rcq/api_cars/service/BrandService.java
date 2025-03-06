package com.mx.rcq.api_cars.service;

import org.springframework.stereotype.Service;

import com.mx.rcq.api_cars.models.Brand;
import com.mx.rcq.api_cars.models.Model;
import com.mx.rcq.api_cars.repository.BrandRepository;

import java.util.List;
import java.util.Optional;
@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final ModelService modelService;

    public BrandService(BrandRepository brandRepository, ModelService modelService) {
        this.brandRepository = brandRepository;
        this.modelService = modelService;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    public Brand addBrand(String name) {
        if (brandRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Brand name already exists.");
        }
        return brandRepository.save(new Brand(name));
    }

    public double calculateAveragePrice(Brand brand) {
        List<Model> models;
        double averagePrice;
        models = modelService.getModelsByBrand(brand.getId());
        if (models == null || models.isEmpty()) {
            return 0.0;
        }
        averagePrice = models.stream()
                .mapToDouble(Model::getAveragePrice)
                .average()
                .orElse(0.0);
        updateBrandAveragePrice(brand.getId(), averagePrice);
        return averagePrice;
    }

    public void updateBrandAveragePrice(Long brandId, double averagePrice) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Brand is not found"));
        brand.setAveragePrice(averagePrice);
        brandRepository.save(brand);
    }
}
