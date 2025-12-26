package com.renault.garage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renault.garage.model.Garage;
import com.renault.garage.service.GarageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/garages")
@Tag(name = "Garage Management", description = "APIs for managing Renault garages")
public class GarageController {

    @Autowired
    private GarageService garageService;

    @GetMapping
    @Operation(summary = "Get all garages", description = "Retrieve a paginated list of all garages")
    public Page<Garage> getAllGarages(Pageable pageable) {
        return garageService.getAllGarages(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get garage by ID", description = "Retrieve a specific garage by its ID")
    public ResponseEntity<Garage> getGarageById(@PathVariable Long id) {
        Garage garage = garageService.getGarageById(id);
        return garage != null ? ResponseEntity.ok(garage) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a new garage", description = "Create a new garage with the provided details")
    public Garage createGarage(@RequestBody Garage garage) {
        return garageService.createGarage(garage);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a garage", description = "Update an existing garage's information")
    public ResponseEntity<Garage> updateGarage(@PathVariable Long id, @RequestBody Garage garageDetails) {
        Garage updatedGarage = garageService.updateGarage(id, garageDetails);
        return updatedGarage != null ? ResponseEntity.ok(updatedGarage) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a garage", description = "Delete a garage by its ID")
    public ResponseEntity<Void> deleteGarage(@PathVariable Long id) {
        garageService.deleteGarage(id);
        return ResponseEntity.noContent().build();
    }
}
