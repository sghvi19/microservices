package com.example.persistence.service;

import com.example.persistence.repository.PersonRepository;
import dev.omedia.domain.Person;
import dev.omedia.exception.PersonNotFoundException;
import dev.omedia.exception.PersonalNoAlreadyExistsException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonService {
    private final PersonRepository repo;

    @Autowired
    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }

    public Iterable<Person> getAll(final int page, final int pageSize) {
        log.info("getting all persons");
        Pageable pageable = PageRequest.of(page, pageSize);
        return repo.findAll(pageable).stream().collect(Collectors.toList());
    }

    public Person getById(final long id) {
        log.info("getting person by id");
        return repo.findById(id).orElseThrow(() -> new PersonNotFoundException(String.format("Person with id: %d not found!", id)));
    }

    public Person update(final long id, final Person person) {
        if (!repo.existsById(id)) {
            log.info("person does not exist");
            throw new PersonNotFoundException(String.format("Person with id: %d not found!", id));
        }

        if (repo.existsByPersonalNo(person.getPersonalNo()) && repo.findPersonByPersonalNo(person.getPersonalNo()).getPersonId() != id) {
            throw new PersonalNoAlreadyExistsException(String.format("Person with personalNo: %s already exists!", person.getPersonalNo()));
        }
        log.info("updating person");
        person.setPersonId(id);
        repo.save(person);
        return person;
    }

    public Person create(final Person person) {
        log.info("creating person");
        if (repo.existsByPersonalNo(person.getPersonalNo())) {
            log.info("person with personalNo:{} already exist",person.getPersonalNo());
            throw new PersonalNoAlreadyExistsException(String.format("Person with personalNo: %s already exists!", person.getPersonalNo()));
        }
        return repo.save(person);
    }

    public Person remove(final long id) {
        log.info("removing person");
        if (repo.existsById(id)) {
            Person person = repo.findById(id).get();
            repo.deleteById(id);
            return person;
        }
        throw new PersonNotFoundException(String.format("Person with id: %d not found!", id));
    }

    public Person findByPersonalNo(final String personalNo){
        log.info("finding person with personalNo");
        return repo.findPersonByPersonalNo(personalNo);
    }
}
