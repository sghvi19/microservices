package com.example.repository;

import com.example.domain.InsuranceCar;
import com.example.domain.InsuranceOwner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceCarRepository extends PagingAndSortingRepository<InsuranceCar,Long>, CrudRepository<InsuranceCar,Long> {
    boolean existsInsuranceCarByLicensePlate(String licensePlate);
    InsuranceCar findInsuranceCarByLicensePlate(String licensePlate);
}
