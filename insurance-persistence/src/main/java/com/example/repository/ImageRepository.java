package com.example.repository;

import com.example.domain.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<Image,Long>, CrudRepository<Image,Long> {
    @Query("select im from Image im where im.insuranceCar.insuranceCarId = :id")
    List<Image> findImages(final long id);
}
