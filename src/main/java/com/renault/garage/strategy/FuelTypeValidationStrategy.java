package com.renault.garage.strategy;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.renault.garage.model.Vehicle;

@Component
public class FuelTypeValidationStrategy implements VehicleValidationStrategy {

    private static final List<String> ALLOWED_FUEL_TYPES = Arrays.asList(
        "Petrol", "Diesel", "Electric", "Hybrid"
    );

    @Override
    public boolean validate(Vehicle vehicle) {
        String fuelType = vehicle.getFuelType();
        return fuelType != null && ALLOWED_FUEL_TYPES.stream()
            .anyMatch(allowed -> allowed.equalsIgnoreCase(fuelType));
    }

    @Override
    public String getErrorMessage() {
        return "Fuel type must be one of: " + String.join(", ", ALLOWED_FUEL_TYPES);
    }
}
