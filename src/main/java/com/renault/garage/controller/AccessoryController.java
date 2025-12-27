package com.renault.garage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renault.garage.model.Accessory;
import com.renault.garage.model.Vehicle;
import com.renault.garage.repository.VehicleRepository;
import com.renault.garage.service.AccessoryService;
import com.renault.garage.service.VehicleService;

@RestController
@RequestMapping("/api")
public class AccessoryController {

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles/{vehicleId}/accessories")
    public List<Accessory> getAccessoriesByVehicle(@PathVariable Long vehicleId) {
        return accessoryService.getAccessoriesByVehicle(vehicleId);
    }

    @PostMapping("/vehicles/{vehicleId}/accessories")
    public ResponseEntity<Accessory> addAccessoryToVehicle(
        @PathVariable Long vehicleId,
        @RequestBody Accessory accessory) {
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        Accessory savedAccessory = accessoryService.addAccessoryToVehicle(accessory, vehicle);
        return ResponseEntity.ok(savedAccessory);
    }

    @DeleteMapping("/accessories/{id}")
    public ResponseEntity<Void> deleteAccessory(@PathVariable Long id) {
        accessoryService.deleteAccessory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/accessories/{id}")
    public ResponseEntity<Accessory> updateAccessory(
        @PathVariable Long id,
        @RequestBody Accessory accessoryDetails) {
        Accessory updatedAccessory = accessoryService.updateAccessory(id, accessoryDetails);
        return updatedAccessory != null ?
            ResponseEntity.ok(updatedAccessory) :
            ResponseEntity.notFound().build();
    }
}
