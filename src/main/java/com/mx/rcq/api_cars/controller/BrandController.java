package com.mx.rcq.api_cars.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mx.rcq.api_cars.models.Brand;
import com.mx.rcq.api_cars.models.Model;
import com.mx.rcq.api_cars.service.BrandService;
import com.mx.rcq.api_cars.service.ModelService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;
    private final ModelService modelService;

    public BrandController(BrandService brandService, ModelService modelService) {
        this.brandService = brandService;
        this.modelService = modelService;
    }

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brands = brandService.getAllBrands().stream()
                .map(brand -> new Brand(brand.getId(), brand.getName(), brandService.calculateAveragePrice(brand)))
                .collect(Collectors.toList());
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{brandId}/models")
    public ResponseEntity<List<Model>> getModelsByBrand(@PathVariable Long brandId) {
        List<Model> models = modelService.getModelsByBrand(brandId);
        return models.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(models);
    }

    @PostMapping
    public ResponseEntity<?> addBrand(@RequestBody Brand brand) {
        try {
            return ResponseEntity.ok(brandService.addBrand(brand.getName()));
        } catch (IllegalArgumentException e) {
            return e.getMessage().contains("duplicate") ? ResponseEntity.badRequest().body("Brand is already exists.") : ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{brandId}/models")
    public ResponseEntity<?> addModel(@PathVariable Long brandId, @RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            Integer averagePrice = (Integer) request.getOrDefault("average_price", 0);

            if (averagePrice != null && averagePrice < 100000) {
                return ResponseEntity.badRequest().body("The average_price must be greater then 100,000.");
            }

            Model model = modelService.addModel(brandId, name, averagePrice);
            Optional<Brand> brand = brandService.getBrandById(brandId);
            if(brand.isPresent()) {
                Brand updatedBrand = new Brand(brand.get().getId(), brand.get().getName(), brandService.calculateAveragePrice(brand.get()));
                brandService.calculateAveragePrice(updatedBrand);
            }
            return ResponseEntity.ok(model);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
