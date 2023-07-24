package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class RestTemplateConfig {
    @Value("${car.service.host:localhost}")
    private String host ;

    @Value("${car.service.port:8081}")
    private Integer port;

    @Value("${car.service.scheme:http}")
    private String scheme;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean("carServiceURIBuilder")
    public UriComponentsBuilder uriBuilder() {
        return UriComponentsBuilder.newInstance()
                .host(host).port(port).scheme(scheme);
    }
}
