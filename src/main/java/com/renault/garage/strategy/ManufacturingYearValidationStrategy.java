package com.renault.garage.strategy;

import java.time.Year;

import org.springframework.stereotype.Component;

import com.renault.garage.model.Vehicle;

@Component
public class ManufacturingYearValidationStrategy implements VehicleValidationStrategy {

    private static final int MIN_YEAR = 1900;
    private final int maxYear = Year.now().getValue();

    @Override
    public boolean validate(Vehicle vehicle) {
        int year = vehicle.getManufacturingYear();
        return year >= MIN_YEAR && year <= maxYear;
    }

    @Override
    public String getErrorMessage() {
        return String.format("Manufacturing year must be between %d and %d", MIN_YEAR, maxYear);
    }
}
