package com.renault.garage.strategy;

import com.renault.garage.model.Vehicle;

public interface VehicleValidationStrategy {
    boolean validate(Vehicle vehicle);

    String getErrorMessage();
}
