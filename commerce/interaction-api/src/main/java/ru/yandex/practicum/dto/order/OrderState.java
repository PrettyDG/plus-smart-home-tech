package ru.yandex.practicum.dto.order;

public enum OrderState {
    NEW,
    ON_PAYMENT,
    PAID,
    ON_DELIVERY,
    ASSEMBLED,
    DELIVERED,
    COMPLETED,
    DELIVERY_FAILED,
    ASSEMBLY_FAILED,
    PAYMENT_FAILED,
    PRODUCT_RETURNED,
    CANCELED
}