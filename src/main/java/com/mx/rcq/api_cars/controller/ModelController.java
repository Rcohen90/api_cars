package com.mx.rcq.api_cars.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mx.rcq.api_cars.models.Model;
import com.mx.rcq.api_cars.service.ModelService;
import com.mx.rcq.api_cars.utils.ErrorResponse;

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
    public ResponseEntity<Object> updateModelPrice(@PathVariable Long modelId, @RequestBody Map<String, Integer> request) {
        Integer newPrice = request.get("average_price");

        if (newPrice == null || newPrice < 100000) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The average_price must be greater than 100,000."));
        }

        Optional<Model> modelOptional = modelService.updateModelPrice(modelId, newPrice);
        if (modelOptional.isPresent()) {
            return ResponseEntity.ok(modelOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Model>> getModelsByPriceRange(
            @RequestParam(required = false) Integer greater,
            @RequestParam(required = false) Integer lower) {
        List<Model> models = modelService.getModelsByPriceRange(greater, lower);
        return models.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(models);
    }

}
