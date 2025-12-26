package com.renault.garage.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renault.garage.model.Accessory;
import com.renault.garage.model.Garage;
import com.renault.garage.model.Vehicle;
import com.renault.garage.repository.AccessoryRepository;
import com.renault.garage.repository.GarageRepository;
import com.renault.garage.repository.VehicleRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccessoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    private Vehicle testVehicle;

    @BeforeEach
    public void setup() {
        accessoryRepository.deleteAll();
        vehicleRepository.deleteAll();
        garageRepository.deleteAll();

        Garage garage = new Garage();
        garage.setName("Renault A");
        garage.setAddress("100 AAA, Marseille");
        garage.setTelephone("+3300000001");
        garage.setEmail("test1@hotmail.fr");
        garage = garageRepository.save(garage);

        testVehicle = new Vehicle();
        testVehicle.setBrand("Renault");
        testVehicle.setModel("Clio");
        testVehicle.setManufacturingYear(2024);
        testVehicle.setFuelType("Diesel");
        testVehicle.setGarage(garage);
        testVehicle = vehicleRepository.save(testVehicle);
    }

    @Test
    public void whenAddAccessoryToVehicleThenStatus200() throws Exception {
        Accessory accessory = new Accessory();
        accessory.setName("Accessory test");
        accessory.setDescription("Accessory test for ABCD");
        accessory.setPrice(new BigDecimal("200.99"));
        accessory.setType("Exterior");

        mockMvc.perform(post("/api/vehicles/" + testVehicle.getId() + "/accessories")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(accessory)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Accessory test"))
            .andExpect(jsonPath("$.price").value(200.99));
    }

    @Test
    public void whenGetAccessoriesByVehicleThenReturnList() throws Exception {

        Accessory accessory1 = new Accessory();
        accessory1.setName("Accessory test 1");
        accessory1.setDescription("Accessory test 1 for ABCD");
        accessory1.setPrice(new BigDecimal("80.99"));
        accessory1.setType("Interior");
        accessory1.setVehicle(testVehicle);
        accessoryRepository.save(accessory1);

        Accessory accessory2 = new Accessory();
        accessory2.setName("Accessory test 2");
        accessory2.setDescription("Accessory test 2 for ABCD");
        accessory2.setPrice(new BigDecimal("45.50"));
        accessory2.setType("Interior");
        accessory2.setVehicle(testVehicle);
        accessoryRepository.save(accessory2);

        mockMvc.perform(get("/api/vehicles/" + testVehicle.getId() + "/accessories"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    public void whenUpdateAccessoryThenStatus200() throws Exception {
        Accessory accessory = new Accessory();
        accessory.setName("Accessory test");
        accessory.setDescription("Accessory test for ABCD");
        accessory.setPrice(new BigDecimal("12.99"));
        accessory.setType("Interior");
        accessory.setVehicle(testVehicle);
        accessory = accessoryRepository.save(accessory);

        accessory.setPrice(new BigDecimal("20.99"));

        mockMvc.perform(put("/api/accessories/" + accessory.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(accessory)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.price").value(20.99));
    }

    @Test
    public void whenDeleteAccessoryThenStatus204() throws Exception {
        Accessory accessory = new Accessory();
        accessory.setName("Accessory test");
        accessory.setDescription("Accessory test for ABCD");
        accessory.setPrice(new BigDecimal("15.99"));
        accessory.setType("Exterior");
        accessory.setVehicle(testVehicle);
        accessory = accessoryRepository.save(accessory);

        mockMvc.perform(delete("/api/accessories/" + accessory.getId()))
            .andExpect(status().isNoContent());
    }
}
