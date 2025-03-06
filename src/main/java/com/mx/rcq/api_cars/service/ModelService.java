package com.mx.rcq.api_cars.service;

import com.mx.rcq.api_cars.models.Brand;
import com.mx.rcq.api_cars.models.Model;
import com.mx.rcq.api_cars.repository.BrandRepository;
import com.mx.rcq.api_cars.repository.ModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ModelService {
    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;

    public ModelService(ModelRepository modelRepository, BrandRepository brandRepository) {
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
    }

    public List<Model> getModelsByBrand(Long brandId) {
        return modelRepository.findByBrandId(brandId);
    }

    @Transactional
    public Model addModel(Long brandId, String name, Integer averagePrice) {
        if (averagePrice != null && averagePrice < 100000) {
            throw new IllegalArgumentException("The average_price must be greater then 100,000.");
        }

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Brand is not found."));

        Model model = new Model(name, averagePrice, brand);
        modelRepository.save(model);
        brandRepository.save(brand);

        return model;
    }

    @Transactional
    public Optional<Model> updateModelPrice(Long modelId, Integer newPrice) {
        if (newPrice < 100000) {
            throw new IllegalArgumentException("The average_price must be greater then 100,000.");
        }
        Model model = modelRepository.findById(modelId)
        .orElseThrow(() -> new IllegalArgumentException("Model is not found."));
                
        model.setAveragePrice(newPrice);        
        modelRepository.save(model);

        return Optional.of(model);
    }

    public List<Model> getModelsByPriceRange(Integer greater, Integer lower) {
        if (greater != null) {
            return modelRepository.findByAveragePriceGreaterThan(greater);
        } else if (lower != null) {
            return modelRepository.findByAveragePriceLessThan(lower);
        }
        return modelRepository.findAll();
    }

    public Optional<Model> updateModelPrice(Long modelId, int newPrice) {
        Optional<Model> modelOptional = modelRepository.findById(modelId);
        if (modelOptional.isPresent()) {
            Model model = modelOptional.get();
            model.setAveragePrice(newPrice);
            return Optional.of(modelRepository.save(model));
        }
        return Optional.empty();
    }
}
