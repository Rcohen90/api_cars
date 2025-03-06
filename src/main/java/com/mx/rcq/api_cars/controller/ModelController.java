package com.mx.rcq.api_cars.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mx.rcq.api_cars.models.Model;
import com.mx.rcq.api_cars.service.ModelService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/models")
public class ModelController {
     private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PutMapping("/{modelId}")
    public ResponseEntity<?> updateModelPrice(@PathVariable Long modelId, @RequestBody Map<String, Integer> request) {
        Integer newPrice = request.get("average_price");

        if (newPrice == null || newPrice < 100000) {
            return ResponseEntity.badRequest().body("El precio promedio debe ser mayor a 100,000.");
        }

        Optional<Model> modelOptional = modelService.updateModelPrice(modelId, newPrice);
        return modelOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Model>> getModelsByPriceRange(
            @RequestParam(required = false) Integer greater,
            @RequestParam(required = false) Integer lower) {
        List<Model> models = modelService.getModelsByPriceRange(greater, lower);
        return models.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(models);
    }
}
