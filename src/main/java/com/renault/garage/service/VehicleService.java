package com.renault.garage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.renault.garage.event.VehicleCreatedEvent;
import com.renault.garage.exceptions.GarageCapacityException;
import com.renault.garage.exceptions.ResourceNotFoundException;
import com.renault.garage.exceptions.ValidationException;
import com.renault.garage.model.Garage;
import com.renault.garage.model.Vehicle;
import com.renault.garage.repository.GarageRepository;
import com.renault.garage.repository.VehicleRepository;
import com.renault.garage.strategy.FuelTypeValidationStrategy;
import com.renault.garage.strategy.ManufacturingYearValidationStrategy;

@Service
public class VehicleService {

    private static final int MAX_VEHICLES_PER_GARAGE = 50;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ManufacturingYearValidationStrategy yearValidationStrategy;

    @Autowired
    private FuelTypeValidationStrategy fuelTypeValidationStrategy;

    public List<Vehicle> getVehiclesByGarage(Long garageId) {
        return vehicleRepository.findByGarageId(garageId);
    }

    public Vehicle addVehicleToGarage(Long garageId, Vehicle vehicle) {
        Garage garage = garageRepository.findById(garageId)
            .orElseThrow(() -> new ResourceNotFoundException("Garage not found with id: " + garageId));

        validateVehicle(vehicle, garageId);

        Vehicle vehicleToSave = Vehicle.builder()
            .brand(vehicle.getBrand())
            .model(vehicle.getModel())
            .manufacturingYear(vehicle.getManufacturingYear())
            .fuelType(vehicle.getFuelType())
            .garage(garage)
            .accessories(new ArrayList<>())
            .build();

        Vehicle savedVehicle = vehicleRepository.save(vehicleToSave);

        eventPublisher.publishEvent(new VehicleCreatedEvent(this, savedVehicle));

        return savedVehicle;
    }

    public List<Vehicle> getVehiclesByModel(String model) {
        return vehicleRepository.findByModel(model);
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    private void validateVehicle(Vehicle vehicle, Long garageId) {
        List<ValidationException.ValidationError> errors = new ArrayList<>();

        if (!yearValidationStrategy.validate(vehicle)) {
            errors.add(new ValidationException.ValidationError(
                "manufacturingYear",
                yearValidationStrategy.getErrorMessage()
            ));
        }

        if (!fuelTypeValidationStrategy.validate(vehicle)) {
            errors.add(new ValidationException.ValidationError(
                "fuelType",
                fuelTypeValidationStrategy.getErrorMessage()
            ));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        long vehicleCount = vehicleRepository.findByGarageId(garageId).size();
        if (vehicleCount >= MAX_VEHICLES_PER_GARAGE) {
            throw new GarageCapacityException((int) vehicleCount, MAX_VEHICLES_PER_GARAGE);
        }
    }
}
