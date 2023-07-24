package com.example.controller;

import com.example.domain.Car;
import com.example.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequestMapping("public")
@Slf4j
public class CarPublicController {
    private final CarService service;

    @Autowired
    public CarPublicController(CarService service) {
        this.service = service;
    }

    @GetMapping("{personalNo}/{licensePlate}")
    @Operation(summary = "Get car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found car by personalNo and licensePlate", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "car not found", content = {@Content(mediaType = "application/json")})})
    public Car getCarByPersonalNoAndLicensePlate(@Parameter(description = "personal no") @PathVariable final String personalNo,
                                                 @Parameter(description = "car license plate") @PathVariable final String licensePlate) throws URISyntaxException {
        log.debug("get car with required personalNo:{} and licensePlate:{}", personalNo,licensePlate);
        return service.getCarByPersonalNoAndLicensePlate(personalNo,licensePlate);
    }
}
