package com.renault.garage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.renault.garage.event.VehicleCreatedEvent;
import com.renault.garage.model.Vehicle;

@Component
public class VehicleEventListener {

    private static final Logger logger = LoggerFactory.getLogger(VehicleEventListener.class);

    @EventListener
    @Async
    public void handleVehicleCreatedEvent(VehicleCreatedEvent event) {
        Vehicle vehicle = event.getVehicle();

        logger.info("*********** [EVENT CONSUMER] Vehicle Created Event Received *****************");
        logger.info("*********** Vehicle ID: {}", vehicle.getId());
        logger.info("*********** Brand: {}", vehicle.getBrand());
        logger.info("*********** Model: {}", vehicle.getModel());
        logger.info("*********** Manufacturing Year: {}", vehicle.getManufacturingYear());
        logger.info("*********** Fuel Type: {}", vehicle.getFuelType());

        processVehicleCreation(vehicle);

        logger.info("*********** [EVENT CONSUMER] Event processing completed for Vehicle ID: {} ***************", vehicle.getId());
    }

    private void processVehicleCreation(Vehicle vehicle) {
        // we can add business logic here!
        logger.debug("Processing vehicle creation business logic for: {}", vehicle.getModel());
    }
}
