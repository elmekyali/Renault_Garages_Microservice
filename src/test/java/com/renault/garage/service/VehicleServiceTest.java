package com.renault.garage.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.renault.garage.model.Garage;
import com.renault.garage.model.Vehicle;
import com.renault.garage.repository.GarageRepository;
import com.renault.garage.repository.VehicleRepository;
import com.renault.garage.strategy.FuelTypeValidationStrategy;
import com.renault.garage.strategy.ManufacturingYearValidationStrategy;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private GarageRepository garageRepository;

    @Mock
    private ManufacturingYearValidationStrategy yearValidationStrategy;

    @Mock
    private FuelTypeValidationStrategy fuelTypeValidationStrategy;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void whenGarageIsFullThenThrowException() {
        Garage fullGarage = new Garage();
        fullGarage.setId(1L);

        Vehicle testVehicle = Vehicle.builder()
            .brand("Renault")
            .model("Clio")
            .manufacturingYear(2023)
            .fuelType("Electric")
            .build();

        List<Vehicle> vehicles = IntStream.range(0, 50).mapToObj(i -> new Vehicle()).collect(Collectors.toList());

        when(garageRepository.findById(1L)).thenReturn(Optional.of(fullGarage));
        when(yearValidationStrategy.validate(any(Vehicle.class))).thenReturn(true);
        when(fuelTypeValidationStrategy.validate(any(Vehicle.class))).thenReturn(true);
        when(vehicleRepository.findByGarageId(1L)).thenReturn(vehicles);

        assertThrows(RuntimeException.class, () -> {
            vehicleService.addVehicleToGarage(1L, testVehicle);
        });

        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }
}
