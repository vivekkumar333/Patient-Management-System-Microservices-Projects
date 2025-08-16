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
                        .title("PMS-Billing-Service")
                        .version("1.0")
                        .description("Service to take care patient billing operation")
                        .contact(new Contact().name("Vivek").email("vivek@example.com"))
                );
    }
}
