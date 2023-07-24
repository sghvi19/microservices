package com.example.domain;

import com.example.enums.CarType;
import com.example.enums.OdometerUnitType;
import jakarta.validation.constraints.Min;
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
@Table(name = "Cars")
public class Car {
    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_id_generator")
    @SequenceGenerator(name = "car_id_generator", sequenceName = "car_id_gen")
    @EqualsAndHashCode.Include
    private long carId;

    @Past
    @NotNull
    @Column(name = "release_date", nullable = false, updatable = false)
    private LocalDate releaseDate;

    @Pattern(regexp = "(?=.*\\d|=.*[A-Z])(?=.*[A-Z])[A-Z\\d]{17}")
    @Column(name = "vin_code", nullable = false,unique = true)
    private String vinCode;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "car_type", nullable = false)
    private CarType carType;

    @Min(0)
    @Column(name = "odometer", nullable = false)
    private int odometer;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "odometer_unit", nullable = false)
    private OdometerUnitType odometerUnitType;

    @Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}")
    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

}
