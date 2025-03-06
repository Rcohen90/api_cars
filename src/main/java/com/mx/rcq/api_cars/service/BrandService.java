package com.mx.rcq.api_cars.service;

import org.springframework.stereotype.Service;

import com.mx.rcq.api_cars.models.Brand;
import com.mx.rcq.api_cars.repository.BrandRepository;

import java.util.List;
import java.util.Optional;
@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
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

    public void updateBrandAveragePrice(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada"));

        brand.updateAveragePrice();
        brandRepository.save(brand);
    }
}
