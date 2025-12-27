package com.renault.garage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renault.garage.exceptions.ResourceNotFoundException;
import com.renault.garage.model.Accessory;
import com.renault.garage.model.Vehicle;
import com.renault.garage.repository.AccessoryRepository;

@Service
public class AccessoryService {

    @Autowired
    private AccessoryRepository accessoryRepository;

    public List<Accessory> getAccessoriesByVehicle(Long vehicleId) {
        return accessoryRepository.findByVehicleId(vehicleId);
    }

    public Accessory addAccessoryToVehicle(Accessory accessory, Vehicle vehicle) {
        accessory.setVehicle(vehicle);
        return accessoryRepository.save(accessory);
    }

    public void deleteAccessory(Long id) {
        accessoryRepository.deleteById(id);
    }

    public Accessory updateAccessory(Long id, Accessory accessoryDetails) {
        Accessory existingAccessory = accessoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Accessory not found with id: " + id));

        Accessory updatedAccessory = Accessory.builder()
            .id(existingAccessory.getId())
            .name(accessoryDetails.getName())
            .description(accessoryDetails.getDescription())
            .price(accessoryDetails.getPrice())
            .type(accessoryDetails.getType())
            .vehicle(existingAccessory.getVehicle())
            .build();
        return accessoryRepository.save(updatedAccessory);
    }
}
