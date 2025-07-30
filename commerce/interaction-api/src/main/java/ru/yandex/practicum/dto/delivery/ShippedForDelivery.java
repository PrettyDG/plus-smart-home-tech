package ru.yandex.practicum.dto.delivery;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippedForDelivery {
    @NotBlank
    private UUID orderId;

    @NotBlank
    private UUID deliveryId;
}