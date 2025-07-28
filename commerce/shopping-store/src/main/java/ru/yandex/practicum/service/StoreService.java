package ru.yandex.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.shoppingStore.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.ProductDto;
import ru.yandex.practicum.dto.shoppingStore.QuantityState;

import java.util.List;
import java.util.UUID;

public interface StoreService {
    List<ProductDto> getProductsByCategoryName(ProductCategory productCategory, Pageable pageable);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    boolean removeProduct(UUID productId);

    boolean changeQuantityState(UUID productID, QuantityState quantityState);

    ProductDto getProductById(UUID productId);
}
