package com.example.domain;


import lombok.*;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "InsuranceCars")
public class InsuranceCar {

    @Id
    @Column(name = "insurance_car_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_car_id_generator")
    @SequenceGenerator(name = "insurance_car_id_generator", sequenceName = "insurance_car_id_gen")
    @EqualsAndHashCode.Include
    private long insuranceCarId;

    @Past
    @NotNull
    @Column(name = "release_date", nullable = false, updatable = false)
    private LocalDate releaseDate;

    @Min(0)
    @Column(name = "odometer", nullable = false)
    private int odometer;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "odometer_unit", nullable = false)
    private OdometerUnitType odometerUnitType;

    @Pattern(regexp = "(?=.*\\d|=.*[A-Z])(?=.*[A-Z])[A-Z\\d]{17}")
    @Column(name = "vin_code", nullable = false)
    private String vinCode;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "car_type", nullable = false)
    private CarType carType;

    @Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}")
    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;


    @Embedded
    private Insurance insurance;

    @ManyToOne
    @JoinColumn(name = "insurance_owner_id", nullable = false)
    private InsuranceOwner owner;

}
