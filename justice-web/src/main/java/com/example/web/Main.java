package com.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.persistence"})
@EntityScan(basePackages = {"com.example.domain"})
@ComponentScan(basePackages = {
        "com.example.persistence",
        "com.example.domain",
        "com.example.web",
        "com.example.exception"
})
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }
}