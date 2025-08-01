package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
public class OrderDto {
    @NotNull
    private UUID orderId;

    @NotBlank
    private String username;

    @NotNull
    private UUID shoppingCartId;

    private UUID paymentId;

    private UUID deliveryId;

    @NotNull
    private OrderState state;

    private Double deliveryWeight;

    private Double deliveryVolume;

    private Boolean fragile;

    private Double totalPrice;

    private Double deliveryPrice;

    private Double productPrice;

    private Map<UUID, Long> products;
}