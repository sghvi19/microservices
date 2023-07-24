package com.example.controller;

import com.example.domain.Insurance;
import com.example.domain.InsuranceCar;
import com.example.service.InsuranceCarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
@RequestMapping("insuranceCars")
@Slf4j
public class InsuranceCarController {
    private InsuranceCarService service;

    @Autowired
    public InsuranceCarController(InsuranceCarService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get insuranceCars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found insuranceCars", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Iterable<InsuranceCar> getAllInsuranceCar(@Parameter(description = "page") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                                     @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.debug("get all insuranceCars with page:{} and pageSize:{}", page, pageSize);
        return service.getAll(page, pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get insuranceCar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found insuranceCar by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "insuranceCar not found", content = {@Content(mediaType = "application/json")})})
    public InsuranceCar getByIdInsuranceCar(@Parameter(description = "insuranceCar id") @PathVariable final long id) {
        log.debug("get insuranceCar with required id:{}", id);
        return service.getById(id);
    }


    @PostMapping("{insuranceAmount}/{personalNo}")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create insuranceCar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created insuranceCar", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "406", description = "Not acceptable", content = {@Content(mediaType = "application/json")})})
    public InsuranceCar createInsuranceCar(@Parameter(description = "car license plate")  @RequestParam final String licensePlate,
                                           @Parameter(description = "owner's personalNo")@RequestParam final String personalNo,
                                           @Parameter(description = "insurance data")@RequestBody @Valid final Insurance insurance) throws URISyntaxException {
        log.debug("creat insuranceCar with required data:{}", licensePlate,personalNo,insurance);
        return service.create(licensePlate,personalNo,insurance);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "remove insuranceCar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed insuranceCar", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "insuranceCar not found", content = {@Content(mediaType = "application/json")})})
    public InsuranceCar removeInsuranceCar(@Parameter(description = "insuranceCar id") @PathVariable final long id) {
        log.debug("remove insuranceCar with required id:{}", id);
        return service.remove(id);
    }
}
