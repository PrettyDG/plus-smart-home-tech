package ru.yandex.practicum.mappers;

import lombok.experimental.UtilityClass;
import ru.yandex.practicum.dto.shoppingStore.ProductDto;
import ru.yandex.practicum.models.Product;

import java.util.List;

@UtilityClass
public class ProductListMapper {
    public static List<ProductDto> toProductDtoList(List<Product> products) {
        return products.stream().map(ProductMapper::toDto).toList();
    }
}
