package com.renault.garage.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                      .title("Renault Garage Management API")
                      .version("1.0.0")
                      .description("REST API for managing Renault garages, vehicles, and accessories with Kafka event streaming")
            )
            .servers(List.of(
                new Server().url("http://localhost:8081").description("Development Server")
            ));
    }
}
