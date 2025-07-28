package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DimensionDto {
    @Min(value = 1, message = "Ширина меньше 1")
    @NotNull
    private Double width;

    @Min(value = 1, message = "Высота меньше 1")
    @NotNull
    private Double height;

    @Min(value = 1, message = "Глубина меньше 1")
    @NotNull
    private Double depth;
}
