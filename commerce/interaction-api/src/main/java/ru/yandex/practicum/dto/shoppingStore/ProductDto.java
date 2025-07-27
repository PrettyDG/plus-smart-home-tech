package ru.yandex.practicum.dto.shoppingStore;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @NotBlank(message = "пустой id продукта")
    private UUID productId;
    @NotBlank(message = "пустой название продукта")
    private String productName;
    @NotBlank(message = "пустое описание продукта")
    private String description;
    private String imageSrc;
    @NotBlank(message = "пустой индикатор количества продукта")
    private QuantityState quantityState;
    @NotBlank(message = "пустое состояние открытости продукта")
    private ProductState productState;

    private ProductCategory productCategory;

    @NotBlank(message = "пустая цена продукта")
    @Min(value = 1, message = "цена меньше 1")
    private double price;
}
