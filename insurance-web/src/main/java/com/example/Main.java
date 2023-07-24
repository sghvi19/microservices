package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"dev.omedia.repository"})
@EntityScan(basePackages = {"dev.omedia.domain"})
@ComponentScan(basePackages = {
        "dev.omedia.repository",
        "dev.omedia.service",
        "com.example.controller",
        "com.example.exceptionhandler",
        "dev.omedia.config",
        "dev.omedia.carandownerreceiver"
})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}