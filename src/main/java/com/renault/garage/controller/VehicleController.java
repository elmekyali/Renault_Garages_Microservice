package com.renault.garage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renault.garage.model.Vehicle;
import com.renault.garage.service.VehicleService;

@RestController
@RequestMapping("/api")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/garages/{garageId}/vehicles")
    public List<Vehicle> getVehiclesByGarage(@PathVariable Long garageId) {
        return vehicleService.getVehiclesByGarage(garageId);
    }

    @PostMapping("/garages/{garageId}/vehicles")
    public ResponseEntity<Vehicle> addVehicleToGarage(@PathVariable Long garageId, @RequestBody Vehicle vehicle) {
        Vehicle savedVehicle = vehicleService.addVehicleToGarage(garageId, vehicle);
        return ResponseEntity.ok(savedVehicle);
    }

    @GetMapping("/vehicles/model/{model}")
    public List<Vehicle> getVehiclesByModel(@PathVariable String model) {
        return vehicleService.getVehiclesByModel(model);
    }
}
