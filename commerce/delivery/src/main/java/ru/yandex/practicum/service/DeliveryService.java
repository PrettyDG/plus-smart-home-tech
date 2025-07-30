package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;

import java.util.UUID;

public interface DeliveryService {
    DeliveryDto createNewDelivery(DeliveryDto deliveryDto);

    DeliveryDto markDelivered(UUID orderId);

    DeliveryDto markPicked(UUID orderId);

    DeliveryDto markFailed(UUID orderId);

    Double calculateDelivery(OrderDto orderDto);
}
