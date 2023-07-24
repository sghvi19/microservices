package com.example.repository;

import com.example.domain.InsuranceOwner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceOwnerRepository extends CrudRepository<InsuranceOwner,Long> {
    boolean existsInsuranceOwnerByPersonalNo(String personalNo);
    InsuranceOwner getInsuranceOwnerByPersonalNo(String personalNo);
}
