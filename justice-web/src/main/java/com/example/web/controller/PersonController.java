package com.example.web.controller;

import dev.omedia.domain.Person;
import com.example.persistence.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("persons")
@Slf4j
public class PersonController {
    private PersonService service;
    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get persons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found persons", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Iterable<Person> getAllPerson(@Parameter(description = "page")@RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                       @Parameter(description = "page size")@RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.debug("get all persons with page:{} and pageSize:{}",page,pageSize);
        return service.getAll(page, pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found person by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "person not found", content = {@Content(mediaType = "application/json")})})
    public Person getByIdPerson(@Parameter(description = "person id") @PathVariable final long id) {
        log.debug("get person with required id:{}",id);
        return service.getById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "update person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "person updated", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "Person with that personNo already exists error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Person updatePerson(@Parameter(description = "person id") @PathVariable final long id,
                               @Parameter(description = "person body") @Valid @RequestBody final Person person) {
        log.debug("update person with required id:{} and branch:{}",id,person);
        return service.update(id, person);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created person", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "Person with that personNo already exists error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")})})
    public Person createPerson(@Parameter(description = "person body ") @RequestBody @Valid final Person person) {
        log.debug("creat person with required branch:{}",person);
        return service.create(person);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "remove person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed person", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "person not found", content = {@Content(mediaType = "application/json")})})
    public Person removePerson(@Parameter(description = "person id") @PathVariable final long id) {
        log.debug("remove person with required id:{}",id);
        return service.remove(id);
    }
}
