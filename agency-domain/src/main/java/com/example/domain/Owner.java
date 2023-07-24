package com.example.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Owners")
public class Owner {
    @Id
    @Column(name = "owner_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_id_generator")
    @SequenceGenerator(name = "owner_id_generator", sequenceName = "owner_id_gen")
    @EqualsAndHashCode.Include
    private long ownerId;

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

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private Set<Car> cars;


}
