package com.example.repository;

import com.example.domain.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends PagingAndSortingRepository<Car,Long>, CrudRepository<Car,Long> {
    boolean existsCarByLicensePlate(String licensePlate);
    Optional<Car> findCarByLicensePlate(String licensePlate);

    boolean existsCarByVinCode(String vinCode);

    Optional<Car> findCarByVinCode(String vinCode);
    Optional<Car> getCarByLicensePlateAndOwnerPersonalNo(String licensePlate,String personalNo);
}
