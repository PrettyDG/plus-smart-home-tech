package ru.yandex.practicum.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "addresses")
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id", updatable = false, nullable = false)
    private UUID addressId;

    private String country;

    private String city;

    private String street;

    private String house;

    private String flat;
}