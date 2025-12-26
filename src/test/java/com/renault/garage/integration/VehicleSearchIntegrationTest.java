package com.renault.garage.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.renault.garage.model.Garage;
import com.renault.garage.model.Vehicle;
import com.renault.garage.repository.GarageRepository;
import com.renault.garage.repository.VehicleRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class VehicleSearchIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeforeEach
    public void setup() {
        vehicleRepository.deleteAll();
        garageRepository.deleteAll();

        Garage garage1 = new Garage();
        garage1.setName("Garage 1");
        garage1.setAddress("Address 1 de Paris");
        garage1.setTelephone("+33111111111");
        garage1.setEmail("test1@hotmail.fr");
        garage1 = garageRepository.save(garage1);

        Garage garage2 = new Garage();
        garage2.setName("Garage 2");
        garage2.setAddress("Address 2 de Paris");
        garage2.setTelephone("+33222222222");
        garage2.setEmail("test2@hotmail.fr");
        garage2 = garageRepository.save(garage2);

        Garage garage3 = new Garage();
        garage3.setName("Garage 3");
        garage3.setAddress("Address 3 de Paris");
        garage3.setTelephone("+33333333333");
        garage3.setEmail("test3@hotmail.fr");
        garage3 = garageRepository.save(garage3);

        Vehicle clio1 = new Vehicle();
        clio1.setBrand("Renault");
        clio1.setModel("Clio");
        clio1.setManufacturingYear(2023);
        clio1.setFuelType("Diesel");
        clio1.setGarage(garage1);
        vehicleRepository.save(clio1);

        Vehicle clio2 = new Vehicle();
        clio2.setBrand("Renault");
        clio2.setModel("Clio");
        clio2.setManufacturingYear(2024);
        clio2.setFuelType("Hybrid");
        clio2.setGarage(garage2);
        vehicleRepository.save(clio2);

        Vehicle clio3 = new Vehicle();
        clio3.setBrand("Renault");
        clio3.setModel("Clio");
        clio3.setManufacturingYear(2023);
        clio3.setFuelType("Hybrid");
        clio3.setGarage(garage3);
        vehicleRepository.save(clio3);

        Vehicle megane = new Vehicle();
        megane.setBrand("Renault");
        megane.setModel("Megane");
        megane.setManufacturingYear(2024);
        megane.setFuelType("Electric");
        megane.setGarage(garage1);
        vehicleRepository.save(megane);
    }

    @Test
    public void whenSearchByModelThenReturnVehiclesFromMultipleGarages() throws Exception {
        mockMvc.perform(get("/api/vehicles/model/Clio"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$[0].model").value("Clio"))
            .andExpect(jsonPath("$[1].model").value("Clio"))
            .andExpect(jsonPath("$[2].model").value("Clio"));
    }

    @Test
    public void whenSearchByModelWithSingleResultThenReturnOne() throws Exception {
        mockMvc.perform(get("/api/vehicles/model/Megane"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].model").value("Megane"));
    }

    @Test
    public void whenSearchByNonExistentModelThenReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/vehicles/model/Kangoo"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));
    }
}
