package com.example.controller;

import com.example.domain.Image;
import com.example.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("images")
@Slf4j
public class ImageController {
    private ImageService service;

    @Autowired
    public ImageController(ImageService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found images", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")})})
    public Iterable<Image> getAllImage(@Parameter(description = "page") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                                     @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.debug("get all images with page:{} and pageSize:{}", page, pageSize);
        return service.getAll(page, pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found image by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "image not found", content = {@Content(mediaType = "application/json")})})
    public Image getByIdImage(@Parameter(description = "image id") @PathVariable final long id) {
        log.debug("get image with required id:{}", id);
        return service.getById(id);
    }

    @GetMapping("car/{identification}")
    @Operation(summary = "Get image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found images by car id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "image not found", content = {@Content(mediaType = "application/json")})})
    public List<byte[]> getImagesByCarId(@Parameter(description = "car id") @PathVariable(name = "identification") final long id) {
        log.debug("get image with required id:{}", id);
        return service.getImages(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created image", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "406", description = "Not acceptable", content = {@Content(mediaType = "application/json")})})
    public Image createImage(@Parameter(description = "image in base64")@RequestBody Image image) {
        log.debug("creat image with required data");
        return service.create(image);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "remove image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed image", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "image not found", content = {@Content(mediaType = "application/json")})})
    public Image removeImage(@Parameter(description = "image id") @PathVariable final long id) {
        log.debug("remove image with required id:{}", id);
        return service.remove(id);
    }
}
