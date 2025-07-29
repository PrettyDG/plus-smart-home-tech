package ru.yandex.practicum.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.delivery.DeliveryState;
import ru.yandex.practicum.dto.delivery.ShippedForDelivery;
import ru.yandex.practicum.dto.order.OrderClient;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.warehouse.WarehouseClient;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.models.Address;
import ru.yandex.practicum.models.Delivery;
import ru.yandex.practicum.storage.DeliveryRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository repository;
    private final OrderClient orderClient;
    private final WarehouseClient warehouseClient;

    private static final Double BASE_COST = 5.0;
    private static final Double WAREHOUSE_ADDRESS_2_SURCHARGE = 2.0;
    private static final Double FRAGILE_SURCHARGE = 0.2;
    private static final Double WEIGHT_SURCHARGE = 0.3;
    private static final Double VOLUME_SURCHARGE = 0.2;
    private static final Double ADDRESS_DELIVERY_SURCHARGE = 0.2;

    @Override
    public DeliveryDto createNewDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = DeliveryMapper.toEntity(deliveryDto);
        delivery.setDeliveryState(DeliveryState.CREATED);
        return DeliveryMapper.toDto(delivery);
    }

    @Override
    public DeliveryDto markDelivered(UUID orderId) {
        Delivery delivery = getDelivery(orderId);
        delivery.setDeliveryState(DeliveryState.DELIVERED);

        orderClient.markDelivered(orderId);

        return DeliveryMapper.toDto(repository.save(delivery));
    }

    @Override
    public DeliveryDto markPicked(UUID orderId) {
        Delivery delivery = getDelivery(orderId);
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);

        ShippedForDelivery deliveryRequest = ShippedForDelivery.builder()
                .orderId(delivery.getOrderId())
                .deliveryId(orderId)
                .build();

        orderClient.markDelivered(orderId);
        warehouseClient.shippedToDelivery(deliveryRequest);
        return DeliveryMapper.toDto(repository.save(delivery));
    }

    @Override
    public DeliveryDto markFailed(UUID orderId) {
        Delivery delivery = getDelivery(orderId);
        delivery.setDeliveryState(DeliveryState.FAILED);
        orderClient.markDeliveryFailed(orderId);
        return DeliveryMapper.toDto(repository.save(delivery));
    }

    @Override
    public Double calculateDelivery(OrderDto orderDto) {
        Delivery delivery = getDelivery(orderDto.getDeliveryId());
        Address fromAddress = delivery.getFromAddress();
        Address toAddress = delivery.getToAddress();
        Double cost = BASE_COST;
        String warehouseAddress = fromAddress.getCity();
        switch (fromAddress.getCity()) {
            case "ADDRESS_1":
                cost += BASE_COST;
                break;
            case "ADDRESS_2":
                cost += BASE_COST * WAREHOUSE_ADDRESS_2_SURCHARGE;
                break;
            default:
                break;
        }
        if (orderDto.getFragile()) {
            cost += cost * FRAGILE_SURCHARGE;
        }
        cost += orderDto.getDeliveryWeight() * WEIGHT_SURCHARGE;
        cost += orderDto.getDeliveryVolume() * VOLUME_SURCHARGE;

        String fullFromAddress = fromAddress.toString();
        if (!fullFromAddress.contains("ADDRESS_1") || !fullFromAddress.contains("ADDRESS_2")) {
            cost += cost * ADDRESS_DELIVERY_SURCHARGE;
        }

        return cost;
    }

    private Delivery getDelivery(UUID id) {
        Optional<Delivery> delivery = repository.findById(id);
        if (delivery.isEmpty()) {
            throw new NotFoundException("Нет доставки с id - " + id);
        }
        return delivery.get();
    }
}
