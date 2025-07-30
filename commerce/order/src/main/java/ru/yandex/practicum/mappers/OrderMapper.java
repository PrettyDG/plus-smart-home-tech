package ru.yandex.practicum.mappers;

import lombok.experimental.UtilityClass;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.models.Order;

import java.util.List;

@UtilityClass
public class OrderMapper {
    public OrderDto toDto (Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .username(order.getUsername())
                .shoppingCartId(order.getShoppingCartId())
                .paymentId(order.getPaymentId())
                .deliveryId(order.getDeliveryId())
                .state(order.getState())
                .deliveryWeight(order.getDeliveryWeight())
                .deliveryVolume(order.getDeliveryVolume())
                .fragile(order.getFragile())
                .totalPrice(order.getTotalPrice())
                .deliveryPrice(order.getDeliveryPrice())
                .productPrice(order.getProductPrice())
                .products(order.getProducts())
                .build();
    }

    public Order toEntity (OrderDto orderDto) {
        return Order.builder()
                .orderId(orderDto.getOrderId())
                .username(orderDto.getUsername())
                .shoppingCartId(orderDto.getShoppingCartId())
                .paymentId(orderDto.getPaymentId())
                .deliveryId(orderDto.getDeliveryId())
                .state(orderDto.getState())
                .deliveryWeight(orderDto.getDeliveryWeight())
                .deliveryVolume(orderDto.getDeliveryVolume())
                .fragile(orderDto.getFragile())
                .totalPrice(orderDto.getTotalPrice())
                .deliveryPrice(orderDto.getDeliveryPrice())
                .productPrice(orderDto.getProductPrice())
                .products(orderDto.getProducts())
                .build();
    }

    public List<OrderDto> toDtoList (List<Order> orders) {
        return orders.stream().map(OrderMapper::toDto).toList();
    }
}
