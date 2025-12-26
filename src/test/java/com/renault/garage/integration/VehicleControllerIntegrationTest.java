package com.renault.garage.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renault.garage.model.Garage;
import com.renault.garage.model.Vehicle;
import com.renault.garage.repository.GarageRepository;
import com.renault.garage.repository.VehicleRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class VehicleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    private Garage testGarage;

    @BeforeEach
    public void setup() {
        vehicleRepository.deleteAll();
        garageRepository.deleteAll();

        testGarage = new Garage();
        testGarage.setName("Garage 1");
        testGarage.setAddress("Address 1, Lyon");
        testGarage.setTelephone("33987654321");
        testGarage.setEmail("test@hotmail.fr");
        testGarage = garageRepository.save(testGarage);
    }

    @Test
    public void whenAddVehicleToGarageThenStatus200() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Renault");
        vehicle.setModel("Megane");
        vehicle.setManufacturingYear(2023);
        vehicle.setFuelType("Hybrid");

        mockMvc.perform(post("/api/garages/" + testGarage.getId() + "/vehicles")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vehicle)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.brand").value("Renault"))
            .andExpect(jsonPath("$.model").value("Megane"));
    }

    @Test
    public void whenGetVehiclesByGarageThenReturnList() throws Exception {
        IntStream.range(0, 3).forEach(i -> {
            Vehicle vehicle = new Vehicle();
            vehicle.setBrand("Renault");
            vehicle.setModel("Clio " + i);
            vehicle.setManufacturingYear(2024);
            vehicle.setFuelType("Diesel");
            vehicle.setGarage(testGarage);
            vehicleRepository.save(vehicle);
        });

        mockMvc.perform(get("/api/garages/" + testGarage.getId() + "/vehicles"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void whenGarageReaches50VehiclesThenRejectNewVehicle() throws Exception {
        IntStream.range(0, 50).forEach(i -> {
            Vehicle vehicle = new Vehicle();
            vehicle.setBrand("Renault");
            vehicle.setModel("Vehicle " + i);
            vehicle.setManufacturingYear(2023);
            vehicle.setFuelType("Diesel");
            vehicle.setGarage(testGarage);
            vehicleRepository.save(vehicle);
        });

        vehicleRepository.flush();
        testGarage = garageRepository.findById(testGarage.getId()).orElseThrow();

        Vehicle extraVehicle = new Vehicle();
        extraVehicle.setBrand("Renault");
        extraVehicle.setModel("Extra Vehicle");
        extraVehicle.setManufacturingYear(2023);
        extraVehicle.setFuelType("Hybrid");

        mockMvc.perform(post("/api/garages/" + testGarage.getId() + "/vehicles")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraVehicle)))
            .andExpect(status().isBadRequest());
    }
}
