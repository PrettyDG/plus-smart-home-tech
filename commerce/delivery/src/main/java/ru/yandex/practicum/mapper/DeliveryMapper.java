package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.models.Delivery;

public class DeliveryMapper {

    public static DeliveryDto toDto(Delivery delivery) {
        return DeliveryDto.builder()
                .deliveryId(delivery.getDeliveryId())
                .fromAddress(AddressMapper.toDto(delivery.getFromAddress()))
                .toAddress(AddressMapper.toDto(delivery.getToAddress()))
                .deliveryState(delivery.getDeliveryState())
                .orderId(delivery.getOrderId())
                .build();
    }

    public static Delivery toEntity(DeliveryDto dto) {
        return Delivery.builder()
                .deliveryId(dto.getDeliveryId())
                .fromAddress(AddressMapper.toEntity(dto.getFromAddress()))
                .toAddress(AddressMapper.toEntity(dto.getToAddress()))
                .deliveryState(dto.getDeliveryState())
                .orderId(dto.getOrderId())
                .build();
    }
}