package com.example.web.controller;


import dev.omedia.domain.Person;
import com.example.persistence.service.PersonService;
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

@RestController
@RequestMapping("public")
@Slf4j
public class PersonPublicController {
    private PersonService service;

    @Autowired
    public PersonPublicController(PersonService service) {
        this.service = service;
    }

    @GetMapping("{personalNo}")
    @Operation(summary = "Get person by personal number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found person by personalNo", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "person not found", content = {@Content(mediaType = "application/json")})})
    public Person getByPersonalNo(@Parameter(description = "person no") @PathVariable final String personalNo) {
        log.debug("get person with required id:{}",personalNo);
        return service.findByPersonalNo(personalNo);
    }

}
