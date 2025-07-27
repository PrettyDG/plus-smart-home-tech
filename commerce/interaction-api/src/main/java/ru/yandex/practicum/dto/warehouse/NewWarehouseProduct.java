package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewWarehouseProduct {
    @NotNull
    private UUID productId;

    @NotNull
    private boolean fragile;

    @NotNull
    private DimensionDto dimension;

    @Min(value = 1, message = "Вес меньше 1")
    private Double weight;
}
