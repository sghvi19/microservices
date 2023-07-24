package com.example.repository;

import com.example.domain.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends CrudRepository<Owner,Long> {
    boolean existsOwnerByPersonalNo(String personalNo);

    Owner getOwnerByPersonalNo(String personalNo);
}
