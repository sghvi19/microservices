package com.example.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Persons")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_generator")
    @SequenceGenerator(name = "person_id_generator", sequenceName = "person_id_gen")
    @EqualsAndHashCode.Include
    private long personId;

    @NotBlank
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Past
    @NotNull
    @Column(name = "birth_date", nullable = false,updatable = false)
    private LocalDate birthDate;

    @Column(name = "personal_no", nullable = false, unique = true)
    @Pattern(regexp = "\\d{11}")
    private String personalNo;

}
