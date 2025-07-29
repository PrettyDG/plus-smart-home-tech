package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.service.DeliveryService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PutMapping
    public DeliveryDto createNewDelivery(@RequestBody DeliveryDto deliveryDto) {
        return deliveryService.createNewDelivery(deliveryDto);
    }

    @PostMapping("/successful")
    public DeliveryDto markDelivered(@RequestBody UUID orderId) {
        return deliveryService.markDelivered(orderId);
    }

    @PostMapping("/picked")
    public DeliveryDto markPicked(@RequestBody UUID orderId) {
        return deliveryService.markPicked(orderId);
    }

    @PostMapping("/failed")
    public DeliveryDto markFailed(@RequestBody UUID orderId) {
        return deliveryService.markFailed(orderId);
    }

    @PostMapping("/cost")
    public Double calculateDelivery(@RequestBody OrderDto orderDto) {
        return deliveryService.calculateDelivery(orderDto);
    }
}
