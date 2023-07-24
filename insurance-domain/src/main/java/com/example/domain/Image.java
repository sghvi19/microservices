package com.example.domain;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "InsuranceCars")
public class Image {
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_id_generator")
    @SequenceGenerator(name = "image_id_generator", sequenceName = "image_id_gen")
    @EqualsAndHashCode.Include
    private long imageId;

    @NotNull
    @Column(name = "base64_image", nullable = false)
    private String base64Image;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "insurance_car_id", nullable = false)
    private InsuranceCar insuranceCar;

}
