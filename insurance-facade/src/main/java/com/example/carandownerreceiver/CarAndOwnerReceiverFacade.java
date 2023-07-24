package com.example.carandownerreceiver;

import dev.omedia.domain.Car;
import dev.omedia.domain.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class CarAndOwnerReceiverFacade {
    private final RestTemplate restTemplate;
    private final UriComponentsBuilder uriBuilder;

    @Autowired
    public CarAndOwnerReceiverFacade(RestTemplate restTemplate, UriComponentsBuilder uriBuilder) {
        this.restTemplate = restTemplate;
        this.uriBuilder = uriBuilder;
    }

    public Car getCar(final String personalNo, final String licensePlate) throws URISyntaxException {
        URI uri = new URI(uriBuilder.cloneBuilder().path(String.format("public/%s/%s", personalNo,licensePlate)).toUriString());
        try {
            ResponseEntity<Car> responseEntity =
                    restTemplate.getForEntity(uri, Car.class);
            return responseEntity.getBody();
        } catch (Exception ex) {
            return null;
        }
    }
}
