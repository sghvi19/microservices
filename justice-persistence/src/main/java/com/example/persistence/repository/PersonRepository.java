package com.example.persistence.repository;

import dev.omedia.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person,Long>, CrudRepository<Person,Long> {
    boolean existsByPersonalNo(String personalNo);
    Person findPersonByPersonalNo(String personalNo);
}
