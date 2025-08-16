package com.vkt.pms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig{

    @Value("${billing.service.url}")
    private String billingServiceBaseUrl;

    @Bean(name = "BillingServiceWebClient")
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder()
                .baseUrl(billingServiceBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }
}
