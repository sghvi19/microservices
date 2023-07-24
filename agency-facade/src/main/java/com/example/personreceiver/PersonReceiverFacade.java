package com.example.personreceiver;

import com.example.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class PersonReceiverFacade {
    private final RestTemplate restTemplate;
    private final UriComponentsBuilder uriBuilder;

    @Autowired
    public PersonReceiverFacade(RestTemplate restTemplate, UriComponentsBuilder uriBuilder) {
        this.restTemplate = restTemplate;
        this.uriBuilder = uriBuilder;
    }

    public Person getPerson(String personalNo) throws URISyntaxException {
        URI uri = new URI(uriBuilder.cloneBuilder().path(String.format("public/%s", personalNo)).toUriString());
        try {
            ResponseEntity<Person> responseEntity =
                    restTemplate.getForEntity(uri, Person.class);
            return responseEntity.getBody();
        }catch (Exception ex){
            return null;
        }
    }
}
