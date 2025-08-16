package com.vkt.pms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customeOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Patient-Service-API")
                        .version("1.0")
                        .description("APIs to Handle patient operations")
                        .contact(new Contact().name("Vivek").email("xyz@example.com"))
                );
    }
}
