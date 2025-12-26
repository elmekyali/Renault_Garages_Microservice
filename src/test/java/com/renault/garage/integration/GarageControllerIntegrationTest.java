package com.renault.garage.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renault.garage.model.Garage;

@SpringBootTest
@AutoConfigureMockMvc
public class GarageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenCreateGarageThenStatus200() throws Exception {
        Garage garage = new Garage();
        garage.setName("Garage 1");
        garage.setAddress("Address 1, Paris");
        garage.setTelephone("3312456789");
        garage.setEmail("test@hotmail.fr");

        mockMvc.perform(post("/api/garages")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(garage)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Garage 1"));
    }
}
