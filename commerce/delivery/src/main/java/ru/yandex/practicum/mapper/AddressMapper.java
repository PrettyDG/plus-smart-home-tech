package ru.yandex.practicum.mapper;


import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.models.Address;

import java.util.UUID;

public class AddressMapper {

    public static AddressDto toDto(Address address) {
        return AddressDto.builder()
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .house(address.getHouse())
                .flat(address.getFlat())
                .build();
    }

    public static Address toEntity(AddressDto dto) {
        return Address.builder()
                .addressId(UUID.randomUUID())
                .country(dto.getCountry())
                .city(dto.getCity())
                .street(dto.getStreet())
                .house(dto.getHouse())
                .flat(dto.getFlat())
                .build();
    }
}