package com.renault.garage.event;

import org.springframework.context.ApplicationEvent;

import com.renault.garage.model.Vehicle;

import lombok.Getter;

@Getter
public class VehicleCreatedEvent extends ApplicationEvent {
    private final Vehicle vehicle;

    public VehicleCreatedEvent(Object source, Vehicle vehicle) {
        super(source);
        this.vehicle = vehicle;
    }
}
