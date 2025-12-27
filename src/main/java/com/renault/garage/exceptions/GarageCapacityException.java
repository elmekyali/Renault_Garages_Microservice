package com.renault.garage.exceptions;

import lombok.Getter;

@Getter
public class GarageCapacityException extends RuntimeException {
    private final int currentCapacity;
    private final int maxCapacity;

    public GarageCapacityException(int currentCapacity, int maxCapacity) {
        super(String.format("Garage is full. Current capacity: %d/%d. Cannot add more vehicles.",
                            currentCapacity, maxCapacity));
        this.currentCapacity = currentCapacity;
        this.maxCapacity = maxCapacity;
    }
}